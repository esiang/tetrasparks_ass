package net.assessment.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import net.assessment.springboot.model.Progress;
import net.assessment.springboot.repository.ProgressRepository;
import java.time.LocalDateTime;
import lombok.Data;
import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public Progress startImport() {
        Progress progress = new Progress(LocalDateTime.now(), "IN_PROGRESS", null);
        return progressRepository.save(progress);
    }

    public void completeImport(Progress progress) {
        progress.setStatus("COMPLETED");
        progress.setCompletedAt(LocalDateTime.now());
        progressRepository.save(progress);
    }

    public void failImport(Progress progress, String errorMessage) {
        progress.setStatus("FAILED");
        progress.setCompletedAt(LocalDateTime.now());
        progress.setErrorMessage(errorMessage);
        progressRepository.save(progress);
    }
}