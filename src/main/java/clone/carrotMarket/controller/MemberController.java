package clone.carrotMarket.controller;

import clone.carrotMarket.dto.user.CreateMemberDTO;
import clone.carrotMarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public String saveMember(@Valid @ModelAttribute CreateMemberDTO createMemberDTO, BindingResult result) {

    }
}
