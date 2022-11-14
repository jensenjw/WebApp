package no.hvl.dat152.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import no.hvl.dat152.model.Item;

@Service
@PropertySource("classpath:application.properties")
public class ItemService {

	@Value("${api.url.viewitems")
	private String BASE_URL;
	
	@Autowired
	RestTemplate template;
	
	public List<Item> getAll() {
		ResponseEntity<Item[]> response = template.getForEntity(BASE_URL, Item[].class);
		return Arrays.asList(response.getBody());
	}
}