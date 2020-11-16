package com.stormdealers.mcp;

import com.stormdealers.mcp.generators.StandardPersistenceGenerator;
import com.stormdealers.mcp.model.ProjectObject;

/**
 * Starter
 * 
 * @author ustaudinger
 *
 */
public class MCPMain {

	public static void main(String[] args) throws Exception {

		String modelHomeFolder = System.getenv("MCP_HOME");
		if (modelHomeFolder == null) {
			System.err
					.println("No MCP_HOME system declaration found. Set/Export MCP_HOME to point to your templates folder. ");
			System.exit(1);
		}

		if (args.length == 0) {
			System.err
					.println("No input file given, at least one input file is needed. ");
			System.exit(1);
		}

		//
		YAMLToModel y = new YAMLToModel();
		// create a project object out of our model file.
		ProjectObject po = y.process(args[0]);

		// pass the project object to the generators ...

		String gs = po.getGeneratorOptions().get("generators");
		// not used right now. ... default to standard persistence generator.
		StandardPersistenceGenerator spg = new StandardPersistenceGenerator();
		spg.processProjectObject(po);
	}

}
