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

    @OneToOne(mappedBy = "sell_id", fetch = FetchType.LAZY)
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
