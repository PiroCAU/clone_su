package clone.carrotMarket.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String content;

    private LocalDateTime created_at;
    private int price;
    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;
    private String title;
    private int latitude;
    private String place;
    private LocalDateTime uplodated_at;
    private int views;

    @OneToMany(mappedBy = "productImage_id", cascade = CascadeType.REFRESH)
    private List<ProductImage> productImage;

    @OneToOne
    @JoinColumn(name = "sellLike_id")
    private SellLike sellLike;
}
