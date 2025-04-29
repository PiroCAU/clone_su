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

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "sell_id")
    private Sell sell;
}
