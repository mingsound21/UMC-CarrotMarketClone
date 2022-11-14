package umc.CarrotMarket_Clone.src.post;

import org.springframework.stereotype.Service;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.BaseResponseStatus;
import umc.CarrotMarket_Clone.src.post.model.*;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.*;

@Service
public class PostService {
    private final PostDao postDao;
    private final PostProvider postProvider;

    public PostService(PostDao postDao, PostProvider postProvider) {
        this.postDao = postDao;
        this.postProvider = postProvider;
    }

    public PostPostRes createPost(PostPostReq postPostReq) throws BaseException {
       try{
           return new PostPostRes(postDao.createPost(postPostReq));
       }catch (Exception e){
           throw new BaseException(DATABASE_ERROR);
       }

    }

    public DeletePostRes deletePost(int id) throws BaseException{
       try{
           int result = postDao.deletePost(id);
           if(result == 1){
               return new DeletePostRes(id, "INACTIVE");
           }else{
               throw new BaseException(MODIFY_FAIL_POST_STATUS);
           }
       }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PatchPostRes updatePost(int id, PatchPostReq patchPostReq) throws BaseException{
      try{
          int result = postDao.updatePost(id, patchPostReq);
          if(result == 1){
              return new PatchPostRes(id, patchPostReq.getPostTitle(), patchPostReq.getPostContent(), patchPostReq.getPrice());
          }else{
              throw new BaseException(MODIFY_FAIL_POST);
          }
      }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
