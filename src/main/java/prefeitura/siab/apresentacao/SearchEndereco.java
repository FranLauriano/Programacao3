package prefeitura.siab.apresentacao;

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
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchEndereco {

	//ATRIBUTOS
	private @Autowired EnderecoController controller;
	private Endereco endereco;
	private List<Endereco> result;
	private boolean enderecoDeletada;
	private EnderecoSearchOptions options;
	private List<Acs> agentes;

	//PROPRIEDADES
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getResult() {
		return result;
	}
	public void setResult(List<Endereco> result) {
		this.result = result;
	}
	
	public boolean isEnderecoDeletada() {
		return enderecoDeletada;
	}
	public void setEnderecoDeletada(boolean enderecoDeletada) {
		this.enderecoDeletada = enderecoDeletada;
	}

	public EnderecoSearchOptions getOptions() {
		return options;
	}
	public void setOptions(EnderecoSearchOptions options) {
		this.options = options;
	}
	
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	
	//CONSTRUTOR
	public SearchEndereco() {
		reset();
	}
	

	//MÉTODOS
	public String search(){
		Endereco endAux = new Endereco();
		endAux.setRua(options.getRua());
		endAux.setBairro(options.getBairro());
		endAux.setAgente(options.getAgente());
		endAux.setCep(options.getCep());
		endAux.setMunicipio(options.getMunicipio());
		endAux.setUf(options.getUf());
		result = controller.searchListEndereco(endAux);
		return null;
	}
	
	public String update(Endereco endereco){
		this.endereco = endereco;
		return "updateEndereco";
	}
	
	public void reset() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		AcsController controllerAcs = applicationContext.getBean(AcsController.class);
		agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
		
		options = new EnderecoSearchOptions();
		options.setAgente(new Acs());
		result = null;
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updateEndereco(endereco);
			reset();
			message.setSummary("Endereço atualizado com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String delete(Endereco endereco){
		this.endereco = endereco;
		this.enderecoDeletada = false;
		return "deleteEndereco";
	}

	
	public String confirmDeletion() throws BusinessException{
		controller.deleteEndereco(endereco);
		this.enderecoDeletada = true;
		reset();
		FacesMessage message = new FacesMessage();
		message.setSummary("O Endereço foi Deletado!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String back(){
		reset();
		return "searchEndereco";
	}
	
}