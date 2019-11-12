package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.launchcode.models.Job;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {


    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

//        /job?id={id}

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job iFoundAJob = new Job();
        iFoundAJob = jobData.findById(id);
        model.addAttribute("job", iFoundAJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,  /*@ModelAttribute Job newJob,*/
                      @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
//            model.addAttribute(new JobForm());
            return "new-job";
        }

/*        newJobby.setName(jobForm.getName());
        newJobby.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
        newJobby.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
        newJobby.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypeId()));
        newJobby.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));*/



        String newJobName = jobForm.getName();
        Employer newJobEmp = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location newJobLoc = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType newJobPT = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency newJobCC = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());


        Job newJob = new Job(newJobName, newJobEmp, newJobLoc, newJobPT, newJobCC);
        jobData.add(newJob);

//        redirectAttributes.addAttribute("id", newJob.getId());
        return "redirect:?id=" + newJob.getId();

    }
}