package umc.CarrotMarket_Clone.src.user.model;



import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.CarrotMarket_Clone.src.common.BaseTimeEntity;
import umc.CarrotMarket_Clone.src.post.model.Post;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@DynamicInsert // insert, update시 null인 field 제외 => mysql에서 설정한 default 값 들어가도록
@DynamicUpdate
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
/*
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
*/

    private String userEmail;
    private String userName;
    private String userImg;
    private String password;
    private Double temperature;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


    @Builder
    public User(Long userId, String userEmail, String userName, String userImg, String password, Double temperature, UserStatus status) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userImg = userImg;
        this.password = password;
        this.temperature = temperature;
        this.status = status;
    }

    public void changeUserInfo(String username, String userEmail, String userImg){
        this.userName = username;
        this.userEmail = userEmail;
        this.userImg = userImg;
    }

    public void deleteUserStatus(){
        this.status = UserStatus.INACTIVE;
    }
}
