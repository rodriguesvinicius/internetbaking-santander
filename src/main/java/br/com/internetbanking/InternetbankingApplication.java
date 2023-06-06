package br.com.internetbanking;

import br.com.internetbanking.internal.configuration.InternetBankingConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class InternetbankingApplication {

	public static void main(String... args) {
		SpringApplication.run(InternetbankingApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(InternetBankingConstants.DEFAULT_TIME_ZONE));
	}
}
