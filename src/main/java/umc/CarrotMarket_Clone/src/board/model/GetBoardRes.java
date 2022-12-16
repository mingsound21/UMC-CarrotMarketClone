package umc.CarrotMarket_Clone.src.board.model;

import lombok.*;
import umc.CarrotMarket_Clone.src.board.Board;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter// ERRPR : No serializer found for class  해결을 위해서 Getter 추가(JSON으로 파싱하기 위해서는 Jaskson입장에서 값을 읽을 수 있어야함)
public class GetBoardRes {
    private String boardTitle;
    private String boardContent;
    private String writerName;

    public GetBoardRes(Board board){
        boardTitle = board.getBoardTitle();
        boardContent = board.getBoardContent();
        writerName = board.getWriter().getUserName();
    }
}
