package tests;

import base.BaseTest;
import base.Consts;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NegativeTests extends BaseTest {

    @Test
    public void invalidCurrencyTest() {
        Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .queryParam("currencies", "000")
                .queryParam("base", "USD")
                .get(Consts.LIVE_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(200)
                .body("success", equalTo(false));
    }
    @Test
    public void noaKeyTest() {
        Response response = given()
                .queryParam("currencies", "EUR")
                .queryParam("base", "USD")
                .get(Consts.LIVE_ENDPOINT);

        System.out.println(response.asString());


        response.then()
                .statusCode(401);
    }
    @Test
    void verifyWrongEndpoint() {
        given()
                .header("apikey", Consts.API_KEY)
                .when()
                .get("/currency_data/live123")
                .then()
                .statusCode(404);
    }

    @Test
    public void noApiKeyTest() {
        Response response = given()
                .queryParam("base", "USD")
                .queryParam("currencies", "CAD,EUR,NIS,RUB")
                .get(Consts.LIVE_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(401);
    }

    @Test
    public void invalidApiKeyTest() {
        Response response = given()
                .queryParam("apikey", "wrong_key")
                .queryParam("base", "USD")
                .queryParam("currencies", "CAD,EUR,NIS,RUB")
                .get(Consts.LIVE_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(401);
    }
}


