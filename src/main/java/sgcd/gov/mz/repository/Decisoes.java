/**
 * 
 */
package sgcd.gov.mz.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sgcd.gov.mz.model.Decisao;
import sgcd.gov.mz.model.PontoSituacao;
import sgcd.gov.mz.repository.helper.decisao.DecisoesQueries;

/**
 * @author Lee dos Santos
 *
 */
public interface Decisoes extends JpaRepository<Decisao, Long>, DecisoesQueries {
	
	public  Optional<Decisao> findByIdDecisao(String idDecisao);

	public Decisao findByCodigo(Long codigo);

	List<Decisao> findByPontoSituacaoAndDataCriacaoBefore(PontoSituacao situacao, LocalDateTime data);
	
	long countByPontoSituacao(PontoSituacao situacao);
	List<Decisao> findByPontoSituacao(PontoSituacao status);
	
	@Query("SELECT d.idDecisao FROM Decisao d WHERE d.idDecisao LIKE CONCAT(:prefixo, '%') ORDER BY d.idDecisao DESC")
	List<String> findUltimoCodigo(@Param("prefixo") String prefixo);
	
	@Query("SELECT d FROM Decisao d WHERE " +
		       "LOWER(d.idDecisao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
		       "LOWER(d.ambito) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
		       "CAST(d.pontoSituacao AS string) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
		       "LOWER(d.descricaoDecisao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
		       "CAST(d.dataDecisao AS string) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
		       "LOWER(d.entidade.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
		List<Decisao> pesquisarTodosCampos(@Param("termo") String termo);
	
	@Query("SELECT e.nome, d.pontoSituacao, COUNT(d) " +
		       "FROM Decisao d JOIN d.entidade e " +
		       "GROUP BY e.nome, d.pontoSituacao")
		List<Object[]> buscarTotaisPorEntidade();


}
