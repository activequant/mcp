package com.stormdealers.mcp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic model object, underlying all entities, projects, packages, etc.
 * Base class for all model descriptions. 
 * 
 * @author ustaudinger
 *
 */
public class ModelObject {

	private String name;
	private String documentation;
	private Map<String, String> generatorOptions = new HashMap<String, String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public Map<String, String> getGeneratorOptions() {
		return generatorOptions;
	}

	public void setGeneratorOptions(Map<String, String> generatorOptions) {
		this.generatorOptions = generatorOptions;
	}

}
