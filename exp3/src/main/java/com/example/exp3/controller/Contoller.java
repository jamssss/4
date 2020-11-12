package com.example.exp3.controller;

import com.example.exp3.model.RegionStates;
import com.example.exp3.service.CsvDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class Contoller {
    @Resource
    private final CsvDataService data;

    @RequestMapping("/{cty}")
    public String home(Model model, @PathVariable("cty") String country) {
        List<RegionStates> allStats;
        System.out.println("123");
        if(country == null || country.equals("all"))
            allStats=data.getAllRegionstats();

        else
            allStats=data.getAllRegionstats()
                    .parallelStream()
                    .filter(RegionStates->RegionStates.getCountry().equals(country))
                    .collect(Collectors.toList());

        model.addAttribute("RegionStatesLists", allStats);
        return "show";
    }
}
