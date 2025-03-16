package net.assessment.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import net.assessment.springboot.model.Game;
import net.assessment.springboot.repository.GameRepository;
import lombok.Data;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.time.LocalDateTime;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository; 

    public void saveAll(List<Game> games) {
        gameRepository.saveAll(games);
    }

    public Page<Game> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public Page<Game> findByDateRange(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return gameRepository.findByDateOfSaleBetween(fromDate, toDate, pageable);
    }

    public Page<Game> findBySalePriceCondition(BigDecimal price, String condition, Pageable pageable) {
        return condition.equalsIgnoreCase("more") ?
                gameRepository.findBySalePriceGreaterThan(price, pageable) :
                gameRepository.findBySalePriceLessThan(price, pageable);
    }

    public List<Map<String, Object>> getDailySales(LocalDateTime fromDate, LocalDateTime toDate, Long gameNo) {
        return gameRepository.getDailySales(fromDate, toDate, gameNo);
    }


}