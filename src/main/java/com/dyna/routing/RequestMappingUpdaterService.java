package com.dyna.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RequestMappingUpdaterService {
    @Autowired
    private GreetingsController greetingsController;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public void addMapping(String methodName, String urlPath) throws NoSuchMethodException {
        unregisterMapping(methodName);
        requestMappingHandlerMapping.getHandlerMethods().keySet().forEach(entry -> {
            System.out.println("getHandlerMethods = "+entry.toString());
        });
        System.out.println("-------------------------------");
        RequestMappingInfo requestMappingInfo = RequestMappingInfo
                .paths(urlPath)
                .methods(RequestMethod.GET)
                .produces(MediaType.TEXT_HTML_VALUE)
                .build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo, greetingsController,
                GreetingsController.class.getDeclaredMethod(methodName, String.class, Model.class));

    }

    private void unregisterMapping(String methodName) {

        Predicate<HandlerMethod> predicate = handlerMethod -> handlerMethod.getMethod().getName().equalsIgnoreCase(methodName);
        List<HandlerMethod> matchingHandlerMethods = requestMappingHandlerMapping.getHandlerMethods().values().stream().filter(predicate).collect(Collectors.toList());
        Iterator<Map.Entry<RequestMappingInfo, HandlerMethod>> iterator = requestMappingHandlerMapping.getHandlerMethods().entrySet().iterator();
        matchingHandlerMethods.forEach(matchingMethod -> {
            System.out.println(matchingMethod.getMethod().getName());
            RequestMappingInfo matchingRequestMappingInfo = getMatchingRequestMapping(iterator, matchingMethod);
            if (null != matchingRequestMappingInfo) {
                requestMappingHandlerMapping.unregisterMapping(matchingRequestMappingInfo);
            }
        });
    }

    private RequestMappingInfo getMatchingRequestMapping(Iterator<Map.Entry<RequestMappingInfo,
            HandlerMethod>> iterator, HandlerMethod matchingMethod) {
        while (iterator.hasNext()) {
            Map.Entry<RequestMappingInfo, HandlerMethod> entry = iterator.next();
            if (entry.getValue().equals(matchingMethod)) {
                return entry.getKey();
            }
            return null;
        }
        return null;
    }
}
