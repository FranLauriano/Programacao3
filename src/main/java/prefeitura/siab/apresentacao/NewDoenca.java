package prefeitura.siab.apresentacao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.DoencaController;
import prefeitura.siab.tabela.Doenca;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewDoenca {
	
	//ATRIBUTOS
	private @Autowired DoencaController controller;
	private Doenca doenca;
	
	//PROPRIEDADES
	public Doenca getDoenca() {
		return doenca;
	}
	public void setDoenca(Doenca doenca) {
		this.doenca = doenca;
	}

	//CONSTRUTOR
	public NewDoenca() {
		doenca = new Doenca();
	}
	
	//MÉTODOS
	public String saveDoenca(){
		FacesMessage message = new FacesMessage();
		try{
			controller.salvarDoenca(doenca);
			message.setSummary("Doença/Condição Cadastrada com Sucesso!");
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
