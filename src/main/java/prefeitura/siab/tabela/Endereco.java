package prefeitura.siab.tabela;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="TB_ENDERECO")
public class Endereco implements Cloneable{

	//ATRIBUTOS
	private String rua;
	private Integer numero;
	private String bairro;
	private String municipio;
	private String uf;
	private Integer cep;
	private List<Acs> agentes;

	//PROPRIEDADES
	
	@Column(name="END_RUA")
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	@Column(name="END_NUMERO")
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	@Column(name="END_BAIRRO")
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name="END_MUNICIPIO")
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	@Column(name="END_UF")
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	@Id
	@Column(name="END_CEP")
	public Integer getCep() {
		return cep;
	}
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	
	@ManyToMany()
	@JoinTable(
			name="TB_ACS_END", 
			joinColumns=@JoinColumn(name="END_CEP"), 
			inverseJoinColumns=@JoinColumn(name="ACS_MAT")
	)
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	
	public Endereco clone() {
		try {
			return (Endereco) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Clonagem Impossível");
		}  
	}
	
	
}

