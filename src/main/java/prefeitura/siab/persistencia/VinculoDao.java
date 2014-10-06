package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.VinculoEmpregaticio;

@Component
public class VinculoDao {

	private @PersistenceContext EntityManager manager;
	
	public void insert(VinculoEmpregaticio vinculo){
		manager.persist(vinculo);
	}

	public void updateVinculo(VinculoEmpregaticio vinculo) {
		manager.merge(vinculo);
	}

	public void delete(VinculoEmpregaticio vinculo) {
		VinculoEmpregaticio vinculoAux = manager.find(VinculoEmpregaticio.class, vinculo.getCodigo());
		manager.remove(vinculoAux);
	}

	public VinculoEmpregaticio searchVinculo(VinculoEmpregaticio vinculo) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0 && vinculo.getNome() != null && vinculo.getNome().length() > 1) {
			predicate.append(" and (vinculoempregaticio.codigo = :vinculoempregaticioCodigo or upper(vinculoempregaticio.nome) like :vinculoempregaticioNome)");
		} else {
			if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0) {
				predicate.append(" and vinculoempregaticio.codigo = :vinculoempregaticioCodigo");
			}
			if (vinculo.getNome() != null && vinculo.getNome().length() > 1) {
				predicate.append(" and upper(vinculoempregaticio.nome) like :vinculoempregaticioNome");
			}
		}
		String jpql = "Select vinculoempregaticio from VinculoEmpregaticio vinculoempregaticio where " + predicate;
		TypedQuery<VinculoEmpregaticio> query = manager.createQuery(jpql, VinculoEmpregaticio.class);
		if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0 && vinculo.getNome() != null && vinculo.getNome().length() > 1) {
			query.setParameter("vinculoempregaticioCodigo", vinculo.getCodigo());
			query.setParameter("vinculoempregaticioNome", vinculo.getNome().toUpperCase());
		}else{
			if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0) {
				query.setParameter("vinculoempregaticioCodigo", vinculo.getCodigo());
			}
			if (vinculo.getNome() != null && vinculo.getNome().length() > 1) {
				query.setParameter("vinculoempregaticioNome", vinculo.getNome().toUpperCase());
			}
		}
		List<VinculoEmpregaticio> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
	
	public List<VinculoEmpregaticio> searchListVinculo(VinculoEmpregaticio vinculo) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0 && vinculo.getNome() != null && vinculo.getNome().length() > 1) {
			predicate.append(" and (vinculoempregaticio.codigo = :vinculoempregaticioCodigo and upper(vinculoempregaticio.nome) like :vinculoempregaticioNome)");
		} else {
			if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0) {
				predicate.append(" and vinculoempregaticio.codigo = :vinculoempregaticioCodigo");
			}
			if (vinculo.getNome() != null && vinculo.getNome().length() > 1) {
				predicate.append(" and upper(vinculoempregaticio.nome) like :vinculoempregaticioNome");
			}
		}
		String jpql = "Select vinculoempregaticio from VinculoEmpregaticio vinculoempregaticio where " + predicate;
		TypedQuery<VinculoEmpregaticio> query = manager.createQuery(jpql, VinculoEmpregaticio.class);
		if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0 && vinculo.getNome() != null && vinculo.getNome().length() > 1) {
			query.setParameter("vinculoempregaticioCodigo", vinculo.getCodigo());
			query.setParameter("vinculoempregaticioNome", vinculo.getNome().toUpperCase());
		}else{
			if (vinculo.getCodigo() != null && vinculo.getCodigo() != 0) {
				query.setParameter("vinculoempregaticioCodigo", vinculo.getCodigo());
			}
			if (vinculo.getNome() != null && vinculo.getNome().length() > 1) {
				query.setParameter("vinculoempregaticioNome", vinculo.getNome().toUpperCase());
			}
		}
		List<VinculoEmpregaticio> result = query.getResultList();
		return result;
		
	}

	public VinculoEmpregaticio searchVinculoName(String nomeVinculo) {
		TypedQuery<VinculoEmpregaticio> query = manager.createQuery("Select vinculo from VinculoEmpregaticio vinculo where upper(vinculo.nome) like :vinculoNome", VinculoEmpregaticio.class);
		query.setParameter("vinculoNome", nomeVinculo.toUpperCase());
		List<VinculoEmpregaticio> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
	
}
