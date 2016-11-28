package application.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.MainApp;
import application.model.TestCase;
import application.model.TestItem;
import application.util.ReflectionProvider;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PersonOverviewController {
	@FXML
	private TreeView<TestItem> testsTreeView;

	@FXML
	private Button click;

	@FXML
	private ChoiceBox<String> selectOS;

	@FXML
	private ChoiceBox<String> selectBrowser;

	@FXML
	private ListView<String> selectedCombinations;

	@FXML
	private Button addCombination;

	private final Image folderIcon = new Image(getClass().getResourceAsStream("/folder_16.png"));
	private final Image testIcon = new Image(getClass().getResourceAsStream("/source_f_16.png"));
	private List<CheckBoxTreeItem<TestItem>> treeItems = new ArrayList<CheckBoxTreeItem<TestItem>>();


	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public PersonOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// init the tree view
		CheckBoxTreeItem<TestItem> rootItem = new CheckBoxTreeItem<TestItem> (new TestItem("Tests", true));
		rootItem.setExpanded(true);

		testsTreeView.setCellFactory(CheckBoxTreeCell.<TestItem>forTreeView()); 

		List<TestCase> results = ReflectionProvider.execute();

		for (TestCase testCase : results) {
			process(testCase, rootItem);
		}

		testsTreeView.setRoot(rootItem);

		click.setOnAction( (event) -> {
			getCheckedTests();

		} );


		addCombination.setOnAction( (event) -> {
			selectedCombinations.getItems().add(
					selectOS.getSelectionModel().getSelectedItem() + " " + selectBrowser.getSelectionModel().getSelectedItem());

		} );



		selectOS.setItems(FXCollections.observableArrayList(
				"Windows 7", "Windows 8", "Windows 10", "Mac")
				);
		
		selectOS.getItems().add("dfsd");

		selectBrowser.setItems(FXCollections.observableArrayList(
				"IE", "Firefox", "Chrome", "Edge")
				);

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}


	public void process(TestCase testCase, TreeItem<TestItem> rootItem){
		List<String> pathItems  = new ArrayList<String>(Arrays.asList(testCase.getFullName().split("/")));
		TreeItem<TestItem> current = rootItem;
		boolean found = false;

		do {
			found = false;
			String item = pathItems.get(0);
			for (TreeItem<TestItem> child : current.getChildren()) {
				if(child.getValue().getItemName().equals((item))) {
					found = true;
					current = child;
					pathItems.remove(0);
					break;
				}
			}

		} while (found == true && pathItems.size() > 0);

		if (pathItems.size() != 0) { // if we exit while with not existing path
			createAll(testCase, pathItems, current);
		}
	}

	public void createAll(TestCase testCase, List<String> pathItems, TreeItem<TestItem>  mountNode){
		TreeItem<TestItem> tmp = mountNode;

		for (int i = 0; i < pathItems.size() -1; i++) {
			TreeItem<TestItem> newItem = new CheckBoxTreeItem<TestItem>(new TestItem(pathItems.get(i), true), new ImageView(folderIcon));
			tmp.getChildren().add(newItem);
			tmp = newItem;
		}

		CheckBoxTreeItem<TestItem> newItem = new CheckBoxTreeItem<TestItem>(new TestItem(pathItems.get(pathItems.size() -1) , false, testCase), new ImageView(testIcon));
		tmp.getChildren().add(newItem);
		treeItems.add(newItem);

	}

	public List<TestCase> getCheckedTests(){
		List<TestCase> tests = new ArrayList<>();
		for (CheckBoxTreeItem<TestItem> treeItem : treeItems) {
			if (treeItem.isSelected()) {
				tests.add(treeItem.getValue().getTestCase());
			}
		}
		return tests;
	}
}