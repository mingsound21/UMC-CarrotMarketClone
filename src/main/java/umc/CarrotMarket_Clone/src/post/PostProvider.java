package umc.CarrotMarket_Clone.src.post;

import org.springframework.stereotype.Service;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.src.post.model.GetPostRes;

import java.util.List;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostProvider {
    private final PostDao postDao;

    public PostProvider(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<GetPostRes> getPosts() throws BaseException{
        try{
            return postDao.getPosts();
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public GetPostRes getPost(int id) throws BaseException{
        try{
            return postDao.getPost(id);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
