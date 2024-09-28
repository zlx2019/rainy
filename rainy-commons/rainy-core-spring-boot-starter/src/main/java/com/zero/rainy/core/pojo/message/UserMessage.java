package com.zero.rainy.core.pojo.message;

import com.zero.rainy.core.pojo.message.delay.DelayMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Zero.
 * <p> Created on 2024/9/28 14:43 </p>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserMessage extends DelayMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
