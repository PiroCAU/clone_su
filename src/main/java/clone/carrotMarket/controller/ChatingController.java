package clone.carrotMarket.controller;

import clone.carrotMarket.dto.chat.ChatMessageDTO;
import clone.carrotMarket.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatingController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message) {
        log.info("message: {}", message);
        chatService.saveChat(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        // 메세지를 특정 채팅방에 있는 사람에게 전송
    }
}
