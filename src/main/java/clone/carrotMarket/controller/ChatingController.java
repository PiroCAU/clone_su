package clone.carrotMarket.controller;

import clone.carrotMarket.dto.chat.ChatMessageDTO;
import clone.carrotMarket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatingController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message) {
        chatService.saveChat(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
