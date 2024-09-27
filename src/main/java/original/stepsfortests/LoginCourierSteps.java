package original.stepsfortests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import original.Constants;
import original.requestbodies.RequestBodyForCreatingCourier;
import original.requestbodies.RequestBodyForLoginCourier;
import original.responsebodies.RightResponseBodyAfterLoginCourier;
import original.responsebodies.RightResponseBodyAfterLoginCourierWithBadRequest;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class LoginCourierSteps {

    @Step("Проверка JSON ответа после успешного логина")
    public void assertLoginResponseBody(Response response, String expectedJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RightResponseBodyAfterLoginCourier rightResponseBody = response.body().as(RightResponseBodyAfterLoginCourier.class);
        String actualJson = gson.toJson(rightResponseBody);
        assertEquals(expectedJson, actualJson);
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
