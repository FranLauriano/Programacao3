package prefeitura.siab.apresentacao;

import java.util.List;

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
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchVinculo {

	//ATRIBUTOS
	private @Autowired VinculoController controller;
	private VinculoEmpregaticio vinculo;
	private List<VinculoEmpregaticio> result;
	private boolean vinculoDeletada;
	private VinculoSearchOptions options;

	//PROPRIEDADES
	public VinculoEmpregaticio getVinculo() {
		return vinculo;
	}
	
	public void setVinculo(VinculoEmpregaticio vinculo) {
		this.vinculo = vinculo;
	}
	
	public List<VinculoEmpregaticio> getResult() {
		return result;
	}
	
	public void setResult(List<VinculoEmpregaticio> result) {
		this.result = result;
	}
	
	public boolean getVinculoDeletada() {
		return vinculoDeletada;
	}

	public void setVinculoDeletada(boolean vinculoDeletada) {
		this.vinculoDeletada = vinculoDeletada;
	}

	public VinculoSearchOptions getOptions() {
		return options;
	}

	public void setOptions(VinculoSearchOptions options) {
		this.options = options;
	}

	//CONSTRUTOR
	public SearchVinculo() {
		reset();
	}
	

	//MÉTODOS
	public String search(){
		VinculoEmpregaticio search = new VinculoEmpregaticio();
		search.setCodigo(options.getCodigo());
		search.setNome(options.getNome());
		result = controller.searchVinculo(search);
		return null;
	}
	
	public String update(VinculoEmpregaticio vinculo){
		VinculoEmpregaticio vinculoAux = new VinculoEmpregaticio();
		vinculoAux.setCodigo(vinculo.getCodigo());
		vinculoAux.setNome(vinculo.getNome());
		this.vinculo = vinculoAux;
		return "updateVinculo";
	}
	
	public void reset() {
		options = new VinculoSearchOptions();
		result = null;
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updateVinculo(vinculo);
			reset();
			message.setSummary("O Vinculo Empregatício foi atualizada!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String delete(VinculoEmpregaticio vinculo){
		this.vinculo = vinculo;
		this.vinculoDeletada = false;
		return "deleteVinculo";
	}

	
	public String confirmDeletion() throws BusinessException{
		controller.deleteVinculo(vinculo);
		this.vinculoDeletada = true;
		reset();
		FacesMessage message = new FacesMessage();
		message.setSummary("O Vinculo foi Deletada!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String back(){
		reset();
		return "searchVinculo";
	}
	
	
	
}