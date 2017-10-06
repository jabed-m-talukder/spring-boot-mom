package com.talukder.mom.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.talukder.mom.domain.Moms;
import com.talukder.mom.model.MomsDao;

import com.talukder.mom.common.Constants;

@Controller
// @RequestMapping(value = "/")
public class MomsController {

	@Autowired
	private MomsDao objMomDao;

	private static final Logger log = LoggerFactory.getLogger(MomsController.class);

	@RequestMapping(value = "/momlist", method = RequestMethod.GET)
	String momList(Model model, HttpSession session) {
		List<Moms> objMoms = objMomDao.list();
		model.addAttribute("momsList", objMoms);
		return "momlist";

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String createNewMoms(Model model) {
		model.addAttribute("mom", new Moms());
		return "new_moms";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String submitNewMoms(@ModelAttribute Moms mom, Model model) {
		try {
			mom.setCreated(new Date());
			mom.setUpdated(new Date());
			objMomDao.addNew(mom);
			model.addAttribute("mom", mom);
			sendEmailUsingSendGrid(mom.getMomsubject(), mom.getMom(), mom.getEmails());

		} catch (Exception e) {
			e.getMessage();
		}

		return "newmoms_success";
	}

	@RequestMapping(value = "/delete/{id}")
	String deleteMoms(@PathVariable("id") int id, Model model) {
		Moms mom = objMomDao.geMomsById(id);
		model.addAttribute("mom", mom);
		try {
			objMomDao.delete(mom);

		} catch (Exception e) {
			e.getMessage();
		}
		return "delete_success";

	}

	private void sendEmailUsingSendGrid(String sub, String body, String emails) {
		// String targetEmailReceipients = emails.
		Email from = new Email("free-moms@talukder.io");
		String subject = sub;
		Email to = new Email(emails);
		Content content = new Content("text/plain", body);

		Mail mail = new Mail(from, subject, to, content);
		// Personalization personalization = new Personalization();

		log.info(Constants.getSendgridAPIKeys());
		SendGrid sg = new SendGrid(Constants.getSendgridAPIKeys());
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";

			// Substitute template ID
			// mail.setTemplateId("e2e95145-67aa-4ec4-a6b6-c497dc5c4941");
			// personalization.addSubstitution("%userName%", "Abir");
			// mail.addPersonalization(personalization);

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
