package clone.carrotMarket.config.security;

import antlr.Token;
import clone.carrotMarket.domain.Member;
import clone.carrotMarket.repository.MemberRepository;
import clone.carrotMarket.service.MemberService;
import clone.carrotMarket.service.TokenBlacklistService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.MemberRemoval;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 매 요청마다 인증 검사
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository repository, JwtUtil jwtUtil, TokenBlacklistService tokenBlacklistService) {
        super(authenticationManager);
        this.memberRepository = repository;
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null) {
            log.info("doFilter: token is not exist in Header");

            //헤더에 값이 없다면 cookies에서 찾는다
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    String name = c.getName();
                    String value = c.getValue();
                    if (name.equals("Authorization")) {
                        header = value;
                        log.info("toekn was found in cookies");

                    }
                }
            }
        }

        log.info("doFilter: cookie: " + header);
        //여전히 없다면 예외처리
        if (header == null) {
            log.info("doFilter: header is null");
            chain.doFilter(request, response);
            return;
        }

        String token = header;
        if (header.startsWith("Bearer")) {
            //앞에 붙은 Bearer 제가
            token = header.replace("Bearer ", "");
            log.info(token);
        }

        //블랙리스트에 있는 토큰인지 확인
        if (tokenBlacklistService.isBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is blacklisted");
            return;
        }

        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
            PrincipalDetails userDetails = new PrincipalDetails(member);

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
