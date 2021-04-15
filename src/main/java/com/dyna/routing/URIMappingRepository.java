package com.dyna.routing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface URIMappingRepository extends JpaRepository<URIMapping, Long> {

    List<URIMapping> findByControllerAndActive(String controller, boolean active);

}
