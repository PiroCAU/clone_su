package clone.carrotMarket.repository;

import clone.carrotMarket.domain.ChatRoom;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findFirstByMemberAndSell(Member member, Sell selll);

    long countByMemberAndSell(Member member, Sell sell);
}
