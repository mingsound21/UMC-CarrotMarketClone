package umc.CarrotMarket_Clone.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post {
    private Long postId;
    private String postTitle;
    private int price;
    private String isFreeShare;
    private String getPriceOffer;
    private String postContent;
    private int showPlaceBoundary;
    private String purchaseStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int categoryId;
    private String status;
    int userId;
}
