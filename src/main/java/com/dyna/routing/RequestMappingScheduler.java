package com.dyna.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RequestMappingScheduler {

    @Autowired
    private URIMappingRepository uriMappingRepository;

    @Autowired
    private RequestMappingUpdaterService requestMappingUpdaterService;

    @Scheduled(fixedDelay = 10000)
    public void updateMappingsJob() {
        List<URIMapping> uriMappings = uriMappingRepository.findByControllerAndActive(GreetingsController.class.getSimpleName(), true);

        Map<String, String> updatedMappings = uriMappings.stream().
                collect(Collectors.toMap(URIMapping::getMethod,
                        URIMapping::getRequestMapping));
        System.out.println(updatedMappings);
        updatedMappings.forEach((key, value) -> {
            try {
                requestMappingUpdaterService.addMapping(key, value);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });


    }
}
