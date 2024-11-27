package com.zero.rainy.sample.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author Zero.
 * <p> Created on 2024/11/6 18:29 </p>
 */
@Data
@ToString
public class GMailRequestDTO {
    private String cookie;
    private String sender;
    private String email;
}
