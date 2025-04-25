package clone.carrotMarket.service;

import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.repository.SellLikeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellLikeService {

    private final SellLikeRepository sellLikeRepository;

    public void unlike(Member member, Sell sell) {
        sellLikeRepository.findByMemberAndSell(member, sell)
                .ifPresent(sellLike ->
        {
            sellLike.delete();
            sellLikeRepository.delete(sellLike);
        });
    }

    public boolean isLiked(Member member, Sell sell) {
        return sellLikeRepository.findByMemberAndSell(member, sell).isPresent();
    }

    public long countSellLike(Sell sell) {
        long cnt = sellLikeRepository.countBySell(sell);
        return cnt;
    }
}
