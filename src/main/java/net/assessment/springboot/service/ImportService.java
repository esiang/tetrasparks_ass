package net.assessment.springboot.service;

import net.assessment.springboot.model.Game;
import net.assessment.springboot.model.Progress;
import net.assessment.springboot.repository.GameRepository;
import net.assessment.springboot.repository.ProgressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.Data;


@Service
@Scope("prototype")  // Create a new instance for each injection
public class ImportService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private GameRepository gameRepository;  // Assuming you're importing game data

    public void importCsv(MultipartFile file) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Progress progress = new Progress(LocalDateTime.now(), "IN_PROGRESS", null);
        progressRepository.save(progress);

        if (file == null || file.isEmpty()) {
            System.err.println("File is empty or null.");
            throw new IllegalArgumentException("Empty file");
        }

        try (
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVReader csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(1).build()
        ) {
            String[] fields;
            List<Game> gamesBatch = new ArrayList<>();
            int batchSize = 1000;

            while ((fields = csvReader.readNext()) != null) {
                try {
                    Game game = new Game(
                        Long.parseLong(fields[0]),
                        Long.parseLong(fields[1]),
                        fields[2],
                        fields[3],
                        Integer.parseInt(fields[4]),
                        new BigDecimal(fields[5]),
                        new BigDecimal(fields[6]),
                        new BigDecimal(fields[7]),
                        LocalDateTime.parse(fields[8], formatter)
                    );
                    gamesBatch.add(game);

                    if (gamesBatch.size() >= batchSize) {
                        gameRepository.saveAll(gamesBatch);
                        gamesBatch.clear();
                    }
                } catch (Exception ex) {
                    System.err.println("Error processing line: " + String.join(",", fields) + " | Error: " + ex.getMessage());
                }
            }

            if (!gamesBatch.isEmpty()) {
                gameRepository.saveAll(gamesBatch);
            }

            progress.setStatus("COMPLETED");
            progress.setCompletedAt(LocalDateTime.now());
            progressRepository.save(progress);

            System.out.println("CSV import completed successfully.");
        } catch (Exception e) {
            progress.setStatus("FAILED");
            progress.setErrorMessage(e.getMessage());
            progress.setCompletedAt(LocalDateTime.now());
            progressRepository.save(progress);

            System.err.println("IO Error during import: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}