package original.StepsForTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import original.Constants;
import original.RequestBodies.RequestBodyForCreatingCourier;
import original.RequestBodies.RequestBodyForLoginCourier;
import original.ResponseBodies.RightResponseBodyAfterLoginCourier;
import original.ResponseBodies.RightResponseBodyAfterLoginCourierWithBadRequest;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class LoginCourierSteps {
    @Step("Создание курьера")
    public void createCourier(RequestBodyForCreatingCourier requestBody) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(Constants.ENDPOINT_FOR_CREATING_COURIER)
                .then()
                .statusCode(201);
    }

    @Step("Логин курьера")
    public Response loginCourier(RequestBodyForLoginCourier requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(Constants.ENDPOINT_FOR_LOGIN_COURIER);
    }

    @Step("Удаление курьера")
    public void deleteCourier(String courierId) {
        String needUrl = Constants.ENDPOINT_FOR_DELETING_COURIER + courierId;
        given().when().delete(needUrl).then().statusCode(200);
    }

    @Step("Проверка статуса ответа")
    public void assertStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Step("Проверка JSON ответа после успешного логина")
    public void assertLoginResponseBody(Response response, String expectedJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RightResponseBodyAfterLoginCourier rightResponseBody = response.body().as(RightResponseBodyAfterLoginCourier.class);
        String actualJson = gson.toJson(rightResponseBody);
        assertEquals(expectedJson, actualJson);
    }

    @Step("Получение ID курьера")
    public String getCourierId(Response response) {
        return response.then().extract().body().path("id").toString();
    }

    @Step("Проверка JSON ответа после ошибки")
    public void assertErrorResponseBody(Response response, String expectedErrorJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RightResponseBodyAfterLoginCourierWithBadRequest errorResponse =
                response.body().as(RightResponseBodyAfterLoginCourierWithBadRequest.class);
        String actualJson = gson.toJson(errorResponse);
        assertEquals(expectedErrorJson, actualJson);
    }

    @Step("Логиним курьера с JSON запросом")
    public Response loginCourierWithJson(File json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(Constants.ENDPOINT_FOR_LOGIN_COURIER);
    }

    @Step("Создание ожидаемого Json ответа")
    public String creatingExpectedJson(String courierId) {
        return "{\n" + "  \"id\": " + courierId + "\n" + "}";
    }
}
