package prefeitura.siab.apresentacao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.FamiliaController;
import prefeitura.siab.tabela.Familia;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewFamilia {

	//ATRIBUTOS
	private @Autowired FamiliaController controller;
	private Familia familia;
	
	//PROPRIEDADES
	public Familia getFamilia() {
		return familia;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
	//CONSTRUTOR
	public NewFamilia() {
		familia = new Familia();
	}
	
	//MÉTODOS
	public String saveFamilia(){
		FacesMessage message = new FacesMessage();
		try{
			controller.salvarFamilia(familia);
			message.setSummary("Família Cadastrada com Sucesso!");
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
