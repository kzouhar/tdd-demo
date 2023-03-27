package utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
        wireMockServer.start();

        configurableApplicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

        TestPropertyValues properties = TestPropertyValues
                .of(
                "microservices.makePayment.endpoint:http://localhost:" + wireMockServer.port() + "/makepay/v1/remote_action/account/{accountAlias}/payment",
                        "microservices.paymentHistory.endpoint:http://localhost:" + wireMockServer.port() + "/paymthist/v1/local_data/account/{accountAlias}/payment",
                        "microservices.bills.endpoint:http://localhost:" + wireMockServer.port() + "/rogers_rest/rogers/documents/activatedUserSummary",
                        "microservices.autopay.endpoint:http://localhost:" + wireMockServer.port() + "/v1/remote_data/promo/qualification/promotions?promoGroup=AUTOPAY&lob=WL",
                        "microservices.order.endpoint:http://localhost:" + wireMockServer.port() + "/v1/remote_action/services/promo/deploy",
                        "microservices.updatePaymentMethod.endpoint:http://localhost:" + wireMockServer.port() + "/paymethod/v1/remote_action/account/{accountAlias}/paymentMethod",
                        "middleware.identity_access_service.validate_token_url:http://localhost:" + wireMockServer.port() + "/api/identity/v1/user/oidcSession",
                        "microservices.createptp.endpoint:http://localhost:" + wireMockServer.port() + "/promisepay/v1/remote_action/account/{accountAlias}/promiseToPay/create",
                        "microservices.getptp.endpoint:http://localhost:" + wireMockServer.port() + "/promisepay/v1/remote_action/account/{accountAlias}/promiseToPay/eligibility",
                        "microservices.reportpayment.endpoint:http://localhost:" + wireMockServer.port() + "/promisepay/v1/remote_action/account/{accountAlias}/promiseToPay/reportPayment",
                        "middleware.fraud.endpoint:http://localhost:" + wireMockServer.port() + "/",
                        "microservices.interaction.endpoint:http://localhost:" + wireMockServer.port() + "/interaction/v1/remote_action/account/{accountAlias}/interaction",
                        "middleware.getCustomerPreference.endpoint:http://localhost:" + wireMockServer.port() + "/UTE/eif/rest/Customers/CustomerPreference/GetCustomerPreferenceList",
                        "middleware.updateCustomerPreference.endpoint:http://localhost:" + wireMockServer.port() + "/UTE/eif/rest/Customers/CustomerPreference/UpdateCustomerPreferenceList",
                        "spring.security.oauth2.client.provider.azure-ad.token-uri:http://localhost:" + wireMockServer.port() + "/0ab4cbbf-4bc7-4826-b52c-a14fed5286b9/oauth2/v2.0/token"

                );

        properties.applyTo(configurableApplicationContext);

    }
}
