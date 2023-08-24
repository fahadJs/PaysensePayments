package pk.paysenseapp.payensese_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String firstName;

    private String lastName;

    private String gender;

    private String city;

    private String address;

    private String email;

    private String username;

    private String pin;

    private String phoneNumber;

}
