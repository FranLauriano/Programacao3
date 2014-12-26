package prefeitura.siab.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.EnderecoDao;
import prefeitura.siab.tabela.Acs;
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
		if(endereco.getCep() == null || endereco.getCep() == 0){
			throw new BusinessException("CEP: Campo Obrigatório.");
		}
		if(endereco.getRua() == null || endereco.getRua().length() == 0){
			throw new BusinessException("Nome da Rua: Campo Obrigatório.");
		}
		if(endereco.getBairro() == null || endereco.getBairro().length() == 0){
			throw new BusinessException("Bairro: Campo Obrigatório.");
		}
		if(endereco.getMunicipio() == null || endereco.getMunicipio().length() == 0){
			throw new BusinessException("Município: Campo Obrigatório.");
		}
		if(endereco.getUf() == null || endereco.getUf().length() == 0){
			throw new BusinessException("UF: Campo Obrigatório.");
		}
		dao.insert(endereco);	
	}
	
	public List<Endereco> searchListEndereco(Endereco endereco) {
		if(endereco.getAgentes() == null || endereco.getAgentes().size() == 0){
			return dao.searchListEndereco(endereco);
		}else{
			List<Endereco> retorno = dao.searchListEndereco(endereco);
			List<Endereco> correto = new ArrayList<>();
			
			for(Acs agente1: endereco.getAgentes()){
				for(Endereco end: retorno){
					for(Acs acs: end.getAgentes()){
						if(acs.getMatricula().equals(agente1.getMatricula())){
							boolean achou = false;
							for(Endereco endCorreto: correto){
								if(endCorreto.getCep().equals(end.getCep())){
									achou = true;
									break;
								}
							}
							if(!achou){
								correto.add(end);
							}
						}
					}
				}
			}
			
			return correto;
		}
		
	}

	@Transactional
	public void updateEndereco(Endereco endereco) throws BusinessException {
		if(endereco.getCep() == null || endereco.getCep() == 0){
			throw new BusinessException("CEP: Campo Obrigatório.");
		}
		if(endereco.getRua() == null || endereco.getRua().length() == 0){
			throw new BusinessException("Nome da Rua: Campo Obrigatório.");
		}
		if(endereco.getBairro() == null || endereco.getBairro().length() == 0){
			throw new BusinessException("Bairro: Campo Obrigatório.");
		}
		if(endereco.getMunicipio() == null || endereco.getMunicipio().length() == 0){
			throw new BusinessException("Município: Campo Obrigatório.");
		}
		if(endereco.getUf() == null || endereco.getUf().length() == 0){
			throw new BusinessException("UF: Campo Obrigatório.");
		}
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
