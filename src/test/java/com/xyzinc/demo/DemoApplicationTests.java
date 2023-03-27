package com.xyzinc.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utils.IOUtils;
import utils.WireMockInitializer;
import utils.WireMockUtils;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Make Payment Tests")
@ContextConfiguration(initializers = {WireMockInitializer.class})
class DemoApplicationTests {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private WireMockServer wireMockServer;
    @BeforeEach
    void contextLoads() {
        mockMvc = MockMvcBuilders.
                webAppContextSetup(context).
                build();
    }

    @DisplayName("")
    @Test
    public void makePaymentWeb() throws Exception{

        WireMockUtils.mockMakePaymentForWeb(wireMockServer);
        WireMockUtils.mockRecordPaymentForWeb(wireMockServer);
        String makePaymentWeb = IOUtils.readAndTrimTestFileContent("data/consumers/consumer_payment_web_request.json");

        MvcResult result = mockMvc.
                perform(
                        post("/v1/payment").
                                header("transaction-id", "1111").
                                header("channel", "Rogers.com").
                                content(makePaymentWeb).
                                contentType(MediaType.APPLICATION_JSON)
                ).
                andReturn();

        mockMvc.perform(asyncDispatch(result)).
                andExpect(status().isOk()).
                andExpect(jsonPath("reference", is("1234")));

    }


}
