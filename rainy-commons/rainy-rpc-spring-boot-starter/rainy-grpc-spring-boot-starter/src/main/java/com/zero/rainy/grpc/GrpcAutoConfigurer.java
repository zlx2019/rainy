package com.zero.rainy.grpc;

import com.zero.rainy.grpc.config.GrpcConfigure;
import org.springframework.context.annotation.Import;

/**
 * gRPC 自动装配器
 *
 * @author Zero.
 * <p> Created on 2024/9/5 15:56 </p>
 */
@Import(GrpcConfigure.class)
public class GrpcAutoConfigurer {
    
}
