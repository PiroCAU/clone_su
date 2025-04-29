package clone.carrotMarket.dto.chat;

import clone.carrotMarket.domain.ChatMessage;

public class ChatMessageDTO {

    private Long roomId;

    private Long senderId;

    private String sender;

    private String message;

    public ChatMessageDTO(ChatMessage message) {
        this.roomId = message.getChatRoom().getId();
        this.senderId = message.getSender().getId();
        this.sender = message.getSender().getNickName();
        this.message = message.getMessage();
    }
}
