package umc.CarrotMarket_Clone.src.post.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchPostReq {
    String postTitle;
    String postContent;
    int price;
}