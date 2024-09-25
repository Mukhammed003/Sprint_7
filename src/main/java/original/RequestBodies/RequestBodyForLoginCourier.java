package original.RequestBodies;

public class RequestBodyForLoginCourier {

    private String login;
    private String password;

    public RequestBodyForLoginCourier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public RequestBodyForLoginCourier() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
