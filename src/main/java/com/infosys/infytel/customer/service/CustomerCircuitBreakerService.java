package com.infosys.infytel.customer.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.customer.controller.CustFriendFeign;
import com.infosys.infytel.customer.controller.CustPlanFeign;
import com.infosys.infytel.customer.dto.PlanDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CustomerCircuitBreakerService {

	@Autowired
	RestTemplate template;

	@Autowired
	CustFriendFeign friendFeign;

	@Autowired
	CustPlanFeign planFeign;

	@CircuitBreaker(name = "customerService")
	public Future<PlanDTO> getPlan(Integer planID) {
		return CompletableFuture.supplyAsync(() -> planFeign.getSpecificPlan(planID));
	}

	@CircuitBreaker(name = "customerService")
	public Future<List<Long>> getFriends(Long phoneNo) {
		return CompletableFuture.supplyAsync(() -> friendFeign.getFriendFamily(phoneNo));
	}

}
