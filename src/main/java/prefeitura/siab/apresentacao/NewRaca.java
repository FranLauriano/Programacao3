package prefeitura.siab.apresentacao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.RacaController;
import prefeitura.siab.tabela.Raca;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewRaca {

	//ATRIBUTOS
	private @Autowired RacaController controller;
	private Raca raca;

	//PROPRIEDADES
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	
	//CONSTRUTOR
	public NewRaca() {
		raca = new Raca();
	}
	
	//MÉTODOS
	public String saveRaca(){
		FacesMessage message = new FacesMessage();
		try{
			controller.salvarRaca(raca);
			message.setSummary("Raça salva com Sucesso!");
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
