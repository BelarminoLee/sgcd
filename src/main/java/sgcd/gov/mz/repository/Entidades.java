/**
 * 
 */
package sgcd.gov.mz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sgcd.gov.mz.model.Entidade;
import sgcd.gov.mz.repository.helper.entidade.EntidadesQueries;

/**
 * @author Lee dos Santos
 *
 */
public interface Entidades extends JpaRepository<Entidade, Long>, EntidadesQueries{
	
	public Optional<Entidade> findByNome(String nome);

}
