package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
		loadView("/gui/About.fxml");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * M�todo responsavel por carregar uma janela dentro da outra, sendo o About dentro da janela principal.
	 */
	
	private synchronized void loadView(String absoluteName) { 
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); // Estanciar uma nova tela
		VBox newVBox = loader.load(); // tela nova sendo carregado como o novo vbox do menu About
		
		Scene mainScene = Main.getMainScene(); // chamar a tela principal da classe Main guardando ela na variavel mainScene.
		VBox mainVBox =  (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Acessando o VBox da classe Main, chamando 
		// o mainScene ja declarado, com o comando getRoot() que traz o primeiro elemento da mainScene la da Classe Main, neste caso
		// o 1� item � o SCrollPane ent�o a necessidade do Casting (ScrollPane), logo depois chama o comando get.Content(), que � o elemento que esta
		// dentro do ScroolPane, quando voce chama o Content voc� fala para o compilador que quer o que esta dentro dele que � o VBox, ai
		// basta fazer mais um casting (VBox) e o compilador entender� que voc� quer s� o Vboc.
		
		Node mainMenu = mainVBox.getChildren().get(0); // Vamo extrair agora os filhos do vBox com o comando mainVBox.getChildren() (que s�o os menus)
		// e guarda-los na variavel mainMenu.
		
		mainVBox.getChildren().clear(); // logo ap�s extrai-los vamos apagar os filhos do mainVBox, com o comando mainVBoc.getChildren()
		
		mainVBox.getChildren().add(mainMenu); // Depois de Excluir, vamos adiciona-los novamente, por�m desta vez s� os menus que est�o na 
		// variavel mainmenu.
		mainVBox.getChildren().addAll(newVBox.getChildren()); // Agora vamos incluir uma cole��o com este comando, para inserirmos os menus
		// que s�o os filhos da variavel newVBox ent�o tudo ficar� funcional os menus da newVBox e os menus da VBox anterior. 
		
		}
		catch(IOException e) {
			Alertas.showAlert("IO Exception", "Erro de carregamento de pagina", e.getMessage(), AlertType.ERROR);
		}
	}
	

}
