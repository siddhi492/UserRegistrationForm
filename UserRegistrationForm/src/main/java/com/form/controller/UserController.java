package com.form.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.form.entity.User;
import com.form.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String homeRedirect() {
		return "redirect:/register";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user, Model model) {
		if (userRepository.findByEmail(user.getEmail()) != null) {
			model.addAttribute("error", "Email already registered!");
			return "register";
		}
		userRepository.save(user);
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
		User dbUser = userRepository.findByEmail(user.getEmail());
		if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
			session.setAttribute("loggedUser", dbUser);
			return "redirect:/dashboard";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "login";
		}
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedUser");
		if (user == null)
			return "redirect:/login";
		model.addAttribute("name", user.getName());
		return "dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
