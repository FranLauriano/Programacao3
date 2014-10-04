package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Familia;

@Component
public class FamiliaDao {

	private @PersistenceContext EntityManager manager;
	
	public void insert(Familia familia){
		manager.persist(familia);
	}

	public Familia searchFamilia(Familia familia) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			predicate.append(" and familia.codigo = :familiaCodigo");
		}
		String jpql = "Select familia from Familia familia where " + predicate;
		TypedQuery<Familia> query = manager.createQuery(jpql, Familia.class);
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			query.setParameter("familiaCodigo", familia.getCodigo());
		}
		List<Familia> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
}
