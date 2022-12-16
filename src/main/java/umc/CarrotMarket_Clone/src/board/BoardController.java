package umc.CarrotMarket_Clone.src.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.src.board.model.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    /**
     * 생성
     */
    @PostMapping("")
    public BaseResponse<PostBoardRes> create(@RequestBody PostBoardReq postBoardReq){
        try{
            PostBoardRes postBoardRes = boardService.create(postBoardReq);
            return new BaseResponse<>(postBoardRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * 조회
     */
    @GetMapping("")
    public BaseResponse<Slice<GetBoardRes>> getBoards(Pageable pageable){
//        List<GetBoardRes> result = boardService.getAll();
        Slice<GetBoardRes> result = boardService.getAll(pageable);
        return new BaseResponse<>(result);
    }


    /**
     *  1개 조회
     */
    @GetMapping("/{boardId}")
    public BaseResponse<GetBoardRes> getBoard(@PathVariable Long boardId){
        try{
            GetBoardRes findOne = boardService.getOne(boardId);
            return new BaseResponse<>(findOne);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }



    /**
     * 수정
     */
    @PatchMapping("/{boardId}")
    public BaseResponse<PatchBoardRes> update(@PathVariable Long boardId, @RequestBody PatchBoardReq patchBoardReq){
        try{
            PatchBoardRes updatedBoard = boardService.update(boardId, patchBoardReq);
            return new BaseResponse<>(updatedBoard);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 삭제
     */
    @PatchMapping("{boardId}/status")
    public BaseResponse<DeleteBoardRes> delete(@PathVariable Long boardId){
        try{
            DeleteBoardRes deletedBoard = boardService.delete(boardId);
            return new BaseResponse<>(deletedBoard);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
