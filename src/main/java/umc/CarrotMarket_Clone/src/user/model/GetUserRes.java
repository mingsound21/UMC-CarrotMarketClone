package umc.CarrotMarket_Clone.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserRes {
    private Long userId;
    private String userEmail;
    private String userName;
    private String userImg;

    public GetUserRes(User entity){
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserEmail();
        this.userName = entity.getUserName();
        this.userImg = entity.getUserImg();
    }
}
