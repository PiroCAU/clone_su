package clone.carrotMarket.repository;

import clone.carrotMarket.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllBySellId(Long sellId);
}
