/**
 * 
 */
package sgcd.gov.mz.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

/**
 * @author Lee dos Santos
 *
 */
@Entity
@DynamicUpdate
public class Decisao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(unique = true)
	private String idDecisao;
	
	private String ambito;
	
	private String descricaoDecisao;
		
	private LocalDate dataDecisao;
	
	private LocalDate prazo;
	
	@Column(name = "ponto_situacao")
	@Enumerated(EnumType.STRING)
	private PontoSituacao pontoSituacao;
	
	@ManyToOne
	@JoinColumn(name = "codigo_entidade")
	private Entidade entidade;
	
	   @ManyToMany
	    @JoinTable(name = "decisao_entidade",
	               joinColumns = @JoinColumn(name = "codigo_decisao"),
	               inverseJoinColumns = @JoinColumn(name = "codigo_entidade"))
	    private List<Entidade> pontosFocais = new ArrayList<>();
	
	private Integer progresso;
	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_criacao")
	private Usuario usuarioCriacao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_actualizacao")
	private Usuario usuarioActualizacao;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataActualizacao;
	
	
	public Long getCodigo() {
		return codigo;
	}

	public String getAmbito() {
		return ambito;
	}

	

	
	public LocalDate getDataDecisao() {
		return dataDecisao;
	}

	public LocalDate getPrazo() {
		return prazo;
	}

	public PontoSituacao getPontoSituacao() {
		return pontoSituacao;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public Integer getProgresso() {
		return progresso;
	}

	
	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public Usuario getUsuarioActualizacao() {
		return usuarioActualizacao;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public LocalDateTime getDataActualizacao() {
		return dataActualizacao;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	
	public String getDescricaoDecisao() {
		return descricaoDecisao;
	}

	public void setDescricaoDecisao(String descricaoDecisao) {
		this.descricaoDecisao = descricaoDecisao;
	}

	public void setDataDecisao(LocalDate dataDecisao) {
		this.dataDecisao = dataDecisao;
	}

	public void setPrazo(LocalDate prazo) {
		this.prazo = prazo;
	}

	public void setPontoSituacao(PontoSituacao pontoSituacao) {
		this.pontoSituacao = pontoSituacao;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public void setProgresso(Integer progresso) {
		this.progresso = progresso;
	}

	

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Entidade> getPontosFocais() {
		return pontosFocais;
	}

	

	public void setPontosFocais(List<Entidade> pontosFocais) {
		this.pontosFocais = pontosFocais;
	}

	

	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public void setUsuarioActualizacao(Usuario usuarioActualizacao) {
		this.usuarioActualizacao = usuarioActualizacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataActualizacao(LocalDateTime dataActualizacao) {
		this.dataActualizacao = dataActualizacao;
	}

	public String getIdDecisao() {
		return idDecisao;
	}

	public void setIdDecisao(String idDecisao) {
		this.idDecisao = idDecisao;
	}

	
	public boolean isNova() {
		return codigo == null;
	}
	
	public String getCorTabela() {
	    switch (this.pontoSituacao) {
	        case PENDENTE:
	            return "table-warning";
	        case EM_PROGRESSO:
	            return "table-info";
	        case CONCLUIDA:
	            return "table-success";
	        case ATRASADA:
	            return "table-danger";
	        default:
	            return "";
	    }
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Decisao other = (Decisao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	

}
