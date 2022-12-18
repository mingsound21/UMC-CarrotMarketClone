package umc.CarrotMarket_Clone.src.board;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.src.board.model.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    /**
     * 생성
     */
    @PostMapping("")
    public BaseResponse<PostBoardRes> create(
            @RequestPart(value = "postBoardReq") PostBoardReq postBoardReq,
            @RequestPart(value = "boardImg") MultipartFile multipartFile){
        String fileName = "";
        if(!multipartFile.getOriginalFilename().equals("")){
            // 파일 업로드
            try{
                fileName = fileService.fileUpload(multipartFile);
            }catch (BaseException e){
                return new BaseResponse<>(e.getStatus());
            }
        }

        // 저장
        try{
            PostBoardRes postBoardRes = boardService.create(postBoardReq, fileName);
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
     * 이미지 조회, 이미지 자체를 보냄
     */
    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> showImage(@PathVariable String fileName){
        try{
            return fileService.fileShow(fileName);

        }catch (BaseException e){
            return null; // 일단 null로 처리
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
