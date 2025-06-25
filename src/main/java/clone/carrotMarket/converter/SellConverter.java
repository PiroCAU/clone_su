package clone.carrotMarket.converter;

import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.ProductImage;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.dto.sell.*;
import clone.carrotMarket.repository.ProductImgRepository;
import clone.carrotMarket.service.ChatService;
import clone.carrotMarket.service.FileStorageService;
import clone.carrotMarket.service.SellLikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
public class SellConverter {

    public static Sell createSellDtoToSell(CreateSellDTO dto, FileStorageService storageService, Member member, ProductImgRepository productImgRepository) {

        List<MultipartFile> files = dto.getImageFiles();
        List<ProductImage> productImages = new ArrayList<>();

        //입력된 멀티파트 이미지들을 fileservice를 통해 저장
        int i = 0;
        for (MultipartFile file : files) {
            String str = storageService.storeSellImg(file);
            ProductImage builder = ProductImage.builder()
                    .imageRank(i++)
                    .imageUrl(str)
                    .build();
            productImages.add(builder);
            productImgRepository.save(builder);
        }

        return Sell.builder()
                .title(dto.getTitle())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .content(dto.getContent())
                .place(dto.getPlace())
                .files(productImages)
                .member(member)
                .build();
    }

    public static SellDetailResponseDto sellToSellDetailResponseDto(Sell sell, List<Sell> othersells, SellLikeService sellLikeService) {
        return SellDetailResponseDto.builder()
                .sellId(sell.getId())
                .title(sell.getTitle())
                .category(sell.getCategory())
                .price(sell.getPrice())
                .content(sell.getContent())
                .place(sell.getPlace())
                .createdAt(sell.getCreatedAt())
                .createdAt(sell.getUpdatedAt())
                .views(sell.getViews())
                .sellLikeCnt(sellLikeService.countSellLike(sell))
                .memberNickname(sell.getMember().getNickName())
                .memberPlace(sell.getMember().getPlace())
                .memberImage(sell.getMember().getProfile_img())
                .memberId(sell.getMember().getId())
                .sellLikeBoolean(sellLikeService.isLiked(sell.getMember(), sell))
                .productImages(productImgToProductImgDTO(sell.getProductImage()))
                .otherSells(sellToOtherSellSimpleDTO(othersells))
                .build();
    }

    public static List<ProductImageDTO> productImgToProductImgDTO(List<ProductImage> images) {
        ArrayList<ProductImageDTO> dtos = new ArrayList<>();
        log.info("Sell's img cnt: {}", images.size());

        for (ProductImage image : images) {
            log.info("image url: {}", image.getImageUrl());
            ProductImageDTO productImageDTO = new ProductImageDTO(image.getImageUrl(), image.getImageRank());
            dtos.add(productImageDTO);
        }

        return dtos;
    }

    public static List<OtherSellSimpleDTO> sellToOtherSellSimpleDTO(List<Sell> sells) {
        List<OtherSellSimpleDTO> result = new ArrayList<>();

        for (Sell sell : sells) {
            OtherSellSimpleDTO build = OtherSellSimpleDTO.builder()
                    .memberId(sell.getMember().getId())
                    .sellId(sell.getId())
                    .title(sell.getTitle())
                    .price(sell.getPrice())
                    .productImages(productImgToProductImgDTO(sell.getProductImage()))
                    .build();
            result.add(build);
        }

        return result;
    }

    public static List<MySellResponseDTO> sellToMySellResponseDTO(List<Sell> sells, Member member, ChatService chatService, SellLikeService sellLikeService) {
        List<MySellResponseDTO> result = new ArrayList<>();
        for (Sell sell : sells) {
            MySellResponseDTO build = MySellResponseDTO.builder()
                    .sellId(sell.getId())
                    .title(sell.getTitle())
                    .sellStatus(sell.getSellStatus())
                    .memberPlace(member.getPlace())
                    .price(sell.getPrice())
                    .sellLikeCnt(sellLikeService.countSellLike(sell))
                    .chatRoomCnt(chatService.countByMemberAndSell(member, sell))
                    .productImages(productImgToProductImgDTO(sell.getProductImage()))
                    .build();
            result.add(build);
        }

        return result;
    }

    public static List<SellDTO> sellToSellDTO(List<Sell> sells, Member member, SellLikeService sellLikeService, ChatService chatService) {
        List<SellDTO> result = new ArrayList<>();
        for (Sell sell : sells) {
            SellDTO build = SellDTO.builder()
                    .sellId(sell.getId())
                    .title(sell.getTitle())
                    .sellStatus(sell.getSellStatus())
                    .memberPlace(member.getPlace())
                    .price(sell.getPrice())
                    .sellLikeCnt(sellLikeService.countSellLike(sell))
                    .chatRoomCnt(chatService.countByMemberAndSell(member, sell))
                    .productImages(productImgToProductImgDTO(sell.getProductImage()))
                    .memberId(member.getId())
                    .build();
            result.add(build);
        }

        return result;
    }

    public static UpdateSellDto sellToUpdateSellDTO(Sell sell) {
        UpdateSellDto build = UpdateSellDto.builder()
                .sellId(sell.getId())
                .title(sell.getTitle())
                .content(sell.getContent())
                .price(sell.getPrice())
                .category(sell.getCategory())
                .place(sell.getPlace())
                .productImages(productImgToProductImgDTO(sell.getProductImage()))
                .build();
        return build;
    }
}
