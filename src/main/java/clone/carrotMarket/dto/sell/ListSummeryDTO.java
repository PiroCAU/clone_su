package clone.carrotMarket.dto.sell;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ListSummeryDTO {

    private Long id;

    private String imageUrl;

    private String title;

    private String location;

    private int price;

    private int likeAmount;
}
