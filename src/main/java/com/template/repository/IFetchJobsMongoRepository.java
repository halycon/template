package com.template.repository;

import com.template.model.repository.JobEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IFetchJobsMongoRepository extends ReactiveCrudRepository<JobEntity, String> {

}
