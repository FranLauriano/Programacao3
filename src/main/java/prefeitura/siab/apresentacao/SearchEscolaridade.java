package prefeitura.siab.apresentacao;

import java.util.List;

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
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchEscolaridade {

	//ATRIBUTOS
	private @Autowired EscolaridadeController controller;
	private Escolaridade escolaridade;
	private List<Escolaridade> result;
	private boolean escolaridadeDeletada;
	private EscolaridadeSearchOptions options;


	//PROPRIEDADES
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	
	public List<Escolaridade> getResult() {
		return result;
	}
	public void setResult(List<Escolaridade> result) {
		this.result = result;
	}
	
	public boolean getEscolaridadeDeletada() {
		return escolaridadeDeletada;
	}
	public void setEscolaridadeDeletada(boolean escolaridadeDeletada) {
		this.escolaridadeDeletada = escolaridadeDeletada;
	}
	
	public EscolaridadeSearchOptions getOptions() {
		return options;
	}
	public void setOptions(EscolaridadeSearchOptions options) {
		this.options = options;
	}
	
	//CONSTRUTOR
	public SearchEscolaridade() {
		reset();
	}

	//MÉTODOS
	public String search(){
		Escolaridade escAux = new Escolaridade();
		escAux.setCodigo(options.getCodigo());
		escAux.setNome(options.getNome());
		result = controller.searchEscolaridade(escAux);
		return null;
	}
	
	public String update(Escolaridade escolaridade){
		this.escolaridade = escolaridade;
		return "updateEscolaridade";
	}
	
	public void reset() {
		options = new EscolaridadeSearchOptions();
		result = null;
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updateEscolaridade(escolaridade);
			reset();
			message.setSummary("Escolaridade atualizada com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String delete(Escolaridade escolaridade){
		this.escolaridade = escolaridade;
		this.escolaridadeDeletada = false;
		return "deleteEscolaridade";
	}

	
	public String confirmDeletion(Escolaridade escolaridade) throws BusinessException{
		controller.deleteEscolaridade(escolaridade);
		this.escolaridadeDeletada = true;
		reset();
		FacesMessage message = new FacesMessage();
		message.setSummary("A Escolaridade foi Deletada!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String back(){
		reset();
		return "searchEscolaridade";
	}
	
}