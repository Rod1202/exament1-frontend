package com.edu.cibertec.examen_frontend_b.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.cibertec.examen_frontend_b.viewmodel.PlacaModel;



@Controller
@RequestMapping("/index")
public class PlacaController {
    @GetMapping("/inicio")
    public String index(Model model) {
        PlacaModel placaModel = new PlacaModel("00","","");
        model.addAttribute("placaModel", placaModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar( @RequestParam("placa") String placa,Model model) {
        String regex = "[A-Za-z]{4}[0-9]{4}";
        if(placa == null || placa.trim().isEmpty() || placa.length() != 8 || !placa.matches(regex)) {
            PlacaModel placaModel = new PlacaModel("01","Error: Placa Invalida","");
            model.addAttribute("placaModel", placaModel);
            return "inicio";
        }
        PlacaModel placaModel = new PlacaModel("00","","");
        model.addAttribute("placaModel", placaModel);
        return "principal";
    }
}
