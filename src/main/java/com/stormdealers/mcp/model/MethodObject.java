package com.stormdealers.mcp.model;

public class MethodObject extends Annotatable {

	private String returnType;
	private String methodBody;

	public String getReturnType() {
		return returnType;
	}

	public String getMethodBody() {
		return methodBody;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public void setMethodBody(String methodBody) {
		this.methodBody = methodBody;
	}

}
