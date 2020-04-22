package com.salary.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataUploadControllerIT {
    //TODO add DB mocking
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeAll
    public void createMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    void uploadEmployerShouldUploadForEmployerWithNameTESTNAMEAndCompanySize1000AndCompanyEarnings10000() throws Exception {
        final String testName = "TESTNAME";
        final int testCompanySize = 1000;
        final int testCompanyEarning = 10000;
        mockMvc.perform(
                MockMvcRequestBuilders.post("/dataUpload/employer")
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(String.format("{" +
                                              "        \"name\": \"%s\",\n" +
                                              "        \"companySize\": %d,\n" +
                                              "        \"companyEarning\": %d\n" +
                                              "    }", testName, testCompanySize, testCompanyEarning)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Employer with %s name %d size %d earnings was added.", testName, testCompanySize, testCompanyEarning)));
    }

    @Test
    void uploadEmployerShouldNotUploadForEmployerWithOneOfFieldNotInRequest() throws Exception {
        final int testCompanySize = 1000;
        final int testCompanyEarning = 10000;
        mockMvc.perform(
                MockMvcRequestBuilders.post("/dataUpload/employer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{" +
                                "        \"companySize\": %d,\n" +
                                "        \"companyEarning\": %d\n" +
                                "    }", testCompanySize, testCompanyEarning)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name should be set."));
    }

    @Test
    void uploadPositionShouldUploadForPositionWithNameNewPositionOneSalary10000AndEmployerNameNewEmployer() throws Exception {
        String positionName = "NewPosition";
        String salaries = "10000";
        String employerName = "NewEmployer";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/dataUpload/position")
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(String.format(" {" +
                                              "         \"positionName\": \"%s\"," +
                                              "        \"salaries\": [%s]," +
                                              "        \"employer\":{" +
                                              "            \"name\": \"%s\"" +
                                              "          }\n" +
                                              "  }", positionName, salaries,employerName)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Position with %s for employer %s name %s salaries was added/updated.",
                        positionName, employerName, salaries)));

    }

    @Test
    void uploadPositionShouldNotUploadForPositionWithOneOfFieldNotInRequest() throws Exception {
        String positionName = "NewPosition";
        String salaries = "10000";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/dataUpload/position")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(" {" +
                                "         \"positionName\": \"%s\"," +
                                "        \"salaries\": [%s]" +
                                "  }", positionName, salaries)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Employer name should be set."));

    }
}