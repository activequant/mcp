package com.stormdealers.mcp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes the top level node of a project
 * @author ustaudinger
 *
 */
public class ProjectObject extends ModelObject {

	private String targetFolder = ".";
	private List<PackageObject> packages = new ArrayList<PackageObject>();

	public List<PackageObject> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageObject> packages) {
		this.packages = packages;
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	/**
	 * searches through all packages if there's some class that has this name.
	 * 
	 * @param className
	 * @return
	 */
	public String packageForGeneratable(String name) {
		for (PackageObject po : getPackages()) {
			for (Generatable g : po.getGeneratables()) {
				if (g.getName().equals(name))
					return po.getName();
			}
		}
		return null;
	}

}
