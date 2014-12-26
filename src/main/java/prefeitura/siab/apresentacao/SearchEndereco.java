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
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchEndereco {

	//ATRIBUTOS
	private @Autowired EnderecoController controller;
	private AcsController controllerAcs;
	private Endereco options;
	private Endereco endereco;
	private boolean enderecoAlterado;
	private List<Endereco> result;
	private List<Acs> agentes;
	private boolean update;
	
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
	
	public boolean getEnderecoAlterado() {
		return enderecoAlterado;
	}
	public void setEnderecoAlterado(boolean enderecoDeletada) {
		this.enderecoAlterado = enderecoDeletada;
	}

	public Endereco getOptions() {
		return options;
	}
	public void setOptions(Endereco options) {
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
		endereco = new Endereco();
		List<Acs> agent = new ArrayList<>();
		endereco.setAgentes(agent);
		reset();
	}
	

	//MÉTODOS
	public String search(){
		result = controller.searchListEndereco(options);
		if(options.getCep() == null || options.getCep() == 0){
			options.setCep(null);
		}
		return null;
	}
	
	public String update(Endereco endereco){
		Endereco aux = endereco.clone();
		this.endereco = aux;
		this.update = true;
		return "updateEndereco";
	}
	
	public void reset() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerAcs = applicationContext.getBean(AcsController.class);
		agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
		
		this.enderecoAlterado = false;
		options = new Endereco();
		List<Acs> ag = new ArrayList<Acs>();
		options.setAgentes(ag);
		result = null;
	}
	
	public void inicializar(){
		this.update = false;
		this.enderecoAlterado = false;
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updateEndereco(endereco);
			reset();
			enderecoAlterado = true;
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
	
	public String confirmDeletion(Endereco endereco) throws BusinessException{
		controller.deleteEndereco(endereco);
		boolean achou = false;
		for(Endereco end: result){
			if(end.getCep().equals(endereco.getCep())){
				achou = true;
			}
		}
		if(achou){
			result.remove(endereco);
		}
		FacesMessage message = new FacesMessage();
		message.setSummary("O Endereço foi Deletado!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public void setAcsMatricula(List<String> matriculas){
		if(update){
			endereco.getAgentes().clear();
			for(String matricula: matriculas){
				for(Acs agente: agentes){
					if(agente.getMatricula().toString().equals(matricula)){
						endereco.getAgentes().add(agente);
					}
				}
			}
		}else{
			options.getAgentes().clear();
			for(String matricula: matriculas){
				for(Acs agente: agentes){
					if(agente.getMatricula().toString().equals(matricula)){
						options.getAgentes().add(agente);
					}
				}
			}
		}
	}

	public List<String> getAcsMatricula(){
		List<String> acs = new ArrayList<>();
		
		if(update){
			for (Acs agente: endereco.getAgentes()){
				acs.add(agente.getMatricula().toString());
			}			
		}else{
			for (Acs agente: options.getAgentes()){
				acs.add(agente.getMatricula().toString());
			}
		}
		
		return acs;

	}
	
	public String back(){
		return "searchEndereco";
	}
	
}