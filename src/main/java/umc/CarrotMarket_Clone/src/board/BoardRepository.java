package umc.CarrotMarket_Clone.src.board;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"writer"}) // 한방 쿼리로 연관된 user까지
    List<Board> findAll();

    // 페이징
    @EntityGraph(attributePaths = {"writer"})
    Slice<Board> findAllBy(Pageable pageable);
}
