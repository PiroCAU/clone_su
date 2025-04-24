package clone.carrotMarket.dto.sell;

import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Category;
import clone.carrotMarket.domain.ProductImage;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SellDetailDTO {

    private Long sellId;
    private String title;
    private String content;
    private List<ProductImageDTO> productImages;

    private int sellLikeCnt;
    private int price;

    private Category category;

    private String tradePlace;

    private SellStatus sellStatus;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long memberId;
    private String memberNickname;

    private String memberImage;

    private String memberPlace;

    private List<Long> roomIds;

    public SellDetailDTO(Sell sell){
        sellId = sell.getId();
        title = sell.getTitle();
        content = sell.getContent();
        productImages =  SellConverter.productImgToProductImgDTO(sell.getProductImage());
        sellLikeCnt = sell.getSellLikes().size();
        price = sell.getPrice();
        category = sell.getCategory();
        tradePlace = sell.getPlace();
        sellStatus = sell.getSellStatus();
        views = sell.getViews();
        createdAt = sell.getCreated_at();
        updatedAt = sell.getUplodated_at();
        memberId = sell.getMember().getId();
        memberNickname = sell.getMember().getNickName();
        memberImage = sell.getMember().getProfile_img();
        memberPlace = sell.getMember().getPlace();
        roomIds = sell.getChatRooms().stream()
                .map(chatRoom -> chatRoom.getId())
                .collect(Collectors.toList());
    }

}
