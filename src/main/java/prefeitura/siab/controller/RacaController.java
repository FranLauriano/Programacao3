package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.RacaDao;
import prefeitura.siab.tabela.Raca;

@Component
public class RacaController {

	private @Autowired RacaDao dao;
	
	@Transactional
	public String salvarRaca(Raca raca) throws BusinessException{
		Raca auxiliar = dao.searchRaca(raca);
		if(auxiliar != null){
			if(auxiliar.getCodigo().equals(raca.getCodigo()) && auxiliar.getNome().equals(raca.getNome())){
				throw new BusinessException("Essa Raça já está cadastrada!!!");
			}else if(auxiliar.getCodigo().equals(raca.getCodigo())){
				throw new BusinessException("Esse Código já foi Cadastrado, favor modificar.");
			}else if(auxiliar.getNome().equals(raca.getNome())){
				throw new BusinessException("Esse Nome já foi Cadastrado, favor modificar. ");
			}
			throw new BusinessException("Impossível salvar essa Raça!");
		}
		dao.insert(raca);
		return null;
	}

	public List<Raca> searchRaca(Raca raca) {
		return dao.searchListRaca(raca);
	}

	@Transactional
	public void updateRaca(Raca raca) throws BusinessException {
		Raca racaAux = dao.searchRacaName(raca.getNome());
		if(racaAux == null){
			dao.updateRaca(raca);
		}else{
			if(raca.getCodigo() == (racaAux.getCodigo())){
				dao.updateRaca(raca);
			}else{
				throw new BusinessException("Já existe uma Raça com o nome: "+ raca.getNome());
			}
		}
	}
	
	@Transactional
	public void deleteRaca(Raca raca) throws BusinessException{
		Raca racaAux = dao.searchRacaName(raca.getNome());
		if(racaAux != null){
			dao.delete(raca);
		}else{
			throw new BusinessException("Impossível deletar, pois essa "+ raca.getNome() + " não existe!");
		}
	}
	
}
