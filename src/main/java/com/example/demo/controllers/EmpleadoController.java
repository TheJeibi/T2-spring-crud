package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Empleado;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.EmpleadoRepository;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private AreaRepository areaRepository;

    @GetMapping
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoRepository.findAll();
        model.addAttribute("empleados", empleados);
        return "lista_empleados";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("areas", areaRepository.findAll());
        return "form_empleado";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        System.out.println("Datos del empleado: " + empleado);
        empleadoRepository.save(empleado);
        return "redirect:/empleados";
    }

    @GetMapping("/editar/{dni}")
    public String mostrarFormularioEditar(@PathVariable("dni") String dni, Model model) {
        Empleado empleado = empleadoRepository.findById(dni)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        model.addAttribute("empleado", empleado);
        model.addAttribute("areas", areaRepository.findAll());
        return "form_empleado";
    }

    @GetMapping("/eliminar/{dni}")
    public String eliminarEmpleado(@PathVariable("dni") String dni) {
        empleadoRepository.deleteById(dni);
        return "redirect:/empleados";
    }
}