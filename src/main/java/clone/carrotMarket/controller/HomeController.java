package clone.carrotMarket.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/sells";  // 또는 redirect:/home, /main 등 원하는 경로
    }
}
