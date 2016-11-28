package application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.testng.annotations.Test;

import application.model.TestCase;

public class ReflectionProvider {

	public static List<TestCase> execute() {
		Reflections reflections = new Reflections("application");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Test.class);
		List<TestCase> testCases = new ArrayList<TestCase>();

		for (Class<?> clazz : annotated) {
			Run annotation = clazz.getAnnotation(Run.class);
			String path = annotation.path();
			
			TestCase test = new TestCase();
			test.setClassName(clazz.getName());
			test.setTestName(clazz.getName());
			test.setTestSetName(path);
			testCases.add(test);

		}
		
		return testCases;
	}
}
