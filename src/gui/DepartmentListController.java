package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Principal;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tableColumId;
	@FXML
	private TableColumn<Department, String> tableColumNome;
	
	@FXML
	private Button btNovo;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNovoAction(ActionEvent evento) {
		Stage palcoPai = Utils.palcoAtual(evento);
		criarFormularioDialogo("/gui/DepartmentForm.fxml", palcoPai);
	}
	
	
	public void setDepartmentServices(DepartmentService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initilizeNodes();
		
	}




	private void initilizeNodes() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Principal.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> lista = service.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewDepartment.setItems(obsList);
	}

	private void criarFormularioDialogo(String absoluteName, Stage parentStage){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane painel = loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do departamento");
			dialogStage.setScene(new Scene(painel));
			dialogStage.setResizable(false); // janela não poderá ser redimensionada.
			dialogStage.initOwner(parentStage);// vamos chamar ajanela pai 
			dialogStage.initModality(Modality.WINDOW_MODAL); //comando fala qua janela vai ser modal, ou seja travada só podera acessar outra janela depois de fechar ela.
			dialogStage.showAndWait();
			
		}
		catch(IOException e) {
			Alertas.showAlert("IO Except", "Erro de carregamento de Janela", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
