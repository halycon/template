package com.template.service;

import com.template.model.service.Job;
import reactor.core.publisher.Flux;

//@FeignClient(name = "IFetchJobsService", fallback = FetchJobsFallBackService.class)
public interface IFetchJobsService {
    Flux<Job> getJobs(String cityName, String description);

    Flux<Job> getJobsAndSortByCompanyName(String cityName,String description);
}
