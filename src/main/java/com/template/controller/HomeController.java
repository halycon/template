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

@Controller
public class HomeController {

    @Resource(name = "FetchGitHubJobsService")
    private IFetchJobsService fetchJobsService;


    @GetMapping("/hello")
    public String home(Model model, WebSession webSession) {
        model.addAttribute("test", "test");
        String sessionKeyValue = "sessionABC";

        webSession.getAttributes().put(sessionKeyValue,sessionKeyValue);
        model.addAttribute(sessionKeyValue, webSession.getAttribute(sessionKeyValue));
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
