package prefeitura.siab.apresentacao;

import java.util.List;

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
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchAcs {

	//ATRIBUTOS
	private @Autowired AcsController controller;
	private Acs acs;
	private List<Acs> result;
	private boolean acsDeletada;
	private AcsSearchOptions options;

	//PROPRIEDADES
	public Acs getAcs() {
		return acs;
	}
	public void setAcs(Acs acs) {
		this.acs = acs;
	}
	public List<Acs> getResult() {
		return result;
	}
	public void setResult(List<Acs> result) {
		this.result = result;
	}
	public boolean isAcsDeletada() {
		return acsDeletada;
	}
	public void setAcsDeletada(boolean acsDeletada) {
		this.acsDeletada = acsDeletada;
	}
	public AcsSearchOptions getOptions() {
		return options;
	}
	public void setOptions(AcsSearchOptions options) {
		this.options = options;
	}
	
	//CONSTRUTOR
	public SearchAcs() {
		reset();
	}
	

	//MÉTODOS
	public String search(){
		result = controller.searchListAcs(options);
		return null;
	}
	
	public String update(Acs acs){
		Acs acsAux = new Acs();
		acsAux.setMatricula(acs.getMatricula());
		//acsAux.setNome(acs.getNome());
		acsAux.setMicroregiao(acs.getMicroregiao());
		acsAux.setArea(acs.getArea());
		acsAux.setMicroarea(acs.getMicroarea());
		this.acs = acsAux;
		return "updateAcs";
	}
	
	public void reset() {
		options = new AcsSearchOptions();
		result = null;
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updateAcs(acs);
			reset();
			message.setSummary("ACS atualizado com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String delete(Acs acs){
		this.acs = acs;
		this.acsDeletada = false;
		return "deleteAcs";
	}

	
	public String confirmDeletion(Acs acs) throws BusinessException{
		controller.deleteAcs(acs);;
		options = new AcsSearchOptions();	
		for(int i = 0; i < result.size(); i++){
			if(result.get(i).equals(acs)){
				result.remove(i);
			}
		}
		FacesMessage message = new FacesMessage();
		message.setSummary("ACS foi Deletado!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String back(){
		reset();
		return "searchAcs";
	}
	
}