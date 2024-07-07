package com.infosys.infytel.customer.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.customer.dto.CustomerDTO;
import com.infosys.infytel.customer.dto.LoginDTO;
import com.infosys.infytel.customer.dto.PlanDTO;
import com.infosys.infytel.customer.service.CustomerCircuitBreakerService;
import com.infosys.infytel.customer.service.CustomerService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@CrossOrigin
//@LoadBalancerClient(name="MyloadBalancer", configuration=LoadBalancerConfig.class)
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerService custService;

	@Autowired
	CustomerCircuitBreakerService breakerService;

//	@Autowired
//	private DiscoveryClient client;

//	@Value("${planURI}")
//	private String planURI;
//
//	@Value("${friendURI}")
//	private String friendURI;

	// Create a new customer
	@RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createCustomer(@RequestBody CustomerDTO custDTO) {
		logger.info("Creation request for customer {}", custDTO);
		custService.createCustomer(custDTO);
	}

	// Login
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@RequestBody LoginDTO loginDTO) {
		logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(), loginDTO.getPassword());
		return custService.login(loginDTO);
	}

	// Fetches full profile of a specific customer
	@RequestMapping(value = "/customers/{phoneNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfile(@PathVariable Long phoneNo) throws InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();
		logger.info("Profile request for customer {}", phoneNo);
		CustomerDTO custDTO = custService.getCustomerProfile(phoneNo);
		
//		List<ServiceInstance> planInstances = client.getInstances("PlanMS");
//		if (planInstances != null && !planInstances.isEmpty())
//			planURI = planInstances.get(0).getUri().toString();
//		logger.info(planURI);
		long planStartTime = System.currentTimeMillis();
		Future<PlanDTO> planFuture = breakerService.getPlan(custDTO.getCurrentPlan().getPlanId());
		long planEndTime = System.currentTimeMillis();
		

//		List<ServiceInstance> friendInstances = client.getInstances("FriendMS");
//		if(friendInstances != null && !friendInstances.isEmpty())
//			friendURI = friendInstances.get(0).getUri().toString();
//		logger.info(friendURI);
		long frienStartTime = System.currentTimeMillis();
		Future<List<Long>> friendsFuture = breakerService.getFriends(phoneNo);
		long friendEndTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		custDTO.setFriendAndFamily(friendsFuture.get());
		custDTO.setCurrentPlan(planFuture.get());
		logger.info("Plan: " + (planEndTime-planStartTime));
		logger.info("Friend: " + (friendEndTime-frienStartTime));
		logger.info("OverAll Time: " + (endTime-startTime));
		return custDTO;
	}

//	public CustomerDTO getCustomerProfileFallback(Long phoneNo, Throwable throwable) {
//		logger.info("============ In Fallback =============");
//		return new CustomerDTO();
//	}
}
