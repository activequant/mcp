package com.stormdealers.mcp.model;

import java.util.ArrayList;
import java.util.List;

public class ClassObject extends Annotatable {
	private boolean isAbstract = false;
	private List<String> imports = new ArrayList<String>();
	private List<PropertyObject> properties = new ArrayList<PropertyObject>();
	private List<MethodObject> methods = new ArrayList<MethodObject>();

	public List<PropertyObject> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyObject> properties) {
		this.properties = properties;
	}

	public List<MethodObject> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodObject> methods) {
		this.methods = methods;
	}

	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports = imports;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

}
