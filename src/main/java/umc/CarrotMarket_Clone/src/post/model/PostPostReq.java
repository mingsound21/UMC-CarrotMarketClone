package umc.CarrotMarket_Clone.src.post.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPostReq {
    private String postTitle;
    private int price;
    private String postContent;
    private int categoryId;

    // dto -> entity
    public Post toEntity(){
       return Post.builder()
                .postTitle(this.postTitle)
                .price(this.price)
                .postContent(this.postContent)
                .categoryId(this.categoryId)
                .build();
    }
}