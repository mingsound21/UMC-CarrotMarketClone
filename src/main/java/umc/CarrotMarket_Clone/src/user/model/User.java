package umc.CarrotMarket_Clone.src.user.model;



import lombok.*;
import umc.CarrotMarket_Clone.src.post.model.Post;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    private String userEmail;
    private String userName;
    private String userImg;
    private String password;
    private Double temperature;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public User(int userId, String userEmail, String userName, String userImg, String password, Double temperature, UserStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userImg = userImg;
        this.password = password;
        this.temperature = temperature;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changeUserInfo(String username, String userEmail, String userImg){
        this.userName = username;
        this.userEmail = userEmail;
        this.userImg = userImg;
    }

    public void changeUserStatus(UserStatus userStatus){
        this.status = userStatus;
    }
}
