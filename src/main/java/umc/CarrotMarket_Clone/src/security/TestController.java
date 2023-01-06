package umc.CarrotMarket_Clone.src.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/auth")
    public Object getTest2() throws Exception{
        return testService.getTest2();
    }


}
