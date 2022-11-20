package umc.CarrotMarket_Clone.src.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.src.user.model.GetUserRes;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 모든 유저 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetUserRes>> getUsers(){
        List<GetUserRes> users = userService.findAll();
        return new BaseResponse<>(users);
    }

    /**
     * 유저 1명 조회
     */
    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUser(@PathVariable int userId){
        GetUserRes user = userService.findOne(userId);
        return new BaseResponse<>(user);
    }


}
