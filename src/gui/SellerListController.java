package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Principal;
import db.DbIntegrityException;
import gui.ouvintes.OuvinteMudancaDados;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, OuvinteMudancaDados {

	private SellerService service;
	
	

	@FXML
	private TableView<Seller> tableViewSeller;
	@FXML
	private TableColumn<Seller, Integer> tableColumId;
	@FXML
	private TableColumn<Seller, String> tableColumNome;
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
	
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	private ObservableList<Seller> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent evento) {
		Stage palcoPai = Utils.palcoAtual(evento);
		Seller obj = new Seller();
		criarFormularioDialogo(obj, "/gui/SellerForm.fxml", palcoPai);
	}

	public void setSellerServices(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initilizeNodes();

	}

	private void initilizeNodes() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);

		Stage stage = (Stage) Principal.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Seller> lista = service.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void criarFormularioDialogo(Seller obj, String absoluteName, Stage parentStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane painel = loader.load();
//
//			SellerFormController controller = loader.getController();
//			controller.setSeller(obj);
//			controller.setSellerService(new SellerService());
//			controller.agregarAlteracaoLista(this);
//			controller.atualizacaoDadosFormulario();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Digite os dados do departamento");
//			dialogStage.setScene(new Scene(painel));
//			dialogStage.setResizable(false); // janela não poderá ser redimensionada.
//			dialogStage.initOwner(parentStage);// vamos chamar ajanela pai
//			dialogStage.initModality(Modality.WINDOW_MODAL); // comando fala qua janela vai ser modal, ou seja travada
//																// só podera acessar outra janela depois de fechar ela.
//			dialogStage.showAndWait();
//
//		} catch (IOException e) {
//			Alertas.showAlert("IO Except", "Erro de carregamento de Janela", e.getMessage(), AlertType.ERROR);
//		}
	}

	@Override
	public void onAlteracaoDados() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> criarFormularioDialogo(obj, "/gui/SellerForm.fxml", Utils.palcoAtual(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType> resultado =  Alertas.showConfirmation("Confirmação", "Tem certeza que deseja deletar este Vendedor?");
		
		if(resultado.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Serviço esta nulo");
			}
			try {
			service.remove(obj);
			updateTableView();
			}
			catch(DbIntegrityException e) {
				Alertas.showAlert("Erro de remoção e objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
		
	}
	

}
