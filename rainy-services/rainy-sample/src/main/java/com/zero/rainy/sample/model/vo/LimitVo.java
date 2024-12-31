package com.zero.rainy.sample.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zero.
 * <p> Created on 2024/11/27 01:06 </p>
 */
@Data
public class LimitVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private String name;
    private int age;
}
