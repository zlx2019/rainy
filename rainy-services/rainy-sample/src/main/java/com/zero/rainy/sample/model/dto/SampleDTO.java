package com.zero.rainy.sample.model.dto;

import com.zero.rainy.core.model.dto.BaseDTO;
import com.zero.rainy.core.model.dto.group.Create;
import com.zero.rainy.core.model.entity.Sample;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;

/**
 * Create {@link Sample} DTO
 *
 * @author Zero.
 * <p> Created on 2024/12/31 12:39 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SampleDTO extends BaseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "must not be empty", groups = Create.class)
    private String name;

    @NotNull(message = "must not be null", groups = Create.class)
    @Range(message = "must be between 10 and 80", min = 10, max = 80, groups = Create.class)
    private int age;
}
