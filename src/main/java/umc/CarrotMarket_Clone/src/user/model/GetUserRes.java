package umc.CarrotMarket_Clone.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserRes {
    private int userId;
    private String userEmail;
    private String userName;
    private String userImg;

    public GetUserRes(User entity){
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserName();
        this.userName = entity.getUserName();
        this.userImg = entity.getUserImg();
    }
}
