package prefeitura.siab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.EnderecoDao;
import prefeitura.siab.tabela.Endereco;

@Component
public class EnderecoController {

	private @Autowired EnderecoDao dao;

	@Transactional
	public void salvarEndereco(Endereco endereco) throws BusinessException {
		Endereco aux = dao.searchEndereco(endereco);
		if(aux != null){
			if(aux.getCep().equals(endereco.getCep())){
				throw new BusinessException("Esse CEP já foi cadastrado na rua " + aux.getRua());
			}
		}
		dao.insert(endereco);
		
	}
	
}
