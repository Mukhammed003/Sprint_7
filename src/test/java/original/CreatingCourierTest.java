package original;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import original.RequestBodies.RequestBodyForCreatingCourier;
import original.RequestBodies.RequestBodyForLoginCourier;
import original.StepsForTests.CreatingCourierSteps;

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
        creatingCourierSteps.verifyCourierCreationStatus(responseAfterCreatingCourier, 201);

        String actualJson = creatingCourierSteps.getFormattedResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER, actualJson);

        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);

        String courierId = creatingCourierSteps.extractCourierId(responseAfterLoginCourier);

        creatingCourierSteps.deleteCourierById(courierId);
    }

    @Test
    public void tryToCreateTwoIdenticalCouriers() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");

        creatingCourierSteps.createCourier(requestBodyForCreatingCourier);

        Response secondResponseAfterCreatingCourier = creatingCourierSteps.createCourier(requestBodyForCreatingCourier);
        creatingCourierSteps.verifyCourierCreationStatus(secondResponseAfterCreatingCourier, 409);

        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);
        String courierId = creatingCourierSteps.extractCourierId(responseAfterLoginCourier);
        creatingCourierSteps.deleteCourierById(courierId);

        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(secondResponseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_IDENTICAL_COURIERS, actualJson);
    }

    @Test
    public void tryToCreateCourierWithoutPassword() {
        File json = new File("src/test/resources/creatingCourierWithoutPassword.json");

        Response responseAfterCreatingCourier = creatingCourierSteps.createCourierFromFile(json);
        creatingCourierSteps.verifyCourierCreationStatus(responseAfterCreatingCourier, 400);

        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER_WITH_BAD_REQUEST, actualJson);
    }

    @Test
    public void tryToCreateCourierWithoutLogin() {
        File json = new File("src/test/resources/creatingCourierWithoutLogin.json");

        Response responseAfterCreatingCourier = creatingCourierSteps.createCourierFromFile(json);
        creatingCourierSteps.verifyCourierCreationStatus(responseAfterCreatingCourier, 400);

        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER_WITH_BAD_REQUEST, actualJson);
    }

    @Test
    public void tryToCreateCourierWithoutFirstName() {
        File json = new File("src/test/resources/creatingCourierWithoutFirstName.json");

        Response responseAfterCreatingCourier = creatingCourierSteps.createCourierFromFile(json);

        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);
        String courierId = creatingCourierSteps.extractCourierId(responseAfterLoginCourier);
        creatingCourierSteps.deleteCourierById(courierId);

        creatingCourierSteps.verifyCourierCreationStatus(responseAfterCreatingCourier, 400);
        String actualJson = creatingCourierSteps.getFormattedErrorResponseBody(responseAfterCreatingCourier);
        creatingCourierSteps.verifyResponseBody(Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER_WITH_BAD_REQUEST, actualJson);
    }
}
