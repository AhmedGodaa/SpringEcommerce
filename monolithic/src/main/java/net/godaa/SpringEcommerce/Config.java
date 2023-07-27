package net.godaa.SpringEcommerce;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.time.Duration;

import static net.godaa.SpringEcommerce.security.jwt.SecurityConstants.BASE_URL;

@Component
public class Config {
    @Bean
    public static RestTemplate restTemplate(RestTemplateBuilder builder) {
        UriTemplateHandler uriTemplateHandler = new RootUriTemplateHandler(BASE_URL);
        return builder
                .uriTemplateHandler(uriTemplateHandler)
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }

    @Bean
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

//    public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
//        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setHttpClient(httpClient);
//        return new RestTemplate(requestFactory);
//    }
}
