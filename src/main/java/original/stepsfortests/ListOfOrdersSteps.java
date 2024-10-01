package original.stepsfortests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import original.Constants;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class ListOfOrdersSteps {
    @Step("Отправляем запрос на получение списка заказов")
    public Response getListOfOrders() {
        return given()
                .when()
                .get(Constants.ENDPOINT_FOR_GETTING_LIST_OF_ORDERS);
    }

    @Step("Проверяем, что список заказов не пуст")
    public void assertOrdersListIsNotNull(Response response) {
        response.then()
                .assertThat()
                .body("orders.size()", greaterThan(0));
    }
}
