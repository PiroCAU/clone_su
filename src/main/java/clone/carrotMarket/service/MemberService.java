package clone.carrotMarket.service;

import clone.carrotMarket.config.security.JwtUtil;
import clone.carrotMarket.config.security.PrincipalDetails;
import clone.carrotMarket.converter.MemberConverter;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.dto.user.CreateMemberDTO;
import clone.carrotMarket.dto.user.EditMemberDTO;
import clone.carrotMarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;

    public String login(String email, String password) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();
        return jwtUtil.createToken(principal.getUsername());
    }

    @Transactional
    public void signup(CreateMemberDTO dto) {

        //이메일 중복 확인
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }

        Member member = MemberConverter.createMemberToMember(dto, passwordEncoder, fileStorageService);

        memberRepository.save(member);
    }

    @Transactional
    public Member editProfile(EditMemberDTO dto) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow(() -> new IllegalStateException("해당 유저가 존재하지 않습니다."));

        member.changeNickName(dto.getNickname());

        MultipartFile file = dto.getImageFile();

        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeProfileImg(file);
            member.changeProfile_img(fileName);
        }

        Member newMember = memberRepository.save(member);
        return newMember;
    }

    @Transactional
    public void deleteProfileImage(Member member) {
        String profile_img = member.getProfile_img();

        if (profile_img != null && !profile_img.isEmpty()) {
            String basic_file = fileStorageService.deleteProfileImg(profile_img);
            member.changeProfile_img(basic_file);
        }

    }
}
