package dot.compta.backend.models.accountant;

import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.address.AddressModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.ACCOUNTANT_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.ACCOUNTANT_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.ACCOUNTANT_MODEL_COMPANY_NAME)
    @NotBlank
    private String companyName;

    @Column(name = ModelConstants.ACCOUNTANT_MODEL_RC)
    @NotNull
    private int rc;

    @Column(name = ModelConstants.ACCOUNTANT_MODEL_EMAIL)
    @NotBlank
    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ModelConstants.ACCOUNTANT_MODEL_ADDRESS_ID, referencedColumnName = ModelConstants.ADDRESS_MODEL_ID)
    private AddressModel address;

    @Column(name = ModelConstants.ACCOUNTANT_MODEL_MOBILE_PHONE)
    @NotBlank
    private String mobilePhone;

    @Column(name = ModelConstants.ACCOUNTANT_MODEL_PHONE)
    private String phone;

    @Column(name = ModelConstants.ACCOUNTANT_MODEL_FAX)
    private String fax;

    @Column(name = ModelConstants.ACCOUNTANT_MODEL_DELETED)
    private boolean deleted;

}
