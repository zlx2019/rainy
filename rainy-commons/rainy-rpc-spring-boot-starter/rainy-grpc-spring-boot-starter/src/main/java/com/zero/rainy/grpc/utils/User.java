package com.zero.rainy.grpc.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author Zero.
 * <p> Created on 2024/9/9 16:50 </p>
 */
@Data
public class User {
    private String username;
    private float score;
    private double price;
    private boolean locked;
    private long uid;
    private LocalDateTime sendTime;
}
