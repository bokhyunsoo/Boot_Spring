package net.Home.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.Home.domain.User;
import net.Home.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		
		User user = userRepository.findByUserId(userId);
		
		if(user == null) {
			return "redirect:/users/loginForm";
		}
		
		if(!user.getPassword().equals(password)) {
			return "redirect:/users/loginForm";
		}
		
		session.setAttribute("sessionedUser", user);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	
	@PostMapping("")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/{Id}/form")
	public String updateForm(@PathVariable Long Id, Model model, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		
		if(sessionedUser == null) {
			return "redirect:/users/loginForm";
		}
		
		if(!sessionedUser.getId().equals(Id)) {
			throw new IllegalStateException("You can't update the another user");
		}
		
		User user = userRepository.findOne(Id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{Id}/update")
	public String update(@PathVariable Long Id, User updatedUser, HttpSession session) {
		
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		
		if(sessionedUser == null) {
			return "redirect:/users/loginForm";
		}
		
		if(!sessionedUser.getId().equals(Id)) {
			throw new IllegalStateException("You can't update the another user");
		}
		User user = userRepository.findOne(Id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
