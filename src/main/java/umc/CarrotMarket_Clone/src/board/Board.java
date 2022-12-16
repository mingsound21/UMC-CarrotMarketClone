package umc.CarrotMarket_Clone.src.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.CarrotMarket_Clone.src.common.BaseTimeEntity;
import umc.CarrotMarket_Clone.src.user.model.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    private String boardTitle;
    private String boardContent;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키
    private User writer; // 필드 이름은 맘대로 해도 되나?

    @Builder
    public Board(String title, String content, User writer) {
        this.boardTitle = title;
        this.boardContent = content;
        this.status = BoardStatus.ACTIVE;
        this.writer = writer;
    }

    public void update(String title, String content){
        this.boardTitle = title;
        this.boardContent = content;
    }

    public void delete(){
        this.status = BoardStatus.INACTIVE;
    }


}
