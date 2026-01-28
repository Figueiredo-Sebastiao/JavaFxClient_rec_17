package lp.JavaFxClient.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
	private static final String BASE_URL = "http://localhost:8080";
	private final HttpClient cliente = HttpClient.newHttpClient();
	private final ObjectMapper mapear = new ObjectMapper().registerModule(new JavaTimeModule());

	// ----------------------
	// GET
	// ----------------------
	public <TipoD> TipoD get(String path, TypeReference<TipoD> tipo) throws Exception {
		HttpRequest pedido = HttpRequest.newBuilder().uri(URI.create(BASE_URL + path)).GET().build();

		HttpResponse<String> reposta = cliente.send(pedido, HttpResponse.BodyHandlers.ofString());

		if (reposta.statusCode() >= 400) {
			throw new RuntimeException(reposta.body());
		}

		return mapear.readValue(reposta.body(), tipo);
	}

	// ----------------------
	// DELETE
	// ----------------------
	public String delete(String path) {
		try {
			HttpRequest pedido = HttpRequest.newBuilder().uri(URI.create(BASE_URL + path)).DELETE().build();
			return cliente.send(pedido,HttpResponse.BodyHandlers.ofString()).body();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}


	// ----------------------
	// POST (aceita Objects)
	// ----------------------
	public void post(String path, Object bodyObject) throws Exception {
		String json = mapear.writeValueAsString(bodyObject);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + path))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response =
				cliente.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() >= 400) {
			throw new RuntimeException(response.body());
		}
	}


	public <T> T post(String endpoint, Object body, Class<T> responseType) throws IOException, InterruptedException {
		String json = mapear.writeValueAsString(body);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + endpoint))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() >= 400) {
			throw new RuntimeException("Erro no servidor: " + response.body());
		}

		return mapear.readValue(response.body(), responseType);
	}

	// ----------------------
	// PUT (aceita Objects)
	// ----------------------
	public String put(String path, Object bodyObject) {
		try {
			String json = mapear.writeValueAsString(bodyObject);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + path))
					.header("Content-Type", "application/json")
					.PUT(HttpRequest.BodyPublishers.ofString(json))
					.build();
			return cliente.send(request,
					HttpResponse.BodyHandlers.ofString()).body();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	public void post(String path) throws Exception {
		URL url = new URL(BASE_URL + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setDoOutput(false); // 🔥 IMPORTANTE
		conn.connect();

		int responseCode = conn.getResponseCode();

		if (responseCode >= 400) {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getErrorStream())
			);
			StringBuilder error = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				error.append(line);
			}
			br.close();
			throw new RuntimeException("Erro HTTP " + responseCode + ": " + error);
		}

		conn.disconnect();
	}



}
