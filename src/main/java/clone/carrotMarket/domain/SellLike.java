package clone.carrotMarket.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SellLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sellLike")
    private Long id;

    @OneToOne(mappedBy = "member_id")
    private Member member;

    @OneToOne(mappedBy = "sell_id")
    private Sell sell;
}
