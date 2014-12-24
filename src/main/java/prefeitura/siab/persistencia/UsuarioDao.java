package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Usuario;

@Component
public class UsuarioDao {
	
	private @PersistenceContext EntityManager manager;

	//INSERIR USUÁRIO NO BANCO DE DADOS
	public void insert(Usuario usuario) {
		manager.persist(usuario);
	}

	//ALTERA USUÁRIO NO BANCO DE DADOS
	public void updateUsuario(Usuario usuario) {
		manager.merge(usuario);
	}

	//DELETA USUÁRIO NO BANCO DE DADOS
	public void deleteUsuario(Usuario usuario) {
		Usuario auxiliar = manager.find(Usuario.class, usuario.getMatricula());
		manager.remove(auxiliar);
	}
	
	
	//PESQUISA POR USUÁRIO E RETORNA APENAS 1
	public Usuario searchUsuario(Usuario usuario) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if(usuario.getMatricula() != null && usuario.getMatricula() != 0 && usuario.getTipo() != null){
			predicate.append(" and (usuario.matricula = :usuarioMatricula or usuario.tipo = :usuarioTipo)");
		}else{
			if(usuario.getMatricula() != null && usuario.getMatricula() != 0){
				predicate.append(" and usuario.matricula = :usuarioMatricula");
			}
			if(usuario.getTipo() != null){
				predicate.append(" and usuario.tipo = :usuarioTipo");
			}
		}
		String jpql = "Select usuario from Usuario usuario where " + predicate;
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		if(usuario.getMatricula() != null && usuario.getMatricula() != 0 && usuario.getTipo() != null){
			query.setParameter("usuarioMatricula", usuario.getMatricula());
			query.setParameter("usuarioTipo", usuario.getTipo());
		}else{
			if(usuario.getMatricula() != null && usuario.getMatricula() != 0){
				query.setParameter("usuarioMatricula", usuario.getMatricula());
			}
			if(usuario.getTipo() != null){
				query.setParameter("usuarioTipo", usuario.getTipo());
			}
		}
		
		List<Usuario> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	//PESQUISA POR USUÁRIO E RETORNA UMA LISTA DE USUÁRIOS
	public List<Usuario> searchListUsuario(Usuario usuario) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if(usuario.getMatricula() != null && usuario.getMatricula() != 0 && usuario.getTipo() != null){
			predicate.append(" and (usuario.matricula = :usuarioMatricula or usuario.tipo = :usuarioTipo)");
		}else{
			if(usuario.getMatricula() != null && usuario.getMatricula() != 0){
				predicate.append(" and usuario.matricula = :usuarioMatricula");
			}
			if(usuario.getTipo() != null){
				predicate.append(" and usuario.tipo = :usuarioTipo");
			}
		}
		String jpql = "Select usuario from Usuario usuario where " + predicate;
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		if(usuario.getMatricula() != null && usuario.getMatricula() != 0 && usuario.getTipo() != null){
			query.setParameter("usuarioMatricula", usuario.getMatricula());
			query.setParameter("usuarioTipo", usuario.getTipo());
		}else{
			if(usuario.getMatricula() != null && usuario.getMatricula() != 0){
				query.setParameter("usuarioMatricula", usuario.getMatricula());
			}
			if(usuario.getTipo() != null){
				query.setParameter("usuarioTipo", usuario.getTipo());
			}
		}
		
		List<Usuario> result = query.getResultList();
		
		return result;

	}

	
	//PESQUISA POR USUÁRIO APENAS PELA MATRICULA
	public Usuario searchUsuarioMatricula(Integer matricula) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if(matricula != null && matricula != 0){
			predicate.append(" and usuario.matricula = :usuarioMatricula");
		}
		String jpql = "Select usuario from Usuario usuario where " + predicate;
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		if(matricula != null && matricula != 0){
			query.setParameter("usuarioMatricula", matricula);
		}
		
		List<Usuario> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public Usuario searchUsuarioAutentication(Usuario usuario) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if(usuario.getMatricula() != null && usuario.getMatricula() != 0 && usuario.getSenha() != null && usuario.getSenha().length() >= 6){
			predicate.append(" and usuario.matricula = :usuarioMatricula and usuario.senha like :usuarioSenha");
		}
		String jpql = "Select usuario from Usuario usuario where " + predicate;
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		if(usuario.getMatricula() != null && usuario.getMatricula() != 0 && usuario.getSenha() != null && usuario.getSenha().length() >= 6){
			query.setParameter("usuarioMatricula", usuario.getMatricula());
			query.setParameter("usuarioSenha", usuario.getSenha());
		}
		
		List<Usuario> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

}
