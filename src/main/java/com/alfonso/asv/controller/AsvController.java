package com.alfonso.asv.controller;

import com.alfonso.asv.model.Municipio;
import com.alfonso.asv.model.PrediccionData;
import com.alfonso.asv.model.PrediccionResponse;
import com.alfonso.asv.service.AsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aemet")
public class AsvController {

    @Autowired
    private AsvService asvService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/municipios")
    public List<Municipio> getMunicipiosByNombre(@RequestParam String nombre) {
        return asvService.getMunicipiosByNombre(nombre);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/municipios/{codigoMunicipio}/prediccion")
    public PrediccionResponse getPrediccion(@PathVariable String codigoMunicipio,@RequestParam String unidad) {
        return asvService.getPrediccion(codigoMunicipio,unidad);
    }

}
