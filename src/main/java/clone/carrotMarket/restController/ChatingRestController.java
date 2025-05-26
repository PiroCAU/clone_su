package clone.carrotMarket.restController;

import clone.carrotMarket.apiPayload.ApiResponse;
import clone.carrotMarket.dto.chat.ChatMessageDTO;
import clone.carrotMarket.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatingRestController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @PostMapping("/message")
    @Operation(summary = "채팅 메시지 전송", description = "채팅 메시지를 저장하고, 해당 채팅방 구독자에게 전송합니다.")
    public ApiResponse<ChatMessageDTO> sendMessage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "채팅 메시지 정보",
                    required = true
            )
            @RequestBody ChatMessageDTO message) {
        // 1. 채팅 저장
        chatService.saveChat(message);

        // 2. 채팅방 구독자에게 메시지 전송 (WebSocket 병행 운영)
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        // 3. 성공 응답 반환
        return ApiResponse.success(message);
    }
}
