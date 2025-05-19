package clone.carrotMarket.dto.sell;

import clone.carrotMarket.domain.Category;
import clone.carrotMarket.domain.ProductImage;
import clone.carrotMarket.domain.SellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder
public class SellDetailResponseDto {

    private Long sellId;

    private String title;
    private Category category;
    private Integer price;
    private String content;
    private String place;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private int views;
    private long sellLikeCnt;

    private String memberNickname;
    private String memberPlace;
    private String memberImage;
    private Long memberId;

    private boolean sellLikeBoolean; // 좋아요 여부

    private List<ProductImageDTO> productImages;

    private List<OtherSellSimpleDTO> otherSells;

}
