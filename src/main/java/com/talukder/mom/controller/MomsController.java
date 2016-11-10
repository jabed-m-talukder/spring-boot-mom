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
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value = "/")
public class MomsController {

	@Autowired
	private MomsDao objMomDao;

	private final String SENDGRID_API_KEY = "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";

	@RequestMapping("/")
	String index(Model model, HttpSession session) {
		// sendEmailUsingSendGrid();
		List<Moms> objMoms = objMomDao.list();
		model.addAttribute("momsList", objMoms);
		return "momlist";

	}

	@RequestMapping(value = "/newmoms", method = RequestMethod.GET)
	public String createNewMoms(Model model) {
		model.addAttribute("mom", new Moms());
		return "new_moms";
	}

	@RequestMapping(value = "/newmoms", method = RequestMethod.POST)
	public String submitNewMoms(@ModelAttribute Moms mom, Model model) {
		try {
			mom.setCreated(new Date());
			mom.setUpdated(new Date());
			objMomDao.addNew(mom);
			model.addAttribute("mom", mom);
			sendEmailUsingSendGrid(mom.getMomsubject(), mom.getMom());
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return "newmoms_success";
	}

	@RequestMapping(value = "/delete")
	String deleteMoms(int id, Model model) {
		Moms mom = objMomDao.geMomsById(id);
		model.addAttribute("mom", mom);
		try {
			objMomDao.delete(mom);

		} catch (Exception e) {
			e.getMessage();
		}
//		String tempStr = "Deleted: " + mom.getId() + "-> " + mom.getMomsubject();
		return "delete_success";

	}

	private void sendEmailUsingSendGrid(String sub, String body) {
		Email from = new Email("admin@free-moms.io");
		String subject = sub;
		Email to = new Email("jabed.talukder@bjitgroup.com");
		Content content = new Content("text/plain", body);
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
