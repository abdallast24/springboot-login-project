package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.model.User;
import com.spring.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/reg")
	public String getSignUp(HttpSession session, Model model) {
		if (session.getAttribute("curuser") != null)
			return "redirect:/";

		model.addAttribute("newuser", new User());
		return "sign-up";
	}

	@RequestMapping("/add")
	public String addUser(@ModelAttribute User user) {
		userService.addUser(user);
		return "first";
	}

	@RequestMapping("/signin")
	public String getSignIn(HttpSession session, Model model) {

		if ((User) session.getAttribute("curuser") != null)
			return "redirect:/";

		return "sign-in";
	}

	@RequestMapping("/process")
	public String processData(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam(name = "remember", defaultValue = "false") boolean isRemebered, HttpSession session,
			Model model, HttpServletResponse response) {

		User user = userService.checkUser(username, password);
		if (user != null) {
			session.setAttribute("curuser", user);

			if (isRemebered) {
				Cookie cookie = new Cookie("saveduser",user.getUsername());
				cookie.setPath("/");
				cookie.setMaxAge(60 * 60 * 24);
				response.addCookie(cookie);
			}

			return "redirect:/";
		}

		return "sign-in";
	}

}
