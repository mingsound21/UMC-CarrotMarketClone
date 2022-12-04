package umc.CarrotMarket_Clone.src.post.model;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetPostRes {
    private int postId;
    private String postTitle;
    private int price;
    private String postContent;
    private int categoryId;

    @Builder
    public GetPostRes(int postId, String postTitle, int price, String postContent, int categoryId) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.price = price;
        this.postContent = postContent;
        this.categoryId = categoryId;
    }
}