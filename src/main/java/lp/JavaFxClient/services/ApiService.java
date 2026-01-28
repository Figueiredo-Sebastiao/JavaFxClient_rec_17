//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lp.JavaFxClient.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ApiService {
	private static final String BASE_URL = "http://localhost:8080";
	private final HttpClient cliente = HttpClient.newHttpClient();
	private final ObjectMapper mapear = (new ObjectMapper()).registerModule(new JavaTimeModule());

	public <TipoD> TipoD get(String path, TypeReference<TipoD> tipo) throws Exception {
		HttpRequest pedido = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080" + path)).GET().build();
		HttpResponse<String> reposta = this.cliente.send(pedido, BodyHandlers.ofString());
		if (reposta.statusCode() >= 400) {
			throw new RuntimeException((String)reposta.body());
		} else {
			return (TipoD)this.mapear.readValue((String)reposta.body(), tipo);
		}
	}

	public String delete(String path) {
		try {
			HttpRequest pedido = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080" + path)).DELETE().build();
			return (String)this.cliente.send(pedido, BodyHandlers.ofString()).body();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	public String post(String path, Object bodyObject) {
		try {
			String json = this.mapear.writeValueAsString(bodyObject);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080" + path)).header("Content-Type", "application/json").POST(BodyPublishers.ofString(json)).build();
			return (String)this.cliente.send(request, BodyHandlers.ofString()).body();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	public String put(String path, Object bodyObject) {
		try {
			String json = this.mapear.writeValueAsString(bodyObject);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080" + path)).header("Content-Type", "application/json").PUT(BodyPublishers.ofString(json)).build();
			return (String)this.cliente.send(request, BodyHandlers.ofString()).body();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}
}
