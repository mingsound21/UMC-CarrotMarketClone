package umc.CarrotMarket_Clone.src.user;


import org.springframework.stereotype.Service;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.src.user.model.GetUserRes;
import umc.CarrotMarket_Clone.src.user.model.PostLoginReq;
import umc.CarrotMarket_Clone.src.user.model.PostLoginRes;

import java.util.List;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.DATABASE_ERROR;
import static umc.CarrotMarket_Clone.config.BaseResponseStatus.FAILED_TO_LOGIN;

@Service
public class UserProvider {
    private  UserDao userDao;

    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    // 로그인
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        try{
            int userId = userDao.getUserObject(postLoginReq).getUserId();
            return new PostLoginRes(userId);
        }catch(Exception exception){
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    // 이메일 중복 확인
    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 유저 전체 정보 조회
    public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 유저 이름으로 정보 조회
    public List<GetUserRes> getUserByUsername(String userName) throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUserByUsername(userName);
            return getUserRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // userId로 정보 조회
    public GetUserRes getUser(int userId) throws BaseException{
        try{
            GetUserRes getUserRes = userDao.getUserByUserId(userId);
            return getUserRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
