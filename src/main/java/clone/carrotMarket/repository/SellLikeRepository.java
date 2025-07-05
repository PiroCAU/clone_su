package clone.carrotMarket.repository;

import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellLike;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.service.SellLikeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SellLikeRepository extends JpaRepository<SellLike, Long> {

    Optional<SellLike> findByMemberAndSell(Member member, Sell sell);

    long countBySell(Sell sell);

    @Query("SELECT sl.sell FROM SellLike sl WHERE sl.member.id = :memberId AND sl.sell.sellStatus IN :statuses")
    List<Sell> findLikedSellsByStatuses(@Param("memberId") Long memberId, @Param("statuses")List<SellStatus> statuses);
}
