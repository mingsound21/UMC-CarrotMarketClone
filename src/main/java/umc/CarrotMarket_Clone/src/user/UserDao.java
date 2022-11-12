package umc.CarrotMarket_Clone.src.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import umc.CarrotMarket_Clone.src.user.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 회원 가입
    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into user(userName, userEmail, userImg) values(?, ?, ?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getUserEmail(), postUserReq.getUserImg()};

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }
    // 로그인

    // 이메일 확인
    public int checkEmail(String email){
        String checkEmailQuery = "select exists (select userEmail from user where userEmail = ?)";
        String checkEmailParams = email;

        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams); // exists라서 반환값이 있으면 1, 없으면 0
    }

    // 회원정보 변경
    public int modifyUserInfo(PatchUserInfoReq patchUserInfoReq){
        String modifyUserInfoQuery = "update user set userName = ?, userEmail = ?, userImg = ? where userId = ?";
        Object[] modifyUserInfoParams = new Object[]{patchUserInfoReq.getUserName(), patchUserInfoReq.getUserEmail(), patchUserInfoReq.getUserImg(), patchUserInfoReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserInfoQuery, modifyUserInfoParams);
    }

    // 유저 전체 조회
    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from user";
        return jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userImg")
                )
        );
    }

    // username으로 유저 조회
    public List<GetUserRes> getUserByUsername(String userName){
        String getUserByUsernameQuery = "select * from user where userName = ?";
        String getUserByUsernameParams = userName;

        return jdbcTemplate.query(getUserByUsernameQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userImg")
                ));
    }

    // userId로 유저 조회
    public GetUserRes getUserByUserId(int userId){
        String getUserByUserIdQuery = "select * from user where userId = ?";
        int getUserByUserIdParam = userId;
        return this.jdbcTemplate.queryForObject(getUserByUserIdQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userImg")
                ),
                getUserByUserIdParam);
    }

    // 로그인을 위해 만든 함수
    public User getUserObject(PostLoginReq postLoginReq){
        String getUserObjectQuery = "select * from user where userEmail = ?";
        String getUserObjectParams = postLoginReq.getUserEmail();

        return this.jdbcTemplate.queryForObject(getUserObjectQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userId"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userImg"),
                        rs.getDouble("temperature"),
                        rs.getObject("status", UserStatus.class),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime())
        );
    }
}

// jdbcTemplate.query(Query, rowMapper) : 여러개
// jdbcTemplate.queryForObject(Query, rowMapper(반환형), Params) : 1개

// jdbcTemplate.update(Query, Params) : 수정
// 리턴 값 : 변경된 행 개수