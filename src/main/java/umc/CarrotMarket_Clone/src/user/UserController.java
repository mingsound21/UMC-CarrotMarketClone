package umc.CarrotMarket_Clone.src.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.config.BaseResponseStatus;
import umc.CarrotMarket_Clone.src.user.model.GetUserRes;
import umc.CarrotMarket_Clone.src.user.model.PostUserReq;
import umc.CarrotMarket_Clone.src.user.model.PostUserRes;

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

    /**
     * 회원 가입
     */
    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> join(@RequestBody PostUserReq postUserReq){
        // 이메일 null 체크
        if(postUserReq.getUserEmail() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
        }


    }

    /**
     * 로그인
     */

    /**
     * 유저 정보 수정
     */

    /**
     * 유저 상태 변경(삭제)
     */


}
