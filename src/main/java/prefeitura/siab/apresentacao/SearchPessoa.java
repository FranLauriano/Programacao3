package prefeitura.siab.apresentacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.PessoaController;
import prefeitura.siab.tabela.Pessoa;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchPessoa {

	//ATRIBUTOS
	private @Autowired PessoaController controller;
	private Pessoa pessoa;
	private List<Pessoa> result;


	//PROPRIEDADES
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public List<Pessoa> getResult() {
		return result;
	}
	public void setResult(List<Pessoa> result) {
		this.result = result;
	}
	
	//CONSTRUTOR
	public SearchPessoa() {
		result = null;
	}
	
	
	//MÉTODOS
	public String search(){
		result = controller.searchPessoa(pessoa);
		return null;
	}
	/*
	public String update(Acs acs){
		Acs acsAux = new Acs();
		acsAux.setMatricula(acs.getMatricula());
		acsAux.setNome(acs.getNome());
		acsAux.setMicroregiao(acs.getMicroregiao());
		acsAux.setArea(acs.getArea());
		acsAux.setMicroarea(acs.getMicroarea());
		this.acs = acsAux;
		return "updateAcs";
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
	*/
}