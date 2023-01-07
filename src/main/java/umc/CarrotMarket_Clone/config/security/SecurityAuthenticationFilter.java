package umc.CarrotMarket_Clone.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 스프링 시큐리티가 인증을 확인하는 단위 : 매 request 요청 마다
// 매 api 요청마다 인증 확인을 하기 위해서는 controller 로직 수행하기 이전에 로직인 filter를 이용
// OncePerRequestFilter : Request 요청마다 한번씩 호출

public class SecurityAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 이부분을 추가하여 /auth로 들어오는 경우는 username이 "test1"이기 때문에 인증을 실패합니다.
        String username = request.getRequestURI().contains("/auth") ? "test1" : "test";

        //아무 값이나 집어넣음.
        UserDetails authentication = customUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken auth = // UsernamePasswordAuthenticationToken은 AbstractAuthenticationToken 상속, AbstractAuthenticationToken는 Authentication 객체 상속
                //여기있는 super.setAuthenticated(true); 를 타야함.
                new UsernamePasswordAuthenticationToken(authentication.getUsername(), null, null);

        SecurityContextHolder.getContext().setAuthentication(auth);// SecurityCOntextHolder에 있는 Context 객체의 authentication 여부에 따라 인증 여부 결정

        filterChain.doFilter(request, response);
    }
}
