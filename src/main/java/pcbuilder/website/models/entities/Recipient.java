package pcbuilder.website.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Recipients")
public class Recipient {
    @Id
    @GeneratedValue
    private Long recipientID;
    private String fullName;
    private String email;
    private String zipCode;
    private String city;
    private String phoneNumber;
}
