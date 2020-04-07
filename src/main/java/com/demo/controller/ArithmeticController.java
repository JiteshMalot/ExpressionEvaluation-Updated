package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.demo.entity.ArithmeticExpressionEntity;
import com.demo.service.ArithmeticService;
import com.model.ArithmeticBean;

@Controller
public class ArithmeticController {

	@Autowired
	ArithmeticService service;

	@RequestMapping(value = "/evaluate", method = RequestMethod.POST)
	public ModelAndView evaluateExpression(
			@ModelAttribute("arithmeticExpressionBean") ArithmeticExpressionEntity arithmeticExpressionBean,
			BindingResult bindingResult) {

		ModelAndView model = new ModelAndView();
		List<ArithmeticBean> expressionHistory = null;
		int result = 0;
		try {
			result = service.evaluateExpression(arithmeticExpressionBean);
		} catch (Exception e) {
			model.addObject("error", "Invalid Expression");
		}
		try {
			expressionHistory = service.getStoredInputOutput();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		model.addObject("expressionResult", result);
		model.addObject("expression", arithmeticExpressionBean.getExpression());
		model.addObject("history", expressionHistory);
		model.setViewName("home");
		return model;
	}

	@GetMapping(value = "/")
	public String home(Model model) {
		model.addAttribute("arithmeticExpressionBean", new ArithmeticExpressionEntity());
		return "home";
	}

}

