package net.Home.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.Home.domain.Answer;
import net.Home.domain.AnswerRepository;
import net.Home.domain.Question;
import net.Home.domain.QuestionRepository;
import net.Home.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	QuestionRepository questionRepository;

	@PostMapping("")
	public String answer(@PathVariable Long questionId, HttpSession session, String contents) {
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(sessionedUser, question ,contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
	}
}
