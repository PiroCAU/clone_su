package clone.carrotMarket.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class JoinDTO {

    @Email @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phonenumber;

    @NotBlank
    private String nickname;

    @NotBlank
    private String location;
}
