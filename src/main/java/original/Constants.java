package original;

public class Constants {
    public final static String BASIC_URL = "https://qa-scooter.praktikum-services.ru";
    public final static String ENDPOINT_FOR_CREATING_COURIER = "/api/v1/courier";
    public final static String ENDPOINT_FOR_DELETING_COURIER = "/api/v1/courier/";
    public final static String ENDPOINT_FOR_LOGIN_COURIER = "/api/v1/courier/login";
    public final static String ENDPOINT_FOR_CREATING_ORDER = "/api/v1/orders";
    public final static String ENDPOINT_FOR_GETTING_LIST_OF_ORDERS = "/api/v1/orders";

    public final static String EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER = "{\n" +
            "  \"ok\": true\n" +
            "}";
    public final static String EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_IDENTICAL_COURIERS = "{\n" +
            "  \"message\": \"Этот логин уже используется\"\n" +
            "}";
    public final static String EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_CREATING_COURIER_WITH_BAD_REQUEST = "{\n" +
            "  \"message\": \"Недостаточно данных для создания учетной записи\"\n" +
            "}";

    public final static String EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_WRONG_DATA = "{\n" +
            "  \"message\": \"Учетная запись не найдена\"\n" +
            "}";
    public final static String EXAMPLE_OF_RIGHT_RESPONSE_BODY_AFTER_LOGIN_COURIER_WITH_BAD_REQUEST = "{\n" +
            "  \"message\": \"Недостаточно данных для входа\"\n" +
            "}";
}
