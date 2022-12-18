package umc.CarrotMarket_Clone.src.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBoardReq {
    private String boardTitle;
    private String boardContent;
    private String writerName; // 일단 유저의 이름이 중복 허용 X라고 가정. -> 추후 jwt 검증을 통해서 권한 있는 사람만 작성, 수정, 삭제 할 수 있도록 변경 가능
}
