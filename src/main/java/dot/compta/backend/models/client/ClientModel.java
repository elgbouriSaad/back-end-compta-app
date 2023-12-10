package dot.compta.backend.models.client;


import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.customer.CustomerModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.CLIENT_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.CLIENT_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.CLIENT_MODEL_COMPANY_NAME)
    @NotBlank
    private String companyName;

    @Column(name = ModelConstants.CLIENT_MODEL_RC)
    @NotNull
    private int rc;

    @Column(name = ModelConstants.CLIENT_MODEL_EMAIL)
    @NotBlank
    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ModelConstants.CLIENT_MODEL_ADDRESS_ID, referencedColumnName = ModelConstants.ADDRESS_MODEL_ID)
    private AddressModel address;

    @ManyToOne
    @JoinColumn(name = ModelConstants.CLIENT_MODEL_CUSTOMER_ID, referencedColumnName = ModelConstants.CUSTOMER_MODEL_ID)
    private CustomerModel customer;

    @Column(name = ModelConstants.CLIENT_MODEL_MOBILE_PHONE)
    @NotBlank
    private String mobilePhone;

    @Column(name = ModelConstants.CLIENT_MODEL_PHONE)
    private String phone;

    @Column(name = ModelConstants.CLIENT_MODEL_FAX)
    private String fax;

    @Column(name = ModelConstants.CLIENT_MODEL_DELETED)
    private boolean deleted;

}
