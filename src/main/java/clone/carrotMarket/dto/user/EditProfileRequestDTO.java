package clone.carrotMarket.dto.user;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditProfileRequestDTO {

    private String nickname;
    private String newProfilePhoto;
}
