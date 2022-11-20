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

    public void save(User user){
        em.persist(user);
    }

    public User findOne(int userId){
        return em.find(User.class, userId);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
