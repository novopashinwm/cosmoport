package com.space.service;

import com.space.model.Ship;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

public class ShipValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Ship.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {
        Ship ship = (Ship)obj;
        Double minSpeed = 0.01;
        Double maxSpeed = 0.99;
        Integer minCrewSize = 1;
        Integer maxCrewSize = 9999;


        ValidationUtils.rejectIfEmptyOrWhitespace(e, "speed", "errorSpeed");

        if (ship.getProdDate()==null || ship.getProdDate().compareTo(new Date(2800,01,01))<0 && (ship.getProdDate().compareTo(new Date(3019,12,31))>0) ){
            e.rejectValue("prodDate","errorDate");
        }
        if (ship.getProdDate()==null || ship.getProdDate().getTime()<0){
            e.rejectValue("prodDate","errorDate");
        }
        if (ship.getSpeed() == null || ship.getSpeed()<minSpeed || ship.getSpeed()>maxSpeed){
            e.rejectValue("speed","errorSpeed");

        }
        if (ship.getName() == null || ship.getName().length()<1 || ship.getName().length()>50){
            e.rejectValue("name","errorName");
        }

        if (ship.getCrewSize() == null || ship.getCrewSize()<minCrewSize || ship.getCrewSize()>maxCrewSize){
            e.rejectValue("crewSize","errorCrewSize");
        }
        if (ship.getPlanet() == null || ship.getPlanet().length()>50 || ship.getPlanet().length()<1){
            e.rejectValue("planet","errorPlanet");
        }



    }
}
