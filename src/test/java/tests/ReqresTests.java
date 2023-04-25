package tests;

import org.junit.jupiter.api.Test;
import tests.models.SingleUserResponse;
import tests.models.SupportResponseModel;
import tests.models.UserBodyResponse;
import tests.models.UserData;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ReqresTests {
    public static final String BASE_URL = "https://reqres.in/api";

    @Test
    void checkUserInListUsers() {
        SupportResponseModel response = given()
                .log().uri()
                .when()
                .get(BASE_URL + "/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(SupportResponseModel.class);
        assertThat(response.getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");

    }
    @Test
    void getSingleUser() {
       SingleUserResponse singleUser = given()
                .log().uri()
                .when()
                .get(BASE_URL + "/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(SingleUserResponse.class);
        assertThat(singleUser.getId()).isEqualTo("2");
        assertThat(singleUser.getEmail()).isEqualTo("ljanet.weaver@reqres.in");

    }

    @Test
    void createUser() {

        UserData data = new UserData();
        data.setName("morpheus");
        data.setJob("leader");

        UserBodyResponse user = given()
                .log().uri()
                .body(data)
                .contentType(JSON)
                .when()
                .post(BASE_URL + "/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(UserBodyResponse.class);
        assertThat(user.getName()).isEqualTo("morpheus");
        assertThat(user.getJob()).isEqualTo("leader");
    }

    @Test
    void updateUser() {
        UserData data = new UserData();
        data.setName("morpheus");
        data.setJob("zion resident");

        UserBodyResponse user = given()
                .log().uri()
                .body(data)
                .contentType(JSON)
                .when()
                .put(BASE_URL + "/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(UserBodyResponse.class);
        assertThat(user.getName()).isEqualTo("morpheus");
        assertThat(user.getJob()).isEqualTo("zion resident");
    }

    @Test
    void deleteUser() {
        given()
                .log().uri()
                .when()
                .delete(BASE_URL + "/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }
}
