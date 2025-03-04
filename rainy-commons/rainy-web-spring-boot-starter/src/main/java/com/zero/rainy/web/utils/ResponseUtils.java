package com.zero.rainy.web.utils;

import com.zero.rainy.core.model.ResponseCode;
import com.zero.rainy.core.model.Result;
import com.zero.rainy.core.utils.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 14:49 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {


    public static void response(HttpServletResponse response, ResponseCode responseCode) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(responseCode.getStatus().value());
        try(PrintWriter writer = response.getWriter()) {
            Result<Void> result = Result.of(responseCode);
            writer.write(JsonUtils.marshal(result));
            writer.flush();
        }catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
