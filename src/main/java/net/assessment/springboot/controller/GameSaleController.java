package net.assessment.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import net.assessment.springboot.model.Game;
import net.assessment.springboot.model.Progress;
import net.assessment.springboot.service.GameService;
import net.assessment.springboot.service.ProgressService;
import java.time.format.DateTimeFormatter; 
import java.time.LocalDateTime;  
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameSaleController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ProgressService progressService;

    // curl -X GET "http://localhost:8080/api/getGameSales"
    // curl -X GET "http://localhost:8080/api/getGameSales?page=1&size=50"
    // curl -X GET "http://localhost:8080/api/getGameSales?page=1&size=10"
    // curl -X GET "http://localhost:8080/api/getGameSales?price=50&priceCondition=greater"
    // curl -X GET "http://localhost:8080/api/getGameSales?fromDate=2024-01-01T00:00:00&toDate=2024-12-31T23:59:59"

    @GetMapping("/getGameSales")
    public ResponseEntity<Page<Game>> getGameSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) String priceCondition) {

        long startTime = System.currentTimeMillis();

        Pageable pageable = PageRequest.of(page, size);
        Page<Game> result;

        if (fromDate != null && toDate != null) {
            result = gameService.findByDateRange(LocalDateTime.parse(fromDate), LocalDateTime.parse(toDate), pageable);
        } else if (price != null && priceCondition != null) {
            result = gameService.findBySalePriceCondition(price, priceCondition, pageable);
        } else {
            result = gameService.findAll(pageable);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time for /api/endpoint: " + executionTime + " ms");
        
        return ResponseEntity.ok(result);
    }

    //  curl -X GET "http://localhost:8080/api/getTotalSales?fromDate=2024-01-01T00:00:00&toDate=2025-12-31T23:59:59&gameNo=2"

    @GetMapping("/getTotalSales")
    public ResponseEntity<List<Map<String, Object>>> getTotalSales(
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam(required = false) Long gameNo) {

        long startTime = System.currentTimeMillis();

        LocalDateTime from = LocalDateTime.parse(fromDate);
        LocalDateTime to = LocalDateTime.parse(toDate);

        List<Map<String, Object>> result = gameService.getDailySales(from, to, gameNo);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time for /api/endpoint: " + executionTime + " ms");

        return ResponseEntity.ok(result);
    }

}