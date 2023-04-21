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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.to.request.CadastroUsuarioRequest;
import br.ucsal.discordadapterapi.to.request.LoginRequest;
import br.ucsal.discordadapterapi.to.request.RespostaRequest;
import br.ucsal.discordadapterapi.to.response.LoginResponse;
import br.ucsal.discordadapterapi.to.response.ResultadoTarefaResponse;
import br.ucsal.discordadapterapi.to.response.TarefaResponse;
import br.ucsal.discordadapterapi.to.response.UsuarioResponse;
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
				System.err.println(Constantes.MSG_ERRO_LOGIN + Constantes.HTTP_STATUS_CODE + response.statusCode());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println(Constantes.MSG_ERRO_LOGIN + e.getMessage());
			e.printStackTrace();
		}
		
		throw new BusinessException(Constantes.MSG_ERRO_LOGIN);
		
	}

	@SuppressWarnings("unchecked")
	public Optional<List<UsuarioResponse>> obterListaUsuarios(String token) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/usuarios"))).GET()
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			if (response.statusCode() == HttpStatus.OK.value()) {
				return Optional.of(JsonUtil.fromJson(response.body(), List.class, UsuarioResponse.class));
			} else {
				System.err.println(Constantes.MSG_ERRO_OBTER_USUARIOS + Constantes.HTTP_STATUS_CODE + response.statusCode());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println(Constantes.MSG_ERRO_OBTER_USUARIOS + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public Optional<UsuarioResponse> cadastrarUsuario(CadastroUsuarioRequest cadastroRequest, String token) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/usuarios/")))
					.POST(HttpRequest.BodyPublishers.ofString(JsonUtil.toJson(cadastroRequest)))
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			if (response.statusCode() == HttpStatus.OK.value()) {
				return Optional.of(JsonUtil.fromJson(response.body(), UsuarioResponse.class));
			} else {
				System.err.println(Constantes.MSG_ERRO_CADASTRAR_USUARIO + Constantes.HTTP_STATUS_CODE + response.statusCode());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println(Constantes.MSG_ERRO_CADASTRAR_USUARIO + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	public List<TarefaResponse> obterTarefas(String token) throws BusinessException {

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/tarefa/"))).GET()
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			if (Objects.nonNull(response) && response.statusCode() == HttpStatus.OK.value()) {
				return JsonUtil.fromJson(response.body(), List.class, TarefaResponse.class);
			} else {
				System.err.println(Constantes.MSG_ERRO_OBTER_TAREFAS + Constantes.HTTP_STATUS_CODE + response.statusCode());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println(Constantes.MSG_ERRO_OBTER_TAREFAS + e.getMessage());
			e.printStackTrace();
		}
		
		throw new BusinessException(Constantes.MSG_ERRO_OBTER_TAREFAS);

	}

	public TarefaResponse obterTarefaPeloId(Long id, String token) throws BusinessException {

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/tarefa/" + id))).GET()
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			if (Objects.nonNull(response) && response.statusCode() == HttpStatus.OK.value()) {
				return JsonUtil.fromJson(response.body(), TarefaResponse.class);
			} else {
				System.err.println(String.format(Constantes.MSG_ERRO_OBTER_TAREFA, id) + Constantes.HTTP_STATUS_CODE + response.statusCode());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println(String.format(Constantes.MSG_ERRO_OBTER_TAREFA, id) + e.getMessage());
			e.printStackTrace();
		}
		
		throw new BusinessException(String.format(Constantes.MSG_ERRO_OBTER_TAREFA, id));

	}

	public Optional<ResultadoTarefaResponse> enviarResposta(RespostaRequest respostaRequest, String token) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/respostas/")))
					.POST(HttpRequest.BodyPublishers.ofString(JsonUtil.toJson(respostaRequest)))
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			if (response.statusCode() == HttpStatus.OK.value()) {
				return Optional.of(JsonUtil.fromJson(response.body(), ResultadoTarefaResponse.class));
			} else {
				System.err.println(Constantes.MSG_ERRO_ENVIAR_RESPOSTA + Constantes.HTTP_STATUS_CODE + response.statusCode());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println(Constantes.MSG_ERRO_ENVIAR_RESPOSTA + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public Optional<ResultadoTarefaResponse> obterResultadoPorId(Long id, String token) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl.concat("api/resultados/" + id))).GET()
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + token).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			if (response.statusCode() == HttpStatus.OK.value()) {
				return Optional.of(JsonUtil.fromJson(response.body(), ResultadoTarefaResponse.class));
			} else {
				System.err.println(Constantes.MSG_ERRO_OBTER_RESPOSTA + Constantes.HTTP_STATUS_CODE + response.statusCode());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println(Constantes.MSG_ERRO_OBTER_RESPOSTA + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
