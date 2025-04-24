package clone.carrotMarket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime created_at;
    private int price;
    private String title;
    private int latitude;
    private String place;
    private LocalDateTime uplodated_at = null;
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
    private List<ProductImage> productImage;

    @OneToMany(mappedBy = "sell",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<SellLike> sellLikes = new ArrayList<>();

    @OneToMany(mappedBy = "sell",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>();


    @Builder
    public Sell(String title, String content, Category category, Integer price, String place, List<ProductImage> files, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.price = price;
        this.place = place;
        this.created_at = LocalDateTime.now();
        this.sellStatus = SellStatus.SELLING;

        this.productImage = files;
        this.sellLikes = null;

        this.setMember(member);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
        if (!member.getSells().contains(this)) {
            member.getSells().add(this);
        }
    }

    public void delete() {
        this.isDeleted = true;
        this.deleteAt = LocalDateTime.now();
    }
}
