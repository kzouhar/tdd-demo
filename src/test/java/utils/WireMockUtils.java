package utils;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockUtils {

    public static void mockMakePaymentForWeb(WireMockServer wireMockServer) throws IOException {
            wireMockServer.stubFor(
                    post(urlEqualTo("/v1/payment")).
                            withHeader("transaction-id", equalTo("111111111")).
                            withHeader("channel", equalTo("web")).
                            withRequestBody(equalToJson(IOUtils.readAndTrimTestFileContent("data/providers/payment/provider_payment_web_request.json"))).
                            willReturn(aResponse().withStatus(200).
                                    withBody(String.format(
                                                    IOUtils.readAndTrimTestFileContent("data/providers/payment/provider_payment_web_response.json")
                                            )
                                    ).
                                    withHeader("Content-Type", "application/json")
                            )
            );
    }

    public static void mockMakePaymentForMobile(WireMockServer wireMockServer) throws IOException {
        wireMockServer.stubFor(
                post(urlEqualTo("/v1/payment")).
                        withHeader("transaction-id", equalTo("111111112")).
                        withHeader("channel", equalTo("web")).
                        withRequestBody(equalToJson(IOUtils.readAndTrimTestFileContent("data/providers/payment/provider_payment_mobile_request.json"))).
                        willReturn(aResponse().withStatus(200).
                                withBody(String.format(
                                                IOUtils.readAndTrimTestFileContent("data/providers/payment/provider_payment_mobile_response.json")
                                        )
                                ).
                                withHeader("Content-Type", "application/json")
                        )
        );
    }

    public static void mockMakePaymentForBot(WireMockServer wireMockServer) throws IOException {
        wireMockServer.stubFor(
                post(urlEqualTo("/v1/payment")).
                        withHeader("transaction-id", equalTo("111111113")).
                        withHeader("channel", equalTo("bot")).
                        withRequestBody(equalToJson(IOUtils.readAndTrimTestFileContent("data/consumers/data/providers/payment/provider_payment_bot_request.json"))).
                        willReturn(aResponse().withStatus(200).
                                withBody(String.format(
                                                IOUtils.readAndTrimTestFileContent("data/consumers/data/providers/payment/provider_payment_bot_response.json")
                                        )
                                ).
                                withHeader("Content-Type", "application/json")
                        )
        );
    }

    public static void mockMakePaymentInvalidCC(WireMockServer wireMockServer) throws IOException {
        wireMockServer.stubFor(
                post(urlEqualTo("/v1/payment")).
                        withHeader("transaction-id", equalTo("111111114")).
                        withHeader("channel", equalTo("bot")).
                        withRequestBody(equalToJson(IOUtils.readAndTrimTestFileContent("data/providers/payment/payment_provider_invalid_cc_request.json"))).
                        willReturn(aResponse().withStatus(200).
                                withBody(String.format(
                                                IOUtils.readAndTrimTestFileContent("data/providers/payment/provider_payment_invalid_cc_response.json")
                                        )
                                ).
                                withHeader("Content-Type", "application/json")
                        )
        );
    }

    public static void mockRecordPaymentForWeb(WireMockServer wireMockServer) throws IOException {
        wireMockServer.stubFor(
                post(urlEqualTo("/v1/customer/activities")).
                        withHeader("transaction-id:-id", equalTo("11111111")).
                        withRequestBody(equalToJson(IOUtils.readAndTrimTestFileContent("data/providers/customer_activities/customer_activities_web_request.json"))).
                        willReturn(aResponse().withStatus(200))
        );
    }

    public static void mockRecordPaymentForMobile(WireMockServer wireMockServer) throws IOException {
        wireMockServer.stubFor(
                post(urlEqualTo("/v1/customer/activities")).
                        withHeader("transaction-id:-id", equalTo("11111112")).
                        withRequestBody(equalToJson(IOUtils.readAndTrimTestFileContent("data/providers/customer_activities/customer_activities_web_request.json"))).
                        willReturn(aResponse().withStatus(200))
        );
    }

    public static void mockRecordPaymentForChat(WireMockServer wireMockServer) throws IOException {
        wireMockServer.stubFor(
                post(urlEqualTo("/v1/customer/activities")).
                        withHeader("transaction-id:-id", equalTo("11111113")).
                        withRequestBody(equalToJson(IOUtils.readAndTrimTestFileContent("data/providers/customer_activities/customer_activities_web_request.json"))).
                        willReturn(aResponse().withStatus(200))
        );
    }

}
