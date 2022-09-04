package com.katsshura.cupcake.core.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.katsshura.cupcake.core.dto.address.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Size(min = 11, max = 11)
    @JsonProperty("cpf")
    private String cpf;

    //TODO: Create custom date validator to verify if date is later than 1900 and less than current day
    @NotNull
    @JsonProperty("birthday_date")
    private LocalDate birthdayDate;

    //TODO: Create custom password validator to verify if the integrity of the encryption
    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotNull
    @JsonProperty("address")
    private AddressDTO address;
}
