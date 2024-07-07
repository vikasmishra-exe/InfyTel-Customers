package com.infosys.infytel.customer.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosys.infytel.customer.dto.PlanDTO;

@FeignClient(name = "PlanMS", url = "http://localhost:7000/")
public interface CustPlanFeign {
	@RequestMapping(value = "plans/{planId}")
	PlanDTO getSpecificPlan(@PathVariable(value = "planId") int planId);
}
