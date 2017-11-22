package nl.qnh.qforce.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entity for storing analytical information
 */
@Entity
public class Analytic {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String origin;

    private LocalDateTime dateTime;


    protected Analytic() {}

    public Analytic(String origin, LocalDateTime dateTime) {
        this.origin = origin;
        this.dateTime = dateTime;
    }
}
