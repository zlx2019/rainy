package com.zero.rainy.security.config;

import com.zero.rainy.security.filters.TokenValidateFilter;
import com.zero.rainy.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置
 *
 * @author Zero.
 * <p> Created on 2025/3/4 13:51 </p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    /** 鉴权失败处理 */
    private final AccessDeniedHandler accessDeniedHandler;
    /** 认证失败处理 */
    private final AuthenticationEntryPoint authenticationEntryPoint;
    /** 令牌校验过滤器 */
    private final TokenValidateFilter tokenValidateFilter;
    /** 认证配置 */
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        // CSRF 禁用, 因为不使用Session
        http.csrf(AbstractHttpConfigurer::disable)
                // 开启跨域
                .cors(Customizer.withDefaults())
                // 使用Token, 无需Session
                .sessionManagement(configurer-> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(configurer-> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 自定义处理器
                .exceptionHandling(exceptionHandlingConfigurer->
                        exceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint));
        // 请求权限配置
        http.authorizeHttpRequests(authorize ->{
            // 静态资源，可匿名访问
            authorize.requestMatchers(HttpMethod.GET, "/*.html", "/*.js", "/*.css").permitAll()
                    // 设置白名单，无需认证
                    .requestMatchers(securityProperties.getIgnoreUrls().toArray(new String[0])).permitAll();
            })
                // 其他所有请求必须认证
                .authorizeHttpRequests(authorize-> authorize.anyRequest().authenticated());
        http.addFilterBefore(tokenValidateFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager Bean
     *
     * @param authenticationConfiguration configuration bean
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
