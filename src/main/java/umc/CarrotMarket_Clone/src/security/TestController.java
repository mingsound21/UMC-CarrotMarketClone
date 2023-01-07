package umc.CarrotMarket_Clone.src.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.CarrotMarket_Clone.config.security.SecurityUser;

@RequiredArgsConstructor
@RequestMapping("/api/test")
@RestController
public class TestController {

    private final TestService testService;

    // 둘다 "test" 문자열 리턴 api
    // 차이 : 인증 거치냐 안거치냐

    // 인증 X
    @GetMapping("/permit-all")
    public Object getTest() throws Exception{
        return testService.getTest();
    }

    // 인증O
    // SecurityConfig에서 @EnableGlobalMethodSecurity 사용으로 .hasRole()을 아래와 같이 어노테이션으로 처리 가능
    @Secured("ROLE_AUTH")
    @GetMapping("/auth")
    public Object getTest2(@AuthenticationPrincipal SecurityUser securityUser) throws Exception{ // 스프링 시큐리티 내부 XXXProvider라는 객체에서 Authentication에 저장된 Principal 객체를 꺼내줌
        return testService.getTest2(securityUser);
    }


}
