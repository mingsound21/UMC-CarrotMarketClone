package umc.CarrotMarket_Clone.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.src.user.model.*;

import java.util.List;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.POST_USERS_EMPTY_EMAIL;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserProvider userProvider;
    private final UserService userService;

    @Autowired
    public UserController(UserProvider userProvider, UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq){

        // 이메일 null 체크
        if(postUserReq.getUserEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);

        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String username){
        try{
            // QueryString 검사해서 username null이면 모든 유저 정보가져오기
            if(username == null){
                System.out.println("user name is null");
                List<GetUserRes> users = userProvider.getUsers();
                for (GetUserRes user : users) {
                    System.out.println("userEmail = " + user.getUserEmail());
                }
                return new BaseResponse<>(users);
            }
            List<GetUserRes> users = userProvider.getUserByUsername(username);
            return new BaseResponse<>(users);


        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUser(@PathVariable int userId){
        try{
            GetUserRes user = userProvider.getUser(userId);
            return new BaseResponse<>(user);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{userId}")
    public BaseResponse<String> modifyUserInfo(@PathVariable int userId, @RequestBody PatchUserInfoReq patchUserInfoReq){
        try{
            userService.modifyUserInfo(patchUserInfoReq);
            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

//    @ResponseBody
//    @PatchMapping("/status/{userId}")
//    public BaseResponse<String> modifyUserStatus(@PathVariable int userId, @RequestBody PatchUserStatusReq patchUserStatusReq){
//        return null;
//    }
}
