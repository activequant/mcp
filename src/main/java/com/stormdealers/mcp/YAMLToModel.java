package com.stormdealers.mcp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;

import com.stormdealers.mcp.model.Annotatable;
import com.stormdealers.mcp.model.ClassObject;
import com.stormdealers.mcp.model.EntityObject;
import com.stormdealers.mcp.model.MethodObject;
import com.stormdealers.mcp.model.ModelObject;
import com.stormdealers.mcp.model.PackageObject;
import com.stormdealers.mcp.model.ProjectObject;
import com.stormdealers.mcp.model.PropertyObject;

public class YAMLToModel {

	private Yaml yaml = new Yaml();

	public YAMLToModel() {

	}

	@SuppressWarnings("unchecked")
	public ProjectObject process(String fileName) throws FileNotFoundException,
			ParseException {
		// "src/sample/sample.model"
		Object fileContent = yaml.load(new FileInputStream(fileName));
		if (fileContent instanceof LinkedHashMap) {
			LinkedHashMap<String, Object> projectDeclaration = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) fileContent)
					.get("project");
			return processProjectDeclaration(projectDeclaration);
		}
		throw new ParseException();
	}

	@SuppressWarnings("unchecked")
	private ProjectObject processProjectDeclaration(
			LinkedHashMap<String, Object> projectDeclaration)
			throws ParseException {
		ProjectObject po = new ProjectObject();
		processModelObject(po, projectDeclaration);
		// ok, now let's process the packages.
		List<Object> packages = (List<Object>) projectDeclaration
				.get("packages");
		processPackages(po, packages);

		String targetFolder = (String) projectDeclaration.get("targetFolder");
		if(targetFolder!=null)
			po.setTargetFolder(targetFolder);
		
		return po;

	}

	@SuppressWarnings("unchecked")
	private void processPackages(ProjectObject po, List<Object> packages)
			throws ParseException {
		for (Object o : packages) {
			Map<String, Object> packageContainer = (Map<String, Object>) o;
			Map<String, Object> packageTag = (Map<String, Object>) packageContainer
					.get("package");
			processPackage(po, packageTag);
		}

	}

	@SuppressWarnings("unchecked")
	private void processPackage(ProjectObject po, Map<String, Object> packageTag)
			throws ParseException {
		PackageObject packObj = new PackageObject();
		//
		processModelObject(packObj, packageTag);
		//
		po.getPackages().add(packObj);
		// ok, lets process the entities in this package.
		Map<String, Object> entities = (Map<String, Object>) packageTag
				.get("entities");
		if (entities != null) {
			processEntities(packObj, entities);
		}
	}

	@SuppressWarnings("unchecked")
	private void processModelObject(ModelObject mo, Map<String, Object> tag)
			throws ParseException {
		if (tag.containsKey("documentation"))
			mo.setDocumentation((String) tag.get("documentation"));
		//
		if (tag.containsKey("name"))
			mo.setName((String) tag.get("name"));

		//
		if (tag.containsKey("generatorOptions")) {
			mo.setGeneratorOptions((Map<String, String>) tag
					.get("generatorOptions"));
		}
	}

	@SuppressWarnings("unchecked")
	private void processEntities(PackageObject po, Map<String, Object> entities)
			throws ParseException {
		Iterator<Entry<String, Object>> iterator = entities.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entity = iterator.next();
			EntityObject eo = new EntityObject();
			eo.setName(entity.getKey());
			eo.setPackageName(po.getName()); // we'll also set the package name
			processEntity(eo, (Map<String, Object>) entity.getValue());
			po.getGeneratables().add(eo);
		}
	}

	@SuppressWarnings("unchecked")
	private void processClassObject(ClassObject co, Map<String, Object> valueMap)
			throws ParseException {
		// 
		processAnnotatable(co, valueMap);
		
		// could be abstract. 
		Boolean isAbstract = (Boolean)valueMap.get("abstract"); 
		if(isAbstract!=null){
			co.setAbstract(isAbstract); 
		}
		
		// a class has imports.
		List<String> imports = (List<String>) valueMap.get("imports");
		if (imports != null) {
			co.getImports().addAll(imports);
		}
		// a class can have methods.
		Map<String, Object> methods = (Map<String, Object>) valueMap
				.get("methods");
		if (methods != null) {
			Iterator<Entry<String, Object>> iterator = methods.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> methodMap = iterator.next();
				MethodObject mo = new MethodObject();
				mo.setName(methodMap.getKey());
				processMethod(mo, (Map<String, Object>) methodMap.getValue());
				co.getMethods().add(mo);
			}
		}
		// a class can have properties.
		Map<String, Object> properties = (Map<String, Object>) valueMap
				.get("properties");
		if (properties != null) {
			Iterator<Entry<String, Object>> propIter = properties.entrySet()
					.iterator();
			while (propIter.hasNext()) {
				Entry<String, Object> propertyMap = propIter.next();
				PropertyObject po = new PropertyObject();
				po.setName(propertyMap.getKey());
				processProperty(po,
						(Map<String, Object>) propertyMap.getValue());
				co.getProperties().add(po);
			}
		}
	}

	private void processProperty(PropertyObject po, Map<String, Object> value)
			throws ParseException {
		processAnnotatable(po, value);
		po.setType((String) value.get("type"));
		Boolean isTransient= (Boolean)value.get("transient"); 
		if(isTransient!=null){
			po.setTransient(isTransient); 
		}
		
	}

	private void processMethod(MethodObject mo, Map<String, Object> value)
			throws ParseException {
		processAnnotatable(mo, value);
		mo.setReturnType((String) value.get("returnType"));
		mo.setMethodBody((String) value.get("methodBody"));
	}

	private void processAnnotatable(Annotatable co, Map<String, Object> valueMap)
			throws ParseException {
		//
		processGeneratable(co, valueMap);
		// let's extract all those annotations.
		List<String> annotations = (List<String>) valueMap.get("annotations");
		if (annotations != null)
			co.getAnnotations().addAll(annotations);
	}

	private void processGeneratable(Annotatable co, Map<String, Object> valueMap)
			throws ParseException {
		//
		processModelObject(co, valueMap);
		//

	}

	private EntityObject processEntity(EntityObject eo,
			Map<String, Object> value) throws ParseException {
		// 1) let's extract the class specific properties.
		processClassObject(eo, value);
		return eo;
	}
}
