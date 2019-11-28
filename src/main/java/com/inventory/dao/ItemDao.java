package com.inventory.dao;

import java.util.List;

import com.inventory.model.Item;

public interface ItemDao {
   void save(Item user);
   List<Item> list();
}
