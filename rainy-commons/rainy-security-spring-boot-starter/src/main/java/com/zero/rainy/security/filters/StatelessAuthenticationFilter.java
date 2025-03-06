package com.zero.rainy.security.filters;

import lombok.extern.slf4j.Slf4j;

/**
 * 无会话授权校验过滤器 (令牌不存在状态，无法将其失效)
 *
 * Stateless
 *
 * @author Zero.
 * <p> Created on 2025/3/4 14:19 </p>
 */
@Slf4j
public class StatelessAuthenticationFilter extends AbstractAuthenticationFilter {

}
