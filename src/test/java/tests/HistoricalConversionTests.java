package tests;


import base.BaseTest;
import base.Consts;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HistoricalConversionTests extends BaseTest {


    @Test
    public void historicalValidDateTest() {
        Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .queryParam("date", "2018-01-01")
                .queryParam("currencies", "CAD,EUR,RUB")
                .get(Consts.HISTORICAL_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("source", equalTo("USD"))
                .body("quotes.USDCAD", notNullValue())
                .body("quotes.USDEUR", notNullValue())
                .body("quotes.USDRUB", notNullValue());
    }

    @Test
    public void historicalOneCurrencyTest() {
        Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .queryParam("date", "2018-01-01")
                .queryParam("currencies", "EUR")
                .get(Consts.HISTORICAL_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("quotes.USDEUR", notNullValue())
                .body("quotes.USDEUR", greaterThan(0f));
    }


    @Test
    public void historicalMissingDateTest() {
        Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .get(Consts.HISTORICAL_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("error.info", containsString("date"));


        System.out.println(response.asString());
    }


    @Test
    public void historicalInvalidDateTest() {
        Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .queryParam("date", "2025-99-99")
                .get(Consts.HISTORICAL_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("error.info", containsString("invalid date"));
    }


    @Test
    public void historicalInvalidCurrencyTest() {
        Response response = given()
                .queryParam("apikey", Consts.API_KEY)
                .queryParam("date", "2026-03-03")
                .queryParam("currencies", "ABC")
                .get(Consts.HISTORICAL_ENDPOINT);

        System.out.println(response.asString());

        response.then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("error.info", containsString("Currency"));
    }
}

