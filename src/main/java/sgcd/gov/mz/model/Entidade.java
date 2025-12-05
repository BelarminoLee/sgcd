/**
 * 
 */
package sgcd.gov.mz.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Lee dos Santos
 *
 */
@Entity
public class Entidade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	private String nome;
	
	private String descricao;
	
	private String pontoFocal;
	
	private String telefone;
	
	private String email;
	
	private boolean activo;
	
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

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getPontoFocal() {
		return pontoFocal;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEmail() {
		return email;
	}

	public boolean isActivo() {
		return activo;
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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setPontoFocal(String pontoFocal) {
		this.pontoFocal = pontoFocal;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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
	
	public boolean isNova() {
		return codigo == null;
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
		Entidade other = (Entidade) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
	

}
