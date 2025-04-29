package clone.carrotMarket.dto.chat;

import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.dto.sell.ProductImageDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class ChatRoomDTO {

    private Long id;
    private Long sellerId;
    private Long senderId;

    private String sellerImage;
    private String sellerNickname;

    private Long sellId;
    private String sellTitle;
    private SellStatus sellStatus;
    private int price;
    private ProductImageDTO productImage;

    private String senderImage;
    private String senderNickname;

    private List<ChatMessageDTO> chatMessages;


}
