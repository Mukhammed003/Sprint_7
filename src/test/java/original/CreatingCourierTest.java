package original;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import original.requestbodies.RequestBodyForCreatingCourier;
import original.requestbodies.RequestBodyForLoginCourier;
import original.stepsfortests.CreatingCourierSteps;
import static org.apache.http.HttpStatus.*;

import java.io.File;

public class CreatingCourierTest {

    CreatingCourierSteps creatingCourierSteps = new CreatingCourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASIC_URL;
    }

    @Test
    public void rightCreatingCourier() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");
        Response responseAfterCreatingCourier = creatingCourierSteps.createCourier(requestBodyForCreatingCourier);
        creatingCourierSteps.verifyStatus(responseAfterCreatingCourier, SC_CREATED);

        String actualJson = creatingCourierSteps.getFormattedResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER, actualJson);
    }

    @Test
    public void tryToCreateTwoIdenticalCouriers() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");

        creatingCourierSteps.createCourier(requestBodyForCreatingCourier);

        Response secondResponseAfterCreatingCourier = creatingCourierSteps.createCourier(requestBodyForCreatingCourier);
        creatingCourierSteps.verifyStatus(secondResponseAfterCreatingCourier, SC_CONFLICT);

        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(secondResponseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_IDENTICAL_COURIERS, actualJson);
    }

    @Test
    public void tryToCreateCourierWithoutPassword() {
        File json = new File("src/test/resources/creatingCourierWithoutPassword.json");

        Response responseAfterCreatingCourier = creatingCourierSteps.createCourierFromFile(json);
        creatingCourierSteps.verifyStatus(responseAfterCreatingCourier, SC_BAD_REQUEST);

        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER_WITH_BAD_REQUEST, actualJson);
    }

    @Test
    public void tryToCreateCourierWithoutLogin() {
        File json = new File("src/test/resources/creatingCourierWithoutLogin.json");

        Response responseAfterCreatingCourier = creatingCourierSteps.createCourierFromFile(json);
        creatingCourierSteps.verifyStatus(responseAfterCreatingCourier, SC_BAD_REQUEST);

        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER_WITH_BAD_REQUEST, actualJson);
    }

    @Test
    public void tryToCreateCourierWithoutFirstName() {
        File json = new File("src/test/resources/creatingCourierWithoutFirstName.json");

        Response responseAfterCreatingCourier = creatingCourierSteps.createCourierFromFile(json);

        creatingCourierSteps.verifyStatus(responseAfterCreatingCourier, SC_BAD_REQUEST);
        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER_WITH_BAD_REQUEST, actualJson);
    }

    @After
    public void setDown() {
        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);
        if(responseAfterLoginCourier.getStatusCode() == SC_OK) {
            String courierId = creatingCourierSteps.extractCourierId(responseAfterLoginCourier);
            creatingCourierSteps.deleteCourierById(courierId);
        }
        else {
            System.out.println("Wrong");
        }
    }
}
