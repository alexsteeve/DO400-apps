package com.redhat.training;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(SolverResource.class)
public class SolverResourceTest {

    @Test
    public void simpleTestAdditionOfTwoNumbersTest() {
        given()
                .when()
                .get("20+4")
                .then()
                .body(is("24.0"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void simpleTestMultiplicationOfTwoNumbersTest() {
        given()
                .when()
                .get("20*4")
                .then()
                .body(is("80.0"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void invalidMultiplicationOfTwoNumbersThrowsException() {
        given()
                .when()
                .get("20*a")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

}
