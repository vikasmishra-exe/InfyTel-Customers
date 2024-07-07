package com.infosys.infytel.customer.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "FriendMS", url = "http://localhost:7000/")
public interface CustFriendFeign {
	
	@RequestMapping(value = "friendFamily/{phoneNo}")
	List<Long> getFriendFamily(@PathVariable(value = "phoneNo") Long phoneNo);

}
