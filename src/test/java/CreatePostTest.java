import io.restassured.RestAssured;
import io.restassured.response.Response;
 
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
 
public class CreatePostTest {
 
    @Test
    public void createPostTest() {
 
        
        RestAssured.baseURI = "https://dev.emeli.in.ua/wp-json/wp/v2";
 
       
        String requestBody = """
                {
                    "title": "test",
                    "content": "test"
                }
                """;
 
        
        Response response = given()
                .auth().preemptive().basic("admin", "Engineer_123")
                .contentType(JSON)
                .body(requestBody)
        .when()
                .post("/posts")
        .then()
                .extract().response();

                
                
                assertEquals(201, response.getStatusCode(), "Status code should be 201");
 
        //проверка что эти поля действительно существуют

        Integer id = response.jsonPath().getInt("id");  //используем getInt в том случае когда ожидаем, что будет число
        String status = response.jsonPath().getString("status");  //используем getString в том случае когда ожидаем, что будет текст или символы
        String titleRaw = response.jsonPath().getString("title.raw");
        String contentRaw = response.jsonPath().getString("content.raw");
        String date = response.jsonPath().getString("date");
        Integer author = response.jsonPath().getInt("author");  
        String link = response.jsonPath().getString("link");
        String slug = response.jsonPath().getString("slug");
        String commentStatus = response.jsonPath().getString("comment_status");
        


        //проверка зеачения этих полей
        assertNotNull(id, "Post ID не должен быть null");
        assertEquals("draft", status, "Post status should be draft");
        assertEquals("test", titleRaw, "Post title should match");
        assertEquals("test", contentRaw, "Post content should match");

        assertNotNull(date, "Дата не должна быть null");
        assertFalse(date.isEmpty(), "Дата не должнв быть пустой"); //assertFalse обозначает что поле не должно быть пустым

        assertNotNull(author, "Author ID не должен быть null");

        assertNotNull(link, "Link не должен быть null");
        assertFalse(link.isEmpty(), "link не должен быть пустым");
        assertTrue(link.startsWith("https://"), "Link должен начинаться c https://");

        assertNotNull(slug, "Slug не должен быть null");
        assertFalse(slug.isEmpty(), "Slug не должен быть пустым");

        assertNotNull(commentStatus, "comment_status не должен быть null");
        assertFalse(commentStatus.isEmpty(), "comment_status не должен быть пустым");
        assertEquals("open", commentStatus, "comment_status должен быть open");


        //вывод зеачений. Чтобы можно было увидеть что реально пришло от API 
        System.out.println("ID:" + id);
        System.out.println("Status:" + status);
        System.out.println("Title:" + titleRaw);
        System.out.println("Content:" + contentRaw);
        System.out.println("Date:" + date);
        System.out.println("Author:" + author);
        System.out.println("Link:" + link);
        System.out.println("Slug:" + slug);
        System.out.println("Comment status: " + commentStatus);
       
               
 
       
        System.out.println(response.asString());

        
    }
}