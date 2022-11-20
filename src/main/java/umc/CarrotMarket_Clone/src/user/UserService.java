package umc.CarrotMarket_Clone.src.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.secret.Secret;
import umc.CarrotMarket_Clone.src.user.model.*;
import umc.CarrotMarket_Clone.utils.AES128;
import umc.CarrotMarket_Clone.utils.JwtService;

import java.util.ArrayList;
import java.util.List;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    /**
     * 모든 유저 조회
     */
    public List<GetUserRes> findAll(){
        List<User> findUsers = userRepository.findAll();
        List<GetUserRes> result = new ArrayList<>();
        for (User findUser : findUsers) {
            result.add(new GetUserRes(findUser));
        }
        System.out.println(result);
        return result;
    }

    /**
     * 유저 1명 조회
     */
    public GetUserRes findOne(int userId){
        return new GetUserRes(userRepository.findOne(userId));
    }

    /**
     * 회원 가입
     */
    @Transactional
    public PostUserRes join(PostUserReq postUserReq) throws BaseException{
        // 이메일 중복 확인
        boolean validateEmail = userRepository.validateDupliacateEmail(postUserReq.getUserEmail());
        if(!validateEmail){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        // 비밀번호 암호화 - 나중에
        String pwd;
        try{
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword()); // 암호화코드
            postUserReq.setPassword(pwd);
        }catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        // jwt
        try{
            int userIdx = userRepository.save(postUserReq.toEntity()); // DB에 유저 넣기
            String jwt = jwtService.createJwt(userIdx); // jwt 생성
            return new PostUserRes(jwt, userIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }


    /**
     * 로그인
     */
    @Transactional
    public void logIn(){

    }

    /**
     * 유저 정보 수정
     */
    @Transactional
    public void updateUser(int userId, PatchUserInfoReq patchUserInfoReq){
        User findUser = userRepository.findOne(userId); // 영속상태
        findUser.changeUserInfo(patchUserInfoReq.getUserName(), patchUserInfoReq.getUserEmail(), patchUserInfoReq.getUserImg()); // 변경 감지(dirty checking)
    }

    /**
     * 유저 상태 변경 = 삭제
     */
    @Transactional
    public void updateUserStatus(int userId){
        User findUser = userRepository.findOne(userId);
//        findUser.changeUserStatus();
    }
}
