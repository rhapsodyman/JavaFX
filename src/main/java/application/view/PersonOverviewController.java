package application.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import application.MainApp;
import application.model.FolderItem;
import application.model.MyItem;
import application.model.TestCase;
import application.model.TestItem;
import application.util.ReflectionProvider;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PersonOverviewController {
	@FXML
	private TreeView<MyItem> testsTreeView;

	@FXML
	private Button click;

	@FXML
	private Button buildTree;

	@FXML
	private ChoiceBox<String> selectOS;

	@FXML
	private ChoiceBox<String> selectBrowser;

	@FXML
	private ListView<String> selectedCombinations;

	@FXML
	private Button addCombination;

	@FXML
	private TextArea progress;

	private final Image folderIcon = new Image(getClass().getResourceAsStream("/folder_16.png"));
	private final Image testIcon = new Image(getClass().getResourceAsStream("/source_f_16.png"));
	private List<CheckBoxTreeItem<MyItem>> treeItems = new ArrayList<CheckBoxTreeItem<MyItem>>();

	private List<TestCase> testCases;

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
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@FXML
	private void initialize() throws InterruptedException, ExecutionException {

		progress.appendText("Initializing UI components\n");

		click.setOnAction( (event) -> {  getCheckedTests(); } );


		selectOS.setItems(FXCollections.observableArrayList("Windows 7", "Windows 8", "Windows 10", "Mac"));
		selectBrowser.setItems(FXCollections.observableArrayList("IE", "Firefox", "Chrome", "Edge"));

		addCombination.setOnAction( (event) -> {
			selectedCombinations.getItems().add(
					selectOS.getSelectionModel().getSelectedItem() + " " + selectBrowser.getSelectionModel().getSelectedItem());
		} );


		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				progress.appendText("Start searching for classes\n");
				testCases = ReflectionProvider.execute();
				progress.appendText("End searching for classes\n");
				return null;
			}
		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();

		buildTree.setOnAction( (event) -> {
			progress.appendText("Building Tree View\n");

			// init the tree view
			TreeItem<MyItem> rootItem = new CheckBoxTreeItem<MyItem> (new FolderItem("Tests"));
			rootItem.setExpanded(true);

			testsTreeView.setCellFactory(CheckBoxTreeCell.<MyItem>forTreeView()); 

			for (TestCase testCase : testCases) {
				process(testCase, rootItem);
			}

			testsTreeView.setRoot(rootItem);
		} );
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}


	public void process(TestCase testCase, TreeItem<MyItem> rootItem){
		List<String> pathItems  = new ArrayList<String>(Arrays.asList(testCase.getFullName().split("/")));
		TreeItem<MyItem> current = rootItem;
		boolean found = false;

		do {
			found = false;
			String item = pathItems.get(0);
			for (TreeItem<MyItem> child : current.getChildren()) {
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

	public void createAll(TestCase testCase, List<String> pathItems, TreeItem<MyItem>  mountNode){
		TreeItem<MyItem> tmp = mountNode;

		for (int i = 0; i < pathItems.size() -1; i++) {
			TreeItem<MyItem> newItem = new CheckBoxTreeItem<MyItem>(new FolderItem(pathItems.get(i)), new ImageView(folderIcon));
			tmp.getChildren().add(newItem);
			tmp = newItem;
		}

		CheckBoxTreeItem<MyItem> newItem = new CheckBoxTreeItem<MyItem>(new TestItem(pathItems.get(pathItems.size() -1) , testCase), new ImageView(testIcon));
		tmp.getChildren().add(newItem);
		treeItems.add(newItem);

	}

	public List<TestCase> getCheckedTests(){
		List<TestCase> tests = new ArrayList<>();
		for (CheckBoxTreeItem<MyItem> treeItem : treeItems) {
			if (treeItem.isSelected()) {
				tests.add(treeItem.getValue().getTestCase());
				System.out.println(treeItem.getValue().getTestCase().getFullName());
			}
		}
		return tests;
	}
}