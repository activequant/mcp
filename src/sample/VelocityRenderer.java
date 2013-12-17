package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import sample.model.EntityObject;
import sample.model.Generatable;
import sample.model.MethodObject;
import sample.model.PackageObject;
import sample.model.ProjectObject;
import sample.model.PropertyObject;

/**
 * The velocity renderer process a project model and renders the model objects.
 * 
 * @author ustaudinger
 * 
 */
public class VelocityRenderer implements IProjectProcess {

	private Logger log = Logger.getLogger(VelocityRenderer.class.getName());

	private void createFolder(String folder) {
		log.info("Creating folder if it doesn't exist: " + folder);
		File f = new File(folder);
		if (!f.exists())
			f.mkdirs();
	}

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

	private String genPackageFolder(String preTgtFolder, String packName) {
		String name = packName.replace(".", File.separator);
		String tgtFolder = new String(preTgtFolder);
		if (!tgtFolder.endsWith(File.separator)) {
			tgtFolder = tgtFolder + File.separator;
		}
		tgtFolder = tgtFolder + name;
		createFolder(tgtFolder);
		return tgtFolder;
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
		renderToFolder(eo, "./src/templates/dao_interface.vm", folder,
				"I" + eo.getName() + "Dao.java");

		folder = genPackageFolder(po.getTargetFolder(), packName
				+ ".interfaces.service");
		renderToFolder(eo, "./src/templates/service_interface.vm", folder, "I"
				+ eo.getName() + "Service.java");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".service");
		renderToFolder(eo, "./src/templates/service_impl.vm", folder,
				eo.getName() + "Service.java");

		folder = genPackageFolder(po.getTargetFolder(), packName
				+ ".service.conv");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".dao");
		renderToFolder(eo, "./src/templates/dao_impl.vm", folder, eo.getName()
				+ "Dao.java");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".entity");
		renderToFolder(eo, "./src/templates/entity.vm", folder, eo.getName()
				+ ".java");

		folder = genPackageFolder(po.getTargetFolder(), packName + ".dto");

	}

	private void renderToFolder(EntityObject eo, String template,
			String folder, String fileName) throws IOException {
		// first, let's build the filename.
		String fullFileName = folder + File.separator + fileName;

		Velocity.init();
		VelocityContext ctx = new VelocityContext();
		ctx.put("ENTITY", eo);
		Template t = Velocity.getTemplate(template);
		StringWriter writer = new StringWriter();
		t.merge(ctx, writer);
		System.out.println(writer);

		FileWriter fileWriter = new FileWriter(fullFileName);
		fileWriter.write(writer.toString());
		fileWriter.flush();
		fileWriter.close();

	}
}
