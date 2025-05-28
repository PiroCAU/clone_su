package clone.carrotMarket.config.security;

import clone.carrotMarket.dto.user.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 필터
 * 사용자가 /api/login으로 email/password를 보내면 인증을 수행하고 JWT를 발급한다.
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/login", "POST")
        );
    }

    /**
     * 로그인 요청시에 호출된다.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
//            ObjectMapper om = new ObjectMapper();
//            LoginDTO loginDTO = om.readValue(request.getInputStream(), LoginDTO.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

            return authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 인증 성공 시 호출
     * - 로그인에 성공한 사용자 정보를 PrincipalDetail에서 꺼냄
     * - JWT를 생성하는 로직 호출
     * principalDetail: UserDetail 구현체로 로그인한 사용자의 정보를 가지고 있다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        String jwt = jwtUtil.createToken(principal.getUsername());
        log.info("---------------successfulAuthentication---------------");

        //jwt 토큰을 헤더에 넣는다.
        response.setHeader("Authorization", "Bearer " + jwt);

        //리다이렉트 위치 지정
        response.sendRedirect("/sells/my");
    }
}
