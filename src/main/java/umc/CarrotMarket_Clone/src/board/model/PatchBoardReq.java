package umc.CarrotMarket_Clone.src.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchBoardReq {
    private String boardTitle;
    private String boardContent;
}
