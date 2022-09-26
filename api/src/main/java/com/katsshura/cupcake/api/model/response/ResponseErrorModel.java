package com.katsshura.cupcake.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseErrorModel {
    @JsonIgnoreProperties(value = {"stackTrace", "cause", "suppressed", "localizedMessage", "message"}, allowSetters = true)
    @JsonProperty("errorReason")
    private final Exception reason;
}
