import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.beans.value.*;
import java.math.BigDecimal;

public class AlbumGUI extends Application
{	//private ObservableList<Album> albums;
	private TableView<Album> table;
	private TextField artistInput;
	private TextField titleInput;
	private TextField priceInput;
	 
	private Scene mainScene;
	public void start(Stage stage)	
	{
		stage.setTitle("Album Organizer");
		VBox vbox = new VBox();
		
		vbox.setSpacing(10);
		Scene mainScene = new Scene(vbox);
		
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("Menu 1");
		menuBar.getMenus().add(menu1);
		
		table = new TableView<Album>();
		table.setEditable(true);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		TableColumn<Album,String> artistCol = new TableColumn<Album,String>("Artist");
		artistCol.prefWidthProperty().bind(mainScene.widthProperty().divide(3).subtract(2.1/3));
		artistCol.maxWidthProperty().bind(artistCol.prefWidthProperty());
		artistCol.setResizable(false);
		artistCol.setCellValueFactory(new PropertyValueFactory<Album, String>("artist"));	
		artistCol.setCellFactory(TextFieldTableCell.forTableColumn());
		artistCol.setOnEditCommit(
			new EventHandler<CellEditEvent<Album,String>>(){
				@Override
				public void handle(CellEditEvent<Album,String> t){
					((Album) t.getTableView().getItems().get(
					t.getTablePosition().getRow())).setArtist(t.getNewValue());;
				}
			}
		);
		
		
		TableColumn<Album,String> titleCol = new TableColumn<Album,String>("Title");
		titleCol.prefWidthProperty().bind(mainScene.widthProperty().divide(3).subtract(2.1/3));
		titleCol.maxWidthProperty().bind(artistCol.prefWidthProperty());
		titleCol.setResizable(false);
		titleCol.setCellValueFactory(new PropertyValueFactory<Album, String>("title"));
		titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
		titleCol.setOnEditCommit(
			new EventHandler<CellEditEvent<Album,String>>(){
				@Override
				public void handle(CellEditEvent<Album,String> t){
					((Album) t.getTableView().getItems().get(
					t.getTablePosition().getRow())).setTitle(t.getNewValue());;
				}
			}
		);
		
		
		
		TableColumn<Album,BigDecimal> priceCol = new TableColumn<Album,BigDecimal>("Price($)");
		priceCol.prefWidthProperty().bind(mainScene.widthProperty().divide(3).subtract(2.1/3));
		priceCol.maxWidthProperty().bind(artistCol.prefWidthProperty());
		priceCol.setResizable(false);
		priceCol.setCellValueFactory(new PropertyValueFactory<Album, BigDecimal>("price"));
		priceCol.setCellFactory(col -> new EditableBigDecimalTableCell());
		//priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
		/* priceCol.setOnEditCommit(
			new EventHandler<CellEditEvent<Album,BigDecimal>>(){
				@Override
				public void handle(CellEditEvent<Album,BigDecimal> e){
					Album editAlbum = ((Album) e.getTableView().getItems().get(
					e.getTablePosition().getRow()));//.setPrice(e.getNewValue());
					try
					{	
						
					}
					catch(NumberFormatException ex)
					{
						System.out.println("Caught EXP ");
					}
				}
			}
		); */
		
		
		table.getColumns().addAll(artistCol,titleCol,priceCol); 
		table.setItems(getAlbums());
		
		Button addBtn = new Button("Add");
		Button removeBtn = new Button("Remove");
		/*addBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				table.getItems().add(new Album());
			}
		});*/
		addBtn.setOnAction(e -> addClicked());
		/*removeBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event){
				Album selectedAlbum = table.getSelectionModel().getSelectedItem();
				table.getItems().remove(selectedAlbum);
			}
		});*/
		removeBtn.setOnAction(e -> removeClicked());
		
		//FlowPane buttonPane = new FlowPane();
		
		//buttonPane.getChildren().add(addButton);
		//buttonPane.getChildren().add(removeButton);
		HBox btnPanel = new HBox();
		btnPanel.setPadding(new Insets(10,10,10,10));
		btnPanel.setSpacing(5);
		Label titleLabel = new Label("Title:");
		Label artistLabel = new Label("Artist:");
		Label priceLabel = new Label("Price:");
		titleInput = new TextField();
		titleInput.setPromptText("Title");
		artistInput = new TextField();
		artistInput.setPromptText("Artist");
		
		
		
		priceInput = new TextField();
		priceInput.setPromptText("Price");	
		priceInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    priceInput.setText(oldValue);
                }
            }
        });	//btnPanel.getChildren().addAll(titleLabel,titleTxtField,artistLabel,artistTxtField,priceLabel,priceTxtField);
		btnPanel.getChildren().addAll(artistInput,titleInput,priceInput,addBtn,removeBtn);
		
		vbox.getChildren().add(menuBar);
		vbox.getChildren().add(table);
		vbox.getChildren().add(btnPanel);
		
		
		
		stage.setScene(mainScene);
		stage.show();
	}
	
	public void addClicked()
	{
		Album album = new Album();
		album.setArtist(artistInput.getText());
		album.setTitle(titleInput.getText());
		try{
			album.setPrice(new BigDecimal(priceInput.getText().replaceAll(",","")));
		}
		catch(NumberFormatException e)
		{
			album.setPrice(new BigDecimal(0));
		}
		table.getItems().add(album);
		artistInput.clear();
		titleInput.clear();
		priceInput.clear();
	}
	
	public void removeClicked()
	{
		ObservableList<Album> selectedAlbums, allAlbums;
		allAlbums = table.getItems();
		selectedAlbums = table.getSelectionModel().getSelectedItems();
		allAlbums.removeAll(selectedAlbums);
	}

	public ObservableList<Album> getAlbums()
	{
		ObservableList<Album> albums = FXCollections.observableArrayList();
		//albums.add(new Album());
		return albums;
	}
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
}