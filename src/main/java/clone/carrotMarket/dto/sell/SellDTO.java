package clone.carrotMarket.dto.sell;

import clone.carrotMarket.domain.SellStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SellDTO {

    private Long sellId;
    private String title;
    private List<ProductImageDTO> productImages;

    private long sellLikeCnt;
    private int price;
    private SellStatus sellStatus;

    private long chatRoomCnt;

    private Long memberId;
    private String memberPlace;
}
