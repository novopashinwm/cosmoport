package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.List;

public interface ShipService {
    Ship createShip(Ship ship);
    Ship getShip(Long id);
    void deleteShip(Long id);
    List<Ship> getShipsList(String name,
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
                            ShipOrder order
    );
}
