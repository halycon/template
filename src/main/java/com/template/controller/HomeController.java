package com.template.controller;

import com.template.model.service.Job;
import com.template.service.IFetchJobsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HomeController {

    @Resource(name = "FetchGitHubJobsService")
    private IFetchJobsService fetchJobsService;
    private List<Job> jobs;

    @GetMapping("/hello")
    public String home(Model model, WebSession webSession) {
        model.addAttribute("test", "test");
        //model.addAttribute("hello", Mono.just("Hi, Freemarker  volkan").block(Duration.ofSeconds(5)));

        webSession.getAttributes().put("sessionABC", "sessionABC");
        model.addAttribute("sessionABC", webSession.getAttribute("sessionABC"));
        model.addAttribute("sessionId", webSession.getId());
        return "hello";
    }

    @GetMapping("/getJobs")
    public @ResponseBody
    Flux<Job> getJobs(@RequestParam String cityName, @RequestParam String description) {
        return fetchJobsService.getJobs(cityName, description);
    }

    @GetMapping("/getJobsAndSortByCompanyName")
    public @ResponseBody
    Flux<Job> getJobsAndSortByCompanyName(@RequestParam String cityName, @RequestParam String description) {
       return  fetchJobsService.getJobsAndSortByCompanyName(cityName, description);
    }

}
