package clone.carrotMarket.converter;

import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.ProductImage;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.dto.sell.CreateSellDTO;
import clone.carrotMarket.dto.sell.OtherSellSimpleDTO;
import clone.carrotMarket.dto.sell.ProductImageDTO;
import clone.carrotMarket.dto.sell.SellDetailResponseDto;
import clone.carrotMarket.service.FileStorageService;
import clone.carrotMarket.service.SellLikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class SellConverter {

    public static Sell createSellDtoToSell(CreateSellDTO dto, FileStorageService storageService, Member member) {

        List<MultipartFile> files = dto.getFiles();
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

    public static SellDetailResponseDto sellToSellDetailResponseDto(Sell sell, List<OtherSellSimpleDTO> othersells, SellLikeService sellLikeService) {
        return SellDetailResponseDto.builder()
                .sellId(sell.getId())
                .title(sell.getTitle())
                .category(sell.getCategory())
                .price(sell.getPrice())
                .content(sell.getContent())
                .place(sell.getPlace())
                .createdAt(sell.getCreated_at())
                .views(sell.getViews())
                .sellLikeCnt(sellLikeService.countSellLike(sell))
                .memberNickname(sell.getMember().getNickName())
                .memberPlace(sell.getMember().getPlace())
                .memberImage(sell.getMember().getProfile_img())
                .sellLikeBoolean(sellLikeService.isLiked(sell.getMember(), sell))
                .productImages(productImgToProductImgDTO(sell.getProductImage())
                .otherSells(othersells)
                .build();
    }

    public static List<ProductImageDTO> productImgToProductImgDTO(List<ProductImage> images) {
        ArrayList<ProductImageDTO> dtos = new ArrayList<>();

        for (ProductImage image : images) {
            ProductImageDTO productImageDTO = new ProductImageDTO(image.getImageUrl(), image.getImageRank());
            dtos.add(productImageDTO);
        }

        return dtos;
    }

    public static List<OtherSellSimpleDTO> sellToOtherSellSimpleDTO(Sell sell) {

    }
}
