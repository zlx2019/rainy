package com.zero.rainy.sample.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zero.rainy.core.model.PageResult;
import com.zero.rainy.core.model.PageableQuery;
import com.zero.rainy.core.model.Result;
import com.zero.rainy.core.model.dto.group.Create;
import com.zero.rainy.core.model.dto.group.Update;
import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.core.utils.CloneUtils;
import com.zero.rainy.sample.model.converts.SampleConvert;
import com.zero.rainy.sample.model.dto.SampleDTO;
import com.zero.rainy.sample.model.vo.SampleVo;
import com.zero.rainy.sample.service.ISampleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Sample API 示例
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:34 </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {
    private final ISampleService sampleService;
    private final SampleConvert convert;

    /**
     * 查询所有记录
     */
    @GetMapping
    public Result<List<SampleVo>> list(){
        return Result.ok(sampleService.list(SampleVo.class, convert::toVo));
    }

    /**
     * 根据ID查询记录
     * @param id    ID主键
     */
    @GetMapping("/{id}")
    public Result<SampleVo> findById(@PathVariable Long id){
        return Result.ok(convert.toVo(sampleService.getById(id)));
    }

    /**
     * 分页查询记录
     * @param query 分页参数
     */
    @GetMapping("/page")
    public Result<PageResult<SampleVo>> list(@Valid PageableQuery query) {
        return Result.ok(sampleService.pages(query, SampleVo.class));
    }

    /**
     * 新增记录
     * @param dto   新增数据
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody @Validated(Create.class) SampleDTO dto){
        return Result.ok(sampleService.save(convert.toEntity(dto)));
    }

    /**
     * 根据ID修改记录
     * @param dto   修改数据
     */
    @PatchMapping
    public Result<SampleVo> updateById(@RequestBody @Validated(Update.class) SampleDTO dto){
        return Result.ok(sampleService.updateById(dto));
    }

    /**
     * 根据ID删除记录
     * @param id    ID主键
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteById(@PathVariable Long id){
        return Result.ok(sampleService.removeById(id));
    }
}
