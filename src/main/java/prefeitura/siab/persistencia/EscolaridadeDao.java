package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Escolaridade;

@Component
public class EscolaridadeDao {

	private @PersistenceContext EntityManager manager;
	
	public void insert(Escolaridade escolaridade){
		manager.persist(escolaridade);
	}

	public void updateEscolaridade(Escolaridade escolaridade) {
		manager.merge(escolaridade);
	}

	public void delete(Escolaridade escolaridade) {
		Escolaridade escolaridadeAux = manager.find(Escolaridade.class, escolaridade.getCodigo());
		manager.remove(escolaridadeAux);
	}

	public Escolaridade searchEscolaridade(Escolaridade escolaridade) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0 && escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
			predicate.append(" and escolaridade.codigo = :escolaridadeCodigo and upper(escolaridade.nome) like :escolaridadeNome");
		} else {
			if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0) {
				predicate.append(" and escolaridade.codigo = :escolaridadeCodigo");
			}
			if (escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
				predicate.append(" and upper(escolaridade.nome) like :escolaridadeNome");
			}
		}
		String jpql = "Select escolaridade from Escolaridade escolaridade where " + predicate;
		TypedQuery<Escolaridade> query = manager.createQuery(jpql, Escolaridade.class);
		if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0 && escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
			query.setParameter("escolaridadeCodigo", escolaridade.getCodigo());
			query.setParameter("escolaridadeNome", escolaridade.getNome().toUpperCase());
		}else{
			if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0) {
				query.setParameter("escolaridadeCodigo", escolaridade.getCodigo());
			}
			if (escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
				query.setParameter("escolaridadeNome", escolaridade.getNome().toUpperCase());
			}
		}
		List<Escolaridade> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}
	
	public List<Escolaridade> searchListEscolaridade(Escolaridade escolaridade) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0 && escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
			predicate.append(" and escolaridade.codigo = :escolaridadeCodigo and upper(escolaridade.nome) like :escolaridadeNome");
		} else {
			if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0) {
				predicate.append(" and escolaridade.codigo = :escolaridadeCodigo");
			}
			if (escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
				predicate.append(" and upper(escolaridade.nome) like :escolaridadeNome");
			}
		}
		String jpql = "Select escolaridade from Escolaridade escolaridade where " + predicate;
		TypedQuery<Escolaridade> query = manager.createQuery(jpql, Escolaridade.class);
		if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0 && escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
			query.setParameter("escolaridadeCodigo", escolaridade.getCodigo());
			query.setParameter("escolaridadeNome", escolaridade.getNome().toUpperCase());
		}else{
			if (escolaridade.getCodigo() != null && escolaridade.getCodigo() != 0) {
				query.setParameter("escolaridadeCodigo", escolaridade.getCodigo());
			}
			if (escolaridade.getNome() != null && escolaridade.getNome().length() > 1) {
				query.setParameter("escolaridadeNome", escolaridade.getNome().toUpperCase());
			}
		}
		List<Escolaridade> result = query.getResultList();
		return result;
	}
	
	public Escolaridade searchEscolaridadeName(String nomeEscolaridade) {
		TypedQuery<Escolaridade> query = manager.createQuery("Select escolaridade from Escolaridade escolaridade where upper(escolaridade.nome) like :escolaridadeNome", Escolaridade.class);
		query.setParameter("escolaridadeNome", nomeEscolaridade.toUpperCase());
		List<Escolaridade> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

}
