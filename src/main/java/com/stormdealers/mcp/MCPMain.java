package com.stormdealers.mcp;

import com.stormdealers.mcp.model.ProjectObject;

/**
 * Starter
 * 
 * @author ustaudinger
 *
 */
public class MCPMain {

	public static void main(String[] args) throws Exception {
		YAMLToModel y = new YAMLToModel();
		ProjectObject po = y.process(args[0]);
		VelocityRenderer vr = new VelocityRenderer(); 
		vr.processProjectObject(po);
	}

}
