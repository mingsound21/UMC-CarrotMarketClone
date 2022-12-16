package umc.CarrotMarket_Clone.src.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.src.user.model.User;
import umc.CarrotMarket_Clone.src.user.model.UserStatus;

import javax.persistence.EntityManager;
import java.util.List;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.POST_USERS_WRONG_EMAIL;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;
/*
    public int save(User user){
        em.persist(user);
        return user.getUserId();
    }

    public User findOne(int userId){
        return em.find(User.class, userId);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u where u.status IS NULL or u.status = :status", User.class)
                .setParameter("status", UserStatus.ACTIVE)
                .getResultList();
    }
*/

    /**
     * 아래 2개는 spring data jpa 사용해서 어떻게 바꾸지
     */
    public boolean validateDupliacateEmail(String email){
        List<User> findSameEmailUser = em.createQuery("select u from User u where u.userEmail = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        if(findSameEmailUser.size() == 0){ // 중복없음 = 성공
            return true;
        }else{ // 중복있음 = 실패
            return false;
        }
    }

    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public User getUserByEmail(String email) throws BaseException{
        try{
            return em.createQuery("select u from User u where u.userEmail = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (Exception e){
            throw new BaseException(POST_USERS_WRONG_EMAIL);
        }
    }
}
