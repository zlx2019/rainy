package com.zero.rainy.sample.model.vo;

import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.core.model.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * {@link Sample} Vo
 *
 * @author Zero.
 * <p> Created on 2024/12/31 12:38 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SampleVo extends BaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private String name;
    private int age;
}
