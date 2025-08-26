import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public class PostApi {

    private final String username = "admin";
    private final String password = "Engineer_123";

    private final AllureRestAssured allure = new AllureRestAssured();

    public Response createPost(String title, String content) {
        String body = String.format("""
                {
                  "title": "%s",
                  "content": "%s"
                }
                """, title, content);

        return given()
                .filter(allure)
                .auth().preemptive().basic(username, password)
                .contentType(JSON)
                .body(body)
        .when()
                .post("/posts")
        .then()
                .extract().response();
    }

    public Response getPost(int postId) {
        return given()
                .filter(allure)
                .auth().preemptive().basic(username, password)
        .when()
                .get("/posts/" + postId)
        .then()
                .extract().response();
    }

    public Response updatePost(int postId, String title, String content) {
        String body = String.format("""
                {
                  "title": "%s",
                  "content": "%s"
                }
                """, title, content);

        return given()
                .filter(allure)
                .auth().preemptive().basic(username, password)
                .contentType(JSON)
                .body(body)
        .when()
                .put("/posts/" + postId)
        .then()
                .extract().response();
    }

    public Response deletePost(int postId) {
        return given()
                .filter(allure)
                .auth().preemptive().basic(username, password)
                .queryParam("force", true)
        .when()
                .delete("/posts/" + postId)
        .then()
                .extract().response();
    }
}
