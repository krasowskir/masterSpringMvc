package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RechnungenController {

    @RequestMapping("/rechnungen")
    public List getRechnungen(Model model) {
	List<Float> rechnungen = new ArrayList<>();
	rechnungen.add((float) 3.44);
	rechnungen.add((float) 13.50);
	rechnungen.add((float) 15.99);
	rechnungen.add((float) 18.30);
	rechnungen.add((float) 19.69);
	rechnungen.add((float) 21.55);

	return rechnungen;
    }

}
