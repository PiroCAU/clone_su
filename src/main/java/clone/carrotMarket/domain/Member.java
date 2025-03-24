package clone.carrotMarket.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    @OneToMany(mappedBy = "sellLike_id")
    private List<SellLike> sellLike;

    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<Sell> sells = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<Purchase> purchases = new ArrayList<>();

}
