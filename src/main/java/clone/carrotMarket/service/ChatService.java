package clone.carrotMarket.service;

import clone.carrotMarket.converter.ChatConverter;
import clone.carrotMarket.domain.ChatRoom;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.dto.chat.ChatMessageDTO;
import clone.carrotMarket.dto.chat.ChatRoomDTO;
import clone.carrotMarket.repository.ChatRepository;
import clone.carrotMarket.repository.MemberRepository;
import clone.carrotMarket.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final SellRepository sellRepository;
    private final MemberRepository memberRepository;

    public ChatRoom findByMemberAndSell(Member member, Sell sell) {
        ChatRoom chat = chatRepository.findFirstByMemberAndSell(member, sell)
                .orElse(null);
        return chat;
    }

    public Long countByMemberAndSell(Member member, Sell sell) {
        return chatRepository.countByMemberAndSell(member, sell);
    }

    //맴버에 따른 치팅 리스트 반환
    public List<ChatRoomDTO> findByMember(Member member) {
        List<ChatRoom> rooms = chatRepository.findAllChatRoomsByMember(member.getId());
        List<ChatRoomDTO> list = rooms.stream().map(ChatConverter::chatRoomToDTO).collect(Collectors.toList());
        return list;
    }

    public List<ChatRoomDTO> findBySell(Long sellId) {
        List<ChatRoom> rooms = chatRepository.findAllBySellId(sellId);
        List<ChatRoomDTO> list = rooms.stream().map(ChatConverter::chatRoomToDTO).collect(Collectors.toList());
        return list;
    }

    @Transactional
    public ChatRoom createChatRoom(Long sellId, Member member) {
        Sell sell = sellRepository.findById(sellId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        Optional<ChatRoom> existedRoom = chatRepository.findBySellAndSender(sell, member);

        if (existedRoom.isPresent()) {
            return existedRoom.get();
        }
        ChatRoom chatRoom = new ChatRoom(sell, member);
        return chatRoom;
    }

    public ChatRoomDTO accessById(Long roomId, Member member) {
        ChatRoom room = chatRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방이니다."));

        Member sender = room.getSender();
        Member writer = room.getSell().getMember();

        //해당 채팅방에 들어갈 수 있는 인물인지 확인한다.
        if (!member.getId().equals(sender.getId()) && !member.getId().equals(writer.getId())) {
            throw new RuntimeException("e");
        }

        ChatRoomDTO chatRoomDTO = ChatConverter.chatRoomToDTO(room);
        return chatRoomDTO;
    }

    @Transactional
    public void chatSave(ChatMessageDTO dto) {
        try {

        }
    }
}
