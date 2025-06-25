package clone.carrotMarket.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private int price;
    private String title;
    private int latitude;
    private String place;
    private LocalDateTime updatedAt = null;
    private int views = 0;
    private boolean isDeleted  =false;
    private LocalDateTime deleteAt;

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "sell", cascade = CascadeType.REFRESH)
    private List<ProductImage> productImage = new ArrayList<>();

    @OneToMany(mappedBy = "sell",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<SellLike> sellLikes = new ArrayList<>();

    @OneToMany(mappedBy = "sell",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToOne(mappedBy = "sell")
    private Purchase purchase;


    @Builder
    public Sell(String title, String content, Category category, Integer price, String place, List<ProductImage> files, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.price = price;
        this.place = place;
        this.createdAt = LocalDateTime.now();
        this.sellStatus = SellStatus.SELLING;

        for (ProductImage file : files) {
            file.setProductImg(this);
        }
        this.sellLikes = null;

        this.member = member;

    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
    public void changeSellStatus(SellStatus status) { this.sellStatus = status;}
    public void changePrice(int price) {
        if (price < 0 || price > 10000000) {
            throw new RuntimeException("비정상적인 가격입니다.");
        }
        this.price = price;
    }
    public void changeCategory(Category category) { this.category = category; }
    public void changePlace(String place) { this.place = place;}
    public void changeUpdatedAt() {this.updatedAt = LocalDateTime.now();}
    public void changePurchase(Purchase purchase) {this.purchase = purchase;}

    public void setMember(Member member) {
        this.member = member;
        if (!member.getSells().contains(this)) {
            member.getSells().add(this);
        }
    }

    public void setProductImage(ProductImage image) {
        if (!this.productImage.contains(image)) {
            this.productImage.add(image);
            image.setProductImg(this);
        }
    }

    public void delete() {
        this.isDeleted = true;
        this.deleteAt = LocalDateTime.now();
    }
}
