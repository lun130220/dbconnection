package com.max.dbconnection.controller;

import com.max.dbconnection.service.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class baseController {

    @Autowired
    private JpaService jpaService;

    @RequestMapping(path = "/list/all")
    public String getList(Model model){
        model.addAttribute("users", jpaService.getAlljpa("a"));
        return "all";
    }

}
