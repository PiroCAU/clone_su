package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Category;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.dto.sell.*;
import clone.carrotMarket.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sells")
public class SaleController {

    private final SellService sellService;
    private final FileStorageService storageService;

//    //TODO: 페이징 관련 정책 수정, 지역별 검색 관련 수정
//    @GetMapping("/list")
//    public String getSellPosts(Model model, @LoginMember Member member) {
//
//        List<SellListResponseDTO> sales = sellService.getAllSellList(member);
//        model.addAttribute("saleList", sales);
//        return "sells/sellList";
//    }

    @ModelAttribute("category")
    public List<Category> categories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.Life);
        categories.add(Category.Digital);
        categories.add(Category.Interior);
        categories.add(Category.Food);
        categories.add(Category.Plant);
        categories.add(Category.Other);
        return categories;
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

    //판매글을 조회한다. 단, 자신이 작성한 글과 status가 FIN인 글 제외
    @GetMapping
    public String findSells(@LoginMember Member member,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        String place = member.getPlace();

        List<SellDTO> sellDTOs = sellService.findOtherUserSells(member.getId(), member, page, size);

        model.addAttribute("myPlace", place);
        model.addAttribute("sells", sellDTOs);

        return "sells/sellList";
    }

    //다른 사람이 작성한 글의 리스트를 반환한다.
    @GetMapping("other/{sellId}")
    public String findOtherSells(@PathVariable Long sellId,
                                 @RequestParam Long memberId,
                                 @RequestParam(required = false) SellStatus sellStatus,
                                 Model model) {
        List<SellDTO> sells = sellService.findOtherSells(sellId, memberId, sellStatus);
        model.addAttribute("sells", sells);

        return "sells/otherSells";
    }


    @GetMapping("/my/{sellId}")
    public String mySellDetail(@PathVariable Long sellId, Model model) {
        SellDetailDTO dto = sellService.findByIdDTO(sellId);
        model.addAttribute("sell", dto);
        return "sells/mySellDetail";
    }

    //sell 수정 폼
    @GetMapping("/edit/{sellId}")
    public String editSellForm(@PathVariable Long sellId, @LoginMember Member member, Model model) {
        UpdateSellDto dto = sellService.editSellRequest(sellId, member);
        model.addAttribute("sell", dto);
        return "sells/editForm";
    }

    @PutMapping("/edit/{sellId}")
    public String editSell(@ModelAttribute("sell") @Valid UpdateSellDto dto, BindingResult result,
                           @LoginMember Member member, Model model) {
        if (result.hasErrors()) {
            return "sells/editForm";
        }

        sellService.editSell(dto, member);

        return "redirect:/sells/my/" + dto.getSellId();
    }

    @PatchMapping("/{sellId}/updateStstus")
    public String updateSellStatus(@PathVariable Long sellId, @LoginMember Member member, @RequestParam SellStatus status, HttpServletRequest request) {
        sellService.updateSellStatus(sellId, member, status);

        //이 요청이 어디서 왔는지 확인하고 해당 위치로 보낸다.
        String referer = request.getHeader("referer");
        String[] split = referer.split("/");
        String str = split[split.length - 1];
        if (str.startsWith("my")) {
            return "redirect:/sells/my";
        }
        return "redirect:/";
    }

    @DeleteMapping("{sellId}")
    public String deleteSell(@PathVariable Long sellId, @LoginMember Member member) {
        sellService.deleteSell(sellId, member);
        return "redirect:/sells/my";
    }
}
