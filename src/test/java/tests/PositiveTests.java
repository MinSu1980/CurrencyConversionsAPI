package tests;

import base.BaseTest;
import base.Consts;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PositiveTests extends BaseTest {
    @Test
    public void liveCurrencyTest() {
        final Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .queryParam("currencies", "EUR,USD")
                .queryParam("base", "USD")
                .get(Consts.LIVE_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("quotes.USDEUR", notNullValue())
                .body("quotes.USDEUR", greaterThan(0f));

    }


    @Test
    public void eurToUsdTest() {
        Response response = given()
                .get(Consts.LIVE_ENDPOINT + "?apikey=" + Consts.API_KEY + "&currencies=USD&base=EUR");

        System.out.println(response.asString());

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("quotes.EURUSD", notNullValue())
                .body("quotes.EURUSD", greaterThan(0f));
    }

    @Test
    public void getLiveRatesTest() {

        Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .queryParam("base", "USD")
                .queryParam("currencies", "CAD,EUR,RUB")
                .get(Consts.LIVE_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("source", equalTo("USD"))
                .body("quotes.USDCAD", notNullValue())
                .body("quotes.USDEUR", notNullValue())
                .body("quotes.USDRUB", notNullValue())
                .body("quotes.USDCAD", closeTo(1.368f, 0.0001f))
                .body("quotes.USDEUR", closeTo(0.8703f, 0.0001f))
                .body("quotes.USDRUB", closeTo(81.246508f, 0.0001f));


    }
}
