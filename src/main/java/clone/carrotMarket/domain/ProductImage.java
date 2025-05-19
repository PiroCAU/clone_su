package clone.carrotMarket.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productImage_id")
    @EqualsAndHashCode.Include
    private Long id;

    private String imageUrl;
    private Integer imageRank;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sell sell;

    @Builder
    public ProductImage(String imageUrl, Integer imageRank) {
        this.imageRank = imageRank;
        this.imageUrl = imageUrl;
    }

    public void setProductImg(Sell sell) {
        this.sell = sell;
        if (!sell.getProductImage().contains(this)) {
            sell.getProductImage().add(this);
        }
    }
}
