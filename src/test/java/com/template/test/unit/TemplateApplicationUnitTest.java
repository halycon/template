package com.template.test.unit;

import com.template.repository.IFetchJobsMongoRepository;
import com.template.service.impl.FetchGitHubJobsService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplateApplicationUnitTest {

    @Mock
    private IFetchJobsMongoRepository fetchJobsRepository;

    private FetchGitHubJobsService fetchJobsService;

    @Before
    public void setUp(){

        RestTemplate restTemplate = new RestTemplate();

        fetchJobsService = new FetchGitHubJobsService(fetchJobsRepository,restTemplate);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void a_fetchJobsRepositoryTest(){
        assertNotNull(fetchJobsService.getJobsFromGithubJobApi("new+york","python"));
    }

}
