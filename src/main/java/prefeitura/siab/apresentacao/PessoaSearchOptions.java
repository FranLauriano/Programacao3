package prefeitura.siab.apresentacao;

import java.util.Date;
import java.util.List;

import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Doenca;
import prefeitura.siab.tabela.Escolaridade;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Raca;
import prefeitura.siab.tabela.VinculoEmpregaticio;

public class PessoaSearchOptions {
	//ATRIBUTOS
	private Familia familia;
	private String sus;
	private String nome;
	private Date dtNascimento;
	private Integer idadeInicial;
	private Integer idadeFinal;
	private char sexo;
	private Raca raca;
	private Escolaridade escolaridade;
	private String mae;
	private VinculoEmpregaticio vinculo;
	private List<Doenca> doencas;
	private Acs agente;

	
	//PROPRIEDADES
	public Familia getFamilia() {
		return familia;
	}
	public VinculoEmpregaticio getVinculo() {
		return vinculo;
	}
	public void setVinculo(VinculoEmpregaticio vinculo) {
		this.vinculo = vinculo;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	public String getSus() {
		return sus;
	}
	public void setSus(String sus) {
		this.sus = sus;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	public Integer getIdadeInicial() {
		return idadeInicial;
	}
	public void setIdadeInicial(Integer idadeInicial) {
		this.idadeInicial = idadeInicial;
	}
	public Integer getIdadeFinal() {
		return idadeFinal;
	}
	public void setIdadeFinal(Integer idadeFinal) {
		this.idadeFinal = idadeFinal;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	public String getMae() {
		return mae;
	}
	public void setMae(String mae) {
		this.mae = mae;
	}
	public List<Doenca> getDoencas() {
		return doencas;
	}
	public void setDoencas(List<Doenca> doencas) {
		this.doencas = doencas;
	}
	public Acs getAgente() {
		return agente;
	}
	public void setAgente(Acs agente) {
		this.agente = agente;
	}
	
}
