package com.demo.service;

import java.util.List;

import com.demo.entity.ArithmeticExpressionEntity;
import com.model.ArithmeticBean;

public interface ArithmeticService {
	  public int evaluateExpression(ArithmeticExpressionEntity expressionEntity) throws Exception;
	  
	  public List<ArithmeticBean> getStoredInputOutput();
}
