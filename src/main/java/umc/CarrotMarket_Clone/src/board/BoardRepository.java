package umc.CarrotMarket_Clone.src.board;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"writer"}) // 한방 쿼리로 연관된 user까지
    List<Board> findAll();
}
