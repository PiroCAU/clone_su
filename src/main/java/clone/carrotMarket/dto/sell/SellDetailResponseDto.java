package clone.carrotMarket.dto.sell;

import clone.carrotMarket.domain.SellStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class SellDetailResponseDto {

    private Long sellId;

    private String title;
    private String category;
    private Integer price;
    private String content;
    private String place;
    private LocalDateTime createdAt;
    private int views;
    private int sellLikeCnt;

    private String memberNickname;
    private String memberPlace;
    private String memberImage;

    private boolean sellLikeBoolean; // 좋아요 여부

    private List<ProductImageDTO> productImages;

    private List<OtherSellSimpleDTO> otherSells;
}
