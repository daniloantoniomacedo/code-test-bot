package br.ucsal.discordadapterapi.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.response.LoginResponse;
import br.ucsal.discordadapterapi.util.Constantes;

@Service
public class TokenService {
	
	@Value("${expiration-token-in-mili-sec}")
	private long expirationTokenInMiliSec;

	@Autowired
	private CodeTestApiClientService codeTestApiClient;

	@Autowired
	private RedisService<String, String> redisService;

	public String obterToken() throws BusinessException {
		String token = redisService.getValue(Constantes.TOKEN);
		
		if (Objects.isNull(token)) {
			LoginResponse loginResponse = codeTestApiClient.login();
			redisService.setValueIfAbset(Constantes.TOKEN, loginResponse.getToken(), expirationTokenInMiliSec);
			System.out.println("TOKEN " + loginResponse.getToken());
			return loginResponse.getToken();
		} else {
			return token;
		}

	}

}
