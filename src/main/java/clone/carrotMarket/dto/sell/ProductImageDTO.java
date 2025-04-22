package clone.carrotMarket.dto.sell;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProductImageDTO {
    private String imageUrl;
    private Integer rank;

    public ProductImageDTO(String imageUrl, Integer rank) {
        this.imageUrl = imageUrl;
        this.rank = rank;
    }
}
