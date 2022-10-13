package com.redhat.training;

import com.redhat.training.service.SolverService;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(AdderResource.class)
public class AdderResourceTest {

    @InjectMock
    @RestClient 
    SolverService solverService;

    @Test
    public void simpleSumOfTwoNumbers() {
        when(solverService.solve("4")).thenReturn(4.0f);
        when(solverService.solve("20")).thenReturn(20.0f);
        given()
                .when()
                .get("4/20")
                .then()
                .body(is("24.0"))
                .statusCode(Response.Status.OK.getStatusCode());
    }
    @Test
    public void simpleSumOfNegativeAndPositiveNumbers() {
        when(solverService.solve("4")).thenReturn(4.0f);
        when(solverService.solve("-20")).thenReturn(-20.0f);
        given()
                .when()
                .get("-20/4")
                .then()
                .body(is("-16.0"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void sumOfInvalidValueThrowsError() {
        when(solverService.solve("a")).thenThrow(new WebApplicationException("Invalid Number", 400));
        when(solverService.solve("4")).thenReturn(4.0f);
        given()
                .when()
                .get("a/4")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

}
