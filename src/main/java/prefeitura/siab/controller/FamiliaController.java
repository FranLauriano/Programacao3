package prefeitura.siab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.FamiliaDao;
import prefeitura.siab.tabela.Familia;

@Component
public class FamiliaController {

	private @Autowired FamiliaDao dao;

	@Transactional
	public void salvarFamilia(Familia familia) throws BusinessException {
		Familia auxiliar = dao.searchFamilia(familia);
		if(auxiliar != null){
			if(auxiliar.getCodigo().equals(familia.getCodigo())){
				throw new BusinessException("Já existe Família cadastrada com esse Código");
			}
		}
		dao.insert(familia);
		
	}
	
	
}
