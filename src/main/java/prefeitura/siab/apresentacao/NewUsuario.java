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

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.EnfermeiraController;
import prefeitura.siab.controller.UsuarioController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Enfermeira;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NewUsuario {

	//ATRIBUTOS
	private @Autowired UsuarioController controller;
	private Usuario usuario;
	private String senha1;
	private String senha2;
	private boolean acs;
	private boolean enfermeira;
	private boolean admin;
	private boolean disabled;
	//ENFERMEIRA
	private @Autowired EnfermeiraController controllerEnfermeira;
	private List<Enfermeira> supervisores;
	
	//PROPRIEDADES
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	public boolean getAcs() {
		return acs;
	}
	public void setAcs(boolean acs) {
		this.acs = acs;
	}
	public boolean getEnfermeira() {
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
	public boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	//CONSTRUTOR
	public NewUsuario() {
		usuario = new Usuario();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerEnfermeira = applicationContext.getBean(EnfermeiraController.class);
		inicializar();		
	}
	
	public void inicializar(){
		supervisores = controllerEnfermeira.searchListEnfermeira(new Enfermeira());
	}

	//MÉTODOS
	public String saveUsuario(){
		FacesMessage message = new FacesMessage();
		if(senha1.equals(senha2)){
			usuario.setSenha(senha1);
			try{
				controller.salvarUsuario(usuario);
				message.setSummary("Usuário salvo com Sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				reset();
			}catch(BusinessException e){
				message.setSummary(e.getMessage());
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
		}else{
			senha1 = null;
			senha2 = null;
			message.setSummary("As senhas informadas estão incorretas");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
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
				this.usuario.setEnfermeira(null);
				this.usuario.setTipo(TipoUsuario.ACS);
			}else if(codigo.equals(1)){
				this.acs = false;
				this.enfermeira = true;
				this.admin = false;
				this.usuario.setAcs(null);
				this.usuario.setEnfermeira(new Enfermeira());
				this.usuario.setTipo(TipoUsuario.ENFERMEIRA);
			}else if(codigo.equals(0)){
				this.enfermeira = false;
				this.acs = false;
				this.admin = true;
				this.usuario.setAcs(null);
				this.usuario.setEnfermeira(null);
				this.usuario.setTipo(TipoUsuario.ADMINISTRADOR);
			}
		}
	}
	public Integer getEscolherTipo(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login autenticacaoBean = (Login) mapa.get("login");
		Usuario usuario = autenticacaoBean.getUsuario();
		if(usuario != null){
			if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				this.disabled = true;
				this.acs = true;
				this.enfermeira = false;
				this.admin = false;
				this.usuario.setAcs(new Acs());
				this.usuario.getAcs().setSupervisor(new Enfermeira());
				this.usuario.setEnfermeira(null);
				this.usuario.setTipo(TipoUsuario.ACS);
				return 2;
			}else if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
				if(this.usuario.getTipo() == null){
					return null;
				}else{
					return this.usuario.getTipo().ordinal();					
				}
			}
		}
		return 100;
	}
	
	public void reset(){
		this.usuario = new Usuario();
		this.senha1 = null;
		this.senha2 = null;
		this.acs = false;
		this.enfermeira = false;
		this.admin = false;
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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login autenticacaoBean = (Login) mapa.get("login");
		Usuario usuario = autenticacaoBean.getUsuario();
		if(usuario != null){
			if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				this.disabled = true;
				this.usuario.getAcs().getSupervisor().setMatricula(usuario.getMatricula());
				return usuario.getMatricula();
			}else if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
				this.disabled = false;
				if(this.usuario.getAcs().getSupervisor() != null){
					return this.usuario.getAcs().getSupervisor().getMatricula();					
				}else{
					return null;
				}
			}
		}
		return null;
	}
	
}
