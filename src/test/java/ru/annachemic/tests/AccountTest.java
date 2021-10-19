package ru.annachemic.tests;

import org.junit.jupiter.api.Test;

import javax.xml.ws.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountTest extends BaseTest {

    @Test
    void getAccountInfoTest(){
        given()
                .header("Authorization",token)
                .when()
                .get("https:api.imqur.com/3/account/{username}",username)
                .then()
                .statusCode(200);
    }
    @Test
    void getAccountInfoWithLoggingTest(){
        given()
                .header("Autorization",token)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https:api.imqur.com/3/account/{username}",username)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void getAccountInfoWithAssertionsGivenTest(){
        given()
                .header("Autorization",token)
                .log()
                .method()
                .log()
                .uri()
                .expect()
                .statusCode(200)
                .body("data.url", equalTo(username))
                .body("success",equalTo(true))
                .body("status",equalTo(200))
                .contentType("aplication/json")
                .when()
                .get("https:api.imqur.com/3/account/{username}",username)
                .prettyPeek();
    }
    @Test
    void getAccountInfoWithAssertionsAfterTest(){
        Response response = (Response) given()
                .header("Autorization",token)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https:api.imqur.com/3/account/{username}",username)
                .prettyPeek();
//        assertThat(response.jsonPath().get("data.url"),equalTo(username));
     
    }
}
