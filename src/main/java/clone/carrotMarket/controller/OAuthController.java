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

    @GetMapping("/login/oauth2/code/google")
    public void googleCallBack(@RequestParam String code, HttpServletResponse response) throws IOException {

        //구글에서 사용자의 프로필을 가져온다
        GoogleAccountProfileResponse profile = googleClient.getGoogleAccountProfile(code);

        //DB에 해당 사용자가 있는지 확인한다. 있으면 로그인, 없으면 회원가입 후 로그인
        Member member = oAuthService.loginOrRegisterGoogle(profile);

        //Member에 대해 토큰을 발행한다.
        jwtResponseHandler.handlerJwtWithCookie(response, member.getEmail());
    }
}
