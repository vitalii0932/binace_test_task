package com.example.binance_task.controller;

import com.example.binance_task.repository.SymbolRepository;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * controller for loading the home page
 */
@Data
@Controller
@RequestMapping("/api/v1/binance")
public class MainPageLoadingController {

    private final SymbolRepository symbolRepository;

    /**
     * load main page function
     *
     * @param model - the model object used to pass data to the view
     * @return the view for the main page
     */
    @GetMapping
    public String loadMainPage(Model model) {
        model.addAttribute("symbols", symbolRepository.findAll());
        return "index";
    }
}
