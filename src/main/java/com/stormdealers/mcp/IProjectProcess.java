package com.stormdealers.mcp;

import com.stormdealers.mcp.model.ProjectObject;

public interface IProjectProcess {
	void processProjectObject(ProjectObject po) throws RenderException;
}
