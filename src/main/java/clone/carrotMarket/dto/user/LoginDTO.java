package clone.carrotMarket.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class LoginDTO {

    @Email
    @NotBlank
    private String userId;

    @NotBlank
    private String password;
}
