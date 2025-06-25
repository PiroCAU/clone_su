package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.domain.ChatRoom;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.dto.chat.ChatRoomDTO;
import clone.carrotMarket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;

    //나의 채팅방 조회
    @GetMapping("/rooms")
    public String myRooms(@LoginMember Member member, Model model) {
        List<ChatRoomDTO> list = chatService.findByMember(member);
        model.addAttribute("list", list);
        model.addAttribute("loginId", member.getId());
        return "chat/rooms";
    }

    //상품 관련 채팅방 목록 조회
    @GetMapping("/rooms/{sellId}")
    public String cheatingRoomBySell(@PathVariable Long sellId, @LoginMember Member member, Model model) {
        List<ChatRoomDTO> list = chatService.findBySell(sellId);
        model.addAttribute("list", list);
        model.addAttribute("loginId", member.getId());
        return "chat/rooms";
    }

    //채팅방 개설
    @PostMapping("rooms/{sellId}")
    public String createChatRoomBySell(@PathVariable Long sellId, @LoginMember Member member, Model model) {
        ChatRoom result = chatService.createChatRoom(sellId, member);

        return "redirect:/chat/room/" + result.getId();
    }

    //채팅방 참여
    @GetMapping("/room/{roomId}")
    public String interChatRoom(@PathVariable Long roomId, @LoginMember Member member, Model model) {
        ChatRoomDTO chatRoomDTO = chatService.accessById(roomId, member);
        chatService.getHistory(roomId);
        model.addAttribute("room", chatRoomDTO);
        model.addAttribute("loginMember", member);
        return "chat/room";
    }

}
