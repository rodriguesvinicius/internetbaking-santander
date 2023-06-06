package br.com.internetbanking.internal.configuration;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(InternetBankingConstants.APP_BASE_PACKAGE)
@EnableConfigurationProperties(InternetBankingProperties.class)
public class InternetBankingConfiguration {
}
