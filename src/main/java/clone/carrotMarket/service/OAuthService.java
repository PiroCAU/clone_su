package clone.carrotMarket.service;

import clone.carrotMarket.config.security.google.GoogleAccountProfileResponse;
import clone.carrotMarket.domain.AuthProvideerEnum;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.domain.OAuthAccount;
import clone.carrotMarket.repository.MemberRepository;
import clone.carrotMarket.repository.OAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final OAuthRepository oAuthRepository;

    @Transactional
    public Member loginOrRegisterGoogle(GoogleAccountProfileResponse profile) {
        OAuthAccount authAccount = oAuthRepository.findByEmailAndProvider(profile.getEmail(), AuthProvideerEnum.GOOGLE)
                .orElseGet(() -> {
                    Member member = Member.builder()
                            .email(profile.getEmail())
                            .nickName(profile.getName())
                            .build();
                    Member savedMember = memberRepository.save(member);
                    OAuthAccount oAuthAccount = OAuthAccount.builder()
                            .email(profile.getEmail())
                            .provider(AuthProvideerEnum.GOOGLE)
                            .build();
                    OAuthAccount savedAuth = oAuthRepository.save(oAuthAccount);
                    savedAuth.setMember(member);

                    return savedAuth;
                });

        return authAccount.getMember();
    }
}
