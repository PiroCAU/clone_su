package clone.carrotMarket.service;

import clone.carrotMarket.converter.SellConverter;
import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.dto.sell.SellDetailResponseDto;
import clone.carrotMarket.repository.SellLikeRepository;
import clone.carrotMarket.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellService {

    private final SellRepository sellRepository;
    private final SellLikeService sellLikeService;

    public Sell save(Sell sell) {
        return sellRepository.save(sell);
    }

    public Sell findById(Long id) {
        return sellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

    public SellDetailResponseDto findDetail(Sell sell) {

        List<Sell> top5ByMember = sellRepository.findTop5ByMember(sell.getMember());

        if (top5ByMember.contains(sell)) {
            top5ByMember.remove(sell);
        }

        SellDetailResponseDto sellDetailResponseDto = SellConverter.sellToSellDetailResponseDto(sell, top5ByMember, sellLikeService);
        return sellDetailResponseDto;
    }

}
