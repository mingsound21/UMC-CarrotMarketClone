package umc.CarrotMarket_Clone.src.user.model;



import lombok.Getter;
import lombok.Setter;
import umc.CarrotMarket_Clone.src.post.model.Post;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
public class User {
    @Id @GeneratedValue
    private int userId;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    private String userEmail;
    private String userName;
    private String userImg;
    private Double temperature;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeUserInfo(String username, String userEmail, String userImg){
        this.userName = username;
        this.userEmail = userEmail;
        this.userImg = userImg;
    }

    public void changeUserStatus(UserStatus userStatus){
        this.status = userStatus;
    }
}
