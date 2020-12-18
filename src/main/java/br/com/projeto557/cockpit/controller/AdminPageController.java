package br.com.projeto557.cockpit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {


    @RequestMapping({"/dashboard","/"})
    public String dashboardPage(){
        return "admin/dashboard";
    }

    @RequestMapping("/estoque/add")
    public String estoqueAdd(){
        return "admin/estoque-add";
    }

    @RequestMapping({"/estoque","estoque/list"})
    public String listEstoque(){
        return "admin/estoque-list";
    }

    @RequestMapping("/sincronizar")
    public String sincronizar(){
        return "admin/sinc";
    }

}
