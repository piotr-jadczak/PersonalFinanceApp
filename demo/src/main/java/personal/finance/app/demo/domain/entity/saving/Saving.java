package personal.finance.app.demo.domain.entity.saving;

import lombok.Data;
import lombok.NoArgsConstructor;
import personal.finance.app.demo.domain.entity.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "saving")
    private User user;

    @OneToMany
    private Set<Stock> stocks;

    @OneToMany
    private Set<Currency> currencies;

}
