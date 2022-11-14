package umc.CarrotMarket_Clone.src.post.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPostReq {
    private String postTitle;
    private int price;
//    private String isFreeShare; // 필수 X - 이런거 값 안들어왔을떄 기본 값 넣어주는 거 있나?
//    private String getPriceOffer;
    private String postContent;
//    private int showPlaceBoundary;
    private int categoryId;
    int userId;
}
