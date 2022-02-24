package personal.finance.app.demo.domain.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {

    private final static int EXPIRY_DAYS_PERIOD = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    private LocalDate expiryDate;

    private LocalDate calculateExpiryDate() {
        return LocalDate.now().plusDays(EXPIRY_DAYS_PERIOD);
    }

    @OneToOne
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
        this.user = user;
    }
}
