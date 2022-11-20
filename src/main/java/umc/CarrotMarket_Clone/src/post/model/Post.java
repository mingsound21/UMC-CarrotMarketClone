package umc.CarrotMarket_Clone.src.post.model;

import lombok.Getter;
import lombok.Setter;
import umc.CarrotMarket_Clone.src.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {
    @Id @GeneratedValue
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

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
}
