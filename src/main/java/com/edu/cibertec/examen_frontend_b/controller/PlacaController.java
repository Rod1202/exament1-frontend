package com.edu.cibertec.examen_frontend_b.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.edu.cibertec.examen_frontend_b.dto.PlacaRequestDTO;
import com.edu.cibertec.examen_frontend_b.dto.PlacaResponseDTO;
import com.edu.cibertec.examen_frontend_b.viewmodel.PlacaModel;



@Controller
@RequestMapping("/index")
public class PlacaController {

    @Autowired
    RestTemplate restTemplate;
    
    @GetMapping("/inicio")
    public String index(Model model) {
        PlacaModel placaModel = new PlacaModel("00","","");
        model.addAttribute("placaModel", placaModel);
        return "inicio";
    }

    
    @SuppressWarnings({ "null" })
    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("placa") String placa,Model model) {
        String regex = "[A-Za-z]{3}-[0-9]{3}";
        if(placa == null || placa.trim().isEmpty() || placa.length() != 7 || !placa.trim().matches(regex)) {
            PlacaModel placaModel = new PlacaModel("01","Error: Placa Invalida","");
            model.addAttribute("placaModel", placaModel);
            return "inicio";
        }
        try {
            String endpoint = "http://localhost:8081/picoplaca/buscar";
            PlacaRequestDTO placaRequestDTO = new PlacaRequestDTO(placa);
            PlacaResponseDTO placaResponseDTO = restTemplate.postForObject(endpoint, placaRequestDTO, PlacaResponseDTO.class);
            
           
            if(placaResponseDTO.placa().equals("00")) {
                PlacaModel placaModel = new PlacaModel("00" ,"",placaResponseDTO.placa());
                model.addAttribute("placaModel", placaModel);
                return "principal";
    
            }else{
                System.out.println("Placa recibida: " + placa);

                PlacaModel placaModel = new PlacaModel("02","Error : Autentificaicon Fallida","");
                model.addAttribute("placaModel", placaModel);
                return "inicio";
            }
            
        } catch (Exception e) {
            PlacaModel placaModel = new PlacaModel("99","Error : Ocurrio un problema","");
                model.addAttribute("placaModel", placaModel);
                System.out.println(e.getMessage());
                return "inicio";
        }
       

        }
    }

