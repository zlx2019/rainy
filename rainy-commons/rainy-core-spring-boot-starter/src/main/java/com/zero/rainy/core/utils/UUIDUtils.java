package com.zero.rainy.core.utils;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * UUID utils
 *
 * @author Zero.
 * <p> Created on 2025/3/7 17:23 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtils {

    public static String fastUuid() {
        return UuidCreator.getRandomBasedFast().toString();
    }
}
