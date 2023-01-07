package umc.CarrotMarket_Clone.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 설정 클래스에 붙이는 어노테이션, 스프링 빈
@EnableWebSecurity // 스프링 시큐리티 활성화, @AuthenticationPrincipal 어노테이션을 통해서 Authentication 객체 속에 들어있는 principal 필드를 가져오올 수 있음.
@EnableGlobalMethodSecurity(securedEnabled = true) // Controller 메소드에 직접적으로 Role 부여 가능
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter : 스프링 시큐리티 설정 관련 클래스, 커스텀 설정 클래스가 이 클래스의 메소드를 오버라이딩해야 스프링 시큐리티에 반영됨

    // 빈 등록
    @Bean
    public SecurityAuthenticationFilter securityAuthenticationFilter() {
        return new SecurityAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and() // CorsFilter 활성화
                .csrf().disable()// 세션을 사용하지 않고, jwt 토큰 사용해 REST API를 만들기 때문에 csrf는 disable
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 스프링 시큐리티에서 세션 관리 X, 서버에서 관리하는 세션없이 클라이언트에서 보내준 토큰을 인증하는 방식 사용할 거라서
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 401(UnAuthorized)에러 - 인증 안됨
                    .accessDeniedHandler(new CustomAccessDeniedHandler()) // 403(Forbidden)에러 - 인증은 되었으나, 권한이 없음
                    .and()
                .authorizeRequests()// 이제 부터 인증 절차에 대한 설정 시작
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/api/test/permit-all").permitAll()
                    .antMatchers("/api/test/auth").hasRole("AUTH") // /auth url에는 AUTH라는 Role이 있어야 접근 성공, "Role_" 이라는 prefix 자동으로 붙음. 따라서 "Role_권한명" 이런식으로 이름 붙임.
                    .antMatchers("/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                .formLogin().disable()
        ;

        http
                .addFilterBefore(securityAuthenticationFilter(), // addBefore - 지정된 필터 앞에 커스텀 필터 추가
                        UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter 앞에 securityAuthenticationFilter 추가 
    }
}

// antMachers() : 특정 url에 대해서 어떻게 인증처리 할지 결정

// permitAll() : 스프링 시큐리티에서 인증되지 않더라도 통과 = 누구에게나 open
// authenticated() : 요청내에 스프링 시큐리티 컨텍스트 내에서 인증이 완료되어야 api 사용, 인증되지 않은 요청은 403(forbidden)
