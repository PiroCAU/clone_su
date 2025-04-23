package clone.carrotMarket.service;

import clone.carrotMarket.domain.ChatRoom;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatRoom findByMemberAndSell(Member member, Sell sell) {
        ChatRoom chat = chatRepository.findFirstByMemberAndSell(member, sell)
                .orElse(null);
        return chat;
    }

    public Long countByMemberAndSell(Member member, Sell sell) {
        return chatRepository.countByMemberAndSell(member, sell);
    }
}
