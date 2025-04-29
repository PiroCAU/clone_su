package clone.carrotMarket.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productImage_id")
    private Long id;

    private String imageUrl;
    private int imageRank;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sell sell;
}
