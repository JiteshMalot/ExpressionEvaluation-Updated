package com.demo.serviceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entity.ArithmeticExpressionEntity;
import com.demo.repo.ArithmeticRepo;
import com.demo.service.ArithmeticService;
import com.model.ArithmeticBean;

@Service
public class ArithmeticServiceImpl implements ArithmeticService {

	@Autowired
	ArithmeticRepo repo;

	@Override
	public int evaluateExpression(ArithmeticExpressionEntity expressionEntity) throws Exception {		
		String postfixexpr = convertExpressionToPostfix(expressionEntity.getExpression());
		Stream<Character> postfixStream = postfixexpr.chars().mapToObj(c -> (char) c);
		Stack<Integer> evaluator = new Stack<>();
		postfixStream.forEach(pe -> {
			if (Character.isDigit(pe))
				evaluator.push(pe - '0');
			else {
				int val1 = evaluator.pop();
				int val2 = evaluator.pop();

				switch (pe) {
				case '+':
					evaluator.push(val2 + val1);
					break;

				case '-':
					evaluator.push(val2 - val1);
					break;

				case '/':
					evaluator.push(val2 / val1);
					break;

				case '*':
					evaluator.push(val2 * val1);
					break;
				}
			}

		});
		int result = evaluator.pop();
		expressionEntity.setResult(String.valueOf(result));
		repo.save(expressionEntity);
		return result;
		

	}
	private static String convertExpressionToPostfix(String expr) throws Exception {
		String result = new String("");
		char[] tokens = expr.toCharArray();
		// initializing empty stack
		Stack<Character> stack = new Stack<>();
		for (int i = 0, n = tokens.length; i < n; i++) {
			char c = tokens[i];
			// If the scanned character is an operand, add it to output.
			if (Character.isLetterOrDigit(c))
				result += c;

			// If the scanned character is an '(', push it to the stack.
			else if (c == '(')
				stack.push(c);

			// If the scanned character is an ')', pop and output from the stack
			// until an '(' is encountered.
			else if (c == ')') {
				while (!stack.isEmpty() && stack.peek() != '(')
					result += stack.pop();

				if (!stack.isEmpty() && stack.peek() != '(')
					throw new Exception("Invalid Expression"); // invalid expression
				else
					stack.pop();
			} else // an operator is encountered
			{
				while (!stack.isEmpty() && checkPrecedence(c) <= checkPrecedence(stack.peek())) {
					if (stack.peek() == '(')
						throw new Exception("Invalid Expression");
					result += stack.pop();
				}
				stack.push(c);
			}

		}

		while (!stack.isEmpty()) {
			if (stack.peek() == '(')
				throw new Exception("Invalid Expression");
			result += stack.pop();
		}
		return result;
	}
	private static int checkPrecedence(char ch) {
		switch (ch) {
		case '+':
		case '-':
			return 1;

		case '*':
		case '/':
			return 2;

		case '^':
			return 3;
		}
		return -1;
	}
	@Override
	public List<ArithmeticBean> getStoredInputOutput() {
		
		List<ArithmeticExpressionEntity> listofExpressions = repo.findAll();
		List<ArithmeticBean> mappedList = new ArrayList<ArithmeticBean>();
		for(ArithmeticExpressionEntity list:listofExpressions) {
			ArithmeticBean bean = new ArithmeticBean();
			bean.setExpression(list.getExpression());
			bean.setResult(list.getResult());
			mappedList.add(bean);
		}
		
		return mappedList;
	}
}
