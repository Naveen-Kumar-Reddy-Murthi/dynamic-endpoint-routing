package com.dyna.routing;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller("/app")
public class GreetingsController implements InitializingBean {

    private static Map<String, String> mappings = new ConcurrentHashMap<>();


    @GetMapping("/greet")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {
            model.addAttribute("name", name);
            return "greeting";
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        updateMappings();
        System.out.println("afterPropertiesSet | mappings = " + mappings);
    }


}
