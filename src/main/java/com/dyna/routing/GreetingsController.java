package com.dyna.routing;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
public class GreetingsController implements InitializingBean {

    private static Map<String, String> mappings = new ConcurrentHashMap<>();
    @Autowired
    private URIMappingRepository uriMappingRepository;

    @GetMapping("/{greeting}")
    public String greeting(@PathVariable String greeting,
                           @RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {

        System.out.println("greeting = "+greeting);

        if (mappings.get("greeting") != null && greeting.equalsIgnoreCase(mappings.get("greeting"))) {
            model.addAttribute("name", name);
            return "greeting";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid Resource Path");
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        updateMappings();
        System.out.println("afterPropertiesSet | mappings = "+mappings);
    }

    private void updateMappings() {
        List<URIMapping> uriMappings = uriMappingRepository.findByControllerAndActive(this.getClass().getSimpleName(), true);

            Map<String, String> updatedMappings = uriMappings.stream().
                    collect(Collectors.toMap(URIMapping::getMethod,
                            URIMapping::getRequestMapping));
        if(null != uriMappings) {
            mappings.putAll(updatedMappings);
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void updateMappingsJob(){
        updateMappings();
        System.out.println("updateMappingsJob | mappings = "+mappings);
    }
}
