package umc.CarrotMarket_Clone.src.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.BaseResponse;
import umc.CarrotMarket_Clone.src.post.model.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostProvider postProvider;

    @Autowired
    public PostController(PostService postService, PostProvider postProvider) {
        this.postService = postService;
        this.postProvider = postProvider;
    }


    @GetMapping("")
    public BaseResponse<List<GetPostRes>> getPosts(){
        try{
            return new BaseResponse<>(postProvider.getPosts());
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/{id}")
    public BaseResponse<GetPostRes> getPost(@PathVariable int id){
        try{
            return new BaseResponse<>(postProvider.getPost(id));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @PostMapping("")
    public BaseResponse<PostPostRes> createPost(@RequestBody PostPostReq postPostReq){
        try{
            return new BaseResponse<>(postService.createPost(postPostReq));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("/status/{id}")
    public BaseResponse<DeletePostRes> deletePost(@PathVariable int id){
        try{
            return new BaseResponse<>(postService.deletePost(id));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("/{id}")
    public BaseResponse<PatchPostRes> updatePost(@PathVariable int id, @RequestBody PatchPostReq patchPostReq) {
        try{
            return new BaseResponse<>(postService.updatePost(id, patchPostReq));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
