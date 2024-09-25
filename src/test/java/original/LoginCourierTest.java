package original;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import original.RequestBodies.RequestBodyForCreatingCourier;
import original.RequestBodies.RequestBodyForLoginCourier;
import original.StepsForTests.LoginCourierSteps;

import java.io.File;

public class LoginCourierTest {

    LoginCourierSteps loginCourierSteps = new LoginCourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASIC_URL;
    }

    @Test
    public void rightLoginCourier() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");
        loginCourierSteps.createCourier(requestBodyForCreatingCourier);

        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourier(requestBodyForLoginCourier);

        loginCourierSteps.assertStatusCode(responseAfterLoginCourier, 200);

        String courierId = loginCourierSteps.getCourierId(responseAfterLoginCourier);

        String expectedJson = loginCourierSteps.creatingExpectedJson(courierId);
        loginCourierSteps.assertLoginResponseBody(responseAfterLoginCourier, expectedJson);

        loginCourierSteps.deleteCourier(courierId);
    }

    @Test
    public void tryToLoginCourierWithWrongLogin() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");
        loginCourierSteps.createCourier(requestBodyForCreatingCourier);

        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhamed", "1234");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourier(requestBodyForLoginCourier);
        loginCourierSteps.assertStatusCode(responseAfterLoginCourier, 404);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_WRONG_DATA);

        RequestBodyForLoginCourier correctRequestBody = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response correctLoginResponse = loginCourierSteps.loginCourier(correctRequestBody);
        String courierId = loginCourierSteps.getCourierId(correctLoginResponse);
        loginCourierSteps.deleteCourier(courierId);
    }

    @Test
    public void tryToLoginCourierWithWrongPassword() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");
        loginCourierSteps.createCourier(requestBodyForCreatingCourier);

        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1334");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourier(requestBodyForLoginCourier);
        loginCourierSteps.assertStatusCode(responseAfterLoginCourier, 404);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_WRONG_DATA);

        RequestBodyForLoginCourier correctRequestBody = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response correctLoginResponse = loginCourierSteps.loginCourier(correctRequestBody);
        String courierId = loginCourierSteps.getCourierId(correctLoginResponse);
        loginCourierSteps.deleteCourier(courierId);
    }

    @Test
    public void tryToLoginCourierWithoutLogin() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");
        loginCourierSteps.createCourier(requestBodyForCreatingCourier);

        File json = new File("src/test/resources/loginCourierWithoutLogin.json");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourierWithJson(json);
        loginCourierSteps.assertStatusCode(responseAfterLoginCourier, 400);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_BAD_REQUEST);

        RequestBodyForLoginCourier correctRequestBody = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response correctLoginResponse = loginCourierSteps.loginCourier(correctRequestBody);
        String courierId = loginCourierSteps.getCourierId(correctLoginResponse);
        loginCourierSteps.deleteCourier(courierId);
    }

    @Test
    public void tryToLoginCourierWithoutPassword() {
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");
        loginCourierSteps.createCourier(requestBodyForCreatingCourier);

        File json = new File("src/test/resources/loginCourierWithoutPassword.json");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourierWithJson(json);

        RequestBodyForLoginCourier correctRequestBody = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response correctLoginResponse = loginCourierSteps.loginCourier(correctRequestBody);
        String courierId = loginCourierSteps.getCourierId(correctLoginResponse);
        loginCourierSteps.deleteCourier(courierId);

        loginCourierSteps.assertStatusCode(responseAfterLoginCourier, 400);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_BAD_REQUEST);
    }

    @Test
    public void tryToLoginCourierLikeNonExistentCourier() {
        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("NonExistentUser", "1234");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourier(requestBodyForLoginCourier);

        loginCourierSteps.assertStatusCode(responseAfterLoginCourier, 404);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_WRONG_DATA);
    }
}
