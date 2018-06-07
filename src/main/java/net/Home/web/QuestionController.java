package net.Home.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.Home.domain.Question;
import net.Home.domain.QuestionRepository;
import net.Home.domain.User;
import net.Home.domain.UserRepository;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/form")
	public String questionForm(HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		return "/qna/form";
	}
	
	@PostMapping("")
	public String question(String title, String contents, HttpSession session) {
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		Question question = new Question(sessionedUser , title, contents);
		questionRepository.save(question);
		return "redirect:/";
	}
	
	@GetMapping("/{Id}")
	public String show(@PathVariable Long Id, Model model, HttpSession session) {
		
		Question question = questionRepository.findOne(Id);
		model.addAttribute("question", question);
		return "/qna/show";
	}
	
	@GetMapping("/{Id}/form")
	public String updateForm(@PathVariable Long Id, Model model, HttpSession session) {
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		Question question = questionRepository.findOne(Id);
		
		if(!question.isSameWriter(sessionedUser)) {
			return "redirect:/users/loginForm";
		}
		
		model.addAttribute("questions", question);
		return "/qna/updateForm";
	}
	
	@PutMapping("/{Id}")
	public String update(@PathVariable Long Id, String title, String contents, HttpSession session) {
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		Question question = questionRepository.findOne(Id);
		
		if(!question.isSameWriter(sessionedUser)) {
			return "redirect:/users/loginForm";
		}
		
		question.update(title,contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", Id);
	}
	
	@DeleteMapping("/{Id}")
	public String delete(@PathVariable Long Id, HttpSession session) {
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		Question question = questionRepository.findOne(Id);
		
		if(!question.isSameWriter(sessionedUser)) {
			return "redirect:/users/loginForm";
		}
		
		questionRepository.delete(Id);
		return "redirect:/";
	}
}
