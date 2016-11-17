package com.talukder.mom.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.talukder.mom.domain.Moms;
import com.talukder.mom.model.MomsDao;

@RestController
@RequestMapping("/api")
public class MomsRestController {

	@Autowired
	private MomsDao objMomDao;

	@RequestMapping("/getall")
	public List<Moms> getAllList() {
		return objMomDao.list();
	}

	@RequestMapping(value = "/addNewMom", method = RequestMethod.POST)
	public String submitNewMoms(@RequestBody Moms mom) {
		try {
			mom.setCreated(new Date());
			mom.setUpdated(new Date());
			objMomDao.addNew(mom);

		} catch (Exception e) {
			e.getMessage();
		}

		return "Success: mom added";
	}
}
