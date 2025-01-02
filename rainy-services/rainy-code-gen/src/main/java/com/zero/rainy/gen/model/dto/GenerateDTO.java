package com.zero.rainy.gen.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * 代码生成参数
 *
 * @author Zero.
 * <p> Created on 2025/1/2 12:45 </p>
 */
@Data
public class GenerateDTO {

    /**
     * 需要生成代码的数据表名
     */
    @NotEmpty(message = "At least one table name is required")
    private List<String> tables;

    /**
     * 模块名
     */
    @NotBlank(message = "must not be empty")
    private String moduleName;

    /**
     * 创建人
     */
    private String author = "Zero";

    /**
     * 包
     */
    @Pattern(regexp = "^[a-z][a-z0-9]*(\\.[a-z][a-z0-9]*)*$", message = "Package name must be legal")
    private String packageName = "com.zero.rainy";
}
