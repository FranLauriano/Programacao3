package prefeitura.siab.apresentacao;

import java.util.List;

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
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchRaca {

	//ATRIBUTOS
	private @Autowired RacaController controller;
	private Raca raca;
	private List<Raca> result;
	private boolean racaDeletada;
	private RacaSearchOptions options;

	//PROPRIEDADES
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	public List<Raca> getResult() {
		return result;
	}
	public void setResult(List<Raca> result) {
		this.result = result;
	}
	public boolean getRacaDeletada() {
		return racaDeletada;
	}
	public void setRacaDeletada(boolean racaDeletada) {
		this.racaDeletada = racaDeletada;
	}
	public RacaSearchOptions getOptions() {
		return options;
	}
	public void setOptions(RacaSearchOptions options) {
		this.options = options;
	}
	
	//CONSTRUTOR
	public SearchRaca() {
		reset();
	}
	
	//MÉTODOS
	public String search(){
		Raca search = new Raca();
		search.setCodigo(options.getCodigo());
		search.setNome(options.getNome());
		result = controller.searchRaca(search);
		return null;
	}
	
	public String update(Raca raca){
		Raca racaAux = new Raca();
		racaAux.setCodigo(raca.getCodigo());
		racaAux.setNome(raca.getNome());
		this.raca = racaAux;
		return "updateRaca";
	}
	
	public void reset() {
		options = new RacaSearchOptions();
		result = null;
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updateRaca(raca);
			reset();
			message.setSummary("Raça foi atualizada!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String delete(Raca raca){
		this.raca = raca;
		this.racaDeletada = false;
		return "deleteRaca";
	}

	
	public String confirmDeletion() throws BusinessException{
		controller.deleteRaca(raca);
		this.racaDeletada = true;
		reset();
		FacesMessage message = new FacesMessage();
		message.setSummary("A Raça foi Deletada!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String back(){
		reset();
		return "searchRaca";
	}
	
}