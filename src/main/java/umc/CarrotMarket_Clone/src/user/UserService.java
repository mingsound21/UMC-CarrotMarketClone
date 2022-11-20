package umc.CarrotMarket_Clone.src.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.CarrotMarket_Clone.src.user.model.GetUserRes;
import umc.CarrotMarket_Clone.src.user.model.PatchUserInfoReq;
import umc.CarrotMarket_Clone.src.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
    public int join(User user){
        // jwt
        userRepository.save(user);
        return user.getUserId();
    }

    /**
     * 로그인
     */
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
