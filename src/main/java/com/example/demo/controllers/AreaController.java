package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Area;
import com.example.demo.repository.AreaRepository;

import java.util.List;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaRepository areaRepository;

    @GetMapping
    public String listarAreas(Model model) {
        List<Area> areas = areaRepository.findAll();
        model.addAttribute("areas", areas);
        return "lista_areas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("area", new Area());
        return "form_area";
    }

    @PostMapping("/guardar")
    public String guardarArea(@ModelAttribute("area") Area area) {
        areaRepository.save(area);
        return "redirect:/area";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") int id, Model model) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid area ID"));
        model.addAttribute("area", area);
        return "form_area";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarArea(@PathVariable("id") int id) {
        areaRepository.deleteById(id);
        return "redirect:/area";
    }
}
