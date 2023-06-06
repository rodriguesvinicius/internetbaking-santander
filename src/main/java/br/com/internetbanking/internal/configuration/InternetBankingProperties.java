package br.com.internetbanking.internal.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = InternetBankingConstants.PROPERTIES_PREFIX)
public class InternetBankingProperties {
}
