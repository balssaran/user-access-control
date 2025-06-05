package org.mounanga.userservice.service.implementation;

import java.util.List;

import org.mounanga.userservice.entity.Branch;
import org.mounanga.userservice.repository.BranchRepository;
import org.mounanga.userservice.service.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BranchServiceImpl implements BranchService{
	private static final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);
	
	 private final BranchRepository branchRepository;

	    public BranchServiceImpl(BranchRepository branchRepository) {
	        this.branchRepository = branchRepository;
	    }
	    
	@Override
    public List<Branch> getAllBranches() {
        log.info("In getAllBranches()");
       
        List<Branch> branches = branchRepository.findAll();
        log.info("{} branches found", branches.size());
        return branches;
    }
}
