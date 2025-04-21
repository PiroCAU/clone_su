package clone.carrotMarket.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "member_id")
    private Long id;

    private String email;
    private int manner;
    private int longitude;
    private String place;
    private String name;
    private String nickName;
    private String password;
    private String phone_number;
    private String profile_img;
    private boolean isDeleted = false;  //soft delete
    private LocalDateTime deleteAt;

    @OneToMany(mappedBy = "sellLike_id")
    private List<SellLike> sellLike;

    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<Sell> sells = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OAuthAccount> oauthAccounts = new ArrayList<>();

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeProfile_img(String filename) {
        this.profile_img = filename;
    }

    public void delete() {
        this.isDeleted = true;
        this.deleteAt = LocalDateTime.now();
    }

    @Builder
    public Member(String email, String place, String name, String nickName, String password, String phone_number,
                  String profile_img) {
        this.email = email;
        this.place = place;
        this.nickName = nickName;
        this.password = password;
        this.name = name;
        this.phone_number = phone_number;
        this.profile_img = profile_img;

    }
}
