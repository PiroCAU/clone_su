package clone.carrotMarket.restController;

import clone.carrotMarket.apiPayload.ApiResponse;
import clone.carrotMarket.config.exception.GeneralException;
import clone.carrotMarket.config.exception.handler.MemberHandler;
import clone.carrotMarket.config.exception.status.ErrorCode;
import clone.carrotMarket.config.security.JwtUtil;
import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.dto.user.CreateMemberDTO;
import clone.carrotMarket.dto.user.EditMemberDTO;
import clone.carrotMarket.dto.user.LoginDTO;
import clone.carrotMarket.dto.user.MypageResponseDTO;
import clone.carrotMarket.service.MemberService;
import clone.carrotMarket.service.TokenBlacklistService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberRestController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/signup")
    public ApiResponse<CreateMemberDTO> newMember(@Valid @RequestBody CreateMemberDTO createMemberDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GeneralException(ErrorCode._BAD_REQUEST);
        }

        memberService.signup(createMemberDTO);

        return ApiResponse.success(createMemberDTO);
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        tokenBlacklistService.blacklistToken(token, 3600000);
        return ApiResponse.success("logout success");
    }

    @PostMapping("/signin")
    public ApiResponse<String> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            throw new MemberHandler(ErrorCode.MEMBER_NOT_FOUND);
        }

        String token = memberService.login(loginDTO.getEmail(), loginDTO.getPassword());

        session.setAttribute("JWT", token);

        return ApiResponse.success(token);
    }

    @GetMapping("/members/mypage")
    public ApiResponse<MypageResponseDTO> myInfo(@Parameter(hidden = true) @LoginMember Member member) {
        return ApiResponse.success(new MypageResponseDTO(member));
    }

    @GetMapping("/members/edit")
    public ApiResponse<EditMemberDTO> getEditMypage(@Parameter(hidden = true) @LoginMember Member member) {
        return ApiResponse.success(new EditMemberDTO(member));
    }

    @PatchMapping("/members/edit")
    public ApiResponse<Member> editMember(@RequestBody EditMemberDTO dto) {
        Member member = memberService.editProfile(dto);
        return ApiResponse.success(member);
    }

    @PatchMapping("/members/deleteImage")
    public ApiResponse<String> deleteImage(@Parameter(hidden = true) @LoginMember Member member) {
        memberService.deleteProfileImage(member);
        return ApiResponse.success("프로필 이미지 삭제 성공");
    }

}
