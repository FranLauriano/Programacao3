package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.UsuarioDao;
import prefeitura.siab.tabela.Usuario;

@Component
public class UsuarioController {

	private @Autowired UsuarioDao dao;
	
	@Transactional
	public void salvarUsuario(Usuario usuario) throws BusinessException{
		Usuario auxiliar = dao.searchUsuario(usuario);
		if(auxiliar != null){
			if(auxiliar.getMatricula().equals(usuario.getMatricula())){
				throw new BusinessException("Essa Matricula já está cadastrada!!!");
			}
		}else if((usuario.getMatricula() == null || usuario.getMatricula() == 0) && (usuario.getSenha() == null || usuario.getSenha().length() < 6)){
			throw new BusinessException("Favor inserir uma Matricula e uma Senha Válida");
		}else if((usuario.getMatricula() == null || usuario.getMatricula() == 0)){
			throw new BusinessException("Favor inserir uma Matricula");
		}else if(usuario.getSenha() == null || usuario.getSenha().length() < 6){
			throw new BusinessException("Favor inserir uma senha acima de 6 digitos");
		}
		dao.insert(usuario);
	}
	
	public List<Usuario> searchListUsuario(Usuario usuario) {
		return dao.searchListUsuario(usuario);
	}
	
	public Usuario searchUsuario(Usuario usuario) {
		return dao.searchUsuario(usuario);
	}
	
	public Usuario searchUsuarioAutentication(Usuario usuario) {
		return dao.searchUsuario(usuario);
	}
	
	@Transactional
	public void updateUsuario(Usuario usuario) throws BusinessException {
		dao.updateUsuario(usuario);
	}
	
	@Transactional
	public void deleteUsuario(Usuario usuario) throws BusinessException{
		Usuario auxiliar = dao.searchUsuarioMatricula(usuario.getMatricula());
		if(auxiliar != null){
			dao.deleteUsuario(usuario);
		}else{
			throw new BusinessException("Impossível deletar, Esse usuário não existe!");
		}
	}

}