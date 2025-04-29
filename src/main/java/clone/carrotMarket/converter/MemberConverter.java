package clone.carrotMarket.converter;

import clone.carrotMarket.domain.Member;
import clone.carrotMarket.dto.user.CreateMemberDTO;
import clone.carrotMarket.service.FileStorageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

public class MemberConverter {

    public static Member createMemberToMember(CreateMemberDTO dto, PasswordEncoder passwordEncoder, FileStorageService storageService) {
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        //기본 프로필을 할당하고 만약 사용자가 입력한 프로필이 있다면 해당 프로필 사진을 저장한다.
        String profile = storageService.getBasicProfile();
        MultipartFile profileImg = dto.getProfileImg();
        if (profileImg != null && !profileImg.isEmpty()) {
            profile = storageService.storeProfileImg(profileImg);
        }

        return Member.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .place(dto.getPlace())
                .name(dto.getName())
                .nickName(dto.getNickname())
                .phone_number(dto.getPhoneNumber())
                .profile_img(profile)
                .build();
    }
}
