package br.ucsal.discordadapterapi.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.response.LoginResponse;

@Service
public class TokenService {
	
	@Value("${expiration-token-in-mili-sec}")
	private long expirationTokenInMiliSec;

	@Autowired
	private CodeTestApiClientService codeTestApiClient;

	private LocalDateTime dataHoraGeracaoToken;
	
	private String token;

	public String obterToken() throws BusinessException {
		if (isTokenExpirado()) {
			LoginResponse loginResponse = codeTestApiClient.login();
			token = loginResponse.getToken();
			dataHoraGeracaoToken = LocalDateTime.now();
			System.out.println("TOKEN " + loginResponse.getToken());
		}
		return token;
	}
	
	private boolean isTokenExpirado() {
		if(Objects.isNull(token) || Objects.isNull(dataHoraGeracaoToken)) {
			return true;
		}
		if(Duration.between(dataHoraGeracaoToken, LocalDateTime.now()).toMillis() <= expirationTokenInMiliSec) {
			return false;
		}
		return true;
	}

}
