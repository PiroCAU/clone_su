package clone.carrotMarket.dto.sell;

import clone.carrotMarket.domain.SellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SellDetailResponseDto {

    private String uploader;

    private String hopingLocation;

    private SellStatus state;

    private String title;

    private String content;

    private int price;

    private boolean isLike;     //요청한 사람이 게시글에 조항요를 눌렀는지 여부
}
