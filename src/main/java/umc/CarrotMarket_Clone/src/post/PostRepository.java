package umc.CarrotMarket_Clone.src.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.CarrotMarket_Clone.src.post.model.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public int create(Post post){
        em.persist(post);
        return post.getPostId();
    }

    public List<Post> getPosts(){
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public Post getPost(int postId){
        return em.find(Post.class, postId);
    }

    public List<Post> getPostsOfPage(int page, int size){
        return em.createQuery("select p from Post p order by p.postId", Post.class)
                .setFirstResult(size * (page-1)) // 조회 시작 위치
                .setMaxResults(size) // 조회할 데이터 수
                .getResultList();
    }
}
