package prefeitura.siab.apresentacao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.VinculoController;
import prefeitura.siab.tabela.VinculoEmpregaticio;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewVinculo {

	//ATRIBUTOS
	private @Autowired VinculoController controller;
	private VinculoEmpregaticio vinculo;

	//PROPRIEDADES
	public VinculoEmpregaticio getVinculo() {
		return vinculo;
	}
	public void setVinculo(VinculoEmpregaticio vinculo) {
		this.vinculo = vinculo;
	}
	
	//CONSTRUTOR
	public NewVinculo() {
		this.vinculo = new VinculoEmpregaticio();
	}
	
	//MÉTODOS
		public String saveVinculo(){
			FacesMessage message = new FacesMessage();
			try{
				controller.salvarVinculo(vinculo);
				message.setSummary("Vinculo salvo com Sucesso!");
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
