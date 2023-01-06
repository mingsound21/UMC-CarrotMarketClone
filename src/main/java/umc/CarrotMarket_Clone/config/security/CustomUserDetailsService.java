package umc.CarrotMarket_Clone.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import umc.CarrotMarket_Clone.src.user.UserSpringDataRepository;

// UserDetailsService 구현체
// 인증 로직 서비스
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    // loadUserByUsername 함수에서는 인증된 결과를 가지고, UserDetails 인터페이스를 구현한 인증 대상 객체를 리턴

    private final UserSpringDataRepository userSpringDataRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        System.out.println("인증을 받습니다.");

        //로그인 로직 시작

        // loginId를 이용하여 DB에서 User 객체를 가져옵니다.
        // User user = mapper.getUser(loginID); // 이건 예시고 repository 사용해도 됨.
        // User의 정보를 SecurityUser 에 담아줍니다. 이는 생성자를 이용하는 편입니다.!
        return new SecurityUser();
    }
}

// ID만으로 인증하는 형태로 보여짐 -> 궁금증) 비번은 확인 안하나?
// loginId로 User 객체를 DB에서 가져와서 SpringSecuirty에서 받아오고 난뒤, 내부적으로 SecurityUser 클래스와 password를 passwordEncodder를 이용해 처리할 수 있는 서비스 로직 존재
// 아직까지는 /auth 인증안되고 403뜸! 별도의 인증을 담당하는 filter 필요