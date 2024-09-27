package original.stepsfortests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import original.Constants;
import original.requestbodies.RequestBodyForCreatingOrder;
import original.responsebodies.RightResponseBodyAfterCreatingOrder;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class CreatingOrderSteps {
    @Step("Создаем заказ")
    public Response createOrder(RequestBodyForCreatingOrder requestBodyForCreatingOrder) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyForCreatingOrder)
                .when()
                .post(Constants.ENDPOINT_FOR_CREATING_ORDER);
    }

    @Step("Получаем трек ID заказа")
    public String getTrackId(Response response) {
        return response.then().extract().body().path("track").toString();
    }

    @Step("Проверяем тело ответа после создания заказа")
    public void assertCreateOrderResponseBody(Response response, String expectedJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RightResponseBodyAfterCreatingOrder rightResponseBodyAfterCreatingOrder =
                response.body().as(RightResponseBodyAfterCreatingOrder.class);
        String actualJson = gson.toJson(rightResponseBodyAfterCreatingOrder);
        assertEquals(expectedJson, actualJson);
    }

    @Step("Формируем ожидаемый JSON с track ID")
    public String generateExpectedJson(String trackId) {
        return "{\n" +
                "  \"track\": " + trackId + "\n" +
                "}";
    }
}
