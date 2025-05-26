package clone.carrotMarket.dto.chat;

import clone.carrotMarket.domain.ChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "채팅 메시지 전송 DTO")
public class ChatMessageDTO {

    @Schema(description = "채팅방 ID", example = "1")
    private Long roomId;

    @Schema(description = "보낸 사람의 ID", example = "101")
    private Long senderId;

    @Schema(description = "보낸 사람의 닉네임", example = "user123")
    private String sender;

    @Schema(description = "메시지 내용", example = "안녕하세요!")
    private String message;

    public ChatMessageDTO(ChatMessage message) {
        this.roomId = message.getChatRoom().getId();
        this.senderId = message.getSender().getId();
        this.sender = message.getSender().getNickName();
        this.message = message.getMessage();
    }
}
