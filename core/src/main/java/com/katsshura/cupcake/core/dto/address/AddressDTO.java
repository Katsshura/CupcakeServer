package com.katsshura.cupcake.core.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {

    @NotEmpty
    @Size(min = 8, max = 8)
    @JsonProperty("cep")
    ///TODO: Define CEP validation
    private String cep;

    @NotEmpty
    @Size(min = 3)
    @JsonProperty("logradouro")
    private String logradouro;

    @NotEmpty
    @JsonProperty("property_number")
    private String propertyNumber;

    @JsonProperty("complement")
    private String complement;
}
