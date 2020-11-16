package com.stormdealers.mcp.generators;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ToolsSuper {

	protected final Logger log = LogManager.getLogger(ToolsSuper.class);

	public ToolsSuper() {
		super();
	}

	protected void createFolder(String folder) {
		File f = new File(folder);
		if (!f.exists()){
			log.info("Creating folder " + folder);
			f.mkdirs();
		}
	}

	protected String genPackageFolder(String preTgtFolder, String packName) {
		String name = packName.replace(".", File.separator);
		String tgtFolder = new String(preTgtFolder);
		if (!tgtFolder.endsWith(File.separator)) {
			tgtFolder = tgtFolder + File.separator;
		}
		tgtFolder = tgtFolder + name;
		createFolder(tgtFolder);
		return tgtFolder;
	}

}
