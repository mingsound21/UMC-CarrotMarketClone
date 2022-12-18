package umc.CarrotMarket_Clone.src.board;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.src.board.model.*;
import umc.CarrotMarket_Clone.src.user.UserSpringDataRepository;
import umc.CarrotMarket_Clone.src.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.FAIL_FIND_BOARD;
import static umc.CarrotMarket_Clone.config.BaseResponseStatus.FAIL_FIND_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserSpringDataRepository userSpringDataRepository;

    // 생성
    public PostBoardRes create(PostBoardReq postBoardReq, String fileName) throws BaseException {
        List<User> userList = userSpringDataRepository.findByUserName(postBoardReq.getWriterName());// 일단 유저 이름을 받기로 함
        if(userList.size() == 0){ // 비어있으면
            throw new BaseException(FAIL_FIND_USER);
        }

        User user = userList.get(0);

        Board newBoard = Board.builder()
                .title(postBoardReq.getBoardTitle())
                .content(postBoardReq.getBoardContent())
                .writer(user)
                .boardImg(fileName)
                .build();
        System.out.println("board title = " + newBoard.getBoardTitle());
        System.out.println("board content = " + newBoard.getBoardContent());

        Board result = boardRepository.save(newBoard);

        return new PostBoardRes(result.getBoardId());
    }

    // 조회
    @Transactional(readOnly = true)
    public List<GetBoardRes> getAll(){
        List<Board> findAll = boardRepository.findAll();
        List<GetBoardRes> result = findAll.stream().map(board -> new GetBoardRes(board)).collect(Collectors.toList());
        return result;
    }

    // 모든 게시물 조회 - 페이징
    @Transactional(readOnly = true)
    public Slice<GetBoardRes> getAll(Pageable pageable){
        Slice<Board> findAll = boardRepository.findAllBy(pageable);
        Slice<GetBoardRes> result = findAll.map(GetBoardRes::new);
        return result;
    }

    
    @Transactional(readOnly = true)
    public GetBoardRes getOne(Long boardId)throws BaseException{
        Optional<Board> findOne = boardRepository.findById(boardId);
        Board board = findOne.orElseThrow(() -> new BaseException(FAIL_FIND_BOARD));
        GetBoardRes result = new GetBoardRes(board);
        return result;
    }

    // 수정
    public PatchBoardRes update(Long boardId, PatchBoardReq patchBoardReq)throws BaseException{
        Optional<Board> findOne = boardRepository.findById(boardId);
        Board board = findOne.orElseThrow(() -> new BaseException(FAIL_FIND_BOARD));

        String newTitle = patchBoardReq.getBoardTitle();
        if(newTitle == null || newTitle.equals("")){
            newTitle = board.getBoardTitle();
        }

        String newContent = patchBoardReq.getBoardContent();
        if(newContent == null ||  newContent.equals("")){
            newContent = board.getBoardContent();
        }

        board.update(newTitle, newContent); // 더티 체킹
        System.out.println("board.new Title = " + board.getBoardTitle());
        return new PatchBoardRes(board.getBoardId());
    }

    // 삭제
    public DeleteBoardRes delete(Long boardId)throws BaseException{
        Optional<Board> findOne = boardRepository.findById(boardId);
        Board board = findOne.orElseThrow(() -> new BaseException(FAIL_FIND_BOARD));

        board.delete(); // 더티 체킹
        return new DeleteBoardRes(board.getBoardId());
    }

}
