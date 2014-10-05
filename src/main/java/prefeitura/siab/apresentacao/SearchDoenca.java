package prefeitura.siab.apresentacao;

import java.util.List;

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
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchDoenca {

	//ATRIBUTOS
	private @Autowired DoencaController controller;
	private Doenca doenca;
	private List<Doenca> result;
	private boolean doencaDeletada;
	private DoencaSearchOptions options;

	//PROPRIEDADES
	public Doenca getDoenca() {
		return doenca;
	}
	public void setDoenca(Doenca raca) {
		this.doenca = raca;
	}
	public List<Doenca> getResult() {
		return result;
	}
	public void setResult(List<Doenca> result) {
		this.result = result;
	}
	public boolean getDoencaDeletada() {
		return doencaDeletada;
	}
	public void setDoencaDeletada(boolean racaDeletada) {
		this.doencaDeletada = racaDeletada;
	}
	public DoencaSearchOptions getOptions() {
		return options;
	}
	public void setOptions(DoencaSearchOptions options) {
		this.options = options;
	}
	
	//CONSTRUTOR
	public SearchDoenca() {
		reset();
	}
	
	//MÉTODOS
	public String search(){
		Doenca search = new Doenca();
		search.setSigla(options.getSigla());
		search.setNome(options.getNome());
		result = controller.searchListDoenca(search);
		return null;
	}
	
	public String update(Doenca doenca){
		Doenca doencaAux = new Doenca();
		doencaAux.setSigla(doenca.getSigla());
		doencaAux.setNome(doenca.getNome());
		this.doenca = doencaAux;
		return "updateDoenca";
	}
	
	public void reset() {
		options = new DoencaSearchOptions();
		result = null;
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updateDoenca(doenca);
			reset();
			message.setSummary("Doença foi atualizada!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String delete(Doenca doenca){
		this.doenca = doenca;
		this.doencaDeletada = false;
		return "deleteDoenca";
	}

	
	public String confirmDeletion() throws BusinessException{
		controller.deleteDoenca(doenca);
		this.doencaDeletada = true;
		reset();
		FacesMessage message = new FacesMessage();
		message.setSummary("A Doença foi Deletada!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String back(){
		reset();
		return "searchDoenca";
	}
	
}