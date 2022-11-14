package umc.CarrotMarket_Clone.src.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.CarrotMarket_Clone.src.post.model.*;

import java.util.List;

@Repository
public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // 게시물 생성
    public int createPost(PostPostReq postPostReq){
        String createPostQuery = "insert into post(postTitle, price, postContent, categoryId, userId) values(?, ?, ?, ?, ?)";
        Object[] createPostParams = new Object[]{
                postPostReq.getPostTitle(),
                postPostReq.getPrice(),
                postPostReq.getPostContent(),
                postPostReq.getCategoryId(),
                postPostReq.getUserId()
        };

        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // 게시물 전체 조회
    public List<GetPostRes> getPosts(){
        String getPostsQuery = "select * from post where status = ?";
        return this.jdbcTemplate.query(getPostsQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postId"),
                        rs.getString("postTitle"),
                        rs.getInt("price"),
                        rs.getString("postContent"),
                        rs.getInt("categoryId")
                ),
                "ACTIVE");
    }

    // 게시물 1개 조회
    public GetPostRes getPost(int id){
        String  getPostQuery = "select * from post where postId = ?";
        int getPostParams = id;
        return this.jdbcTemplate.queryForObject(getPostQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postId"),
                        rs.getString("postTitle"),
                        rs.getInt("price"),
                        rs.getString("postContent"),
                        rs.getInt("categoryId")),
                getPostParams);

    }

    // 게시물 삭제
    public int deletePost(int id){
        String deletePostQuery = "update post set status = ? where postId = ?";
        Object[] deletePostParams = new Object[]{
                "INACTIVE",
                id
        };

        return this.jdbcTemplate.update(deletePostQuery, deletePostParams);// 0 실패, 1 성공
    }

    // 게시물 수정
    public int updatePost(int id, PatchPostReq patchPostReq){
        String updatePostQuery = "update post set postTitle = ?, postContent = ?, price = ? where postId = ?";
        Object[] updatePostParams = new Object[]{
                patchPostReq.getPostTitle(),
                patchPostReq.getPostContent(),
                patchPostReq.getPrice(),
                id
        };

        return this.jdbcTemplate.update(updatePostQuery, updatePostParams); // 0 실패, 1 성공

    }

}
