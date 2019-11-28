package com.inventory.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.dao.ItemDao;
import com.inventory.model.Item;

@Service
public class ItemServiceImpl implements ItemService {

   @Autowired
   private ItemDao userDao;

  
   public void save(Item user) {
      userDao.save(user);
   }

   
   public List<Item> list() {
      return userDao.list();
   }
   
   public void csvItemList(Path fileLocation) {
	   try {
		   Files.deleteIfExists(fileLocation);
		PrintWriter csvWriter = new PrintWriter(fileLocation.toFile());
		list().stream().forEach(item->convertToCSV(item, csvWriter));
		csvWriter.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	      
   }
   
    void convertToCSV(Item item, PrintWriter csvWriter) {
    	String itemName = item.getName();
    	String serialNumber = item.getSerialNumber();
    	BigDecimal price = item.getPrice();
    	csvWriter.println(itemName+","+serialNumber+","+price);
   }

   
}
