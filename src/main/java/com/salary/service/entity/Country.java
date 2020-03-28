package com.salary.service.entity;

import lombok.Getter;
import lombok.Setter;

//    {
//        "compoundId": "C3297851",
//        "countryName": "Switzerland",
//        "id": "C3297851",
//        "label": "Zürich (Switzerland)",
//        "locationId": 3297851,
//        "locationType": "C",
//        "longName": "Zürich (Switzerland)",
//        "realId": 3297851
//    }
@Getter
@Setter
public class Country {
    private String compoundId;
    private String countryName;
    private String id;
    private String label;
    private int locationId;
    private String locationType;
    private String longName;
    private int realId;
}
