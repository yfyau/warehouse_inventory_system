package com.jasonyau.warehouse_inventory_system.service;

import com.jasonyau.warehouse_inventory_system.models.Location;
import com.jasonyau.warehouse_inventory_system.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> listAllLocation() {
        return locationRepository.findAll();
    }

    public void saveLocation(Location user) {
        locationRepository.save(user);
    }

    public boolean isLocationExist(String id) {
        return locationRepository.existsById(id);
    }

    public Location getLocation(String id) {
        return locationRepository.findById(id).get();
    }

    public void deleteLocation(String id) {
        locationRepository.deleteById(id);
    }
}
