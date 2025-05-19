package clone.carrotMarket.domain;

import javax.persistence.*;


@Entity
public class OAuthAccount {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthProvideerEnum provider; // GOOGLE, APPLE, KAKAO 등

    private String providerId; // 해당 플랫폼에서 발급된 유저 식별자

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
