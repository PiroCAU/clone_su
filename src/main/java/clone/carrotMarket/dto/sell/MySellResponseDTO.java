package clone.carrotMarket.dto.sell;

import clone.carrotMarket.domain.SellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MySellResponseDTO {

    private Long sellId;

    private String title;

    private SellStatus sellStatus;

    private String memberPlace;

    private Integer price;

    private Long sellLikeCnt;

    private Long chatRoomCnt;

    private List<ProductImageDTO> productImages; // 이미지가 여러 개일 수 있으므로 리스트로 처리
}
