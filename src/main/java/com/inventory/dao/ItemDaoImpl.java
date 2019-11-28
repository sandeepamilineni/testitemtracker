package com.inventory.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.model.Item;

@Component
public class ItemDaoImpl implements ItemDao {

	@Override
	public List<Item> list() {
		return getItemList();
	}

	@Override
	public void save(Item item) {
		storeInJsonDb(item);

	}

	public static void storeInJsonDb(Item item) {
		Path jsonFilePath = Paths.get("JsonDB.json");

		ObjectMapper mapper = new ObjectMapper();
		try {
			String newItem = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);
			boolean fileExists = jsonFilePath.toFile().exists();

			if (!fileExists) {
				String bracketString = '[' + newItem + ']';
				Files.write(jsonFilePath, bracketString.getBytes());
				return;
			}
			String existingJson = new String(Files.readAllBytes(Paths.get("JsonDB.json")));

			String modifiedJson = existingJson.replace("]", ",").concat(newItem).concat("]");

			Files.write(jsonFilePath, modifiedJson.getBytes(), StandardOpenOption.WRITE);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Item> getItemList() {
		ObjectMapper mapper = new ObjectMapper();
		List<Item> participantJsonList = null;
		try {

			String json = new String(Files.readAllBytes(Paths.get("JsonDB.json")));

			participantJsonList = mapper.readValue(json, new TypeReference<List<Item>>() {
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

		return participantJsonList;
	}

}
