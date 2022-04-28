package com.api.junit.one.petclinic.controllers;

import com.api.junit.one.petclinic.fauxspring.Model;
import com.api.junit.one.petclinic.services.VetService;

public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    public String listVets(Model model){

        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}
