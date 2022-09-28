package com.katsshura.cupcake.api.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.katsshura.cupcake.api.config.ControllerTestsConfiguration;
import com.katsshura.cupcake.api.config.aggregator.UserDtoAggregator;
import com.katsshura.cupcake.core.dto.user.UserDTO;
import com.katsshura.cupcake.core.services.user.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTestsConfiguration
public class UserControllerTest {

    private static final String REQUEST_URL = "/user";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeAll
    static void setup() {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @ParameterizedTest(name = "#[{index}] Should assert status code 200 for valid user!")
    @CsvFileSource(resources = "/csv/user/UserInputValidData.csv", numLinesToSkip = 1)
    void shouldCreateUser(@AggregateWith(UserDtoAggregator.class) UserDTO user) throws Exception {
        final var content = new JSONObject(MAPPER.writeValueAsString(user));
        content.put("password", user.getPassword());

        when(userService.persistUser(user)).thenReturn(user);

        mockMvc.perform(post(REQUEST_URL)
                .content(content.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated());
    }

    @ParameterizedTest(name = "#[{index}] Should assert status code 400 for invalid user!")
    @CsvFileSource(resources = "/csv/user/UserInputInvalidData.csv", numLinesToSkip = 1)
    void shouldNotCreateUser(@AggregateWith(UserDtoAggregator.class) UserDTO user) throws Exception {
        mockMvc.perform(post(REQUEST_URL)
                .content(MAPPER.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isBadRequest());
    }
}
