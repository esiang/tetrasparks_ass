package net.assessment.springboot.model;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@Data
@Entity
@Table(name = "Game")
@NoArgsConstructor  // Lombok generates a no-args constructor
@AllArgsConstructor // Lombok generates an all-args constructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;
    private Long gameNo;
    private String gameName;
    private String gameCode;
    private int type;
    private BigDecimal costPrice;
    private BigDecimal tax;
    private BigDecimal salePrice;

    @Column(name = "date_of_sale")
    private LocalDateTime dateOfSale;

    public Game(Long gameId, Long gameNo, String gameName, String gameCode,
                Integer type, BigDecimal costPrice, BigDecimal tax,
                BigDecimal salePrice, LocalDateTime dateOfSale) {
        this.gameId = gameId;
        this.gameNo = gameNo;
        this.gameName = gameName;
        this.gameCode = gameCode;
        this.type = type;
        this.costPrice = costPrice;
        this.tax = tax;
        this.salePrice = salePrice;
        this.dateOfSale = dateOfSale;
    }
}
