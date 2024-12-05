package com.zero.rainy.sample.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮箱临时存储库
 *
 * @author Zero.
 * <p> Created on 2024/12/5 13:39 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MailStorage extends HashMap<String, MailMessage> implements Storage<MailMessage> {
    /**
     * 邮箱账号, 存储库的缓存Key
     */
    private String email;

    @Override
    public String getKey() {
        return this.email;
    }

    @Override
    public Map<String, MailMessage> getStore(){
        return this;
    }

}
