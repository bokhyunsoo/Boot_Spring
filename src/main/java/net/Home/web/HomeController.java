package net.Home.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.Home.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@RequestMapping("")
	public String Home(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		return "index";
	}
}
