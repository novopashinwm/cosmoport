package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ShipServiceReal implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public List<Ship> getShipsList(
            String name,
            String planet,
            ShipType shipType,
            Long after,
            Long before,
            Boolean isUsed,
            Double minSpeed,
            Double maxSpeed,
            Integer minCrewSize,
            Integer maxCrewSize,
            Double minRating,
            Double maxRating,
            ShipOrder order)
    {

        List<Ship> ships = new ArrayList<>();

        Iterator<Ship> iterator = shipRepository.findAll(Sort.by(order.getFieldName()).and(Sort.by("id"))).iterator();
        while (iterator.hasNext()){
            Ship ship = iterator.next();
            if (name != null && !name.isEmpty()){
                if (!ship.getName().toUpperCase().contains(name.toUpperCase())){
                    continue;
                }
            }
            if (planet != null && !planet.isEmpty()){
                if (!ship.getPlanet().toUpperCase().contains(planet.toUpperCase())){
                    continue;
                }
            }
            if (shipType !=null){
                if (shipType != ship.getShipType()){
                    continue;
                }
            }
            if (after != null){
                if (after > ship.getProdDate().getTime()){
                    continue;
                }
            }
            if (before != null){
                if (before < ship.getProdDate().getTime()){
                    continue;
                }
            }
            if (isUsed != null){
                if (!isUsed.equals(ship.getUsed())){
                    continue;
                }
            }
            if (minSpeed != null){
                if (minSpeed > ship.getSpeed()){
                    continue;
                }
            }
            if (maxSpeed != null){
                if (maxSpeed < ship.getSpeed()){
                    continue;
                }
            }
            if (minCrewSize != null){
                if (minCrewSize > ship.getCrewSize()){
                    continue;
                }
            }
            if (maxCrewSize != null){
                if (maxCrewSize < ship.getCrewSize()){
                    continue;
                }
            }
            if (minRating != null){
                if (minRating > ship.getRating()){
                    continue;
                }
            }
            if (maxRating != null){
                if (maxRating < ship.getRating()){
                    continue;
                }
            }
            ships.add(ship);
        }

        return ships;
    }

    @Override
    public Ship createShip(Ship ship) {

        Ship createShip = shipRepository.saveAndFlush(ship);
        return createShip;
    }
    @Override
    public Ship getShip(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }

}
