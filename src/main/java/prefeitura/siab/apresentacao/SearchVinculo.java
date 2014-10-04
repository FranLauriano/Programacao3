package prefeitura.siab.apresentacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.VinculoController;
import prefeitura.siab.tabela.VinculoEmpregaticio;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class SearchVinculo {

	//ATRIBUTOS
	private @Autowired VinculoController controller;
	private VinculoEmpregaticio vinculo;
	private List<VinculoEmpregaticio> result;

	//PROPRIEDADES
	public VinculoEmpregaticio getVinculo() {
		return vinculo;
	}
	
	public void setVinculo(VinculoEmpregaticio vinculo) {
		this.vinculo = vinculo;
	}
	
	public List<VinculoEmpregaticio> getResult() {
		return result;
	}
	
	public void setResult(List<VinculoEmpregaticio> result) {
		this.result = result;
	}
	
	
	//CONSTRUTOR
	public SearchVinculo() {
		vinculo = new VinculoEmpregaticio();
	}
	

	//MÉTODOS
	public String search(){
		result = controller.searchVinculo(vinculo);
		return null;
	}
	
	
}