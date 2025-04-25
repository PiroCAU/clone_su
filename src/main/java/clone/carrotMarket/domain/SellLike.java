package clone.carrotMarket.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SellLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sellLike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sell_id")
    private Sell sell;

    public SellLike(Member member, Sell sell) {
        this.member = member;
        if (!member.getSellLikes().contains(this)) {
            member.getSellLikes().add(this);
        }

        this.sell = sell;
        if (!sell.getSellLikes().contains(this)) {
            sell.getSellLikes().add(this);
        }
    }

    public void delete() {
        this.member.getSellLikes().remove(this);
        this.sell.getSellLikes().remove(this);
    }
}
