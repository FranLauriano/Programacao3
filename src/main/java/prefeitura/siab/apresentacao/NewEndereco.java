package prefeitura.siab.apresentacao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.EnderecoController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Endereco;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewEndereco{
	
	//ATRIBUTOS
	private @Autowired EnderecoController controller;
	private Endereco endereco;
	private List<Acs> agentes;

	
	//PROPRIEDADES
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	
	//CONSTRUTOR
	public NewEndereco() {
		endereco = new Endereco();
		List<Acs> agent = new ArrayList<>();
		endereco.setAgentes(agent);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		AcsController controllerAcs = applicationContext.getBean(AcsController.class);
		agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
	}
	
	//MÉTODOS
	public String saveEndereco(){
		FacesMessage message = new FacesMessage();
		try{
			controller.salvarEndereco(endereco);
			message.setSummary("Endereço Cadastrado com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public void setAcsMatricula(List<String> matriculas){
		endereco.getAgentes().clear();
		for(String matricula: matriculas){
			for(Acs agente: agentes){
				if(agente.getMatricula().toString().equals(matricula)){
					endereco.getAgentes().add(agente);
				}
			}
		}
	}

	public List<String> getAcsMatricula(){
		List<String> acs = new ArrayList<>();
		
		for (Acs agente: endereco.getAgentes()){
			acs.add(agente.getMatricula().toString());
		}
		return acs;

	}
	
}
