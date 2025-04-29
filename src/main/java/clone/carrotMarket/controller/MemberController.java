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
@RequestMapping()
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    @GetMapping("/signup")
    public String newMemberForm(Model model) {
        model.addAttribute("createMemberDto", new CreateMemberDTO());
        return "members/createMemberForm";
    }

    @PostMapping("/signup")
    public String newMember(@Valid @ModelAttribute CreateMemberDTO createMemberDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        memberService.signup(createMemberDTO);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        tokenBlacklistService.blacklistToken(token, 3600000);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/signin")
    public String getLogin(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "members/loginForm";
    }

    @PostMapping("/signin")
    public String login(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "members/loginForm";
        }

        String token = memberService.login(loginDTO.getEmail(), loginDTO.getPassword());

        session.setAttribute("JWT", token);

        return "redirect:/";
    }

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
    public String editMemder(@ModelAttribute("member") EditMemberDTO dto) {
        memberService.editProfile(dto);
        return "redirect:/members/myPage";
    }

    @PatchMapping("/members/deleteImage")
    public String deleteImage(@LoginMember Member member) {
        memberService.deleteProfileImage(member);
        return "redirect:/members/myPage";
    }

}
