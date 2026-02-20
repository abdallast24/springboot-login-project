package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.model.User;
import com.spring.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String getFirstPage(HttpSession session, HttpServletRequest request, Model model) {

		User user = (User) session.getAttribute("curuser");

		if (user != null) {
			model.addAttribute("username", user.getUsername());
			return "welcome";
		}

		Cookie[] cookies = request.getCookies();
		String val = "-1";

		if (cookies != null)
			for (int i = 0; i < cookies.length; i++)
				if (cookies[i].getName().equals("saveduser"))
					val = cookies[i].getValue();

		if (!val.equals("-1")) {
			user = userService.getUser(val);

			if (user != null) {
				session.setAttribute("curuser", user);

				model.addAttribute("username", user.getUsername());
				return "welcome";
			}
		}

		return "first";

	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		session.invalidate();

		Cookie[] cookies = request.getCookies();

		if (cookies != null)
			for (int i = 0; i < cookies.length; i++)
				if (cookies[i].getName().equals("saveduser")) {

					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);

				}
		
		return "first";

	}

}
