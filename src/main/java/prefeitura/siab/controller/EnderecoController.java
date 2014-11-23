package prefeitura.siab.controller;

import java.util.List;

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
	
	public List<Endereco> searchListEndereco(Endereco endereco) {
		return dao.searchListEndereco(endereco);
	}

	@Transactional
	public void updateEndereco(Endereco endereco) throws BusinessException {
		dao.updateEndereco(endereco);
	}
	
	@Transactional
	public void deleteEndereco(Endereco endereco) throws BusinessException{
		Endereco enderecoAux = dao.searchEnderecoName(endereco.getRua());
		if(enderecoAux != null){
			dao.delete(enderecoAux);
		}else{
			throw new BusinessException("Impossível deletar, pois a rua "+ endereco.getRua() + " não existe!");
		}
	}
	
}
