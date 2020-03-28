package com.salary.service.entity;

//import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employer {
    /*
    {
        "suggestion": "Novartis",
        "source": "REVIEWS",
        "confidence": 7.188131,
        "version": "NEW",
        "category": "company",
        "employerId": "6667",
        "logoUrl": "sqls/6667/novartis-squarelogo-1434960197508.png"
    },
     */
    private String suggestion;
    private String source;
    private Double confidence;
    private String version;
    private String category;
    private int employerId;
    private String logoUrl;
}
