package com.inventory.service;

import java.nio.file.Path;
import java.util.List;

import com.inventory.model.Item;

public interface ItemService {
   void save(Item user);

   List<Item> list();
   
  void csvItemList(Path location);
}
