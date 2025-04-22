package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.dto.sell.CreateSellDTO;
import clone.carrotMarket.dto.sell.SellListResponseDTO;
import clone.carrotMarket.service.;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sells")
public class SaleController {

    private final SellService sellService;
    private final FileStorageService storageService;

    @GetMapping("/list")
    public String getSellPosts(Model model) {
        /**
         * 수정 필요: 전체 리스트 반환? 페이징 필요
         */
        List<SellListResponseDTO> sales = sellService.getAllSellList();
        model.addAttribute("saleList", sales);
        return "sells/sellList";
    }

    @GetMapping("/add")
    public String createSellPage(Model model) {
        model.addAttribute("createDTO", new CreateSellDTO());
        return "sells/addForm";
    }

    @PostMapping("/add")
    public String createSellPost(@Valid @ModelAttribute CreateSellDTO dto, BindingResult result, @LoginMember Member member) {
        if (result.hasErrors()) {
            return "sells/addForm";
        }

        Sell sell = SellConverter.createSellDtoToSell(dto, storageService, member);

        Sell savedSell = sellService.save(sell);
        return "redirect:/sells/detail/" + savedSell.getId();
    }

    @GetMapping("/detail/{sellId}")
    public String detailSell(@PathVariable Long sellId) {
        Sell sell = sellService.findById(sellId);
    }
}
