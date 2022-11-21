package umc.CarrotMarket_Clone.src.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.src.post.model.GetPostRes;
import umc.CarrotMarket_Clone.src.post.model.PatchPostReq;
import umc.CarrotMarket_Clone.src.post.model.PostPostReq;
import umc.CarrotMarket_Clone.src.post.model.PostPostRes;
import umc.CarrotMarket_Clone.utils.JwtService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final JwtService jwtService;

    /**
     * 게시물 생성
     */
    @PostMapping("")
    public BaseResponse<PostPostRes> createPost(@RequestBody PostPostReq postPostReq){
        try{
            int userIdByJwt = jwtService.getUserIdx();

            PostPostRes postPostRes = postService.create(userIdByJwt, postPostReq);
            return new BaseResponse<>(postPostRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시물 전체 조회(page, size 쿼리스트링으로 받으면 해당 개수만)
     */
    @GetMapping("")
    public BaseResponse<List<GetPostRes>> getPosts(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){

        List<GetPostRes> posts = postService.getPosts(page, size);
        return new BaseResponse<>(posts);
    }

    /**
     * 게시물 1개 조회
     */
    @GetMapping("/{postId}")
    public BaseResponse<GetPostRes> getPost(@PathVariable int postId){
        GetPostRes post = postService.getPost(postId);
        return new BaseResponse<>(post);
    }

    /**
     * 게시물 수정
     */
    @PatchMapping("/{postId}")
    public BaseResponse<String> updatePost(@PathVariable int postId, @RequestBody PatchPostReq patchPostReq){
        try{
            int userIdByJwt = jwtService.getUserIdx();

            postService.updatePost(postId, patchPostReq);
            String result = "게시물 수정을 성공적으로 완료했습니다.";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시물 삭제
     */
    @PatchMapping("/{postId}/status")
    public BaseResponse<String> deletePost(@PathVariable int postId){
        try{
            int userIdByJwt = jwtService.getUserIdx();

            postService.deletePost(postId);
            String result = "게시물 삭제를 성공적으로 완료했습니다.";

            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
