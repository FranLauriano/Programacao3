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

	public void updateFamilia(Familia familia) {
		manager.merge(familia);
	}

	public void delete(Familia familia) {
		Familia familiaAux = manager.find(Familia.class, familia.getCodigo());
		manager.remove(familiaAux);
	}

	public Familia searchFamilia(Familia familia) {
		StringBuilder predicate = new StringBuilder("1 = 1");

		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			predicate.append(" and familia.codigo = :familiaCodigo");
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			predicate.append(" and familia.agente.matricula = :familiaAgenteMatricula");
		}
		if (familia.getRua() != null && familia.getRua().getRua() != null &&familia.getRua().getRua().length() != 0) {
			predicate.append(" and upper(familia.rua.rua) like :familiaRuaRua");
		}

		
		String jpql = "Select familia from Familia familia where " + predicate;
		TypedQuery<Familia> query = manager.createQuery(jpql, Familia.class);
		
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			query.setParameter("familiaCodigo", familia.getCodigo());
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			query.setParameter("familiaAgenteMatricula", familia.getAgente().getMatricula());
		}
		if (familia.getRua() != null && familia.getRua().getRua() != null &&familia.getRua().getRua().length() != 0) {
			query.setParameter("familiaRuaRua", familia.getRua().getRua().toUpperCase());
		}


		List<Familia> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
	
	public Familia searchCodAcs(Familia familia) {
		StringBuilder predicate = new StringBuilder("1 = 1");

		if (familia.getNumeroFamilia() != null && familia.getNumeroFamilia() != 0) {
			predicate.append(" and familia.numeroFamilia = :familiaNumeroFamilia");
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			predicate.append(" and familia.agente.matricula = :familiaAgenteMatricula");
		}
		
		String jpql = "Select familia from Familia familia where " + predicate;
		TypedQuery<Familia> query = manager.createQuery(jpql, Familia.class);
		
		if (familia.getNumeroFamilia() != null && familia.getNumeroFamilia() != 0) {
			query.setParameter("familiaNumeroFamilia", familia.getNumeroFamilia());
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			query.setParameter("familiaAgenteMatricula", familia.getAgente().getMatricula());
		}

		List<Familia> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
	
	public List<Familia> searchListFamilia(Familia familia) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			predicate.append(" and familia.codigo = :familiaCodigo");
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != null && familia.getAgente().getMatricula() != 0) {
			predicate.append(" and familia.agente.matricula = :familiaAgenteMatricula");
		}
		if (familia.getRua() != null && familia.getRua().getRua().length() != 0) {
			predicate.append(" and upper(familia.rua.rua) like :familiaRuaRua");
		}
		if (familia.getAgente() != null && (familia.getAgente().getMatricula() == null || familia.getAgente().getMatricula() == 0) && familia.getAgente().getSupervisor() != null && familia.getAgente().getSupervisor().getMatricula() != 0){
			predicate.append(" and familia.agente.supervisor.matricula = :familiaSupervisorMatricula");
		}

		String jpql = "Select familia from Familia familia where " + predicate + " order by familia.numeroFamilia";
		TypedQuery<Familia> query = manager.createQuery(jpql, Familia.class);
		
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			query.setParameter("familiaCodigo", familia.getCodigo());
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != null && familia.getAgente().getMatricula() != 0) {
			query.setParameter("familiaAgenteMatricula", familia.getAgente().getMatricula());
		}
		if (familia.getRua() != null && familia.getRua().getRua().length() != 0) {
			query.setParameter("familiaRuaRua", familia.getRua().getRua().toUpperCase());
		}
		if (familia.getAgente() != null && (familia.getAgente().getMatricula() == null || familia.getAgente().getMatricula() == 0) && familia.getAgente().getSupervisor() != null && familia.getAgente().getSupervisor().getMatricula() != 0){
			query.setParameter("familiaSupervisorMatricula", familia.getAgente().getSupervisor().getMatricula());
		}
		List<Familia> result = query.getResultList();
		return result;
	}

	public Familia searchFamiliaCodigo(int codigoFamilia) {
		TypedQuery<Familia> query = manager.createQuery("Select familia from Familia familia where familia.codigo = :familiaCodigo", Familia.class);
		query.setParameter("familiaCodigo", codigoFamilia);
		List<Familia> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

}
