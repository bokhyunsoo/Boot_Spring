package net.Home.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.Home.domain.Answer;
import net.Home.domain.AnswerRepository;
import net.Home.domain.Question;
import net.Home.domain.QuestionRepository;
import net.Home.domain.Result;
import net.Home.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@PostMapping("")
	public Answer answer(@PathVariable Long questionId, HttpSession session, String contents) {

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);

		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}

		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(sessionedUser, question, contents);
		return answerRepository.save(answer);
	}

	@DeleteMapping("/{Id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long Id, HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}

		Answer answer = answerRepository.findOne(Id);
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);

		if (!answer.isSameWriter(sessionedUser)) {
			return Result.fail("자신의 댓글만 삭제가 가능합니다.");
		}

		answerRepository.delete(Id);
		return Result.ok();
	}
}
