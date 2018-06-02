package net.Home.web;

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
	public String updateForm(@PathVariable Long Id, Model model) {
		User user = userRepository.findOne(Id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{Id}/update")
	public String update(@PathVariable Long Id, User updatedUser) {
		User user = userRepository.findOne(Id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
