package clone.carrotMarket.dto.sell;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class OtherSellSimpleDTO {
    private Long sellId;
    private Long memberId;
    private String title;
    private Integer price;
    private List<ProductImageDTO> productImages;
}
