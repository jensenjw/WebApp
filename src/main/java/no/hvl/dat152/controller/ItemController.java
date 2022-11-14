package no.hvl.dat152.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import no.hvl.dat152.model.Item;
import no.hvl.dat152.repositories.ItemDAOMemorySingleton;
import service.ItemService;

@Controller
public class ItemController {
	
	
	@Autowired
	private ItemService _itemService;
	
	public ItemController() {
		_itemService = new ItemService();
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewShoppingDefault() {
		return "index";
    }
	
	@RequestMapping(value = "/viewitems", method = RequestMethod.GET)
    public String viewShoppingList(Model model) {
		
		final List<Item> items = _itemService.getAll();
		
		model.addAttribute("items", items);
		
		return "shoppinglist";
        
    }
	
	@RequestMapping(value = "/viewitem/{id}", method = RequestMethod.GET)
    protected String viewItem(@PathVariable String id, Model model) {

        final Item item = ItemDAOMemorySingleton.getInstance().findItem(id);
        model.addAttribute("item", item);

        return "viewitem";
    }
	
	@RequestMapping(value = "/createitem", method = RequestMethod.GET)
    protected String createItem(Model model) {

        final String id = ItemDAOMemorySingleton.getInstance().getNextId();
        
        Item item = new Item(id);
        
        model.addAttribute("item",item);
        
        return "createitem";
    }
	
	@RequestMapping(value = "/createitem", method = RequestMethod.POST)
    protected String createItem(@RequestParam String id, @RequestParam String name, 
    		                    @RequestParam Double price, @RequestParam String description) {

		final Item newItem = new Item(id, name, price, description);
		
		ItemDAOMemorySingleton.getInstance().createItem(newItem); 
        
        return "redirect:viewitems";
    }
	
	@RequestMapping(value = "/deleteitem/{id}", method = RequestMethod.GET)
	protected String deleteItem(@PathVariable String id, Model model) {
		Optional<Item> item = Optional.of(ItemDAOMemorySingleton.getInstance().findItem(id));
		
		model.addAttribute(item.get());
		
		return "deleteitem";
	}
	
	@RequestMapping(value = "/deleteitemsave/{id}", method = RequestMethod.GET)
	protected String deleteItemSave(@PathVariable String id, Model model) {
		ItemDAOMemorySingleton.getInstance().deleteItem(id);
		
		return "redirect:/viewitems";
	}
	
	@RequestMapping(value = "/updateitem/{id}", method = RequestMethod.GET)
	protected String updateItem(@PathVariable String id, Model model) {
		Optional<Item> item = Optional.of(ItemDAOMemorySingleton.getInstance().findItem(id));
		
		if (item.isEmpty()) {
			return "redirect:/viewitems";
		}
		
		model.addAttribute(item.get());
		
		return "createitem";
	}
	
}
