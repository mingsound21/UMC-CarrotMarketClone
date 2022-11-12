package umc.CarrotMarket_Clone.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private int userId;
    private String userEmail;
    private String userName;
    private String userImg;
    private Double temperature;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
