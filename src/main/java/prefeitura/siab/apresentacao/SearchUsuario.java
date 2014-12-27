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

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.EnfermeiraController;
import prefeitura.siab.controller.UsuarioController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Enfermeira;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchUsuario {

	//ATRIBUTOS
	private @Autowired UsuarioController controller;
	private @Autowired EnfermeiraController controllerEnfermeira;
	private List<Enfermeira> supervisores;
	private List<Usuario> result;
	private Usuario usuario;
	private Usuario options;
	private String senha1;
	private String senha2;
	private String senhaAntiga;
	private boolean acs;
	private boolean enfermeira;
	private boolean admin;


	//PROPRIEDADES
	public List<Usuario> getResult() {
		return result;
	}
	public void setResult(List<Usuario> result) {
		this.result = result;
	}
	public Usuario getOptions() {
		return options;
	}
	public void setOptions(Usuario options) {
		this.options = options;
	}
	public String getSenha1() {
		return senha1;
	}
	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}
	public String getSenha2() {
		return senha2;
	}
	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getSenhaAntiga() {
		return senhaAntiga;
	}
	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}
	public boolean isAcs() {
		return acs;
	}
	public void setAcs(boolean acs) {
		this.acs = acs;
	}
	public boolean isEnfermeira() {
		return enfermeira;
	}
	public void setEnfermeira(boolean enfermeira) {
		this.enfermeira = enfermeira;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public List<Enfermeira> getSupervisores() {
		return supervisores;
	}
	public void setSupervisores(List<Enfermeira> supervisores) {
		this.supervisores = supervisores;
	}

	//CONSTRUTOR
	public SearchUsuario() {
		reset();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerEnfermeira = applicationContext.getBean(EnfermeiraController.class);
		supervisores = controllerEnfermeira.searchListEnfermeira(new Enfermeira());
	}
	
	//MÉTODOS
	public String search(){
		result = controller.searchListUsuario(options);
		return null;
	}
	
	public String update(Usuario usuario){
		Usuario auxiliar = usuario.clone();
		if(auxiliar.getTipo().equals(TipoUsuario.ACS)){
			this.acs = true;
			this.enfermeira = false;
			this.admin = false;
		}else if(auxiliar.getTipo().equals(TipoUsuario.ENFERMEIRA)){
			this.acs = false;
			this.enfermeira = true;
			this.admin = false;
		}else{
			this.acs = false;
			this.enfermeira = false;
			this.admin = true;
		}
		this.usuario = auxiliar;
		return "updateUsuario";
	}
	
	public String reset() {
		options = new Usuario();
		usuario = new Usuario();
		result = null;
		return "searchUsuario";
	}
	
	public void resetSenhas(){
		this.setSenha1(null);
		this.setSenha2(null);
		this.setSenhaAntiga(null);
	}
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		
		try{
			controller.updateUsuario(usuario, senha1, senha2);
			resetSenhas();
			message.setSummary("Usuario atualizado com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	
	public String confirmDeletion(Usuario usuario) throws BusinessException{
		controller.deleteUsuario(usuario);
		options = new Usuario();	
		boolean achou = false;
		for(Usuario usu: result){
			if(usu.getMatricula().equals(usuario.getMatricula())){
				achou = true;
			}
		}
		if(achou){
			result.remove(usuario);
		}
		FacesMessage message = new FacesMessage();
		message.setSummary("Usuario Deletado com Sucesso!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String back(){
		reset();
		return "searchUpdate";
	}
	
	//ESCOLHER TIPO
	public void setEscolherTipo(Integer codigo){
		if(codigo.equals(100)){
			this.usuario.setTipo(null);
			this.acs = false;
			this.enfermeira = false;
			this.admin = false;
		}else{
			if(codigo.equals(2)){
				this.acs = true;
				this.enfermeira = false;
				this.admin = false;
				this.usuario.setAcs(new Acs());
				this.usuario.getAcs().setSupervisor(new Enfermeira());
				this.usuario.setTipo(TipoUsuario.ACS);
			}else if(codigo.equals(1)){
				this.acs = false;
				this.enfermeira = true;
				this.admin = false;
				this.usuario.setEnfermeira(new Enfermeira());
				this.usuario.setTipo(TipoUsuario.ENFERMEIRA);
			}else if(codigo.equals(0)){
				this.enfermeira = false;
				this.acs = false;
				this.admin = true;
				this.usuario.setTipo(TipoUsuario.ADMINISTRADOR);
			}
		}
	}
	public Integer getEscolherTipo(){
		if(usuario.getTipo() != null){
			return usuario.getTipo().ordinal();
		}
		return 100;
	}
	
	//CARREGA A LISTA DE ENFERMEIRAS NO SELECTMENU
	public void setEnfermeiras(Integer matricula){
		if(matricula == null || matricula == 0){
			usuario.getAcs().getSupervisor().setMatricula(null);
		}else{
			for(Enfermeira sup: supervisores){
				if(sup.getMatricula().equals(matricula)){
					usuario.getAcs().getSupervisor().setMatricula(matricula);
					break;
				}
			}
		}
	}
	public Integer getEnfermeiras(){
		
		if(usuario.getTipo().equals(TipoUsuario.ACS)){
			if(usuario.getAcs() != null && usuario.getAcs().getSupervisor() != null){
				return usuario.getAcs().getSupervisor().getMatricula();				
			}else{
				return null;
			}
		}			
		
		return null;
	}

}