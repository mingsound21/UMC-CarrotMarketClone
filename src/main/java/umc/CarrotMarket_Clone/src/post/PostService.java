package umc.CarrotMarket_Clone.src.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.CarrotMarket_Clone.src.post.model.*;
import umc.CarrotMarket_Clone.src.user.UserRepository;
import umc.CarrotMarket_Clone.src.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 게시물 생성
     */
    public PostPostRes create(int userId, PostPostReq postPostReq){
        User findUser = userRepository.findOne(userId);

        Post newPost = postPostReq.toEntity();
        newPost.setUser(findUser);

        int postId = postRepository.create(newPost);

        // 실제로 user posts에 데이터가 있는지
        for (Post post : findUser.getPosts()) {
            System.out.println(post.getPostTitle());
        }
        System.out.println("유저의 posts.size = " + findUser.getPosts().size() );
        return new PostPostRes(postId);
    }


    /**
     * 게시물 전체 조회
     */
    @Transactional(readOnly = true)
    public List<GetPostRes> getPosts(Integer page, Integer size){
        List<Post> posts;

        if(page == null || size ==null){// page, size 안 주어지면 전체 게시물 조회
            posts = postRepository.getPosts();
        }else{ // page, size 주어지면 그만큼만
            posts = postRepository.getPostsOfPage(page, size);
        }

        List<GetPostRes> result = new ArrayList<>();
        for (Post post : posts) {
            GetPostRes getPostRes = GetPostRes.builder()
                    .postId(post.getPostId())
                    .postTitle(post.getPostTitle())
                    .price(post.getPrice())
                    .postContent(post.getPostContent())
                    .categoryId(post.getCategoryId())
                    .build();
            result.add(getPostRes);
        }

        return result;
    }

    /**
     * 게시물 1개 조회
     */
    @Transactional(readOnly = true)
    public GetPostRes getPost(int postId){
        Post post = postRepository.getPost(postId);
        GetPostRes result = GetPostRes.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .price(post.getPrice())
                .postContent(post.getPostContent())
                .categoryId(post.getCategoryId())
                .build();

        return result;
    }

    /**
     * 게시물 정보 수정
     */
    public void updatePost(int postId, PatchPostReq patchPostReq){
        Post updatePost = postRepository.getPost(postId); // 영속성 컨텍스트
        updatePost.change(patchPostReq.getPostTitle(), patchPostReq.getPostContent(), patchPostReq.getPrice());
    }

    /**
     * 게시물 삭제
     */
    public void deletePost(int postId){
        Post deletePost = postRepository.getPost(postId); // 영속성 컨텍스트
        deletePost.delete();
    }


}
