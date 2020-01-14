package com.service.controller;


import com.service.ServiceApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("Duplicates")
@CrossOrigin
@RestController
@RequestMapping("/api/app")
public class AppControler {

    @GetMapping("/getStadiumSectors")
    public String[] getStadiumSectors() {
        return ServiceApplication.stadiumState.getFullSectorNamesArray();
    }
}
