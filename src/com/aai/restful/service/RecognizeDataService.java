package com.aai.restful.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aai.info.Records;
import com.aai.info.Status;

@Controller
public class RecognizeDataService {

	@RequestMapping(method=RequestMethod.GET, value="/employee/{id}")
	public @ResponseBody Status getEmployee(@PathVariable String id) {
		System.out.println(id);
		
		Status status = new Status();
		status.setCode(0);
		status.setMessage("SUCCESS");
		
		return status;
		
	}

		@RequestMapping(value="/recognizedata", method = RequestMethod.POST)
		public	@ResponseBody Status recognizeData(@RequestBody Records records){
			
			System.out.println(records);
			
			Status status = new Status();
			status.setCode(0);
			status.setMessage("SUCCESS");
			
			return status;
			
		}
		
		@RequestMapping(value="/recognizestring.do", method = RequestMethod.POST)
		public	@ResponseBody Status recognizeString(@RequestBody String records){
			System.out.println(records);
			Status status = new Status();
			status.setCode(0);
			status.setMessage("SUCCESS");
			
			return status;
			
		}
		
		
		/*@RequestMapping(method=RequestMethod.POST)
		public @ResponseBody Map<String, ? extends Object> create(@RequestBody Account account, HttpServletResponse response) {
		    Set<ConstraintViolation<Account>> failures = validator.validate(account);
		    if (!failures.isEmpty()) {
		        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		        return validationMessages(failures);
		    } else {
		        accounts.put(account.assignId(), account);
		        return Collections.singletonMap("id", account.getId());
		    }
		}*/
		
		
		/*$("#account").submit(function() {
		    var account = $(this).serializeObject();
		    $.postJSON("account", account, function(data) {
		        $("#assignedId").val(data.id);
		        showPopup();
		    });
		    return false;
		});*/



}
