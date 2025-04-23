package clone.carrotMarket.repository;

import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellStatus;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellRepository extends JpaRepository<Sell, Long> {

    List<Sell> findTop5ByMember(Member member);

    List<Sell> findAllByMemberAndSellStatusOrderByCreatedAtDesc(Member member, SellStatus sellStatus);
}
