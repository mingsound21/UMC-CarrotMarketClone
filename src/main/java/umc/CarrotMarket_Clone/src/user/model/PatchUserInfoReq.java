package umc.CarrotMarket_Clone.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserInfoReq {
    private String userName;
    private String userEmail;
    private String userImg;
}
