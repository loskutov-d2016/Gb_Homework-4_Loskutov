package ru.annachemic.tests;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.BeforeAll;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import static org.hamcrest.Matchers.notNullValue;
import static sun.nio.cs.Surrogate.is;

public class ImageTest extends BaseTest {
    private final String PATH_TO_IMAGE = "srx/test/resources/luca_02.jpeg";
    static String encodedFile;
    String uploadedImageId;

    @BeforeAll
    void beforeTest() {
        byte[] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);

    }


    @Test
    void UploadedImageId(){
        uploadedImageId = given()
        .headers("Autorization", token)
                .multiPart("image", encodedFile)
                .formParam("title", "ImageTitle")
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue())
                .when()
                .post("https://api.imqur.com/3/image")
                .prettyPeek()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

     @Test
    void uploadFileImageTest(){
        uploadedImageId = given()
        .headers("Autorization",token)
                .multiPart("image", new File(PATH_TO_IMAGE))
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imqur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }
    @AfterEach
    void tearDown(){
        given()
                .headers("Autorization",token)
                .when()
                .delete("https://api.imqur.com/3/account/{username}/image/{deleteHash}",uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    private byte[] getFileContent() {
        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }


}
