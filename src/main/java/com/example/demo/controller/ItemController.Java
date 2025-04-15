@Controller  
public class ItemController {  
    @Autowired  
    private ItemService itemService;  

    @PostMapping("/items/{id}/price")  
    public String updatePrice(  
        @PathVariable Long id,  
        @RequestParam BigDecimal price,  
        RedirectAttributes redirectAttributes) {  

        itemService.updatePrice(id, price);  
        redirectAttributes.addFlashAttribute("message", "価格を更新しました");  
        return "redirect:/items";  
    }  

    @PostMapping("/items/{id}/stock")  
    public String updateStock(  
        @PathVariable Long id,  
        @RequestParam Integer quantity,  
        RedirectAttributes redirectAttributes) {  

        itemService.updateStock(id, quantity);  
        redirectAttributes.addFlashAttribute("message", "在庫数を更新しました");  
        return "redirect:/items";  
    }  
    @GetMapping("/items")  
    public String list(Model model) {  
        model.addAttribute("items", itemService.findAll());  
        return "item/list";  // templates/item/list.html を参照  
    }  
}  