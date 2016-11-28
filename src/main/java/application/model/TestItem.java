package application.model;

public class TestItem {

	private String itemName;
	private TestCase testCase;
	private boolean isFolder = false;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public TestItem(String itemName, boolean isFolder) {
		this(itemName, isFolder, null);
	}

	public TestItem(String itemName, boolean isFolder, TestCase testCase) {
		this.itemName = itemName;
		this.testCase = testCase;
		this.isFolder = isFolder;
	}

	@Override
	public String toString() {
		return itemName;
	}

}
