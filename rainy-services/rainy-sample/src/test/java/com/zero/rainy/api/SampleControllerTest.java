package com.zero.rainy.api;

import com.zero.rainy.sample.controller.SampleController;
import com.zero.rainy.test.AssuredAbstractsApiTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * {@link SampleController} Unit Test
 *
 * @author Zero.
 * <p> Created on 2024/12/31 14:27 </p>
 */
public class SampleControllerTest extends AssuredAbstractsApiTest {

    @Test
    public void listTest() {
        given().contentType(ContentType.JSON)
                .body(""" 
                      {"name": "赵六","age": 30}
                      """)
                .when()
                .post("/sample")
                .then()
                .log().all().statusCode(HttpStatus.OK.value());

        given()
                .when()
                .get("/sample")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("code", is(0))
                .body("data.size()", is(1))
                .body("data[0].name", equalTo("赵六"));
    }
}
