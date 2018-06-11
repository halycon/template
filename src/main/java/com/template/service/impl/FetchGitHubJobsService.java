package com.template.service.impl;

import com.google.gson.Gson;
import com.template.model.repository.JobEntity;
import com.template.model.service.Job;
import com.template.repository.IFetchJobsMongoRepository;
import com.template.service.IFetchJobsService;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component("FetchGitHubJobsService")
public class FetchGitHubJobsService implements IFetchJobsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFetchJobsMongoRepository fetchJobsMongoRepository;

    @Autowired
    private RestTemplate restTemplate;

    private ParameterizedTypeReference<List<Job>> parameterizedTypeReference = new ParameterizedTypeReference<List<Job>>() {
    };

    @Autowired
    private Gson gson;

    @Override
    public Flux<Job> getJobs(String cityName, String description) {

        List<Job> jobs = getJobsFromGithubJobApi(cityName,description);

        Flux<Job> response;
        if(jobs!=null) {
            response = Flux.fromIterable(jobs);
            if (updateJobsOnRepository(response)) return Flux.empty();
        }else
            return Flux.empty();
        return response;
    }



    @Override
    public Flux<Job> getJobsAndSortByCompanyName(String cityName, String description) {

        List<Job> jobs = getJobsFromGithubJobApi(cityName,description);

        Flux<Job> response;
        if(jobs!=null){
            Collections.sort(jobs, Comparator.comparing(Job::getCompany));
            response = Flux.fromIterable(jobs);
            if (updateJobsOnRepository(response)) return Flux.empty();
        }else
            return Flux.empty();
        return response;
    }

    private Flux<Job> convertJobEntityListToJobList(Flux<JobEntity> jobs) {

        return jobs.map(jobEntity -> {
            Job job = new Job();
            job.setCompany(jobEntity.getCompany());
            job.setCompany_logo(jobEntity.getCompany_logo());
            job.setCompany_url(jobEntity.getCompany_url());
            job.setDate(jobEntity.getDate());
            job.setHow_to_apply(jobEntity.getHow_to_apply());
            job.setDescription(jobEntity.getDescription());
            job.setId(jobEntity.getId());
            job.setLocation(jobEntity.getLocation());
            job.setTitle(jobEntity.getTitle());
            job.setType(jobEntity.getType());
            job.setUrl(jobEntity.getUrl());
            return job;
        }).publish();
    }

    private Flux<JobEntity> convertJobListToJobEntityList(Flux<Job> jobs) {

        return jobs.map(job -> {
            JobEntity jobEntity = new JobEntity();
            jobEntity.setCompany(job.getCompany());
            jobEntity.setCompany_logo(job.getCompany_logo());
            jobEntity.setCompany_url(job.getCompany_url());
            jobEntity.setDate(job.getDate());
            jobEntity.setHow_to_apply(job.getHow_to_apply());
            jobEntity.setDescription(job.getDescription());
            jobEntity.setId(job.getId());
            jobEntity.setLocation(job.getLocation());
            jobEntity.setTitle(job.getTitle());
            jobEntity.setType(job.getType());
            jobEntity.setUrl(job.getUrl());
            return jobEntity;
        });
    }

    private boolean updateJobsOnRepository(Flux<Job> jobs) {
        if (jobs == null)
            return true;
        else
        {
            Flux<JobEntity> jobEntityList;
            jobEntityList = convertJobListToJobEntityList(jobs);
            Flux<JobEntity> finalJobEntityList = jobEntityList;

            fetchJobsMongoRepository.deleteAll(finalJobEntityList).subscribe(new Subscriber<Void>() {
                @Override
                public void onSubscribe(Subscription subscription) {

                }

                @Override
                public void onNext(Void aVoid) {

                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {
                    fetchJobsMongoRepository.saveAll(finalJobEntityList).then().subscribe();
                }
            });

        }
        return false;
    }

    public List<Job> getJobsFromGithubJobApi(String cityName, String description){
        String url = "https://jobs.github.com//positions.json?location=" + cityName + "&description=" + description;
        logger.info("url :: {}",url);
        ResponseEntity<List<Job>> responseHttpGet = restTemplate.exchange(url,
                HttpMethod.GET, null, parameterizedTypeReference);
        logger.info("response status :: {}",responseHttpGet.getStatusCode());
        return responseHttpGet.getBody();
    }

    public FetchGitHubJobsService(IFetchJobsMongoRepository repository, RestTemplate restTemplate) {
        this.fetchJobsMongoRepository = repository;
        this.restTemplate = restTemplate;
    }
}
