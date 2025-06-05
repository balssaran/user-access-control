package org.mounanga.userservice.web;

import java.util.ArrayList;
import java.util.List;

import org.mounanga.userservice.entity.Branch;
import org.mounanga.userservice.service.BranchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch")
public class BranchRestController {

    private final BranchService branchService;

    public BranchRestController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/list")
    public List<Branch> getAllBranches(){
    	List<Branch> branch= new ArrayList<Branch>();
    	branch = branchService.getAllBranches();
    	return branch;
    }

    
}