package prefeitura.siab.apresentacao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.EscolaridadeController;
import prefeitura.siab.tabela.Escolaridade;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewEscolaridade {

	//ATRIBUTOS
	private @Autowired EscolaridadeController controller;
	private Escolaridade escolaridade;
	
	//PROPRIEDADES
	public EscolaridadeController getController() {
		return controller;
	}
	public void setController(EscolaridadeController controller) {
		this.controller = controller;
	}
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	
	//CONSTRUTOR
	public NewEscolaridade() {
		escolaridade = new Escolaridade();
	}
	
	//MÉTODOS
	public String saveEscolaridade(){
		FacesMessage message = new FacesMessage();
		try{
			controller.salvarEscolaridade(escolaridade);
			message.setSummary("Escolaridade Cadastrada com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
}
