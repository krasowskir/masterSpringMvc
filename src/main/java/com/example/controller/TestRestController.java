package com.example.controller;


import com.example.user.Richard;
import com.example.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestRestController {

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("/user/richard")
    @ResponseBody
    public User getUserRichard(){

        User richard = new User();
        richard.setDate(LocalDate.now());
        richard.setEmail("krasowslki.richard91@gmail.com");
        List<String> geschmaecker = new ArrayList<>();
        geschmaecker.add("Honigmelone");
        geschmaecker.add("Bananen");
        geschmaecker.add("Äpfel");

        Richard nutzer = new Richard();
        nutzer.setTelefonNummer(151404608);
        nutzer.setGeburtsName("Vasili");
        richard.setTastes(geschmaecker);
        richard.setRichard(nutzer);

        try {
            System.out.println(objectMapper.writeValueAsString(richard));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return richard;
    }
}
