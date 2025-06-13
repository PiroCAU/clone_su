package clone.carrotMarket.controller;

import clone.carrotMarket.config.security.JwtResponseHandler;
import clone.carrotMarket.config.security.JwtUtil;
import clone.carrotMarket.config.security.google.GoogleAccountProfileResponse;
import clone.carrotMarket.config.security.google.GoogleClient;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.service.MemberService;
import clone.carrotMarket.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final GoogleClient googleClient;
    private final MemberService memberService;
    private final OAuthService oAuthService;
    private final JwtUtil jwtUtil;
    private final JwtResponseHandler jwtResponseHandler;

    /**
     *	1.	✅ Spring Security가 구글 로그인 URL 자동 생성 (HTML에서 구글 로그인이 들어오면)
     * 	2.	✅ 사용자를 구글 로그인 화면으로 리디렉션
     * 	3.	✅ 로그인 후 구글이 redirect_uri(/login/oauth2/code/google)로 authorization code 전달
     * 	4.	✅ Spring Security가 authorization code를 받고
     * 	5.	✅ access token 요청 → 사용자 프로필 조회
     * 	6.	✅ 내부적으로 OAuth2User 객체를 생성하고 인증 완료
     * 	7.	✅ 설정해둔 defaultSuccessUrl로 자동 이동
     */

//    @GetMapping("/login/oauth2/code/google")
//    public String googleCallBack(@RequestParam String code, HttpServletResponse response) throws IOException {
//        log.info("trying google login :" + code);
//
//        //구글에서 사용자의 프로필을 가져온다
//        GoogleAccountProfileResponse profile = googleClient.getGoogleAccountProfile(code);
//
//        //DB에 해당 사용자가 있는지 확인한다. 있으면 로그인, 없으면 회원가입 후 로그인
//        Member member = oAuthService.loginOrRegisterGoogle(profile);
//
//        //Member에 대해 토큰을 발행한다.
//        jwtResponseHandler.handlerJwtWithCookie(response, member.getEmail());
//
//        return "redirect:/sells/my";
//    }

    @GetMapping("/oauth/success")
    public String oauthSuccess(HttpServletResponse response,
                               @AuthenticationPrincipal OAuth2User oAuth2User) throws IOException {
        log.info("OauthSuccess");

        // ✅ OAuth2User에서 email 같은 정보 꺼내기
        String email = oAuth2User.getAttribute("email");

        // ✅ 사용자 등록 및 로그인 처리
        Member member = oAuthService.loginOrRegisterGoogle(oAuth2User);

        // ✅ JWT 쿠키 발급
        jwtResponseHandler.handlerJwtWithCookie(response, member.getEmail());

        return "redirect:/sells/my";
    }
}
