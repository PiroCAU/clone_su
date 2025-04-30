package clone.carrotMarket.repository;

import clone.carrotMarket.domain.ChatRoom;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findFirstByMemberAndSell(Member member, Sell selll);

    long countByMemberAndSell(Member member, Sell sell);

    @Query("SELECT c FROM ChatRoom c WHERE c.sender.id = :memberId OR c.sell.member.id = :memberId")
    List<ChatRoom> findAllChatRoomsByMember(@Param("memberId") Long memberId);

    List<ChatRoom> findAllBySellId(Long sellId);

    Optional<ChatRoom> findBySellAndSender(Sell sell, Member member);
}
