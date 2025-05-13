package clone.carrotMarket.converter;

import clone.carrotMarket.domain.ChatMessage;
import clone.carrotMarket.domain.ChatRoom;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.dto.chat.ChatMessageDTO;
import clone.carrotMarket.dto.chat.ChatRoomDTO;
import clone.carrotMarket.dto.sell.ProductImageDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ChatConverter {

    public static ChatRoomDTO chatRoomToDTO(ChatRoom room) {

        Sell sell = room.getSell();

        List<ProductImageDTO> productImageDTOS =
                SellConverter.productImgToProductImgDTO(sell.getProductImage());

        List<ChatMessageDTO> chatMessages =
                room.getChatMessage().stream()
                .map(ChatMessageDTO::new).collect(Collectors.toList());

        ChatRoomDTO build = ChatRoomDTO.builder()
                .id(room.getId())
                .sellerId(sell.getMember().getId())
                .senderId(room.getSender().getId())

                .sellerImage(sell.getMember().getProfile_img())
                .sellerNickname(sell.getMember().getNickName())
                .sellId(sell.getId())
                .sellTitle(sell.getTitle())
                .sellStatus(sell.getSellStatus())
                .price(sell.getPrice())
                .productImage(productImageDTOS.get(0))

                .senderImage(room.getSender().getProfile_img())
                .senderNickname(room.getSender().getNickName())
                .chatMessages(chatMessages)
                .build();

        return build;
    }

    public static ChatMessageDTO chatMessageToDTO(ChatMessage message) {
        return new ChatMessageDTO(message);
    }
}
