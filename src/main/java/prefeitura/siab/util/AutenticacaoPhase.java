package prefeitura.siab.util;

import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import prefeitura.siab.apresentacao.Login;
import prefeitura.siab.tabela.Acs;

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
				Acs servidor = autenticacaoBean.getServidor();

				if(servidor == null){
					NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
					nh.handleNavigation(facesContext, null, "loginPage");
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
