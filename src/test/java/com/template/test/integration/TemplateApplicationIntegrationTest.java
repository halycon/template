package com.template.test.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.template.service.IFetchJobsService;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemplateApplicationIntegrationTest {

    @Resource(name = "FetchGitHubJobsService")
    private IFetchJobsService fetchJobsService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void checkIfFetchGitHubJobsService_getJobsReturnsSomething() {

        ResponseEntity<List> response = testRestTemplate.
                getForEntity("/getJobs?cityName=new+york&description=python", List.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull("Should not throw an exception", new JSONArray(response.getBody()));
    }

    @Test
    public void checkIfFetchGitHubJobsService_getJobsAndSortByCompanyNameReturnsSomething() {

        ResponseEntity<List> response = testRestTemplate.
                getForEntity("/getJobs?cityName=new+york&description=python", List.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull("Should not throw an exception", new JSONArray(response.getBody()));
    }

}
