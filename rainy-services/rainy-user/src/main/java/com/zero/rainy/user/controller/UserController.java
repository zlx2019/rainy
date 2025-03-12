package com.zero.rainy.user.controller;

import com.zero.rainy.core.model.PageResult;
import com.zero.rainy.core.model.PageableParam;
import com.zero.rainy.core.model.Result;
import com.zero.rainy.core.model.dto.LoginRequestDTO;
import com.zero.rainy.core.model.dto.LoginResponseDTO;
import com.zero.rainy.web.model.group.Create;
import com.zero.rainy.web.model.group.Update;
import com.zero.rainy.user.model.converts.UserConvert;
import com.zero.rainy.user.model.dto.UserDTO;
import com.zero.rainy.user.model.vo.UserVo;
import com.zero.rainy.user.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * User Controller
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final UserConvert convert;

    /**
     * 查询所有记录
     */
    @GetMapping
    public Result<List<UserVo>> list(){
        return Result.ok(userService.list(UserVo.class, convert::toVo));
    }

    /**
     * 根据ID查询记录
     */
    @GetMapping("/{id}")
    public Result<UserVo> findById(@PathVariable("id") Long id){
        return Result.ok(convert.toVo(userService.getById(id)));
    }

    /**
     * 分页查询记录
     */
    @GetMapping("/page")
    public Result<PageResult<UserVo>> list(@Valid PageableParam query) {
        return Result.ok(userService.pages(query, UserVo.class, convert::toVo));
    }

    /**
     * 新增记录
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody @Validated(Create.class) UserDTO dto){
        return Result.ok(userService.save(convert.toEntity(dto)));
    }

    /**
     * 根据ID修改记录
     */
    @PatchMapping
    public Result<Boolean> updateById(@RequestBody @Validated(Update.class) UserDTO dto){
        return Result.ok(userService.updateById(dto));
    }

    /**
     * 根据ID删除记录
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteById(@PathVariable Long id){
        return Result.ok(userService.removeById(id));
    }


    /**
     * WebClient 异步接口 - 用户登录.
     *
     * @param dto   登录用户信息
     * @return      用户信息
     */
    @PostMapping("/login")
    public Mono<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
        log.info("user login: {}", dto);
        LoginResponseDTO responseDTO = new LoginResponseDTO()
                .setId(UUID.randomUUID().toString())
                .setAge(19)
                .setName("zero")
                .setEmail("zero@zero.com");
        return Mono.just(responseDTO);
    }
}
