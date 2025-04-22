package clone.carrotMarket.controller;

import clone.carrotMarket.dto.sell.CreateSellDTO;
import clone.carrotMarket.dto.sell.SellListResponseDTO;
import clone.carrotMarket.service.;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/sells")
public class SaleController {

    private final SaleService saleService;

    @GetMapping("/list")
    public String getSalesPosts(Model model) {
        List<SellListResponseDTO> sales = saleService.getAllSellList();
        model.addAttribute("saleList", sales);
        return "";
    }

    @GetMapping("create")
    public String createSalePage(Model model) {
        model.addAttribute("createDTO", new CreateSellDTO());
        return "";
    }

    @PostMapping
    public String createSalePost(@ModelAttribute CreateSellDTO dto) {
        saleService.save(dto);
        return
    }

}
