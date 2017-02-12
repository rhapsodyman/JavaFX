package application.model;

public class TestItem implements MyItem {

	private String itemName;
	private TestCase testCase;

	public TestItem(String itemName, TestCase testCase) {
		this.itemName = itemName;
		this.testCase = testCase;
	}

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

	@Override
	public boolean isFolder() {
		return false;
	}

	@Override
	public String toString() {
		return itemName;
	}

}
