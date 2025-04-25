package clone.carrotMarket.repository;

import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellLike;
import clone.carrotMarket.service.SellLikeService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellLikeRepository extends JpaRepository<SellLike, Long> {

    Optional<SellLike> findByMemberAndSell(Member member, Sell sell);

    long countBySell(Sell sell);
}
