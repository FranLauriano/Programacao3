package prefeitura.siab.apresentacao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.EscolaridadeController;
import prefeitura.siab.tabela.Escolaridade;

public class SearchEscolaridade {
	// ATRIBUTOS
	private @Autowired
	EscolaridadeController controller;
	private Escolaridade escolaridade;
	private List<Escolaridade> result;
	private EscolaridadeSearchOptions options;

	// PROPRIEDADES
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public List<Escolaridade> getResult() {
		return result;
	}

	public void setResult(List<Escolaridade> result) {
		this.result = result;
	}

	public EscolaridadeSearchOptions getOptions() {
		return options;
	}

	public void setOptions(EscolaridadeSearchOptions options) {
		this.options = options;
	}

	// CONSTRUTOR
	public SearchEscolaridade() {
		reset();
	}

	// MÉTODOS
	public String search() {
		Escolaridade search = new Escolaridade();
		search.setCodigo(options.getCodigo());
		search.setNome(options.getNome());
		result = controller.searchEscolaridade(search);
		return null;
	}

	public String update(Escolaridade escolaridade) {
		Escolaridade escolaridadeAux = new Escolaridade();
		escolaridadeAux.setCodigo(escolaridade.getCodigo());
		escolaridadeAux.setNome(escolaridade.getNome());
		this.escolaridade = escolaridadeAux;
		return "updateEscolaridade";
	}

	public void reset() {
		options = new EscolaridadeSearchOptions();
		result = null;
	}

	public String confirmUpdate() {
		FacesMessage message = new FacesMessage();
		try {
			controller.updateEscolaridade(escolaridade);
			reset();
			message.setSummary("Raça foi atualizada!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		} catch (BusinessException e) {
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}

	public String confirmDeletion(Escolaridade escolaridadeAux) throws BusinessException {
		controller.deleteEscolaridade(escolaridadeAux);
		options = new EscolaridadeSearchOptions();
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).equals(escolaridade)) {
				result.remove(i);
			}
		}
		FacesMessage message = new FacesMessage();
		message.setSummary("Escolaridade foi Deletada!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}

	public String back() {
		reset();
		return "searchEscolaridade";
	}
}
