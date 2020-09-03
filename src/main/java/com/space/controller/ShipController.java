package com.space.controller;

import com.space.exception.BadRequestException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for Ship API
 */
@RestController
@RequestMapping("/rest")
public class ShipController {

    private ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "/ships")
    public List<Ship> getAll(@RequestParam Optional<String> name,
                             @RequestParam Optional<String> planet,
                             @RequestParam Optional<ShipType> shipType,
                             @RequestParam Optional<Long> after,
                             @RequestParam Optional<Long> before,
                             @RequestParam Optional<Boolean> isUsed,
                             @RequestParam Optional<Double> minSpeed,
                             @RequestParam Optional<Double> maxSpeed,
                             @RequestParam Optional<Integer> minCrewSize,
                             @RequestParam Optional<Integer> maxCrewSize,
                             @RequestParam Optional<Double> minRating,
                             @RequestParam Optional<Double> maxRating,
                             @RequestParam Optional<ShipOrder> order,
                             @RequestParam Optional<Integer> pageNumber,
                             @RequestParam Optional<Integer> pageSize) {

        return shipService.getAll(name.orElse(null),
                planet.orElse(null),
                shipType.orElse(null),
                after.orElse(null),
                before.orElse(null),
                isUsed.orElse(null),
                minSpeed.orElse(null),
                maxSpeed.orElse(null),
                minCrewSize.orElse(null),
                maxCrewSize.orElse(null),
                minRating.orElse(null),
                maxRating.orElse(null),
                order.orElse(ShipOrder.ID),
                pageNumber.orElse(0),
                pageSize.orElse(3));
    }

    @GetMapping(value = "/ships/count")
    public int getCount(@RequestParam Optional<String> name,
                        @RequestParam Optional<String> planet,
                        @RequestParam Optional<ShipType> shipType,
                        @RequestParam Optional<Long> after,
                        @RequestParam Optional<Long> before,
                        @RequestParam Optional<Boolean> isUsed,
                        @RequestParam Optional<Double> minSpeed,
                        @RequestParam Optional<Double> maxSpeed,
                        @RequestParam Optional<Integer> minCrewSize,
                        @RequestParam Optional<Integer> maxCrewSize,
                        @RequestParam Optional<Double> minRating,
                        @RequestParam Optional<Double> maxRating) {
        return shipService.getCount(name.orElse(null),
                planet.orElse(null),
                shipType.orElse(null),
                after.orElse(null),
                before.orElse(null),
                isUsed.orElse(null),
                minSpeed.orElse(null),
                maxSpeed.orElse(null),
                minCrewSize.orElse(null),
                maxCrewSize.orElse(null),
                minRating.orElse(null),
                maxRating.orElse(null));
    }

    @GetMapping(value = "/ships/{id}")
    public Ship getById(@PathVariable("id") Long id) {
        if (!isIdValid(id)) {
            throw new BadRequestException();
        }

        return shipService.getById(id);
    }

    @DeleteMapping(value = "/ships/{id}")
    public void delete(@PathVariable("id") Long id) {
        if (!isIdValid(id)) {
            throw new BadRequestException();
        }
        shipService.delete(id);
    }

    @PostMapping(value = "/ships/{id}")
    public Ship update(@PathVariable("id") Long id,
                       @RequestBody(required = false) Ship shipUpdateInfo) {
        if (!isIdValid(id)) {
            throw new BadRequestException();
        }

        return shipService.update(id, shipUpdateInfo);
    }

    @PostMapping(value = "/ships")
    public Ship create(@RequestBody(required = false) Ship ship) {
        return shipService.create(ship);
    }

    private Boolean isIdValid(Long id) {
        return id != null &&
                id == Math.floor(id) &&
                id > 0;
    }

}
