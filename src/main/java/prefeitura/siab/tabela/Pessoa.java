package prefeitura.siab.tabela;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="TB_PESSOA")
public class Pessoa {

	//ATRIBUTOS
	private Integer sus;
	private String nome;
	private Date dtnascimento;
	private Integer idade;
	private char sexo;
	private Raca raca;
	private boolean frequentaescola;
	private Escolaridade escolaridade;
	private VinculoEmpregaticio vinculo;
	private String mae;
	private String parentesco;
	private List<Doenca> situacao;
	private boolean bolsaescola;
	private Familia familia;
	
	//PROPRIEDADES
	@Id
	@Column(name="PES_SUS")
	public Integer getSus() {
		return sus;
	}
	public void setSus(Integer sus) {
		this.sus = sus;
	}
	
	@Column(name="PES_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="PES_NASCIMENTO")
	public Date getDtnascimento() {
		return dtnascimento;
	}
	public void setDtnascimento(Date dtnascimento) {
		this.dtnascimento = dtnascimento;
	}
	
	@Column(name="PES_IDADE")
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	@Column(name="PES_SEXO")
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	@OneToOne
	@JoinColumn(name="PES_RACA")
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	
	@Column(name="PES_FREQUENCIA_ESC")
	public boolean getFrequentaescola() {
		return frequentaescola;
	}
	public void setFrequentaescola(boolean frequentaescola) {
		this.frequentaescola = frequentaescola;
	}
	
	@OneToOne
	@JoinColumn(name="PES_ESCOLARIDADE")
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	
	@OneToOne
	@JoinColumn(name="PES_VINCULO")
	public VinculoEmpregaticio getVinculo() {
		return vinculo;
	}
	public void setVinculo(VinculoEmpregaticio vinculo) {
		this.vinculo = vinculo;
	}
	
	@Column(name="PES_MAE")
	public String getMae() {
		return mae;
	}
	public void setMae(String mae) {
		this.mae = mae;
	}
	
	@Column(name="PES_PARENTESCO")
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="TB_PES_DOE", 
			joinColumns=@JoinColumn(name="PES_CODIGO"), 
			inverseJoinColumns=@JoinColumn(name="DOE_SIGLA")
	)
	public List<Doenca> getSituacao() {
		return situacao;
	}
	public void setSituacao(List<Doenca> situacao) {
		this.situacao = situacao;
	}
	
	@Column(name="PES_BOLSA_ESCOLA")
	public boolean getBolsaescola() {
		return bolsaescola;
	}
	public void setBolsaescola(boolean bolsaescola) {
		this.bolsaescola = bolsaescola;
	}
	
	@ManyToOne
	@JoinColumn(name="PES_FAMILIA")
	public Familia getFamilia() {
		return familia;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
}
