package com.stormdealers.mcp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Carries all annotations of an annotatable object.
 * 
 * @author ustaudinger
 *
 */
public class Annotatable extends Generatable {

	private List<String> annotations = new ArrayList<String>();

	public List<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

}
