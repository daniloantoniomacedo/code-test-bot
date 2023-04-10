package br.ucsal.discordadapterapi.http.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.model.Tarefa;
import br.ucsal.discordadapterapi.to.request.LoginRequest;
import br.ucsal.discordadapterapi.to.response.LoginResponse;
import br.ucsal.discordadapterapi.util.Constantes;
import br.ucsal.discordadapterapi.util.JsonUtil;

@Service
public class CodeTestApiClientService {

	@Value("${base-url-code-test}")
	private String baseUrl;

	@Value("${usuario-code-test-api}")
	private String usuario;

	@Value("${senha-code-test-api}")
	private String senha;

	public LoginResponse login() throws BusinessException {
		LoginRequest loginRequest = new LoginRequest(usuario, senha);
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/login/")))
					.POST(BodyPublishers.ofString(JsonUtil.toJson(loginRequest)))
					.header("Content-Type", "application/json").build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			if (Objects.nonNull(response) && response.statusCode() == HttpStatus.OK.value()) {
				return JsonUtil.fromJson(response.body(), LoginResponse.class);
			} else {
				throw new BusinessException(Constantes.MSG_ERRO_LOGIN);
			}

		} catch (IOException | InterruptedException e) {
			throw new BusinessException(Constantes.MSG_ERRO_LOGIN.concat(e.getMessage()), e);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Tarefa> obterTarefas(String token) throws BusinessException {

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/tarefa/"))).GET()
					.header("Content-Type", "application/json").header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (Objects.nonNull(response) && response.statusCode() == HttpStatus.OK.value()) {
				return JsonUtil.fromJson(response.body(), List.class, Tarefa.class);
			} else {
				throw new BusinessException(Constantes.MSG_ERRO_OBTER_TAREFAS);
			}

		} catch (IOException | InterruptedException e) {
			throw new BusinessException(Constantes.MSG_ERRO_OBTER_TAREFAS.concat(e.getMessage()), e);
		}

	}
	
	public Tarefa obterTarefaPeloId(Long id, String token) throws BusinessException {

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/tarefa/" + id))).GET()
					.header("Content-Type", "application/json").header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (Objects.nonNull(response) && response.statusCode() == HttpStatus.OK.value()) {
				return JsonUtil.fromJson(response.body(), Tarefa.class);
			} else {
				throw new BusinessException(String.format(Constantes.MSG_ERRO_OBTER_TAREFA, id));
			}

		} catch (IOException | InterruptedException e) {
			throw new BusinessException(String.format(Constantes.MSG_ERRO_OBTER_TAREFA, id).concat(e.getMessage()), e);
		}

	}

}
