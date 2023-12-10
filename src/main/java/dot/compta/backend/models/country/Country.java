package dot.compta.backend.models.country;

import dot.compta.backend.constants.ModelConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.COUNTRY_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.CITY_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.COUNTRY_MODEL_NAME)
    @NotBlank
    private String name;

}
