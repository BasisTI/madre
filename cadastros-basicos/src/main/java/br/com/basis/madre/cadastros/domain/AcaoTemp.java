package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;

public class AcaoTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer id_funcionalidade;
	private String nm_funcionalidade;
	private String cd_funcionalidade;
	private String nm_acao;
	private String cd_acao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_funcionalidade() {
		return id_funcionalidade;
	}

	public void setId_funcionalidade(Integer id_funcionalidade) {
		this.id_funcionalidade = id_funcionalidade;
	}

	public String getNm_funcionalidade() {
		return nm_funcionalidade;
	}

	public void setNm_funcionalidade(String nm_funcionalidade) {
		this.nm_funcionalidade = nm_funcionalidade;
	}

	public String getCd_funcionalidade() {
		return cd_funcionalidade;
	}

	public void setCd_funcionalidade(String cd_funcionalidade) {
		this.cd_funcionalidade = cd_funcionalidade;
	}

	public String getNm_acao() {
		return nm_acao;
	}

	public void setNm_acao(String nm_acao) {
		this.nm_acao = nm_acao;
	}

	public String getCd_acao() {
		return cd_acao;
	}

	public void setCd_acao(String cd_acao) {
		this.cd_acao = cd_acao;
	}

}
