package original.requestbodies;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestBodyForCreatingCourier {

    private String login;
    private String password;
    private String firstName;
}
