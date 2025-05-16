@Service
public class YoloService {
    public List<String> detectIngredients(MultipartFile file) throws IOException {
        HttpClient client = HttpClient.newHttpClient();

        // ファイルをバイナリ送信（YOLO APIは自前で作っておく）
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/yolo"))
            .header("Content-Type", "application/octet-stream")
            .POST(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<List<String>>() {});
    }
}
