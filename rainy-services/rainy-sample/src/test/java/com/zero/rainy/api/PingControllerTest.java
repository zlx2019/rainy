package com.zero.rainy.api;

import com.zero.rainy.test.AssuredAbstractsApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Zero.
 * <p> Created on 2024/12/26 11:28 </p>
 */
public class PingControllerTest extends AssuredAbstractsApiTest {
    @Test
    public void test(){
        given()
                .when()
                .get("/ping")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body(is("ok"));
    }
}
