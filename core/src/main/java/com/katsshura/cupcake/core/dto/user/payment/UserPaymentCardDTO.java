package com.katsshura.cupcake.core.dto.user.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPaymentCardDTO {

    @NotBlank
    @Size(min = 16, max = 16)
    @JsonProperty("card_number")
    private String cardNumber;

    @NotBlank
    @Size(min = 5, max = 5)
    @JsonProperty("expiring_date")
    private String expiringDate;

    @NotNull
    @Positive
    @JsonProperty("cvv")
    private Integer cvv;

    @NotNull
    @JsonProperty("custom_name")
    private String customName;

}
