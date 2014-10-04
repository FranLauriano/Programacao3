package prefeitura.siab.apresentacao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.tabela.Acs;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewAcs {

	//ATRIBUTOS
	private @Autowired AcsController controller;
	private Acs agente;
	
	//PROPRIEDADES
	public AcsController getController() {
		return controller;
	}
	public void setController(AcsController controller) {
		this.controller = controller;
	}
	public Acs getAgente() {
		return agente;
	}
	public void setAgente(Acs agente) {
		this.agente = agente;
	}
	
	//CONSTRUTOR
	public NewAcs() {
		agente = new Acs();
	}
	
	//MÉTODOS
		public String saveAcs(){
			FacesMessage message = new FacesMessage();
			try{
				controller.salvarAcs(agente);
				message.setSummary("ACS Cadastrado com Sucesso!");
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
