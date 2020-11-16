package com.stormdealers.mcp.model;

public class PropertyObject extends Annotatable {

	private String type;

	private String columnAnnotations = null; 
		
	private boolean isTransient = false; 

	public boolean isTransient() {
		return isTransient;
	}

	public void setTransient(boolean isTransient) {
		this.isTransient = isTransient;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColumnAnnotations(){
		return columnAnnotations; 
	}

	public void setColumnAnnotations(String s){
		this.columnAnnotations = s; 
	}

	/**
	 * helper method to get the camel case name. 
	 * @return
	 */
	public String getCamelCaseName() {
		if (getName().length() > 1) {
			String name = this.getName();
			name = name.toUpperCase().substring(0, 1) + name.substring(1);
			return name;
		} else
			return getName().toUpperCase();
	}
}
