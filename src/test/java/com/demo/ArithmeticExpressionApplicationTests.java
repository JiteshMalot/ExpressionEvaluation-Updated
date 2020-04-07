package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.entity.ArithmeticExpressionEntity;
import com.demo.service.ArithmeticService;

@SpringBootTest
class ArithmeticExpressionApplicationTests {

	@Autowired
	private ArithmeticService arithmeticService;

	@Test
	void testExpression() {

		// Test 1
		ArithmeticExpressionEntity expEntity = new ArithmeticExpressionEntity();
		expEntity.setExpression("(1+(3-2))");
		try {
			int res = arithmeticService.evaluateExpression(expEntity);
			assertEquals(2,res);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
