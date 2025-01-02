package com.zero.rainy.gen.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.gen.model.dto.GenerateDTO;
import com.zero.rainy.gen.model.vo.TableVo;
import com.zero.rainy.gen.service.CodeGeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码生成
 *
 * @author Zero.
 * <p> Created on 2024/12/30 15:06 </p>
 */
@RestController
@RequestMapping("/generator")
@RequiredArgsConstructor
public class GeneratorController {
    private final CodeGeneratorService generatorService;

    /**
     * 查询所有数据表
     */
    @GetMapping("/tables")
    public Result<List<TableVo>> tables(){
        return Result.ok(generatorService.queryAll());
    }

    /**
     * 根据数据表生成代码.
     */
    @GetMapping
    public ResponseEntity<byte[]> generate(@Valid GenerateDTO dto){
        byte[] bytes = generatorService.generate(dto);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.zip")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(bytes.length))
                .body(bytes);
    }
}
