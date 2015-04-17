package com.stormdealers.mcp.model;

import java.util.ArrayList;
import java.util.List;

public class PackageObject extends ModelObject {

	private List<Generatable> generatables = new ArrayList<Generatable>();

	public List<Generatable> getGeneratables() {
		return generatables;
	}

	public void setGeneratables(List<Generatable> generatables) {
		this.generatables = generatables;
	}
}
