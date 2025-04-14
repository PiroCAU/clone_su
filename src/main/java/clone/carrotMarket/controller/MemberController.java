package clone.carrotMarket.controller;

import clone.carrotMarket.dto.user.CreateMemberDTO;
import clone.carrotMarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String newMemberForm(Model model) {
        model.addAttribute("createMemberDTO", new CreateMemberDTO());
        return "members/createMemberForm";
    }

    @GetMapping("/signup")
    public String saveMember(@Vaild @ModelAttribute CreateMemberDTO createMemberDTO, BindingResult result) {

    }
}
