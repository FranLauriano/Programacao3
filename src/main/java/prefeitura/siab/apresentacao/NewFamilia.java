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
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Endereco;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Pessoa;


@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewFamilia {

	//ATRIBUTOS
	private @Autowired FamiliaController familiaController;
	//private @Autowired Facade facade;
	private AcsController controllerAcs;
	private EnderecoController controllerEndereco;
	private Familia familia;
	private Pessoa aux;
	private List<Pessoa> pessoas;
	private List<Endereco> endereco;
	private List<Acs> agentes; 
	
	//PROPRIEDADES
	public Familia getFamilia() {
		return familia;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	public Pessoa getAux() {
		return aux;
	}
	public void setAux(Pessoa aux) {
		this.aux = aux;
	}
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	public List<Endereco> getEndereco() {
		return endereco;
	}
	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	
	//CONSTRUTOR
	public NewFamilia() {
		familia = new Familia();
		familia.setAgente(new Acs());
		familia.setRua(new Endereco());
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerAcs = applicationContext.getBean(AcsController.class);
		controllerEndereco = applicationContext.getBean(EnderecoController.class);
		agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
		endereco = controllerEndereco.searchListEndereco(new Endereco());
	}
	

	//MÉTODOS
	public String saveFamilia(){
		FacesMessage message = new FacesMessage();
		try{
			familiaController.salvarFamilia(familia);
			message.setSummary("Família Cadastrada com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public void setAcsMatricula(Integer matricula){
		if(matricula == 0 || matricula == null){
			familia.getRua().setAgente(null);
		}else{
			for(Acs agente: agentes){
				if(agente.getMatricula().equals(matricula)){
					familia.getRua().setAgente(agente);
					Endereco aux = new Endereco();
					aux.setAgente(agente);
					endereco = controllerEndereco.searchListEndereco(aux);
					break;
				}
			}
		}
	}
	
	public Integer getAcsMatricula(){
		if(familia.getRua().getAgente() == null){
			return null;			
		}else{
			return familia.getRua().getAgente().getMatricula();
		}
	}
	
	public void setEnderecoFamilia(Integer cep){
		if(cep == null || cep == 0){
			familia.getRua().setRua(null);
		}else{
			for(Endereco rua: endereco){
				if(rua.getCep().equals(cep)){
					familia.setRua(rua);
					familia.setAgente(rua.getAgente());
					break;
				}
			}
		}
	}
	
	public Integer getEnderecoFamilia(){
		if(familia.getRua() == null){
			return null;			
		}else{
			return familia.getRua().getCep();
		}
	}
	
	public void teste(){
		System.out.println("Testando!!!!!");
	}
}
