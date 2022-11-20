package umc.CarrotMarket_Clone.src.user.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUserReq {
    private String userName;
    private String userEmail;
    private String userImg;
    private String password;

    @Builder
    public PostUserReq(String userName, String userEmail, String userImg, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImg = userImg;
        this.password = password;
    }

    // dto -> entity
    public User toEntity(){
        return User.builder()
                .userName(userName)
                .userEmail(userEmail)
                .userImg(userImg)
                .password(password)
                .build();
    }
}
