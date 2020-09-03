package com.space.specification;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Custom specificication class for filtering our response from database by some fields
 */
public class ShipSpecification implements Specification<Ship> {

    private String name;
    private String planet;
    private ShipType shipType;
    private Long after;
    private Long before;
    private Boolean isUsed;
    private Double minSpeed;
    private Double maxSpeed;
    private Integer minCrewSize;
    private Integer maxCrewSize;
    private Double minRating;
    private Double maxRating;

    public ShipSpecification(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.after = after;
        this.before = before;
        this.isUsed = isUsed;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minCrewSize = minCrewSize;
        this.maxCrewSize = maxCrewSize;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    @Override
    public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (planet != null) {
            predicates.add(criteriaBuilder.like(root.get("planet"), "%" + planet + "%"));
        }
        if (shipType != null) {
            predicates.add(criteriaBuilder.equal(root.get("shipType"), shipType));
        }
        if (after != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after)));
        }
        if (before != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"), new Date(before)));
        }
        if (isUsed != null) {
            predicates.add(criteriaBuilder.equal(root.get("isUsed"), isUsed));
        }
        if (minSpeed != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), minSpeed));
        }
        if (maxSpeed != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("speed"), maxSpeed));
        }
        if (minCrewSize != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize));
        }
        if (maxCrewSize != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize));
        }
        if (minRating != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating));
        }
        if (maxRating != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

