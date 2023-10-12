package myWallets.myWallets.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @Size(min = 3, message = "Invalid Customer name [ cotains at least 3 characters ]")
    private String customerName;

    @NotNull
    @Size(min = 2,max = 3 , message = "country code should be of 2-3 digits")
    private String countryCode;

    @NotNull
    @Size(min = 10, max = 14 ,message = "Invalid Mobile Number [ 9-13 Digit Only ] ")
    private String mobileNumber;

    @NotNull
//    @Size(min = 6, max = 12, message = "Invalid Password [ must be 6 to 8 characters ]")
    private String password;

    @NotNull
    @Size(min = 10, message = "Invalid Address [ must be at least 10 characters ]")
    private String address;

    @NotNull
    @Size(min = 6, max = 50, message = "Invalid Email [ must be 6 to 50 characters ]")
    @Email
    private String email;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @NotNull
    private String gender;
    private ZonedDateTime createdDate;
    private ZonedDateTime updateTime;
    private String otp;
    private boolean isActive = true; // Whether the account is active or not
    private boolean isVerified; // Whether the user's identity is verified or not

    @Transient
    private String temporary;

    @OneToMany( fetch = FetchType.LAZY ,cascade = CascadeType.ALL )
    @JsonIgnore
    private List<CustomerAccountDetails> customerAccountDetails;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "bank_branch_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BankBranches bankBranches;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "currentUserSessions_Id")
    @JsonIgnore
    @ToString.Exclude
    private CurrentUserSession currentUserSession;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles" ,
            joinColumns =@JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Roles> rolesSet;

    @PrePersist
    public void TranslateNameAndAddressIntoUpparCase(){
        customerName = customerName.toUpperCase();
        address = address.toUpperCase();
        createdDate = ZonedDateTime.now();
        countryCode= "+" + countryCode;
    }

}
