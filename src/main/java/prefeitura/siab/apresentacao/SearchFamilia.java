package prefeitura.siab.apresentacao;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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
import prefeitura.siab.controller.EnfermeiraController;
import prefeitura.siab.controller.FamiliaController;
import prefeitura.siab.controller.PessoaController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Endereco;
import prefeitura.siab.tabela.Enfermeira;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Pessoa;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchFamilia {

	//ATRIBUTOS
	private @Autowired FamiliaController controller;
	private Familia options;
	private List<Familia> result;
	private FamiliaForm form;
	//ENFERMEIRAS
	private EnfermeiraController controllerEnfermeira;
	private List<Enfermeira> supervisores;
	//ACS's
	private AcsController controllerAcs;
	private List<Acs> agentes;
	//ENDEREÇOS
	private EnderecoController controllerEndereco;
	private List<Endereco> enderecos;
	private PessoaController controllerPessoa;
	private boolean disabled;
	private boolean disabledSuper;
	
	
	
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
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isDisabledSuper() {
		return disabledSuper;
	}
	public void setDisabledSuper(boolean disabledSuper) {
		this.disabledSuper = disabledSuper;
	}
	public List<Enfermeira> getSupervisores() {
		return supervisores;
	}
	public void setSupervisores(List<Enfermeira> supervisores) {
		this.supervisores = supervisores;
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
		controllerEnfermeira = applicationContext.getBean(EnfermeiraController.class);

		enderecos = controllerEndereco.searchListEndereco(new Endereco());
		supervisores = controllerEnfermeira.searchListEnfermeira(new Enfermeira());
		init_agentes();
	}
	
	public void init_agentes(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login autenticacaoBean = (Login) mapa.get("login");
		Usuario usuario = autenticacaoBean.getUsuario();
		if(usuario != null){
			if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				agentes = usuario.getEnfermeira().getAgentes();
			}else{
				agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
			}
		}
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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login autenticacaoBean = (Login) mapa.get("login");
		Acs servidor = autenticacaoBean.getAgente().getAgente();
		if(servidor == null){
			return null;
		}else{
			Endereco aux = new Endereco();
			aux.setAgente(servidor);
			enderecos = controllerEndereco.searchListEndereco(aux);
			options.setAgente(servidor);
			this.disabled = true;
			return servidor.getMatricula();
		}
	}
	
	public Integer getSupervisorMatricula(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login autenticacaoBean = (Login) mapa.get("login");
		Usuario usuario = autenticacaoBean.getUsuario();
		if(usuario != null){
			if(usuario.getTipo().equals(TipoUsuario.ACS)){
				//options.getAgente().setSupervisor(usuario.getAcs().getSupervisor());
				this.disabledSuper = true;
				return usuario.getAcs().getSupervisor().getMatricula();
			}else if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				//options.getAgente().setSupervisor(usuario.getEnfermeira());
				this.disabledSuper = true;
				return usuario.getEnfermeira().getMatricula();
			}else if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
				if(usuario.getAcs() == null && usuario.getEnfermeira() == null){
					this.disabled = false;
					this.disabledSuper = false;
					return null;
				}else if(usuario.getAcs() == null){
					//options.getAgente().setSupervisor(usuario.getAcs().getSupervisor());
					this.disabledSuper = true;
					return usuario.getEnfermeira().getMatricula();
				}else if(usuario.getEnfermeira() == null){
					this.disabled = true;
					this.disabledSuper = true;
					//options.setAgente(usuario.getAcs());
					return usuario.getAcs().getSupervisor().getMatricula();
				}
			}
		}
		return null;
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
		familiaAux.setNumeroFamilia(familia.getNumeroFamilia());
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
			PessoaSearchOptions pAux = new PessoaSearchOptions();
			Familia fam = new Familia();
			fam.setCodigo(options.getCodigo());
			pAux.setFamilia(fam);
			List<Pessoa> jaCadastrada = controllerPessoa.searchListPessoa(pAux);
			for(Pessoa p: options.getPessoas()){
				Pessoa aux = controllerPessoa.searchPessoaCodigo(p.getCodigo());
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