package prefeitura.siab.tabela;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_ENFERMEIRA")
public class Enfermeira implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;

	//ATRIBUTOS
	private Integer matricula;
	private String nome;
	private Integer coren;
	private String equipe;
	private Usuario usuario;
	private List<Acs> agentes;
	
	@Id
	@Column(name="ENF_MATRICULA")
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	@Column(name="ENF_COREN")
	public Integer getCoren() {
		return coren;
	}
	public void setCoren(Integer coren) {
		this.coren = coren;
	}
	
	@Column(name="ENF_EQUIPE")
	public String getEquipe() {
		return equipe;
	}
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}
	
	@OneToOne(mappedBy="enfermeira")
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@OneToMany(mappedBy="supervisor", fetch=FetchType.EAGER)
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	
	@Column(name="ENF_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enfermeira other = (Enfermeira) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}
	
	public Enfermeira clone() {
		try {
			return (Enfermeira) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Impossível clonar esse Usuário!");
		}
	}
	
	
	
}
