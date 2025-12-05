/**
 * 
 */
package sgcd.gov.mz.repository.filter;

/**
 * @author Lee dos Santos
 *
 */
public class EntidadeFilter {

	private String nome;

	private String pontoFocal;

	private String telefone;
	
	private String email;

	public String getNome() {
		return nome;
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

	public void setNome(String nome) {
		this.nome = nome;
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
	
	
}
