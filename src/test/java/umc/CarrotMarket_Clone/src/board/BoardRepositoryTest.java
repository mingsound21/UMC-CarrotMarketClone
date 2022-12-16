package umc.CarrotMarket_Clone.src.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class BoardRepositoryTest {
    @Autowired BoardRepository boardRepository;

    @Test
    public void test() {
        List<Board> result = boardRepository.findAll();

        for (Board board : result) {
            // board - user : fetch LAZY 설정해서
            // boardRepository에서 @EntityGraph 사용하면 fetch join 해서 쿼리 1방으로 모든 board, user 가져옴
            // 안그러면 user 초기화 될 때마다 쿼리 1개씩 날라감. 처음에는 프록시 객체로 존재함.
            System.out.println("board.Title = " + board.getBoardTitle());
            System.out.println("board.writer = "  + board.getWriter().getUserName());
        }

    }

}