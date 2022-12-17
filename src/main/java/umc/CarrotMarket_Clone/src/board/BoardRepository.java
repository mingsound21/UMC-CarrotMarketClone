package umc.CarrotMarket_Clone.src.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"writer"}) // 한방 쿼리로 연관된 user까지
    List<Board> findAll();

    // 페이징
    @EntityGraph(attributePaths = {"writer"})
    @Query("select b from Board b where b.status = 'ACTIVE'")
    Slice<Board> findAllBy(Pageable pageable); // findAll 함수 이름을 사용할 수 X,,, Page<Board> findAll(Pageable pageable)은 가능
    // 참고: Page extends Slice

    Optional<Board> findByBoardTitleAndCreatedAt(String boardTitle, LocalDateTime createdAt);
}
