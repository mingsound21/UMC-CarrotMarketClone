package umc.CarrotMarket_Clone.src.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.CarrotMarket_Clone.src.user.model.User;
import umc.CarrotMarket_Clone.src.user.model.UserStatus;

import java.util.List;
import java.util.Optional;


public interface UserSpringDataRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    List<User> findByUserEmail(String userEmail);

    List<User> findByUserName(String userName);

    @Query("select u from User u where u.status IS NULL or u.status = :status")
    List<User> findAll(@Param("status") UserStatus ACTIVE);

}
