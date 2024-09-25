package original;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import original.RequestBodies.RequestBodyForCreatingOrder;
import original.StepsForTests.CreatingOrderSteps;

@RunWith(Parameterized.class)
public class CreatingOrderTest {

    CreatingOrderSteps creatingOrderSteps = new CreatingOrderSteps();

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASIC_URL;
    }

    public CreatingOrderTest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]  {
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2024-09-09", "Saske, come back to Konoha", new String[]{"GREY", "BLACK"}},
                {"Mukhammed", "Nurmukhanov", "Astana, 142 apt.", "3", "+7 999 322 53 53", 4, "2024-09-09", "No comments", new String[]{"GREY"}},
                {"Komado", "Tangiro", "Almaty, 130/4", "2", "+7 777 777 77 77", 3, "2024-09-09", "Yahoo", new String[]{"BLACK"}},
                {"Eren", "Yegger", "Aksay, Petrov", "1", "+7 666 666 66 66", 2, "2024-09-09", "Helo", null},
        };
    }

    @Test
    public void rightCreatingOrder() {
        RequestBodyForCreatingOrder requestBodyForCreatingOrder = new RequestBodyForCreatingOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        Response responseAfterCreatingOrder = creatingOrderSteps.createOrder(requestBodyForCreatingOrder);

        creatingOrderSteps.assertStatusCode(responseAfterCreatingOrder, 201);

        String trackId = creatingOrderSteps.getTrackId(responseAfterCreatingOrder);

        String expectedJson = creatingOrderSteps.generateExpectedJson(trackId);

        creatingOrderSteps.assertCreateOrderResponseBody(responseAfterCreatingOrder, expectedJson);
    }
}
