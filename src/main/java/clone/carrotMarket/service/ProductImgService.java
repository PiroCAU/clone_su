package clone.carrotMarket.service;

import clone.carrotMarket.domain.ProductImage;
import clone.carrotMarket.repository.ProductImgRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductImgService {

    private final ProductImgRepository productImgRepository;

    public List<ProductImage> findListBySellId(Long sellId) {
        List<ProductImage> imgList = productImgRepository.findAllBySellId(sellId);
        return imgList;
    }
}
