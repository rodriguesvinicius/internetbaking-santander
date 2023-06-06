package br.com.internetbanking.configuration;

import br.com.internetbanking.internal.configuration.InternetBankingConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(InternetBankingConfiguration.class)
public class InternetBankingTestConfiguration {

}
