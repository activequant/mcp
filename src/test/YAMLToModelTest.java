package test;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sample.ParseException;
import sample.RenderException;
import sample.VelocityRenderer;
import sample.YAMLToModel;
import sample.model.EntityObject;
import sample.model.PackageObject;
import sample.model.ProjectObject;
import sample.model.PropertyObject;

public class YAMLToModelTest {

	@Test
	public void testProcessProjectDeclaration() throws FileNotFoundException, ParseException, RenderException {
		YAMLToModel y = new YAMLToModel();
		ProjectObject po = y.process("src/test/sample.model");
		Assert.assertNotNull(po);
		Assert.assertTrue(po.getDocumentation().startsWith("This sample"));
		Assert.assertNotNull(po.getGeneratorOptions());
		Assert.assertEquals("B", po.getGeneratorOptions().get("A"));
		Assert.assertEquals("D", po.getGeneratorOptions().get("C"));
		Assert.assertNotNull(po.getPackages());
		Assert.assertEquals(2, po.getPackages().size());
		PackageObject packObj = po.getPackages().get(0);
		Assert.assertEquals("com.fe", packObj.getName());
		Assert.assertEquals(0, packObj.getGeneratorOptions().size());
		PackageObject packObj2 = po.getPackages().get(1);
		Assert.assertEquals("com.activequant", packObj2.getName());
		Assert.assertEquals("This is a package", packObj2.getDocumentation());
		// now on to the generatables. 
		Assert.assertEquals(3, packObj2.getGeneratables().size());
		EntityObject eo = (EntityObject) packObj2.getGeneratables().get(2);
		Assert.assertEquals("Instrument", eo.getName());
		Assert.assertTrue(eo.isAbstract());
		List<PropertyObject> props = eo.getProperties();
		Assert.assertEquals(2, props.size());
		Assert.assertEquals("name", props.get(0).getName());
		Assert.assertEquals("description", props.get(1).getName());
		Assert.assertEquals("String", props.get(0).getType());
		Assert.assertEquals(1, props.get(0).getAnnotations().size());
		Assert.assertEquals("@SomethingCustom", props.get(0).getAnnotations().get(0));
		
		VelocityRenderer vr = new VelocityRenderer(); 
		vr.processProjectObject(po);
		
	}

}
