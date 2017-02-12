package application.model;

public class FolderItem implements MyItem {

	private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public FolderItem(String itemName) {
		super();
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return itemName;
	}

	@Override
	public boolean isFolder() {
		return true;
	}

	@Override
	public TestCase getTestCase() {
		return null;
	}
}
