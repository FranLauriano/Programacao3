package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Doenca;

@Component
public class DoencaDao {

	private @PersistenceContext EntityManager manager;
	
	public void insert(Doenca doenca){
		manager.persist(doenca);
	}
	
	public Doenca searchDoenca(Doenca doenca){
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (doenca.getSigla() != null && doenca.getSigla().length() > 0 && doenca.getNome() != null && doenca.getNome().length() > 0) {
			predicate.append(" and (upper(doenca.sigla) = :doencaSigla or upper(doenca.nome) = :doencaNome)");
		} else {
			if (doenca.getSigla() != null && doenca.getSigla().length() > 0) {
				predicate.append(" and upper(doenca.sigla) = :doencaSigla");
			}
			if (doenca.getNome() != null && doenca.getNome().length() > 0) {
				predicate.append(" and upper(doenca.nome) = :doencaNome");
			}
		}
		String jpql = "Select doenca from Doenca doenca where " + predicate;
		TypedQuery<Doenca> query = manager.createQuery(jpql, Doenca.class);
		if (doenca.getSigla() != null && doenca.getSigla().length() > 0 && doenca.getNome() != null && doenca.getNome().length() > 0) {
			query.setParameter("doencaSigla", doenca.getSigla().toUpperCase());
			query.setParameter("doencaNome", doenca.getNome().toUpperCase());
		}else{
			if (doenca.getSigla() != null && doenca.getSigla().length() > 0) {
				query.setParameter("doencaSigla", doenca.getSigla().toUpperCase());
			}
			if (doenca.getNome() != null && doenca.getNome().length() > 0) {
				query.setParameter("doencaNome", doenca.getNome().toUpperCase());
			}
		}
		List<Doenca> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
}
