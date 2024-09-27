package original;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import original.stepsfortests.ListOfOrdersSteps;

public class ListOfOrdersTest {

    ListOfOrdersSteps listOfOrdersSteps = new ListOfOrdersSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASIC_URL;
    }

    @Test
    public void listOfOrdersIsNotNull() {
        Response responseAfterGettingListOfOrders = listOfOrdersSteps.getListOfOrders();

        listOfOrdersSteps.assertOrdersListIsNotNull(responseAfterGettingListOfOrders);
    }
}
