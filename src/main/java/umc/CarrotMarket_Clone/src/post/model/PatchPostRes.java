package umc.CarrotMarket_Clone.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchPostRes {
    private int postId;
    String postTitle;
    String postContent;
    int price;
}
