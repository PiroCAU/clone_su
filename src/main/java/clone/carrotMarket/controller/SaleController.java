package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.dto.sell.CreateSellDTO;
import clone.carrotMarket.dto.sell.MySellResponseDTO;
import clone.carrotMarket.dto.sell.SellDetailResponseDto;
import clone.carrotMarket.dto.sell.SellListResponseDTO;
import clone.carrotMarket.service.*;
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

    //TODO: 페이징 관련 정책 수정, 지역별 검색 관련 수정
    @GetMapping("/list")
    public String getSellPosts(Model model) {

        List<SellListResponseDTO> sales = sellService.getAllSellList();
        model.addAttribute("saleList", sales);
        return "sells/sellList";
    }

    //게시글 작성 요청
    @GetMapping("/add")
    public String createSellPage(Model model) {
        model.addAttribute("sell", new CreateSellDTO());
        return "sells/addForm";
    }

    //게시글 작성 관련 처리
    @PostMapping("/add")
    public String createSellPost(@Valid @ModelAttribute CreateSellDTO dto, BindingResult result, @LoginMember Member member) {
        if (result.hasErrors()) {
            return "sells/addForm";
        }

        Sell sell = SellConverter.createSellDtoToSell(dto, storageService, member);

        Sell savedSell = sellService.save(sell);
        return "redirect:/sells/detail/" + savedSell.getId();
    }

    //게시글 detail 관련 처리
    @GetMapping("/{sellId}")
    public String detailSell(@PathVariable Long sellId, Model model) {
        Sell sell = sellService.findById(sellId);

        SellDetailResponseDto detail = sellService.findDetail(sell);
        model.addAttribute("sell", detail);
        return "sells/sellDetail";
    }

    //마이페이지: 내 판매글
    @GetMapping("/my")
    public String findMySell(@LoginMember Member member, @RequestParam(defaultValue = "SELLING") SellStatus sellStatus, Model model) {
        List<MySellResponseDTO> dtos = sellService.findMySell(member, sellStatus);
        model.addAttribute("sells", dtos);

        return "sells/mySells";
    }
}
