package prefeitura.siab.util;

import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import prefeitura.siab.apresentacao.Login;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;

public class AutenticacaoPhase implements PhaseListener{

	private static final long serialVersionUID = 1L;

	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String paginaAtual = facesContext.getViewRoot().getViewId();
		
		boolean pageLogin = (paginaAtual.lastIndexOf("login.xhtml") > -1);
		
		if(!pageLogin){
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> mapa = externalContext.getSessionMap();
			Login autenticacaoBean = (Login) mapa.get("login");
			if(autenticacaoBean != null){
				Usuario usuario = autenticacaoBean.getUsuario();
				
				if(usuario == null){
					NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
					nh.handleNavigation(facesContext, null, "loginPage");
				}else{
					if(usuario.getTipo() != null){
						if(usuario.getTipo().equals(TipoUsuario.ACS)){
							boolean tenhoPermisao = (	paginaAtual.lastIndexOf("/familia/") > -1) 
													|| (paginaAtual.lastIndexOf("/pessoa/") > -1)
													|| (paginaAtual.lastIndexOf("/logout/") > -1)
													|| (paginaAtual.lastIndexOf("index.xhtml") > -1) 
													|| (paginaAtual.lastIndexOf("403.xhtml") > -1) 
													|| (paginaAtual.lastIndexOf("500.xhtml") > -1) 
													|| (paginaAtual.lastIndexOf("404.xhtml") > -1);
							
							if(!tenhoPermisao){
								NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
								nh.handleNavigation(facesContext, null, "403");
							}
						}
						if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
							boolean tenhoPermisao = !	(paginaAtual.lastIndexOf("/usuario/") > -1)
													|| 	(paginaAtual.lastIndexOf("/logout/") > -1)
													|| 	(paginaAtual.lastIndexOf("newUsuario.xhtml") > -1)
													|| 	(paginaAtual.lastIndexOf("index.xhtml") > -1) 
													|| 	(paginaAtual.lastIndexOf("403.xhtml") > -1) 
													|| 	(paginaAtual.lastIndexOf("500.xhtml") > -1) 
													|| 	(paginaAtual.lastIndexOf("404.xhtml") > -1);
							
							if(!tenhoPermisao){
								NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
								nh.handleNavigation(facesContext, null, "403");
							}
						}
					}else{
						NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
						nh.handleNavigation(facesContext, null, "loginPage");
					}	
				}
			}else{
				NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
				nh.handleNavigation(facesContext, null, "loginPage");
			}
			
		}
	}

	public void beforePhase(PhaseEvent event) {
		
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
