package dot.compta.backend.models.customer;

import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.accountant.AccountantModel;
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
@Table(name = ModelConstants.CUSTOMER_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.CUSTOMER_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.CUSTOMER_MODEL_COMPANY_NAME)
    @NotBlank
    private String companyName;

    @Column(name = ModelConstants.CUSTOMER_MODEL_RC)
    @NotNull
    private int rc;

    @Column(name = ModelConstants.CUSTOMER_MODEL_EMAIL)
    @NotBlank
    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ModelConstants.CUSTOMER_MODEL_ADDRESS_ID, referencedColumnName = ModelConstants.ADDRESS_MODEL_ID)
    private AddressModel address;

    @Column(name = ModelConstants.CUSTOMER_MODEL_MOBILE_PHONE)
    @NotBlank
    private String mobilePhone;

    @Column(name = ModelConstants.CUSTOMER_MODEL_PHONE)
    private String phone;

    @Column(name = ModelConstants.CUSTOMER_MODEL_FAX)
    private String fax;

    @Column(name = ModelConstants.CUSTOMER_MODEL_DELETED)
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = ModelConstants.CUSTOMER_MODEL_ACCOUNTANT_ID, referencedColumnName = ModelConstants.ACCOUNTANT_MODEL_ID)
    private AccountantModel accountant;

}
