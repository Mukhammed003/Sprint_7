package original.StepsForTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import original.Constants;
import original.RequestBodies.RequestBodyForCreatingCourier;
import original.RequestBodies.RequestBodyForLoginCourier;
import original.ResponseBodies.RightResponseBodyAfterCreatingCourier;
import original.ResponseBodies.RightResponseBodyAfterCreatingCourierWithBadRequest;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class CreatingCourierSteps {
    @Step("Создание курьера с данными: {requestBodyForCreatingCourier}")
    public Response createCourier(RequestBodyForCreatingCourier requestBodyForCreatingCourier) {
        Response response =
                given()
                .header("Content-type", "application/json")
                .body(requestBodyForCreatingCourier)
                .when()
                .post(Constants.ENDPOINT_FOR_CREATING_COURIER);
        return response;
    }

    @Step("Проверка, что статус ответа при создании курьера равен {expectedStatusCode}")
    public void verifyCourierCreationStatus(Response response, Integer expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Step("Форматирование тела ответа в форматированный JSON")
    public String getFormattedResponseBody(Response response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RightResponseBodyAfterCreatingCourier rightResponseBodyAfterCreatingCourier =
                response.body().as(RightResponseBodyAfterCreatingCourier.class);
        return gson.toJson(rightResponseBodyAfterCreatingCourier);
    }

    @Step("Логин курьера с данными: {requestBodyForLoginCourier}")
    public Response loginCourier(RequestBodyForLoginCourier requestBodyForLoginCourier) {
        return given()
                .header("Content-type", "application/json")
                .body(requestBodyForLoginCourier)
                .when()
                .post(Constants.ENDPOINT_FOR_LOGIN_COURIER);
    }

    @Step("Извлечение ID курьера из ответа")
    public String extractCourierId(Response response) {
        return response.then().extract().body().path("id").toString();
    }

    @Step("Удаление курьера по ID: {courierId}")
    public void deleteCourierById(String courierId) {
        String needUrl = Constants.ENDPOINT_FOR_DELETING_COURIER + courierId;
        given().when().delete(needUrl);
    }

    @Step("Отправка запроса с телом {jsonFile}")
    public Response createCourierFromFile(File jsonFile) {
        return given()
                .header("Content-type", "application/json")
                .body(jsonFile)
                .when()
                .post(Constants.ENDPOINT_FOR_CREATING_COURIER);
    }

    @Step("Проверка тела ответа после ошибки (Bad Request)")
    public String getFormattedErrorResponseBody(Response response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RightResponseBodyAfterCreatingCourierWithBadRequest errorResponse =
                response.body().as(RightResponseBodyAfterCreatingCourierWithBadRequest.class);
        return gson.toJson(errorResponse);
    }

    @Step("Проверка, что тело ответа совпадает с ожиданием")
    public void verifyResponseBody(String expectedJson, String actualJson) {
        assertEquals(expectedJson, actualJson);
    }
}
