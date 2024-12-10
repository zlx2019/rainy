package com.zero.rainy.core.model.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Zero.
 * <p> Created on 2024/9/26 15:50 </p>
 */
@Data
public class MessageBo implements Serializable {
    @Serial
    private static final long serialVersionUID = 4741163919117323156L;

    private String name;
    private Integer age;
    private LocalDateTime date;
}
