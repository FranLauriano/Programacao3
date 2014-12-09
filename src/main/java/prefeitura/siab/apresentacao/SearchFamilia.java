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
import prefeitura.siab.controller.FamiliaController;
import prefeitura.siab.controller.PessoaController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Endereco;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Pessoa;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchFamilia {

	//ATRIBUTOS
	private @Autowired FamiliaController controller;
	private Familia options;
	private List<Familia> result;
	private FamiliaForm form;
	//ACS's
	private AcsController controllerAcs;
	private List<Acs> agentes;
	//ENDEREÇOS
	private EnderecoController controllerEndereco;
	private List<Endereco> enderecos;
	private PessoaController controllerPessoa;
	
	
	
	//PROPRIEDADES
	public Familia getOptions() {
		return options;
	}
	public void setOptions(Familia options) {
		this.options = options;
	}
	public List<Familia> getResult() {
		return result;
	}
	public void setResult(List<Familia> result) {
		this.result = result;
	}
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
		public FamiliaForm getForm() {
		return form;
	}
	public void setForm(FamiliaForm form) {
		this.form = form;
	}
	
	//CONSTRUTOR
	public SearchFamilia() {
		result = null;
		reset();
		form = new FamiliaForm();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerAcs = applicationContext.getBean(AcsController.class);
		controllerEndereco = applicationContext.getBean(EnderecoController.class);
		controllerPessoa = applicationContext.getBean(PessoaController.class);
		agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
		enderecos = controllerEndereco.searchListEndereco(new Endereco());
	}
	
	
	//MÉTODOS
	public void reset(){
		options = new Familia();
	}
	
	public String search(){
		result = controller.searchFamilia(options);
		return null;
	}
	
	//Carrega todos os ACS's
	public void setAcsMatricula(Integer matricula){
		if(matricula == 0 || matricula == null){
			options.setAgente(null);
			enderecos = controllerEndereco.searchListEndereco(new Endereco());
		}else{
			for(Acs agente: agentes){
				if(agente.getMatricula().equals(matricula)){
					options.setAgente(agente);
					Endereco aux = new Endereco();
					aux.setAgente(agente);
					enderecos = controllerEndereco.searchListEndereco(aux);
					break;
				}
			}
			
		}
	}
	
	public Integer getAcsMatricula(){
		if(options.getAgente() == null){
			return null;			
		}else{
			return options.getAgente().getMatricula();
		}
	}
	
	//Carrega todos os Endereços
	public void setEnderecoFamilia(Integer cep){
		if(cep == 0 || cep == null){
			options.setRua(null);
		}else{
			for(Endereco end: enderecos){
				if(end.getCep().equals(cep)){
					options.setRua(end);
					options.setAgente(end.getAgente());
					break;
				}
			}
		}
	}
	
	public Integer getEnderecoFamilia(){
		if(options.getRua() == null){
			return null;			
		}else{
			return options.getRua().getCep();
		}
	}
	
	
	public String update(Familia familia){
		Familia familiaAux = new Familia();
		familiaAux.setAgente(familia.getAgente());
		familiaAux.setCodigo(familia.getCodigo());
		familiaAux.setNumero(familia.getNumero());
		familiaAux.setPessoas(familia.getPessoas());
		familiaAux.setRua(familia.getRua());
		this.options = familiaAux;
		form.setFamilia(familiaAux);
		form.setPessoas(familiaAux.getPessoas());
		//reset();
		//result = null;
		return "updateFamilia";
	}
	
	
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			for(Pessoa p: options.getPessoas()){
				Pessoa aux = controllerPessoa.searchPessoaSus(p.getSus());
				if(aux != null){
					controllerPessoa.updatePessoa(p);
				}else{
					controllerPessoa.salvarPessoa(p);
				}
			}
			controller.updateFamilia(options);
			reset();
			result = null;
			message.setSummary("Família atualizado com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	 
	
	
	public String confirmDeletion(Familia familia) throws BusinessException{
		controller.deleteFamilia(familia);
		options = new Familia();
		for(int i = 0; i < result.size(); i++){
			if(result.get(i).equals(familia)){
				result.remove(i);
			}
		}
		FacesMessage message = new FacesMessage();
		message.setSummary("Família deletada com Sucesso!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
}