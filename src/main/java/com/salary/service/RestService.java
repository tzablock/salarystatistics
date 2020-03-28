package com.salary.service;


import com.salary.service.entity.Country;
import com.salary.service.entity.Employer;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
public class RestService {
    private static final String URL = "https://www.glassdoor.com";
    private Client client;

    public RestService() {
        this.client = ClientBuilder.newClient();
    }
//https://www.glassdoor.com/searchsuggest/typeahead%3Fsource=Review&version=New&input=Nov&rf=full
    public List<Employer> getEmployers(String prefix){
        return client.target(URL)
                     .path("/searchsuggest/typeahead")
                     .queryParam("source", "Review")
                     .queryParam("version", "New")
                     .queryParam("input", prefix)
                     .queryParam("rf", "full")
                     .request(MediaType.APPLICATION_JSON)
                     .get(new GenericType<List<Employer>>(){});
    }
  //https://www.glassdoor.com/findPopularLocationAjax.htm?term=Z&maxLocationsToReturn=10
    public List<Country> getCountries(String prefix){
        return client.target(URL)
                .path("/findPopularLocationAjax.htm")
                .queryParam("term", prefix)
                .queryParam("maxLocationsToReturn", 10)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Country>>(){});
    }
}
