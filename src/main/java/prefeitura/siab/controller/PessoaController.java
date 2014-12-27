package prefeitura.siab.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.apresentacao.PessoaSearchOptions;
import prefeitura.siab.persistencia.PessoaDao;
import prefeitura.siab.tabela.Doenca;
import prefeitura.siab.tabela.Pessoa;

@Component
public class PessoaController {

	private @Autowired PessoaDao dao;
	
	@Transactional
	public String salvarPessoa(Pessoa pessoa) throws BusinessException{
		Pessoa auxiliar = dao.searchPessoaSus(pessoa.getSus());
		if(auxiliar != null){
			if(auxiliar.getSus().equals(pessoa.getSus()) && auxiliar.getNome().equals(pessoa.getNome())){
				throw new BusinessException("Essa Pessoa já está cadastrada!!!");
			}else if(auxiliar.getSus().equals(pessoa.getSus())){
				throw new BusinessException("Esse SUS já foi Cadastrado, favor modificar.");
			}else if(auxiliar.getNome().equals(pessoa.getNome())){
				throw new BusinessException("Esse Nome já foi Cadastrado, favor modificar. ");
			}
			throw new BusinessException("Impossível salvar essa Pessoa!");
		}
		if(pessoa.getFamilia().getCodigo() == null || pessoa.getFamilia().getCodigo() == 0){
			throw new BusinessException("Família: Favor escolher uma Família Válida!");
		}
		if(pessoa.getSus() == null || pessoa.getSus().length() < 6){
			throw new BusinessException("SUS: Favor inserir um Número do SUS Válido");
		}
		if(pessoa.getNome() == null || pessoa.getNome().length() < 5){
			throw new BusinessException("Nome: Favor inserir um Nome Válido");	
		}
		if(pessoa.getDtnascimento() == null){
			throw new BusinessException("Data de Nascimento: Favor escolher uma Data de Nascimento Válida!");
		}
		if(pessoa.getRaca().getCodigo() == null || pessoa.getRaca().getCodigo() == 0){
			throw new BusinessException("Raça: Favor escolher uma Raça Válida!");
		}
		if(pessoa.getEscolaridade().getCodigo() == null || pessoa.getEscolaridade().getCodigo() == 0){
			throw new BusinessException("Escolaridade: Favor escolher uma Escolaridade Válida!");
		}
		if(pessoa.getVinculo().getCodigo() == null || pessoa.getVinculo().getCodigo() == 0){
			throw new BusinessException("Vínculo Empregatício: Favor escolher um Vínculo Empregatício Válido!");
		}
		dao.insert(pessoa);
		return null;
	}
	
	public void verificaPessoa(Pessoa pessoa) throws BusinessException{
		if(pessoa.getSus() == null || pessoa.getSus().length() < 6){
			throw new BusinessException("SUS: Favor inserir um Número do SUS Válido");
		}
		if(pessoa.getNome() == null || pessoa.getNome().length() < 5){
			throw new BusinessException("Nome: Favor inserir um Nome Válido");	
		}
		if(pessoa.getDtnascimento() == null){
			throw new BusinessException("Data de Nascimento: Favor escolher uma Data de Nascimento Válida!");
		}
		if(pessoa.getRaca().getCodigo() == null || pessoa.getRaca().getCodigo() == 0){
			throw new BusinessException("Raça: Favor escolher uma Raça Válida!");
		}
		if(pessoa.getEscolaridade().getCodigo() == null || pessoa.getEscolaridade().getCodigo() == 0){
			throw new BusinessException("Escolaridade: Favor escolher uma Escolaridade Válida!");
		}
		if(pessoa.getVinculo().getCodigo() == null || pessoa.getVinculo().getCodigo() == 0){
			throw new BusinessException("Vínculo Empregatício: Favor escolher um Vínculo Empregatício Válido!");
		}
	}
	
	@Transactional
	public void updatePessoa(Pessoa pessoa) throws BusinessException {
		Pessoa pessoaAux = dao.searchPessoaSus(pessoa.getSus());
		if(pessoaAux == null){
			dao.updatePessoa(pessoa);
		}else{
			if(pessoa.getSus().equals(pessoaAux.getSus())){
				dao.updatePessoa(pessoa);
			}else{
				throw new BusinessException("Já existe uma cadastrada com esse número do SUS: ");
			}
		}
	}

	@Transactional
	public void deletePessoa(Pessoa pessoa) throws BusinessException{
		Pessoa pessoaAux = dao.searchPessoaCodigo(pessoa.getCodigo());
		if(pessoaAux != null){
			dao.delete(pessoa);
		}else{
			throw new BusinessException("Impossível deletar, pois essa pessoa '"+ pessoa.getNome() + "' não existe!");
		}
	}

	@Transactional
	public List<Pessoa> searchListPessoa(PessoaSearchOptions options) {
		atualizar();
		if(options.getDoencas() != null && options.getDoencas().size() != 0){
			List<Pessoa> resultado = new ArrayList<>();
			List<Pessoa> resultDao = dao.searchListOptionsPessoa(options);
			boolean achou = false;
			
			for(Pessoa pessoa: resultDao){
				for(Doenca doenca: options.getDoencas()){
					for(Doenca pessoaDoente: pessoa.getSituacao()){
						String sigla = pessoaDoente.getSigla();
						if(sigla.equals(doenca.getSigla())){
							achou = true;
							break;
						}else{
							achou = false;
						}
					}
					if(!achou){
						break;
					}
				}
				if(achou){
					resultado.add(pessoa);
					achou = false;
				}
			}
			return resultado;
		}else{
			return dao.searchListOptionsPessoa(options);
		}
	}

	public Pessoa searchPessoaCodigo(Integer codigo) {
		return dao.searchPessoaCodigo(codigo);
	}
	
	
	public void atualizar(){
		List<Pessoa> pessoas = dao.searchListOptionsPessoa(new PessoaSearchOptions());
		for(Pessoa p: pessoas){
			calculaIdade(p);
		}
	}
	
	
	public void calculaIdade(Pessoa pessoa) {  
	    Calendar dataNascimento = Calendar.getInstance();  
	    dataNascimento.setTime(pessoa.getDtnascimento());  
	    Calendar dataAtual = Calendar.getInstance();  
	  
	    Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);  
	    Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);  
	    Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));  
	  
	    if(diferencaMes < 0  || (diferencaMes == 0 && diferencaDia < 0)) {  
	        idade--;  
	    }  
	      
	    pessoa.setIdade(idade);  
	    try{
	    	this.updatePessoa(pessoa);	    	
	    }catch(BusinessException e){
	    	System.out.println(e.toString());
	    }
	}

}
