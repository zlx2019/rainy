package com.zero.rainy.gen.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 代码生成参数
 *
 * @author Zero.
 * <p> Created on 2025/1/2 12:45 </p>
 */
@Data
public class GenerateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String module;

    private String author = "Zero";
    private String packageName = "com.zero.rainy";
}
