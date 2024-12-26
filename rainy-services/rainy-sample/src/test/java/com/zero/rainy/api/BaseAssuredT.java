package com.zero.rainy.api;

import com.zero.rainy.RainySampleApplication;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Assured Config
 *
 * @author Zero.
 * <p> Created on 2024/12/26 11:16 </p>
 */
@SpringBootTest(classes = RainySampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseAssuredT {
    @LocalServerPort
    public int serverPort;
    @PostConstruct
    public void initRestAssured(){
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
