package umc.CarrotMarket_Clone.src.board.model;

import lombok.*;
import umc.CarrotMarket_Clone.src.board.Board;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter// ERRPR : No serializer found for class  해결을 위해서 Getter 추가(JSON으로 파싱하기 위해서는 Jaskson입장에서 값을 읽을 수 있어야함)
public class GetBoardRes {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private String writerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageName;

    public GetBoardRes(Board board){
        boardId = board.getBoardId();
        boardTitle = board.getBoardTitle();
        boardContent = board.getBoardContent();
        writerName = board.getWriter().getUserName();
        createdAt = board.getCreatedAt();
        updatedAt = board.getUpdatedAt();
        imageName = board.getBoardImg();
    }
}
