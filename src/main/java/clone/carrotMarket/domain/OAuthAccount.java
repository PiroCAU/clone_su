package clone.carrotMarket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthAccount {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthProvideerEnum provider; // GOOGLE, APPLE, KAKAO 등

    private String email; // 해당 플랫폼에서 발급된 유저 식별자

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        member.getOauthAccounts().add(this);
    }
}
