package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.ouvintes.OuvinteMudancaDados;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exception.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private Seller entidade;

	private SellerService service;

	private List<OuvinteMudancaDados> ouvintesMudancaDados = new ArrayList<>();

	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Label labelErrorBaseSalary;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;

	public void setSeller(Seller entidade) {
		this.entidade = entidade;
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}

	public void agregarAlteracaoLista(OuvinteMudancaDados ouvinte) {
		ouvintesMudancaDados.add(ouvinte);
	}

	@FXML
	public void onBtSaveAction(ActionEvent evento) {
		if (entidade == null) {
			throw new IllegalStateException("Entidade esta nula!");
		}
		if (service == null) {
			throw new IllegalStateException("Servi�o esta nulo!");
		}
		try {
			entidade = getObterDadosFormulario();
			service.salvarOuAtualizar(entidade);
			notificarAlteracaoDadosLista();
			Utils.palcoAtual(evento).close();
		} 
		catch (ValidationException e) {
			setMensagemErro(e.getErros());
		} 
		catch (DbException e) {
			Alertas.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}

	}

	private void notificarAlteracaoDadosLista() {
		for (OuvinteMudancaDados ouvintes : ouvintesMudancaDados) {
			ouvintes.onAlteracaoDados();
		}

	}

	private Seller getObterDadosFormulario() {
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Erro de Valida��o");

		obj.setId(Utils.converterParaInteiro(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.adicionarErros("name", "O campo n�o pode ser vazio");
		}
		obj.setName(txtName.getText());

		if (exception.getErros().size() > 0) {
			throw exception;
		}
		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent evento) {
		Utils.palcoAtual(evento).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtName, 70);
		Restricoes.setTextFieldDouble(txtBaseSalary);
		Restricoes.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
	}

	public void atualizacaoDadosFormulario() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade esta nula");
		}

		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getName());
		txtEmail.setText(entidade.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entidade.getBaseSalary()));
		if(entidade.getBirthDate() != null) {
		dpBirthDate.setValue(LocalDate.ofInstant(entidade.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}

	}

	private void setMensagemErro(Map<String, String> erros) {
		Set<String> campos = erros.keySet();

		if (campos.contains("name")) {
			labelErrorName.setText(erros.get("name"));
		}
	}

}