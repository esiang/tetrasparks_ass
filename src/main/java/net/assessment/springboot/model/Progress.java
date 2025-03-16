package net.assessment.springboot.model;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "Progress")
@NoArgsConstructor  // Lombok generates a no-args constructor
@AllArgsConstructor // Lombok generates an all-args constructor
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String status;
    private String errorMessage;

    // Custom constructor to handle the fields you want to pass
    public Progress(LocalDateTime startedAt, String status, String errorMessage) {
        this.startedAt = startedAt;
        this.status = status;
        this.errorMessage = errorMessage;
        this.completedAt = null; // Set completedAt to null by default
    }
}