package dot.compta.backend.models.city;

import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.country.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.CITY_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.CITY_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.CITY_MODEL_NAME)
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = ModelConstants.CITY_MODEL_COUNTRY_ID, referencedColumnName = ModelConstants.CITY_MODEL_ID)
    private Country country;

}
