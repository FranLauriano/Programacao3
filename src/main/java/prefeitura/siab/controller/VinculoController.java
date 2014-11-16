package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.VinculoDao;
import prefeitura.siab.tabela.VinculoEmpregaticio;

@Component
public class VinculoController {

	private @Autowired VinculoDao dao;

	@Transactional
	public void salvarVinculo(VinculoEmpregaticio vinculo) throws BusinessException {
		VinculoEmpregaticio auxiliar = dao.searchVinculo(vinculo);
		if(auxiliar != null){
			if(auxiliar.getCodigo().equals(vinculo.getCodigo()) && auxiliar.getNome().equals(vinculo.getNome())){
				throw new BusinessException("Esse Vinculo já está cadastrado!!!");
			}else if(auxiliar.getCodigo().equals(vinculo.getCodigo())){
				throw new BusinessException("Esse Código já foi Cadastrado, favor modificar.");
			}else if(auxiliar.getNome().equals(vinculo.getNome())){
				throw new BusinessException("Esse Nome já foi Cadastrado, favor modificar. ");
			}
			throw new BusinessException("Impossível salvar esse Vinculo!");
		}else if(vinculo.getCodigo() == 0 && vinculo.getNome().length() == 0){
			throw new BusinessException("Favor inserir um Código e um Nome Válido");
		}else if(vinculo.getCodigo() == 0){
			throw new BusinessException("Favor inserir um Código Válido");
		}else if(vinculo.getNome().length() == 0){
			throw new BusinessException("Favor inserir um Nome Válido");
		}
		dao.insert(vinculo);
	}

	public List<VinculoEmpregaticio> searchVinculo(VinculoEmpregaticio vinculo) {
		return dao.searchListVinculo(vinculo);
	}
	
	@Transactional
	public void updateVinculo(VinculoEmpregaticio vinculo) throws BusinessException {
		VinculoEmpregaticio vinculoAux = dao.searchVinculoName(vinculo.getNome());
		if(vinculoAux == null){
			dao.updateVinculo(vinculo);
		}else{
			if(vinculo.getCodigo() == (vinculoAux.getCodigo())){
				dao.updateVinculo(vinculo);
			}else{
				throw new BusinessException("Já existe um vinculo com o nome: "+ vinculo.getNome());
			}
		}
	}
	
	@Transactional
	public void deleteVinculo(VinculoEmpregaticio vinculo) throws BusinessException{
		VinculoEmpregaticio vinculoAux = dao.searchVinculoName(vinculo.getNome());
		if(vinculoAux != null){
			dao.delete(vinculo);
		}else{
			throw new BusinessException("Impossível deletar, pois essa "+ vinculo.getNome() + " não existe!");
		}
	}
	
}
