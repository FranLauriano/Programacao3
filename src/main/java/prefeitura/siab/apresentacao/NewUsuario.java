package prefeitura.siab.apresentacao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.UsuarioController;
import prefeitura.siab.tabela.Usuario;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewUsuario {

	//ATRIBUTOS
	private @Autowired UsuarioController controller;
	private Usuario usuario;
	private String senha1;
	private String senha2;

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
	
	//CONSTRUTOR
	public NewUsuario() {
		usuario = new Usuario();
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
	
	
}
