package prefeitura.siab.apresentacao;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.controller.UsuarioController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Login implements Serializable{

	private static final long serialVersionUID = 1L;

	//ATRIBUTOS
	private @Autowired AcsController controllerAcs;
	private @Autowired UsuarioController controllerUsuario;
	private String template;
	private String nome;
	private Usuario usuario;
	private AgenteForm agente;

	//PROPRIEDADES
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getTemplate() {
		return template;
	}
	public AgenteForm getAgente() {
		return agente;
	}
	public void setAgente(AgenteForm agente) {
		this.agente = agente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//CONSTRUTOR
	public Login() {
		usuario = new Usuario();
		agente = new AgenteForm();
	}


	//MÉTODOS
	public String entrar(){
		FacesMessage message = new FacesMessage();
		if(usuario.getMatricula() != null && usuario.getMatricula() != 0 && usuario.getSenha() != null && usuario.getSenha().length() > 6){
			Usuario usuarioAux = controllerUsuario.searchUsuarioAutentication(usuario);
			if(usuarioAux == null){
				message.setSummary("Matricula e/ou Senha Incorretas");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				reset();
			}else{
				usuario = usuarioAux;
				AcsSearchOptions aux = new AcsSearchOptions();
				aux.setMatricula(usuario.getMatricula());
				Acs acsAux = controllerAcs.searchAcs(aux);
				agente.setAgente(acsAux);
				nome = usuario.getNome();
				if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
					this.template = "/templateAdmin.xhtml";
				}else if(usuario.getTipo().equals(TipoUsuario.ACS)){
					this.template = "/templateAcs.xhtml";
				}else if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
					this.template = "/templateAdmin.xhtml";
				}else{
					this.template = "/403.xhtml";
					return "403";
				}
				return "inicio";
			}
		}else{
			message.setSummary("Matricula e/ou Senha Incorretas");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			reset();
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public void reset(){
		usuario.setMatricula(null);
		usuario.setSenha(null);
	}
	
	public String sair(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		mapa.clear();
		return "sair";
	}
	
	public String voltar(){
		return "inicio";
	}
	
	
}
