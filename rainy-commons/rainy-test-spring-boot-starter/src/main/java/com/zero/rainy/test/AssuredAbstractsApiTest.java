package com.zero.rainy.test;

import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Assured base Config
 *
 * @author Zero.
 * <p> Created on 2024/12/27 15:15 </p>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AssuredAbstractsApiTest {
    @LocalServerPort
    public int serverPort;
    @PostConstruct
    public void initAssured(){
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
