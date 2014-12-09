package prefeitura.siab.controller;

import java.util.ArrayList;
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
		dao.insert(pessoa);
		return null;
	}

	public List<Pessoa> searchPessoa(Pessoa pessoa) {
		return dao.searchListPessoa(pessoa);
	}

	public Pessoa searchPessoaSus(Integer sus) {
		return dao.searchPessoaSus(sus);
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
				throw new BusinessException("Já existe uma Pessoa com o nome: "+ pessoa.getNome());
			}
		}
	}

	@Transactional
	public void deletePessoa(Pessoa pessoa) throws BusinessException{
		Pessoa pessoaAux = dao.searchPessoaSus(pessoa.getSus());
		if(pessoaAux != null){
			dao.delete(pessoa);
		}else{
			throw new BusinessException("Impossível deletar, pois essa pessoa '"+ pessoa.getNome() + "' não existe!");
		}
	}

	public List<Pessoa> searchListPessoa(PessoaSearchOptions options) {
		
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

}
