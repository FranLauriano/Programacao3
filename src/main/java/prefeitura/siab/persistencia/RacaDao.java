package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Raca;

@Component
public class RacaDao {

	private @PersistenceContext
	EntityManager manager;

	public Raca searchRaca(Raca raca) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (raca.getCodigo() != null && raca.getCodigo() != 0 && raca.getNome() != null && raca.getNome().length() > 1) {
			predicate.append(" and (raca.codigo = :racaCodigo or upper(raca.nome) like :racaNome)");
		} else {
			if (raca.getCodigo() != null && raca.getCodigo() != 0) {
				predicate.append(" and raca.codigo = :racaCodigo");
			}
			if (raca.getNome() != null && raca.getNome().length() > 1) {
				predicate.append(" and upper(raca.nome) like :racaNome");
			}
		}
		String jpql = "Select raca from Raca raca where " + predicate;
		TypedQuery<Raca> query = manager.createQuery(jpql, Raca.class);
		if (raca.getCodigo() != null && raca.getCodigo() != 0 && raca.getNome() != null && raca.getNome().length() > 1) {
			query.setParameter("racaCodigo", raca.getCodigo());
			query.setParameter("racaNome", raca.getNome().toUpperCase());
		}else{
			if (raca.getCodigo() != null && raca.getCodigo() != 0) {
				query.setParameter("racaCodigo", raca.getCodigo());
			}
			if (raca.getNome() != null && raca.getNome().length() > 1) {
				query.setParameter("racaNome", raca.getNome().toUpperCase());
			}
		}
		List<Raca> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public void insert(Raca raca) {
		manager.persist(raca);
	}

	public List<Raca> searchListRaca(Raca raca) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (raca.getCodigo() != null && raca.getCodigo() != 0 && raca.getNome() != null && raca.getNome().length() > 1) {
			predicate.append(" and raca.codigo = :racaCodigo and upper(raca.nome) like :racaNome");
		} else {
			if (raca.getCodigo() != null && raca.getCodigo() != 0) {
				predicate.append(" and raca.codigo = :racaCodigo");
			}
			if (raca.getNome() != null && raca.getNome().length() > 1) {
				predicate.append(" and upper(raca.nome) like :racaNome");
			}
		}
		String jpql = "Select raca from Raca raca where " + predicate;
		TypedQuery<Raca> query = manager.createQuery(jpql, Raca.class);
		if (raca.getCodigo() != null && raca.getCodigo() != 0 && raca.getNome() != null && raca.getNome().length() > 1) {
			query.setParameter("racaCodigo", raca.getCodigo());
			query.setParameter("racaNome", raca.getNome().toUpperCase());
		}else{
			if (raca.getCodigo() != null && raca.getCodigo() != 0) {
				query.setParameter("racaCodigo", raca.getCodigo());
			}
			if (raca.getNome() != null && raca.getNome().length() > 1) {
				query.setParameter("racaNome", raca.getNome().toUpperCase());
			}
		}
		List<Raca> result = query.getResultList();
		System.out.println(result);
		return result;
	}
	
	
}
