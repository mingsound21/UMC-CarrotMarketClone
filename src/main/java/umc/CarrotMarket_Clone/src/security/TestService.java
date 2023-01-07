package umc.CarrotMarket_Clone.src.security;

import org.springframework.stereotype.Service;
import umc.CarrotMarket_Clone.config.security.SecurityUser;

@Service
public class TestService {
    public Object getTest() {
        return "test";
    }

    public Object getTest2(SecurityUser securityUser) {
        return securityUser;
    }
}
