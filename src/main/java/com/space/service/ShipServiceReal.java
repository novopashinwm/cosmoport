package com.space.service;

import com.space.controller.ShipOrder;
import com.space.exception.BadRequestException;
import com.space.exception.NotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.specification.ShipSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service

public class ShipServiceReal implements ShipService {

    private ShipRepository shipRepository;

    @Autowired
    public ShipServiceReal(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    /**
     * Get ships list
     * @return ships list
     */
    @Override
    public List<Ship> getAll(String name,
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
                             ShipOrder order,
                             Integer pageNumber,
                             Integer pageSize) {

        ShipSpecification shipSpecification = new ShipSpecification(name,
                planet,
                shipType,
                after,
                before,
                isUsed,
                minSpeed,
                maxSpeed,
                minCrewSize,
                maxCrewSize,
                minRating,
                maxRating);

        return shipRepository.findAll(shipSpecification, PageRequest.of(pageNumber,pageSize, Sort.by(order.getFieldName()))).getContent();
    }

    /**
     * Get count of ships list
     * @return count of ships list
     */
    @Override
    public int getCount(String name,
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
                        Double maxRating) {
        ShipSpecification shipSpecification = new ShipSpecification(name,
                planet,
                shipType,
                after,
                before,
                isUsed,
                minSpeed,
                maxSpeed,
                minCrewSize,
                maxCrewSize,
                minRating,
                maxRating);

        return shipRepository.findAll(shipSpecification).size();
    }

    /**
     * Create new ship
     * @return new ship
     */
    @Override
    public Ship create(Ship ship) {
        if (ship == null ||
                ship.getName() == null ||
                ship.getPlanet() == null ||
                ship.getShipType() == null ||
                ship.getProdDate() == null ||
                ship.getSpeed() == null ||
                ship.getCrewSize() == null) {
            throw new BadRequestException();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ship.getProdDate());

        if (ship.getName() .length() > 50 ||
                ship.getName() .isEmpty() ||
                ship.getPlanet() .length() > 50 ||
                ship.getPlanet() .isEmpty() ||
                ship.getSpeed() < 0.01d ||
                ship.getSpeed() > 0.99d ||
                ship.getCrewSize() < 1 ||
                ship.getCrewSize() > 9999 ||
                calendar.get(Calendar.YEAR) < 2800 ||
                calendar.get(Calendar.YEAR) > 3019) {
            throw new BadRequestException();
        }

        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }

        ship.setRating(calculateShipRating(ship));
        return shipRepository.save(ship);
    }

    /**
     * Get ship with this Id
     * @param id Id of ship
     * @return created ship
     */
    @Override
    @Transactional
    public Ship getById(Long id) {
        Optional<Ship> ship = shipRepository.findById(id);
        if (ship.isPresent()) {
            return ship.get();
        }
        throw new NotFoundException();
    }

    /**
     * Update ship with this Id
     * @param id Id of ship
     * @return updated ship
     */
    @Override
    public Ship update(Long id, Ship ship) {
        Ship updatedShip = getById(id);

        if (ship != null) {

            if (ship.getName() != null) {
                updatedShip.setName(ship.getName());
            }
            if (ship.getPlanet() != null) {
                updatedShip.setPlanet(ship.getPlanet());
            }
            if (ship.getShipType() != null) {
                updatedShip.setShipType(ship.getShipType());
            }
            if (ship.getProdDate() != null) {
                updatedShip.setProdDate(ship.getProdDate());
            }
            if (ship.getUsed() != null) {
                updatedShip.setUsed(ship.getUsed());
            }
            if (ship.getSpeed() != null) {
                updatedShip.setSpeed(ship.getSpeed());
            }
            if (ship.getCrewSize() != null) {
                updatedShip.setCrewSize(ship.getCrewSize());
            }

            //updatedShip.setRating(calculateShipRating(updatedShip));
            return create(updatedShip);
        }
        throw new BadRequestException();
    }

    /**
     * Delete ship with this Id
     * @param id Id of ship
     * @return true if ship deleted, false - if not deleted
     */
    @Override
    public boolean delete(Long id) {
        if (shipRepository.existsById(id)) {
            shipRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException();
    }

    private double calculateShipRating(Ship ship) {
        double usedCoef = ship.getUsed() ? 0.5 : 1;
        int currentYear = 3019;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ship.getProdDate());
        double rating = (80 * ship.getSpeed() * usedCoef) / (currentYear - calendar.get(Calendar.YEAR) + 1);
        return (double) Math.round(rating*100) / 100;
    }
}

