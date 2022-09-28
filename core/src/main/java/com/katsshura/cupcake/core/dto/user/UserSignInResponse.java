package com.katsshura.cupcake.core.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignInResponse {

    @NotBlank
    @JsonProperty("access_token")
    private String accessToken;

    @NotBlank
    @JsonProperty("token_type")
    private String tokenType;
}
