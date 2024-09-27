package original.requestbodies;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestBodyForLoginCourier {

    private String login;
    private String password;
}
