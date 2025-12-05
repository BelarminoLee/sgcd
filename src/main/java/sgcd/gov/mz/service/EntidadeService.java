/**
 * 
 */
package sgcd.gov.mz.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sgcd.gov.mz.model.Entidade;
import sgcd.gov.mz.repository.Entidades;
import sgcd.gov.mz.service.exception.ImpossivelExcluirEntidadeException;
import sgcd.gov.mz.service.exception.NomeEstiloJaCadastradoException;

/**
 * @author Lee dos Santos
 *
 */
@Service
public class EntidadeService {

	@Autowired
	private Entidades entidades;
	
	@Transactional
	public Entidade salvar(Entidade entidade) {
		Optional<Entidade> entidadeOptional = entidades.findByNome(entidade.getNome());
		if (entidadeOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome da entidade já cadastrado");
		}
		
		return entidades.save(entidade);
	}
	
	@Transactional
	public void excluir(Entidade entidade) {
		try {
			
			entidades.delete(entidade);
			entidades.flush();
			
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar entidade pois tem alguma accao no sistema.");
		}
	}
	
}
