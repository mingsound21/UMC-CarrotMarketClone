package umc.CarrotMarket_Clone.src.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.CarrotMarket_Clone.src.user.model.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public int save(User user){
        em.persist(user);
        return user.getUserId();
    }

    public User findOne(int userId){
        return em.find(User.class, userId);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

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
    public User getUserByEmail(String email){
        return em.createQuery("select u from User u where u.userEmail = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
