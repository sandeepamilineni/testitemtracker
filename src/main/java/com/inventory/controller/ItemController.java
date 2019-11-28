package com.inventory.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inventory.model.Item;
import com.inventory.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@GetMapping("/")
	public String itemEntryForm(Locale locale, Model model) {
		model.addAttribute("items", itemService.list());
		return "editItems";
	}
	
	@ModelAttribute("item")
    public Item formBackingObject() {
        return new Item();
    }

	@PostMapping("/addItem")
	public String saveUser(@ModelAttribute("item") @Valid Item item, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("items", itemService.list());
			return "editItems";
		}

		itemService.save(item);
		return "redirect:/";
	}
	
	@RequestMapping("/csv/{fileName:.+}")
    public void downloadCSVResource( HttpServletRequest request, 
                                     HttpServletResponse response, 
                                     @PathVariable("fileName") String fileName) 
    {
         
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/downloads/csv/");
        Path file = Paths.get(dataDirectory, fileName);
        itemService.csvItemList(file);
        if (Files.exists(file)) 
        {
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
