package clone.carrotMarket.dto.user;


import clone.carrotMarket.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class EditMemberDTO {

    private Long id;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    private MultipartFile imageFile; // 파일 업로드용

    public EditMemberDTO(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickName();
    }
}
