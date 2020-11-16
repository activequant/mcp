package com.stormdealers.mcp.generators;

import com.stormdealers.mcp.model.ProjectObject;

public interface IProjectProcess {
	/**
	 * Entry method that takes a process object.
	 * 
	 * @param po
	 *            an input project structure.
	 * @throws RenderException
	 */
	void processProjectObject(ProjectObject po) throws RenderException;
}
