package com.katsshura.cupcake.core.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.katsshura.cupcake.core.dto.address.AddressDTO;
import com.katsshura.cupcake.core.validation.date.DateValidator;
import com.katsshura.cupcake.core.validation.encrypted.Encrypted;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = { "password" }, allowSetters = true)
@Encrypted(propertiesName = { "password" })
public class UserDTO {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Size(min = 11, max = 11)
    @JsonProperty("cpf")
    ///TODO: Define cpf validator
    private String cpf;

    @NotNull
    @JsonProperty("birthday_date")
    @DateValidator(between = {"1870-01-01"})
    private LocalDate birthdayDate;

    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotNull
    @Valid
    @JsonProperty("addresses")
    private List<AddressDTO> addresses;
}
