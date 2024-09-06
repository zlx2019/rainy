package com.zero.rainy.grpc.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zero.
 * <p> Created on 2024/9/6 15:52 </p>
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
