@Service
public class ChatGptService {

    private final String apiKey = "YOUR_OPENAI_API_KEY";

    public String generateRecipe(List<String> ingredients) throws IOException, InterruptedException {
        String prompt = "以下の食材で作れる料理を1つ教えてください: " + String.join(", ", ingredients);

        String body = """
        {
          "model": "gpt-3.5-turbo",
          "messages": [{"role": "user", "content": "%s"}]
        }
        """.formatted(prompt);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // JSONから抽出（簡易）
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        return root.path("choices").get(0).path("message").path("content").asText();
    }
}
