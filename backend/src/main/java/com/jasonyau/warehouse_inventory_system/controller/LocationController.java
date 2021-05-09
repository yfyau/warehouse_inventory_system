package com.jasonyau.warehouse_inventory_system.controller;

import com.jasonyau.warehouse_inventory_system.models.Location;
import com.jasonyau.warehouse_inventory_system.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    LocationService LocationService;

    @GetMapping("")
    public List<Location> list() {
        return LocationService.listAllLocation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> get(@PathVariable String id) {
        try {
            Location Location = LocationService.getLocation(id);
            return new ResponseEntity<Location>(Location, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public void add(@RequestBody Location location) {
        LocationService.saveLocation(location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Location location, @PathVariable String id) {
        try {
            Location existLocation = LocationService.getLocation(id);
            location.setId(id);
            LocationService.saveLocation(location);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        LocationService.deleteLocation(id);
    }
}