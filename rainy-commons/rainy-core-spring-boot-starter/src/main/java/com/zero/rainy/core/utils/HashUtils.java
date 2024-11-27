package com.zero.rainy.core.utils;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

/**
 * Hash 工具
 *
 * @author Zero.
 * <p> Created on 2024/11/27 15:17 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashUtils {
    private static final HashFunction HASH = Hashing.murmur3_128();

    public static String hash(String input){
        return HASH.hashString(input, StandardCharsets.UTF_8).toString();
    }
}
