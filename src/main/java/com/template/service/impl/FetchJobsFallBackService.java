package com.template.service.impl;

import com.template.model.service.Job;
import com.template.service.IFetchJobsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("FetchJobsFallBackService")
public class FetchJobsFallBackService implements IFetchJobsService {


    @Override
    public Flux<Job> getJobs(String cityName, String description) {
        return Flux.empty();
    }

    @Override
    public Flux<Job> getJobsAndSortByCompanyName(String cityName, String description) {
        return Flux.empty();
    }
}
