package application.model;

public class TestCase {
	public String testName;
	public String className;
	public String testSetName;
	public String excelFileName;
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTestSetName() {
		return testSetName;
	}
	public void setTestSetName(String testSetName) {
		this.testSetName = testSetName;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
	@Override
	public String toString() {
		return testName;
	}
	
	public String getFullName(){
		return testSetName + testName;
	}

}
