package com.stormdealers.mcp.generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.apache.velocity.tools.generic.DisplayTool;

import com.stormdealers.mcp.model.EntityObject;
import com.stormdealers.mcp.model.Generatable;
import com.stormdealers.mcp.model.MethodObject;
import com.stormdealers.mcp.model.PackageObject;
import com.stormdealers.mcp.model.ProjectObject;
import com.stormdealers.mcp.model.PropertyObject;


/**
 * Uses velocity to render a standard persistence layer from a project object. 
 * @author ustaudinger
 *
 */
public class StandardPersistenceGenerator extends ToolsSuper implements
		IProjectProcess {

	private final Logger log = LogManager.getLogger(VelocityRenderer.class);

	private void createFolder(String folder) {
		File f = new File(folder);
		if (!f.exists()) {
			log.info("Creating folder " + folder);
			f.mkdirs();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public void processProjectObject(ProjectObject po) throws RenderException {
		// ok, first of all, let's look at the project target folder.
		String tgtFolder = po.getTargetFolder();
		createFolder(tgtFolder);

		// now, let's process the packages folders.
		try {
			processPackageStructures(po);
		} catch (IOException e) {
			throw new RenderException(e);
		}
	}

	private void processPackageStructures(ProjectObject po) throws IOException {
		for (PackageObject packObj : po.getPackages()) {
			processPackage(po, packObj);
		}
	}

	private void processPackage(ProjectObject po, PackageObject packObj)
			throws IOException {
		String tgtFolder = po.getTargetFolder();
		String name = packObj.getName();
		genPackageFolder(tgtFolder, name);
		// ok, target folder created, let's render the entities ...
		for (Generatable g : packObj.getGeneratables()) {
			processGeneratable(po, packObj, g);
		}
	}

	private void processGeneratable(ProjectObject po, PackageObject packObj,
			Generatable g) throws IOException {
		if (g instanceof EntityObject) {
			renderEntity(po, (EntityObject) g);
		}
	}

	/**
	 * Entities result in an entity class, a dao class, a service class and a
	 * fully populated DTO plus the corresponding DTO converter.
	 * 
	 * @throws IOException
	 * 
	 */
	private void renderEntity(ProjectObject po, EntityObject eo)
			throws IOException {

		log.info("Rendering entity " + eo.getName());
		// let's see if we refer some other entity from our model.
		for (PropertyObject property : eo.getProperties()) {
			String type = property.getType();
			String packageName = po.packageForGeneratable(type);
			if (packageName != null) {
				// ok, let's add it to the list of imports in this entity.

				String importName = packageName + ".entity." + type;
				if (!eo.getImports().contains(importName))
					eo.getImports().add(importName);

			}
		}

		// same story for the method return types and parameter types.
		for (MethodObject mo : eo.getMethods()) {
			String type = mo.getReturnType();
			String packageName = po.packageForGeneratable(type);
			if (packageName != null) {
				// ok, let's add it to the list of imports in this entity.

				String importName = packageName + ".entity." + type;
				if (!eo.getImports().contains(importName))
					eo.getImports().add(importName);
			}

		}

		String packName = eo.getPackageName();

		String folder = genPackageFolder(po.getTargetFolder(), packName
				+ ".interfaces.dao");
		renderToFolder(eo, "templates/dao_interface.vm", folder,
				"I" + eo.getName() + "Dao.java");

		folder = genPackageFolder(po.getTargetFolder(), packName
				+ ".interfaces.service");
		renderToFolder(eo, "templates/service_interface.vm", folder,
				"I" + eo.getName() + "Service.java");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".service");
		renderToFolder(eo, "templates/service_impl.vm", folder, eo.getName()
				+ "Service.java");

		folder = genPackageFolder(po.getTargetFolder(), packName
				+ ".service.conv");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".dao");
		renderToFolder(eo, "templates/dao_impl.vm", folder, eo.getName()
				+ "Dao.java");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".entity");
		renderToFolder(eo, "templates/entity.vm", folder, eo.getName()
				+ ".java");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".dto");

	}

	private void renderToFolder(EntityObject eo, String template,
			String folder, String fileName) throws IOException {
		// first, let's build the filename.
		String fullFileName = folder + File.separator + fileName;

		VelocityEngine ve = new VelocityEngine();
		// Tried using classpath, but discarded it.
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		ve.setProperty("file.resource.loader.class",
				FileResourceLoader.class.getName());
		// setting it empty, so we can use an absolute path
		ve.setProperty("file.resource.loader.path", "");
		ve.init();
		VelocityContext ctx = new VelocityContext();
		ctx.put("ENTITY", eo);
		ctx.put("display", new DisplayTool());
		// now, let's expand the template path in case we are using one of our
		// default templates.
		if (template.startsWith("templates/")) {
			// ok, we assume it's one of our default templates in MCP_HOME
			String modelHomeFolder = System.getenv("MCP_HOME");
			if (!modelHomeFolder.endsWith(File.separator))
				modelHomeFolder = modelHomeFolder + File.separator;
			template = modelHomeFolder + template;
		}

		Template t = ve.getTemplate(template);
		StringWriter writer = new StringWriter();
		t.merge(ctx, writer);
		// System.out.println(writer);

		log.info("Writing file " + fullFileName);
		FileWriter fileWriter = new FileWriter(fullFileName);
		fileWriter.write(writer.toString());
		fileWriter.flush();
		fileWriter.close();
	}
}
