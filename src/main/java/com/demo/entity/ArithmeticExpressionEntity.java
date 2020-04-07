package com.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ArithmeticExpressionEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer seriaNo;
	
	private String expression;
	
	private String result;

	public Integer getSeriaNo() {
		return seriaNo;
	}

	public void setSeriaNo(Integer seriaNo) {
		this.seriaNo = seriaNo;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
	
	
}
