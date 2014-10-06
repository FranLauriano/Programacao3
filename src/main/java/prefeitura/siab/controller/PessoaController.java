package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.PessoaDao;
import prefeitura.siab.tabela.Pessoa;

@Component
public class PessoaController {

	private @Autowired PessoaDao dao;
	
	@Transactional
	public String salvarPessoa(Pessoa pessoa) throws BusinessException{
		Pessoa auxiliar = dao.searchPessoa(pessoa);
		if(auxiliar != null){
			if(auxiliar.getCodigo().equals(pessoa.getCodigo()) && auxiliar.getNome().equals(pessoa.getNome())){
				throw new BusinessException("Essa Pessoa já está cadastrada!!!");
			}else if(auxiliar.getCodigo().equals(pessoa.getCodigo())){
				throw new BusinessException("Esse Código já foi Cadastrado, favor modificar.");
			}else if(auxiliar.getNome().equals(pessoa.getNome())){
				throw new BusinessException("Esse Nome já foi Cadastrado, favor modificar. ");
			}
			throw new BusinessException("Impossível salvar essa Raça!");
		}
		dao.insert(pessoa);
		return null;
	}

	public List<Pessoa> searchPessoa(Pessoa pessoa) {
		return dao.searchListPessoa(pessoa);
	}

	@Transactional
	public void updatePessoa(Pessoa pessoa) throws BusinessException {
		Pessoa pessoaAux = dao.searchPessoaName(pessoa.getNome());
		if(pessoaAux == null){
			dao.updatePessoa(pessoa);
		}else{
			if(pessoa.getCodigo() == pessoaAux.getCodigo() && pessoa.getNome().equals(pessoaAux.getNome())){
				dao.updatePessoa(pessoa);
			}else{
				throw new BusinessException("Já existe uma Pessoa com o nome: "+ pessoa.getNome());
			}
		}
	}

	@Transactional
	public void deletePessoa(Pessoa pessoa) throws BusinessException{
		Pessoa pessoaAux = dao.searchPessoaName(pessoa.getNome());
		if(pessoaAux != null){
			dao.delete(pessoa);
		}else{
			throw new BusinessException("Impossível deletar, pois essa pessoa '"+ pessoa.getNome() + "' não existe!");
		}
	}

}
