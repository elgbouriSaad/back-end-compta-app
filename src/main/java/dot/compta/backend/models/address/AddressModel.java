package dot.compta.backend.models.address;


import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.city.City;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.ADDRESS_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.ADDRESS_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.ADDRESS_MODEL_PRIMARY_ADDRESS)
    @NotBlank
    private String primaryAddress;

    @Column(name = ModelConstants.ADDRESS_MODEL_SECONDARY_ADDRESS)
    private String secondaryAddress;

    @Column(name = ModelConstants.ADDRESS_MODEL_POSTAL_CODE)
    @NotNull
    private int postalCode;

    @ManyToOne
    @JoinColumn(name = ModelConstants.ADDRESS_MODEL_CITY_ID, referencedColumnName = ModelConstants.CITY_MODEL_ID)
    private City city;

}
