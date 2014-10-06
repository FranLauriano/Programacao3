package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.EscolaridadeDao;
import prefeitura.siab.tabela.Escolaridade;

@Component
public class EscolaridadeController {

	private @Autowired
	EscolaridadeDao dao;

	@Transactional
	public void salvarEscolaridade(Escolaridade escolaridade) throws BusinessException{
		Escolaridade auxiliar = dao.searchEscolaridade(escolaridade);
		if (auxiliar != null) {
			if (auxiliar.getCodigo().equals(escolaridade.getCodigo()) && auxiliar.getNome().equals(escolaridade.getNome())) {
				throw new BusinessException("Essa Escolaridade já está cadastrada!!!");
			} else if (auxiliar.getCodigo().equals(escolaridade.getCodigo())) {
				throw new BusinessException("Esse Código já foi Cadastrado, favor modificar.");
			} else if (auxiliar.getNome().equals(escolaridade.getNome())) {
				throw new BusinessException("Esse Nome já foi Cadastrado, favor modificar. ");
			}
			throw new BusinessException("Impossível cadastrar essa Escolaridade!");
		}
		dao.insert(escolaridade);

	}

	public List<Escolaridade> searchEscolaridade(Escolaridade escolaridade) {
		return dao.searchListEscolaridade(escolaridade);
	}

	@Transactional
	public void updateEscolaridade(Escolaridade escolaridade) throws BusinessException {
		Escolaridade escolaridadeAux = dao.searchEscolaridadeName(escolaridade.getNome());
		if(escolaridadeAux == null){
			dao.updateEscolaridade(escolaridade);
		}else{
			if(escolaridade.getCodigo() == (escolaridadeAux.getCodigo())){
				dao.updateEscolaridade(escolaridade);
			}else{
				throw new BusinessException("Já existe uma Escolaridade com o nome: "+ escolaridade.getNome());
			}
		}
	}

	@Transactional
	public void deleteEscolaridade(Escolaridade escolaridade) throws BusinessException{
		Escolaridade escolaridadeAux = dao.searchEscolaridadeName(escolaridade.getNome());
		if(escolaridadeAux != null){
			dao.delete(escolaridade);
		}else{
			throw new BusinessException("Impossível deletar, pois essa escolaridade '"+ escolaridade.getNome() + "' não existe!");
		}
	}

}
