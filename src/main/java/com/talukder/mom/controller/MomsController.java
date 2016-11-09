package com.talukder.mom.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.talukder.mom.domain.Moms;
import com.talukder.mom.model.MomsDao;

@Controller
@RequestMapping(value = "/moms")
public class MomsController {

	@Autowired
	private MomsDao objMomDao;

	private final String SENDGRID_API_KEY = "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";

	@RequestMapping("/")
	String index(Model model, HttpSession session) {
		// sendEmailUsingSendGrid();
		List<Moms> objMoms = objMomDao.list();
		model.addAttribute("momsList", objMoms);
		return "MoMmain";

	}

	@RequestMapping(value = "/newmoms")
	@ResponseBody
	public String CreateNewMoms(String momsSubject, String momsBody) {
		try {
			Moms objMom = new Moms();
			objMom.setMomSubject(momsSubject);
			objMom.setMom(momsBody);
			objMom.setCreated(new Date());
			objMom.setUpdated(new Date());
			objMomDao.save(objMom);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "Moms created successfully";
	}

	private void sendEmailUsingSendGrid() {
		Email from = new Email("admin@otta-payment.com");
		String subject = "Hello World from the SendGrid Java Library";
		Email to = new Email("jabed.talukder@bjitgroup.com");
		Content content = new Content("text/plain", "some text here");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			Response response = sg.api(request);
			System.out.println(response.statusCode);
			System.out.println(response.body);
			System.out.println(response.headers);
		} catch (IOException ex) {
			try {
				throw ex;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
