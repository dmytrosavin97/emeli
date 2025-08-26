import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WordPressCRUDTest {

    private static Integer postId;
    private static PostApi postApi;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://dev.emeli.in.ua/wp-json/wp/v2";
        postApi = new PostApi();
    }

    @Test
    @Order(1)
    public void createPostTest() {
        Response response = postApi.createPost("POM Test Post", "Content for POM test");

        assertEquals(201, response.getStatusCode(), "Ожидался статус 201 при создании");

        postId = response.jsonPath().getInt("id");
        assertNotNull(postId, "id должен вернуться после создания поста");

       
        System.out.println("Created post ID: " + postId);
      
    }

    @Test
    @Order(2)
    public void readPostTest() {
        Response response = postApi.getPost(postId);

        assertEquals(200, response.getStatusCode(), "Ожидался статус 200 при чтении");
        assertEquals("POM Test Post",
                response.jsonPath().getString("title.rendered"),
                "title.rendered должен совпадать с исходным заголовком");
    }

    @Test
    @Order(3)
    public void updatePostTest() {
        Response response = postApi.updatePost(postId, "Updated POM Test Post", "Updated content");

        assertEquals(200, response.getStatusCode(), "Ожидался статус 200 при обновлении");
        assertEquals("Updated POM Test Post",
                response.jsonPath().getString("title.rendered"),
                "title.rendered должен обновиться");
    }

    @Test
    @Order(4)
    public void deletePostTest() {
        Response response = postApi.deletePost(postId);

        assertEquals(200, response.getStatusCode(), "Ожидался статус 200 при удалении");
        
        assertEquals(postId, response.jsonPath().getInt("previous.id"),
                "previous.id должен равняться удалённому postId");

        System.out.println("Deleted post ID: " + postId);
    }
}
