package net.assessment.springboot.repository;

import net.assessment.springboot.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.time.LocalDateTime;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findByDateOfSaleBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<Game> findBySalePriceGreaterThan(BigDecimal price, Pageable pageable);
    Page<Game> findBySalePriceLessThan(BigDecimal price, Pageable pageable);


    @Query("SELECT NEW map(FUNCTION('DATE', g.dateOfSale) AS date, " +
              "COUNT(g) AS totalCount, " +
              "SUM(g.salePrice) AS totalPrice) " +
              "FROM Game g " +
              "WHERE g.dateOfSale BETWEEN :fromDate AND :toDate " +
              "AND (:gameNo IS NULL OR g.gameNo = :gameNo) " +
              "GROUP BY FUNCTION('DATE', g.dateOfSale) " +
              "ORDER BY FUNCTION('DATE', g.dateOfSale)")
       List<Map<String, Object>> getDailySales(@Param("fromDate") LocalDateTime fromDate,
                                          @Param("toDate") LocalDateTime toDate,
                                          @Param("gameNo") Long gameNo);





}