package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.LoginMember;
import clone.carrotMarket.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    //나의 채팅방 조회
    @GetMapping("/rooms")
    public String myRooms(@LoginMember Member member, Model model) {

    }


    //상품 관련 채팅방 목록 조회



    //채팅방 개설



    //채팅방 참여
}
