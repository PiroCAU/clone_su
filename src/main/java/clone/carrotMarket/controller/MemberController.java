package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.JwtUtil;
import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.dto.user.CreateMemberDTO;
import clone.carrotMarket.dto.user.EditMemberDTO;
import clone.carrotMarket.dto.user.LoginDTO;
import clone.carrotMarket.dto.user.MypageResponseDTO;
import clone.carrotMarket.service.MemberService;
import clone.carrotMarket.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @GetMapping("/signup")
    public String newMemberForm(Model model) {
        model.addAttribute("createMemberDto", new CreateMemberDTO());
        return "members/createMemberForm";
    }

    @PostMapping("/signup")
    public String newMember(@Valid @ModelAttribute CreateMemberDTO createMemberDTO, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> log.warn("Validation error: {}", error));
            return "redirect:/signup";
        }

        memberService.signup(createMemberDTO);

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        tokenBlacklistService.blacklistToken(token, 3600000);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/login")
    public String login(Model model) {
        log.info("access to login with getMapping");
        model.addAttribute("loginDTO", new LoginDTO());
        model.addAttribute("clientId", clientId);
        model.addAttribute("redirectUri", redirectUri);
        return "members/loginForm";
    }

//    /**
//     * 실제로 호출되지 않는다.
//     * spring security가 중간에 가로채서 처리하기 때문에
//     */
//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult result, HttpSession session) {
//        if (result.hasErrors()) {
//            log.info("Signin: result error");
//            return "members/loginForm";
//        }
//        log.info("login service: token");
//        String token = memberService.login(loginDTO.getEmail(), loginDTO.getPassword());
//
//        session.setAttribute("JWT", token);
//
//        return "redirect:/sells/my";
//    }

    @GetMapping("/members/mypage")
    public String myInfo(Model model, @LoginMember Member member) {
        model.addAttribute("member", new MypageResponseDTO(member));
        return "members/myPage";
    }

    @GetMapping("/members/edit")
    public String getEditMypage(Model model, @LoginMember Member member) {
        model.addAttribute("member", new EditMemberDTO(member));
        return "members/editProfile";
    }

    @PatchMapping("/members/edit")
    public String editMember(@ModelAttribute("member") EditMemberDTO dto) {
        memberService.editProfile(dto);
        return "redirect:/members/myPage";
    }

    @PatchMapping("/members/deleteImage")
    public String deleteImage(@LoginMember Member member) {
        memberService.deleteProfileImage(member);
        return "redirect:/members/myPage";
    }

}
