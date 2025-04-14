package clone.carrotMarket.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateMemberDTO {

    @NotBlank
    private String name;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String nickname;

    @NotBlank
    private String place;

    @NotNull
    private Integer latitude;

    @NotNull
    private Integer longitude;
}
