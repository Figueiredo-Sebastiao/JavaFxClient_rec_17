package lp.JavaFxClient.services;

	import com.fasterxml.jackson.core.type.TypeReference;
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
	import java.net.URI;
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
	public String post(String path, Object bodyObject) {
		try {
			String json = mapear.writeValueAsString(bodyObject);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + path))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.build();
			return cliente.send(request,
					HttpResponse.BodyHandlers.ofString()).body();
		} catch (Exception e) {

			return "ERROR: " + e.getMessage();
		}
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

}
