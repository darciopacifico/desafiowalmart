/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package walmart;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Location REST controller
 * 
 * @author Darcio
 */
@Controller
@RequestMapping("/locationAlgo")
public class LocationRestController {

	
	@Autowired
	private LocationRepository locationRepo;
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = { "/", "/locations" }, method = GET)
	@ResponseBody
	public Iterable<Location> index(@RequestParam(required = false) String name) {
		
		
		Iterable<Location> locals = locationRepo.findAll();
		
		return locals;
	}

	/**
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/{id}", method = GET)
	@ResponseBody
	public Location getLocation(@PathVariable Long id) throws JsonProcessingException {
		return null;
	}

	/**
	 * Cria novo usuario
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method = POST)
	@ResponseBody
	public Location create(@RequestBody Location location) {
		
		
		
		return null;
	}

	/**
	 * Atualiza usuario
	 * 
	 * @param userid
	 * @param user
	 */
	@RequestMapping(value = "/{userid}", method = POST, consumes = "application/json")
	@ResponseBody
	public void update(@PathVariable String name, @RequestBody Location loacation) {
		System.out.println("update");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable String id) {
		System.out.println("delete");
	}

}
