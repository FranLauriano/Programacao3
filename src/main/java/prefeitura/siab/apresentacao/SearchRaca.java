package prefeitura.siab.apresentacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.RacaController;
import prefeitura.siab.tabela.Raca;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class SearchRaca {

	//ATRIBUTOS
	private @Autowired RacaController controller;
	private Raca raca;
	private List<Raca> result;

	//PROPRIEDADES
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	public List<Raca> getResult() {
		return result;
	}
	public void setResult(List<Raca> result) {
		this.result = result;
	}
	
	//CONSTRUTOR
	public SearchRaca() {
		raca = new Raca();
	}
	
	//MÉTODOS
	public String search(){
		result = controller.searchRaca(raca);
		return null;
	}
	
	
}