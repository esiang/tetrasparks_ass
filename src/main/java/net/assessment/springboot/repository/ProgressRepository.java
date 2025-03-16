package net.assessment.springboot.repository;

import net.assessment.springboot.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

}