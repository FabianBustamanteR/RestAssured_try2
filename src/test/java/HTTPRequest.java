import com.github.fge.jsonschema.core.processing.ProcessorSelector;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class HTTPRequest {
    int id;
    @Test(priority = 1)
    void GetUsers() {
        given()
            .when()
                .get("https://reqres.in/api/users?page=2")
            .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }

    @Test(priority = 2)
    void GetUser() {
        given()
            .when()
                .get("https://reqres.in/api/users/2")
            .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 3)
    void createUser(){
        JSONObject data=new JSONObject();
        data.put("name","morpheus");
        data.put("job","leader");
        id=given()
            .contentType("application/json")
                .body(data)
            .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");
/*            .then()
                .statusCode(201)
                .log().all()*/
    }

    @Test(priority = 4)
    void updateUser(){
        JSONObject data=new JSONObject();
        data.put("name","Fabian");
        data.put("job","QA");
        given()
                .contentType("application/json")
                    .body(data)
                .when()
                    .post("https://reqres.in/api/users"+id)
                .then()
                    .statusCode(201)
                    .log().all();
    }

    @Test(priority = 5)
    void removeUser(){
        JSONObject data=new JSONObject();
        data.put("name","Fabian");
        data.put("job","QA");
        given()
                .contentType("application/json")
                .body(data)
                .when()
                .delete("https://reqres.in/api/users"+id)
                .then()
                .statusCode(204)
                .log().all();
    }

}
