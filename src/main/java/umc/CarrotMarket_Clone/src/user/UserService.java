package umc.CarrotMarket_Clone.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.src.user.model.PatchUserInfoReq;
import umc.CarrotMarket_Clone.src.user.model.PostUserReq;
import umc.CarrotMarket_Clone.src.user.model.PostUserRes;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.*;

@Service
public class UserService {
    private UserDao userDao;
    private UserProvider userProvider;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider) {
        this.userDao = userDao;
        this.userProvider = userProvider;
    }

    // 회원가입
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException{
        // 이메일 중복 확인 - provider를 거쳐서
        if(userProvider.checkEmail(postUserReq.getUserEmail())== 1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        try{
            int userId = userDao.createUser(postUserReq);
            return new PostUserRes(userId);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 회원 정보 수정
    public void modifyUserInfo(PatchUserInfoReq patchUserInfoReq) throws BaseException{
        try{
            int result = userDao.modifyUserInfo(patchUserInfoReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
