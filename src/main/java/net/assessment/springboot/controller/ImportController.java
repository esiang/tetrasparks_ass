package net.assessment.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import net.assessment.springboot.model.Game;
import net.assessment.springboot.model.Progress;
import net.assessment.springboot.service.GameService;
import net.assessment.springboot.service.ImportService;
import java.time.format.DateTimeFormatter; 
import java.time.LocalDateTime;  
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/import")
public class ImportController {

    private final ObjectFactory<ImportService> importServiceFactory;

    public ImportController(ObjectFactory<ImportService> importServiceFactory) {
        this.importServiceFactory = importServiceFactory;
    }

    @PostMapping
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {

            long startTime = System.currentTimeMillis();
            // Create a new instance of ImportService per request
            ImportService importService = importServiceFactory.getObject();
            importService.importCsv(file);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Execution time for /api/endpoint: " + executionTime + " ms");

            return ResponseEntity.ok("Import started successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Import failed: " + e.getMessage());
        }
    }
}

/*
@RestController
@RequestMapping("/import")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            importService.importCsv(file);
            return ResponseEntity.ok("Import started successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Import failed: " + e.getMessage());
        }
    }
}
*/
