package clone.carrotMarket.service;

import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.domain.SellLike;
import clone.carrotMarket.domain.SellStatus;
import clone.carrotMarket.dto.sell.SellDTO;
import clone.carrotMarket.repository.SellLikeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SellLikeService {

    private final SellLikeRepository sellLikeRepository;
    private final SellService sellService;
    private final MemberService memberService;
    private final ChatService chatService;

    public boolean unlike(Member member, Sell sell) {
        sellLikeRepository.findByMemberAndSell(member, sell)
                .ifPresent(sellLike ->
        {
            sellLike.delete();
            sellLikeRepository.delete(sellLike);
        });
        return true;
    }

    public boolean unlikeBySellId(Member member, Long sellId) {
        Sell sell = sellService.findById(sellId);
        return unlike(member ,sell);
    }

    public boolean createLike(Member member, Sell sell) {
        SellLike sellLike = new SellLike(member, sell);
        SellLike save = sellLikeRepository.save(sellLike);
        sellLikeRepository.save(save);
        return true;
    }

    public boolean isLiked(Member member, Sell sell) {
        return sellLikeRepository.findByMemberAndSell(member, sell).isPresent();
    }

    public long countSellLike(Sell sell) {
        long cnt = sellLikeRepository.countBySell(sell);
        return cnt;
    }

    public void likeSell(Long sellId, Long sellerId, Member member) {
        //만약 게시글 작성자와 동일한 사람이 버튼을 누른다면 무시되어야 한다.
        if (sellerId.equals(member.getId())) {
            return;
        }

        Sell sell = sellService.findById(sellId);

        if (isLiked(member, sell)) {
            unlike(member, sell);
        } else {
            createLike(member, sell);
        }
    }

    public List<SellDTO> getSellDTOByLikesANDStatus(Long memberId, SellStatus sellStatus) {
        List<Sell> sells = getLikedSellsByStatus(memberId, sellStatus);
        Member member = memberService.findById(memberId);
        List<SellDTO> sellDTOS = SellConverter.sellToSellDTO(sells, member, this, chatService);
        return sellDTOS;
    }

    private List<Sell> getLikedSellsByStatus(Long memberId, SellStatus sellStatus) {
        List<Sell> sells = null;
        if (sellStatus != null && !sellStatus.equals(SellStatus.SELLING)) {
            sells = sellLikeRepository.findLikedSellsByStatuses(memberId, List.of(SellStatus.SELLING, SellStatus.BOOKING));
        } else if (sellStatus.equals(SellStatus.FIN)) {
            sells = sellLikeRepository.findLikedSellsByStatuses(memberId, List.of(SellStatus.FIN));
        }

        return sells;
    }
}
