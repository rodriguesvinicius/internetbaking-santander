package br.com.internetbanking.internal.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InternetBankingConstants {

    public final String PROPERTIES_PREFIX = "internetbanking";

    public final String APP_BASE_PACKAGE = "br.com." + PROPERTIES_PREFIX;

    public final String DEFAULT_TIME_ZONE = "America/Sao_Paulo";

    public final String API_VERSION_V1 = "/api/v1";

}
