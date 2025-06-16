package clone.carrotMarket.config.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtResponseHandler {

    private final JwtUtil jwtUtil;

    public JwtResponseHandler(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    public void handlerJwtWithCookie(HttpServletResponse response, String email) throws IOException  {
        String jwt = jwtUtil.createToken(email);

        //jwt 토큰을 헤더에 넣는다.
        response.setHeader("Authorization", "Bearer " + jwt);

        //cookie 방식
        Cookie accessToken = new Cookie("Authorization", jwt);
        accessToken.setMaxAge(60 * 60); // 1시간 동안 유효
        accessToken.setPath("/");
        accessToken.setDomain("localhost");
        accessToken.setSecure(false);

        response.addCookie(accessToken);

//        response.sendRedirect("/sells/my");
    }
}
