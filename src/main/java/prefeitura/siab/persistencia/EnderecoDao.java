package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Endereco;

@Component
public class EnderecoDao {

	private @PersistenceContext EntityManager manager;

	public void insert(Endereco endereco) {
		manager.persist(endereco);
	}
	
	public void updateEndereco(Endereco endereco) {
		manager.merge(endereco);
	}

	public void delete(Endereco endereco) {
		Endereco enderecoAux = manager.find(Endereco.class, endereco.getCep());
		manager.remove(enderecoAux);
	}

	public Endereco searchEndereco(Endereco endereco) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (endereco.getCep() != null && endereco.getCep() != 0) {
			predicate.append(" and endereco.cep = :enderecoCep");
		}
		String jpql = "Select endereco from Endereco endereco where " + predicate;
		TypedQuery<Endereco> query = manager.createQuery(jpql, Endereco.class);
		if (endereco.getCep() != null && endereco.getCep() != 0) {
			query.setParameter("enderecoCep", endereco.getCep());
		}
		List<Endereco> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}

	public List<Endereco> searchListEndereco(Endereco endereco) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (endereco.getRua() != null && endereco.getRua().length() != 0 && endereco.getCep() != 0) {
			predicate.append(" and upper(endereco.rua) like :enderecoRua and endereco.cep = :enderecoCep");
		} else {
			if (endereco.getRua() != null && endereco.getRua().length() != 0) {
				predicate.append(" and upper(endereco.rua) like :enderecoRua");
			}
			if (endereco.getCep() != null && endereco.getCep() != 0) {
				predicate.append(" and endereco.cep = :enderecoCep");
			}
		}
		if(endereco.getAgente() != null){
			if (endereco.getAgente().getMatricula() != null && endereco.getAgente().getMatricula() != 0){
				predicate.append(" and endereco.agente.matricula = :matriculaAcs");
			}
		}
		String jpql = "Select endereco from Endereco endereco where " + predicate;
		TypedQuery<Endereco> query = manager.createQuery(jpql, Endereco.class);
		if (endereco.getRua() != null && endereco.getRua().length() != 0 && endereco.getCep() != 0) {
			query.setParameter("enderecoRua", endereco.getRua().toUpperCase());
			query.setParameter("enderecoCep", endereco.getCep());
		}else{
			if (endereco.getRua() != null && endereco.getRua().length() != 0) {
				query.setParameter("enderecoRua", endereco.getRua().toUpperCase());
			}
			if (endereco.getCep() != null && endereco.getCep() != 0) {
				query.setParameter("enderecoCep", endereco.getCep());
			}
		}
		if(endereco.getAgente() != null){
			if (endereco.getAgente().getMatricula() != null && endereco.getAgente().getMatricula() != 0){
				query.setParameter("matriculaAcs", endereco.getAgente().getMatricula());
			}
		}
		List<Endereco> result = query.getResultList();
		System.out.println(result);
		return result;
	}
	
	public Endereco searchEnderecoName(String nomeRua) {
		TypedQuery<Endereco> query = manager.createQuery("Select endereco from Endereco endereco where upper(endereco.rua) like :enderecoRua", Endereco.class);
		query.setParameter("enderecoRua", nomeRua.toUpperCase());
		List<Endereco> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
	
	public Endereco searchEnderecoCep(Integer cep) {
		TypedQuery<Endereco> query = manager.createQuery("Select endereco from Endereco endereco where endereco.cep = :enderecoCep", Endereco.class);
		query.setParameter("enderecoCep", cep);
		List<Endereco> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
	
}
