package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entidade;
	
	private DepartmentService service;

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label labelErrorName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;

	public void setDepartment(Department entidade) {
		this.entidade = entidade;
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	
	@FXML
	public void onBtSaveAction(ActionEvent evento) {
		if (entidade == null) {
			throw new IllegalStateException("Entidade esta nula!");
		}
		if(service == null) {
			throw new IllegalStateException("Serviço esta nulo!");
		}
		try {
		entidade = getObterDadosFormulario();
		service.salvarOuAtualizar(entidade);
		Utils.palcoAtual(evento).close();
		}
		catch(DbException e) {
			Alertas.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private Department getObterDadosFormulario() {
		Department obj = new Department();
		
		obj.setId(Utils.converterParaInteiro(txtId.getText()));
		obj.setName(txtName.getText());
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
		Restricoes.setTextFieldMaxLength(txtName, 50);
	}

	public void atualizacaoDadosFormulario() {
		if(entidade == null) {
			throw new IllegalStateException("Entidade esta nula");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getName());

	}
}
