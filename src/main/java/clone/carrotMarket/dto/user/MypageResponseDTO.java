package clone.carrotMarket.dto.user;

import clone.carrotMarket.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MypageResponseDTO {

    private Long id;
    private String nickname;
    private String profileImage;

    public MypageResponseDTO(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickName();
        this.profileImage = member.getProfile_img();
    }
}
