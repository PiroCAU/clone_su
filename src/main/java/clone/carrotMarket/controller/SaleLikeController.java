package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.service.SellLikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/sells/like")
public class SaleLikeController {

    private final SellLikeService sellLikeService;

    @PostMapping("/{sellId}")
    public String likeSell(@PathVariable Long sellId,
                           @RequestParam Long sellerId, @LoginMember Member member) {
        sellLikeService.likeSell(sellId, sellerId, member);
        return "redirect:/sells/" + sellId + "?sellerId=" + sellerId;
    }

    @DeleteMapping("/{sellId}")
    public String deleteSellLike(@PathVariable Long sellId,
                                 @RequestParam Long sellerId, @LoginMember Member member) {
        sellLikeService.unlikeBySellId(member, sellId);
        return "redirect:/sells/" + sellId + "?sellerId=" + sellerId;
    }


}
