package umc.CarrotMarket_Clone.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetPostRes {
    private int postId;
    private String postTitle;
    private int price;
    private String postContent;
    private int categoryId;
}
