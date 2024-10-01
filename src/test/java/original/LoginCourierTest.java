package original;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import original.requestbodies.RequestBodyForCreatingCourier;
import original.requestbodies.RequestBodyForLoginCourier;
import original.stepsfortests.CreatingCourierSteps;
import original.stepsfortests.LoginCourierSteps;
import static org.apache.http.HttpStatus.*;

import java.io.File;

public class LoginCourierTest {

    private Boolean isNeedToDeleteCourier;

    public Boolean getIsNeedToDeleteCourier() {
        return isNeedToDeleteCourier;
    }

    public void setIsNeedToDeleteCourier(Boolean needToDeleteCourier) {
        isNeedToDeleteCourier = needToDeleteCourier;
    }

    CreatingCourierSteps creatingCourierSteps = new CreatingCourierSteps();
    LoginCourierSteps loginCourierSteps = new LoginCourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASIC_URL;
        RequestBodyForCreatingCourier requestBodyForCreatingCourier = new RequestBodyForCreatingCourier("Mukhammed", "1234", "Sasuke");
        creatingCourierSteps.createCourier(requestBodyForCreatingCourier);
    }

    @Test
    public void rightLoginCourier() {
        setIsNeedToDeleteCourier(false);
        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1234");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);

        creatingCourierSteps.verifyStatus(responseAfterLoginCourier, SC_OK);

        String courierId = creatingCourierSteps.extractCourierId(responseAfterLoginCourier);

        String expectedJson = loginCourierSteps.creatingExpectedJson(courierId);
        loginCourierSteps.assertLoginResponseBody(responseAfterLoginCourier, expectedJson);

        creatingCourierSteps.deleteCourierById(courierId);
    }

    @Test
    public void tryToLoginCourierWithWrongLogin() {
        setIsNeedToDeleteCourier(true);
        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhamed", "1234");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);
        creatingCourierSteps.verifyStatus(responseAfterLoginCourier, SC_NOT_FOUND);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_WRONG_DATA);
    }

    @Test
    public void tryToLoginCourierWithWrongPassword() {
        setIsNeedToDeleteCourier(true);
        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("Mukhammed", "1334");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);
        creatingCourierSteps.verifyStatus(responseAfterLoginCourier, SC_NOT_FOUND);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_WRONG_DATA);
    }

    @Test
    public void tryToLoginCourierWithoutLogin() {
        setIsNeedToDeleteCourier(true);
        File json = new File("src/test/resources/loginCourierWithoutLogin.json");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourierWithJson(json);
        creatingCourierSteps.verifyStatus(responseAfterLoginCourier, SC_BAD_REQUEST);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_BAD_REQUEST);
    }

    @Test
    public void tryToLoginCourierWithoutPassword() {
        setIsNeedToDeleteCourier(true);
        File json = new File("src/test/resources/loginCourierWithoutPassword.json");
        Response responseAfterLoginCourier = loginCourierSteps.loginCourierWithJson(json);

        creatingCourierSteps.verifyStatus(responseAfterLoginCourier, SC_BAD_REQUEST);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_BAD_REQUEST);
    }

    @Test
    public void tryToLoginCourierLikeNonExistentCourier() {
        setIsNeedToDeleteCourier(true);
        RequestBodyForLoginCourier requestBodyForLoginCourier = new RequestBodyForLoginCourier("NonExistentUser", "1234");
        Response responseAfterLoginCourier = creatingCourierSteps.loginCourier(requestBodyForLoginCourier);

        creatingCourierSteps.verifyStatus(responseAfterLoginCourier, SC_NOT_FOUND);

        loginCourierSteps.assertErrorResponseBody(responseAfterLoginCourier, Constants.EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_WRONG_DATA);
    }

    @After
    public void setDown() {
        if (getIsNeedToDeleteCourier() == true) {
            RequestBodyForLoginCourier correctRequestBody = new RequestBodyForLoginCourier("Mukhammed", "1234");
            Response correctLoginResponse = creatingCourierSteps.loginCourier(correctRequestBody);
            String courierId = creatingCourierSteps.extractCourierId(correctLoginResponse);
            creatingCourierSteps.deleteCourierById(courierId);
        }
        else {
            System.out.println("Wrong");
        }
    }
}
