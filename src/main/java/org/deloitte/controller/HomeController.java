package org.deloitte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
class HomeController {
    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    public String getIndexPage(){

        System.out.println("In home controller");
        return "index";
    }
}
