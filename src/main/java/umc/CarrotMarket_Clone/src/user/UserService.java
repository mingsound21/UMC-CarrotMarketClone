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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private Map<String, String> refreshTokens = new HashMap<>(); // 보통은 DB에 저장한다고 함. 지금은 그냥 map에...

    /**
     * 모든 유저 조회
     */
    public List<GetUserRes> findAll(){
        List<User> findUsers = userRepository.findAll();
        List<GetUserRes> result = new ArrayList<>();
        for (User findUser : findUsers) {
            result.add(new GetUserRes(findUser));
        }
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
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        User loginUser = userRepository.getUserByEmail(postLoginReq.getUserEmail());
        String password = loginUser.getPassword();

        // 비번 확인
        try{
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(loginUser.getPassword()); // 암호화
        }catch (Exception ignored){
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        // 비번 일치 확인 후, jwt 값 생성
        if(postLoginReq.getPassword().equals(password)){
            int userIdx = loginUser.getUserId();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);
        }else{// 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    /**
     * 로그인 유지 확인
     */
    public Boolean checkLoginStatus(){
        try{
            return jwtService.validateAccessToken();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 리프레쉬 토큰 저장
     */
    public void saveRefreshToken(String email, String refreshToken){
        refreshTokens.put(email, refreshToken);
    }

    /**
     * 리프레쉬 토큰 얻기
     */
    public String getRefreshToken(String email){
        return refreshTokens.get(email);
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
        findUser.deleteUserStatus();
    }
}
