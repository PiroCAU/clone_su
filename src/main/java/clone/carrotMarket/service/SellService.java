package clone.carrotMarket.service;

import clone.carrotMarket.domain.Sell;
import clone.carrotMarket.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellService {

    private final SellRepository sellRepository;

    public Sell save(Sell sell) {
        return sellRepository.save(sell);
    }

    public Sell findById(Long id) {
        return sellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

}
