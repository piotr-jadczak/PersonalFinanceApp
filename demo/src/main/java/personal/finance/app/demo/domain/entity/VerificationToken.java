package personal.finance.app.demo.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    private LocalDate expiryDate;

    private LocalDate calculateExpiryDate() {
        return LocalDate.now().plusDays(3);
    }

    @OneToOne
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
        this.user = user;
    }
}
