package clone.carrotMarket.restController;


import clone.carrotMarket.apiPayload.ApiResponse;
import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Category;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.dto.sell.*;
import clone.carrotMarket.service.FileStorageService;
import clone.carrotMarket.service.SellService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sells")
public class SaleRestController {

    private final SellService sellService;
    private final FileStorageService storageService;

    @ModelAttribute("category")
    public List<Category> categories() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.Life);
        categories.add(Category.Digital);
        categories.add(Category.Interior);
        categories.add(Category.Food);
        categories.add(Category.Plant);
        categories.add(Category.Other);
        return categories;
    }

    @PostMapping("/add")
    public ApiResponse<Long> createSellPost(@Valid @RequestBody CreateSellDTO dto,
                                            @Parameter(hidden = true) @LoginMember Member member) {
        Sell sell = SellConverter.createSellDtoToSell(dto, storageService, member);
        Sell savedSell = sellService.save(sell);
        return ApiResponse.success(savedSell.getId());
    }

    @GetMapping("/{sellId}")
    public ApiResponse<SellDetailResponseDto> detailSell(@PathVariable Long sellId) {
        Sell sell = sellService.findById(sellId);
        SellDetailResponseDto detail = sellService.findDetail(sell);
        return ApiResponse.success(detail);
    }

    @GetMapping("/my")
    public ApiResponse<List<MySellResponseDTO>> findMySell(@Parameter(hidden = true) @LoginMember Member member,
                                                           @RequestParam(defaultValue = "SELLING") SellStatus sellStatus) {
        List<MySellResponseDTO> dtos = sellService.findMySell(member, sellStatus);
        return ApiResponse.success(dtos);
    }

    @GetMapping
    public ApiResponse<List<SellDTO>> findSells(@Parameter(hidden = true) @LoginMember Member member,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        List<SellDTO> sellDTOs = sellService.findOtherUserSells(member.getId(), member, page, size);
        return ApiResponse.success(sellDTOs);
    }

    @GetMapping("/other/{sellId}")
    public ApiResponse<List<SellDTO>> findOtherSells(@PathVariable Long sellId,
                                                     @RequestParam Long memberId,
                                                     @RequestParam(required = false) SellStatus sellStatus) {
        List<SellDTO> sells = sellService.findOtherSells(sellId, memberId, sellStatus);
        return ApiResponse.success(sells);
    }

    @GetMapping("/my/{sellId}")
    public ApiResponse<SellDetailDTO> mySellDetail(@PathVariable Long sellId) {
        SellDetailDTO dto = sellService.findByIdDTO(sellId);
        return ApiResponse.success(dto);
    }

    @GetMapping("/edit/{sellId}")
    public ApiResponse<UpdateSellDto> editSellForm(@PathVariable Long sellId,
                                                   @Parameter(hidden = true) @LoginMember Member member) {
        UpdateSellDto dto = sellService.editSellRequest(sellId, member);
        return ApiResponse.success(dto);
    }

    @PutMapping("/edit/{sellId}")
    public ApiResponse<Void> editSell(@Valid @RequestBody UpdateSellDto dto,
                                      @Parameter(hidden = true) @LoginMember Member member) {
        sellService.editSell(dto, member);
        return ApiResponse.success(null);
    }

    @PatchMapping("/{sellId}/updateStatus")
    public ApiResponse<Void> updateSellStatus(@PathVariable Long sellId,
                                              @Parameter(hidden = true) @LoginMember Member member,
                                              @RequestParam SellStatus status) {
        sellService.updateSellStatus(sellId, member, status);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{sellId}")
    public ApiResponse<Void> deleteSell(@PathVariable Long sellId,
                                        @Parameter(hidden = true) @LoginMember Member member) {
        sellService.deleteSell(sellId, member);
        return ApiResponse.success(null);
    }
}
