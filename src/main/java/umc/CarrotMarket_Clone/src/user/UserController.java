package umc.CarrotMarket_Clone.src.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.config.BaseResponseStatus;
import umc.CarrotMarket_Clone.src.user.model.*;
import umc.CarrotMarket_Clone.utils.JwtService;

import java.util.List;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.EXPIRED_JWT;
import static umc.CarrotMarket_Clone.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 일단 그냥 써봄 아무 지식 없이
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

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
        if(postUserReq.getUserEmail().equals("")){ // null 체크에서 빈 값체크로 변경
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
        }

        // 회원 가입
        try{
            PostUserRes postUserRes = userService.join(postUserReq);
            return new BaseResponse<>(postUserRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인
     */
    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            PostLoginRes postLoginRes = userService.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인 유지 확인
     */
    @PostMapping("/log-in/expiration")
    public BaseResponse<PostLoginValidateRes> checkLoginStatus(){
        try{
            Boolean result = userService.checkLoginStatus();
            return new BaseResponse<>(new PostLoginValidateRes(result));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 정보 수정
     */
    @PatchMapping("")
    public BaseResponse<String> updateUser(@RequestBody PatchUserInfoReq patchUserInfoReq){
        try{
            int userIdByJwt = jwtService.getUserIdx(); // jwt에서 userId 추출 => 올바르지 않으면 에러 발생시킴

//            if(userId != userIdByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            userService.updateUser(userIdByJwt, patchUserInfoReq);
            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 상태 변경(삭제)
     */
    @PatchMapping("/status")
    public BaseResponse<String> updateUser(){
        try{
            int userIdByJwt = jwtService.getUserIdx(); // jwt에서 userId 추출  => 올바르지 않으면 에러 발생시킴

            userService.updateUserStatus(userIdByJwt);
            String result = "회원이 정상적으로 삭제되었습니다.";
            return new BaseResponse<>(result);

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
