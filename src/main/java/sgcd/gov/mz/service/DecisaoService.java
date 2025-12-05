/**
 * 
 */
package sgcd.gov.mz.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sgcd.gov.mz.dto.EstatisticaEntidadeDTO;
import sgcd.gov.mz.model.Decisao;
import sgcd.gov.mz.model.PontoSituacao;
import sgcd.gov.mz.model.Usuario;
import sgcd.gov.mz.repository.Decisoes;
import sgcd.gov.mz.service.exception.EmailUsuarioJaCadastradoException;
import sgcd.gov.mz.service.exception.ImpossivelExcluirEntidadeException;
import sgcd.gov.mz.service.exception.NomeEstiloJaCadastradoException;

/**
 * @author Lee dos Santos
 *
 */
@Service
public class DecisaoService {
	
	@Autowired
	Decisoes decisaoRepo;
	

	@Transactional
	public Decisao salvar(Decisao decisao) {
		      
		/*
		 * Optional<Decisao> entidadeOptional =
		 * decisaoRepo.findByIdDecisao(decisao.getIdDecisao()); if
		 * (entidadeOptional.isPresent()&& entidadeOptional.get().equals(decisao)) {
		 * throw new NomeEstiloJaCadastradoException("Nome da decisao já cadastrada"); }
		 */
		
		
		return decisaoRepo.save(decisao);
	}
	
	public List<Decisao> pesquisarTodosCampos(String termo) {
	    return decisaoRepo.pesquisarTodosCampos(termo.toLowerCase());
	}
	
	@Transactional
	public void excluir(Decisao decisao) {
		try {
			
			decisaoRepo.delete(decisao);
			decisaoRepo.flush();
			
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar a decisao.");
		}
	}
	
	public void gerarCodigo(Decisao decisao) {
	    LocalDate hoje = LocalDate.now();
	    String mes = hoje.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
	    int ano = hoje.getYear();
	    String prefixo = mes + "-" + ano + "-";

	    List<String> codigos = decisaoRepo.findUltimoCodigo(prefixo);
	    int sequencia = 1;

	    if (!codigos.isEmpty()) {
	        String ultimoCodigo = codigos.get(0);
	        String[] partes = ultimoCodigo.split("-");
	        try {
	            sequencia = Integer.parseInt(partes[2]) + 1;
	        } catch (NumberFormatException e) {
	            // ignora e usa 1
	        }
	    }

	    String codigoFormatado = String.format("%04d", sequencia);
	    decisao.setIdDecisao(prefixo + codigoFormatado);
	}
	
	
	
	@Scheduled(cron = "0 0 * * * *") // Executa de hora em hora
	public void atualizarSituacoesAutomaticamente() {
        atualizarSituacoes(); // seu método já criado
    }
	
	 public Decisao buscarPorId(Long codigo) {
	        return decisaoRepo.findById(codigo)
	                .orElseThrow(() -> new EntityNotFoundException("Decisao não encontrada com código: " + codigo));
	    }
	 
	 public void atualizarSituacoes() {
	        List<Decisao> decisoes = decisaoRepo.findAll();
	        LocalDate hoje = LocalDate.now();

	        for (Decisao d : decisoes) {
	            if (d.getPontoSituacao() == PontoSituacao.CONCLUIDA) continue;

	            if (d.getPrazo().isBefore(d.getDataDecisao())) {
	                // regra de validação: dataDecisao não pode ser depois do prazo
	                continue;
	            }
	            long horasDesdeDecisao = ChronoUnit.HOURS.between(d.getDataDecisao().atStartOfDay(), hoje.atStartOfDay());

	            if (hoje.isAfter(d.getPrazo())) {
	                d.setPontoSituacao(PontoSituacao.ATRASADA);
	            } else if (horasDesdeDecisao >= 24) {
	                d.setPontoSituacao(PontoSituacao.EM_PROGRESSO);
	            } else {
	                d.setPontoSituacao(PontoSituacao.PENDENTE);
	            }

	            decisaoRepo.save(d);
	        }
	    }
	 
	 public List<EstatisticaEntidadeDTO> carregarEstatisticas() {
		    List<Object[]> dados = decisaoRepo.buscarTotaisPorEntidade();
		    Map<String, EstatisticaEntidadeDTO> mapa = new LinkedHashMap<>();

		    for (Object[] linha : dados) {
		        String nome = (String) linha[0];
		        PontoSituacao situacao = (PontoSituacao) linha[1];
		 Long total = (Long) linha[2];

		        mapa.computeIfAbsent(nome, EstatisticaEntidadeDTO::new)
		            .adicionar(situacao, total);
		    }

		    return new ArrayList<>(mapa.values());
		}
	}

