package umc.CarrotMarket_Clone.src.post.model;

import lombok.*;
import umc.CarrotMarket_Clone.src.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne(fetch = FetchType.LAZY) // 연관관계의 주인, XXXToOne 명시적 LAZY
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
    @Enumerated(EnumType.STRING)
   private PostStatus status;

    @Builder
    public Post(User user, String postTitle, int price, String postContent, int categoryId) {
        this.user = user;
        this.postTitle = postTitle;
        this.price = price;
        this.postContent = postContent;
        this.categoryId = categoryId;
    }

    public void setUser(User user){
        this.user = user;
//        user.getPosts().add(this); => 이걸 하면 오히려 추가된 post가 중복으로 1개 더 들어감
    }

    public void change(String postTitle, String postContent, int price){
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.price = price;
    }

    public void delete(){
        this.status = PostStatus.INACTIVE;
    }
}
