@Controller
public class FridgeController {

    @Autowired
    private YoloService yoloService;
    @Autowired
    private ChatGptService chatGptService;

    @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        List<String> ingredients = yoloService.detectIngredients(file);
        String recipe = chatGptService.generateRecipe(ingredients);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("recipe", recipe);
        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
