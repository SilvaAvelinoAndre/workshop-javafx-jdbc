package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable { // classe de controle dos itens da tela ele implementa o Initializable para que 
	// as coisa possam ser iniciadas

	@FXML
	private MenuItem menuItemVendedor; // atributo do tipo item de menu neste caso o item � um vendedor
	@FXML
	private MenuItem menuItemDepartamento; // atributo do tipo item de menu neste caso o item � um departamento
	@FXML 
	private MenuItem menuItemAbout; // atributo do tipo item de menu neste caso o item � um about
	
	@FXML // assinatura do m�todo para intercambio com o Scene Builder(programa que gera as telas)
	public void onMenuItemVendedorAction() {// m�todo para o menu do vendedor que vai gerar uma a��o na tela
		System.out.println("onMenuItemVendedorAction");
	}
	
	@FXML
	public void onMenuItemDepartamentoAction() {// m�todo para o menu do departamento que vai gerar uma a��o na tela
		System.out.println("onMenuItemDepartamentoAction");
	}
	
	@FXML
	public void onMenuItemAboutAction() { // m�todo para o menu do about que vai gerar uma a��o na tela
		System.out.println("onMenuItemAboutAction");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
