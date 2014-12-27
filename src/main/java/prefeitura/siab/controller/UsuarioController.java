package prefeitura.siab.controller;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.UsuarioDao;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Enfermeira;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;

@Component
public class UsuarioController {

	private @Autowired UsuarioDao dao;
	private @Autowired AcsController controllerAcs;
	private @Autowired EnfermeiraController controllerEnfermeira;
	
	@Transactional
	public void salvarUsuario(Usuario usuario) throws BusinessException{
		Usuario auxiliar = dao.searchUsuario(usuario);
		if(auxiliar != null){
			if(auxiliar.getMatricula().equals(usuario.getMatricula())){
				throw new BusinessException("Essa Matricula já está cadastrada!!!");
			}
		}
		if((usuario.getMatricula() == null || usuario.getMatricula() == 0) && (usuario.getSenha() == null || usuario.getSenha().length() < 6)){
			throw new BusinessException("Favor inserir uma Matricula e uma Senha Válida");
		}
		if((usuario.getMatricula() == null || usuario.getMatricula() == 0)){
			throw new BusinessException("Favor inserir uma Matricula");
		}
		if((usuario.getNome() == null || usuario.getNome().length() < 5)){
			throw new BusinessException("Favor inserir um Nome");
		}
		if(usuario.getSenha() == null || usuario.getSenha().length() < 6){
			throw new BusinessException("Favor inserir uma senha acima de 6 digitos");
		}
		auxiliar = dao.searchUsuarioMatricula(usuario.getMatricula());
		if(auxiliar != null){
			throw new BusinessException("Essa Matricula já está cadastrada!!!");
		}
		
		if(usuario.getAcs() != null){
			usuario.getAcs().setMatricula(usuario.getMatricula());
			usuario.getAcs().setNome(usuario.getNome());
			controllerAcs.salvarAcs(usuario.getAcs());
		}else if(usuario.getEnfermeira() != null){
			usuario.getEnfermeira().setMatricula(usuario.getMatricula());
			usuario.getEnfermeira().setNome(usuario.getNome());
			controllerEnfermeira.salvarEnfermeira(usuario.getEnfermeira());	
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
		return dao.searchUsuarioAutentication(usuario);
	}
	
	@Transactional
	public void updateUsuario(Usuario usuario, String senha1, String senha2) throws BusinessException {
		Usuario auxiliar = dao.searchUsuarioMatricula(usuario.getMatricula());
		if((senha1 == null || senha1.length() == 0) && (senha2 == null || senha2.length() == 0)){
			if(usuario.getTipo().equals(TipoUsuario.ACS)){
				usuario.setSenha(auxiliar.getSenha());
				usuario.setEnfermeira(null);
				Acs acsAux = controllerAcs.searchAcsMatricula(usuario.getMatricula());
				if(acsAux != null){
					acsAux.setNome(usuario.getNome());
					acsAux.setMicroarea(usuario.getAcs().getMicroarea());
					acsAux.setArea(usuario.getAcs().getArea());
					acsAux.setMicroregiao(usuario.getAcs().getMicroregiao());
					acsAux.setSupervisor(usuario.getAcs().getSupervisor());
					usuario.setAcs(acsAux);
					controllerAcs.updateAcs(controllerAcs.searchAcsMatricula(usuario.getMatricula()));
					dao.updateUsuario(usuario);
				}else{
					throw new BusinessException("Impossível modificar para o tipo ACS!!!");
				}
			}else if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				usuario.setSenha(auxiliar.getSenha());
				usuario.setAcs(null);
				Enfermeira enfAux = controllerEnfermeira.searchEnfermeiraMatricula(usuario.getMatricula());
				if(enfAux != null){
					enfAux.setCoren(usuario.getEnfermeira().getCoren());
					enfAux.setEquipe(usuario.getEnfermeira().getEquipe());
					enfAux.setNome(usuario.getNome());
					usuario.setEnfermeira(enfAux);
					controllerEnfermeira.updateEnfermeira(enfAux);
					dao.updateUsuario(usuario);					
				}else{
					throw new BusinessException("Impossível modificar para o tipo Enfermeira!!!");
				}
			}else{
				usuario.setAcs(null);
				usuario.setEnfermeira(null);
				usuario.setSenha(auxiliar.getSenha());
				dao.updateUsuario(usuario);
			}
		}else{
			
		}
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
	
	@Transactional
	public void updateMyAccount(Usuario usuario, String senha1, String senha2, String senhaAntiga) throws BusinessException{
		Usuario auxiliar = dao.searchUsuarioMatricula(usuario.getMatricula());
		if((senhaAntiga == null || senhaAntiga.length() == 0) && (senha1 == null || senha1.length() == 0) && (senha2 == null || senha2.length() == 0)){
			if(usuario.getTipo().equals(TipoUsuario.ACS)){
				usuario.setSenha(auxiliar.getSenha());
				dao.updateUsuario(usuario);
				controllerAcs.updateAcs(usuario.getAcs());
			}else if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				usuario.setSenha(auxiliar.getSenha());
				dao.updateUsuario(usuario);
				controllerEnfermeira.updateEnfermeira(usuario.getEnfermeira());
			}else{
				usuario.setSenha(auxiliar.getSenha());
				dao.updateUsuario(usuario);	
			}
		}else{
			senhaAntiga = DigestUtils.md5Hex(senhaAntiga);
			if(auxiliar.getSenha().equals(senhaAntiga)){
				if(senha1.equals(senha2)){
					if(senha1.length() >= 6){
						senha1 = DigestUtils.md5Hex(senha1);
						usuario.setSenha(senha1);
						dao.updateUsuario(usuario);						
					}else{
						throw new BusinessException("Tamanho mínimo para senha é de 6 Caracteres!");
					}
				}else{
					throw new BusinessException("As novas senhas são diferentes!");
				}
			}else{
				throw new BusinessException("Senha antiga não confere!");
			}
		}
	}

}
