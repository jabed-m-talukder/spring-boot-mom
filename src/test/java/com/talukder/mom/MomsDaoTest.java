package com.talukder.mom;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.talukder.mom.domain.Moms;
import com.talukder.mom.model.MomsDao;

public class MomsDaoTest extends MeetingOfMinutesApplicationTests {
	
	final private String momsSubject = "Meeting with Otta";
	final private String momsBody = "1. Multiple email";
	
	@Autowired
	private MomsDao momsDao;
	
	public void testSaveMoms(){
		Moms moms = new Moms();
		moms.setMom(momsBody);
		moms.setMomsubject(momsSubject);
		moms.setCreated(new Date());
		moms.setUpdated(new Date());
		momsDao.addNew(moms);
	}


	

}
