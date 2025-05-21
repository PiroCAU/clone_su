package clone.carrotMarket.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sell_id")
    private Sell sell;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
