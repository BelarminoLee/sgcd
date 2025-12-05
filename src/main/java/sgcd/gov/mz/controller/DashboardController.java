package sgcd.gov.mz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import sgcd.gov.mz.dto.EstatisticaEntidadeDTO;
import sgcd.gov.mz.model.PontoSituacao;
import sgcd.gov.mz.repository.Decisoes;
import sgcd.gov.mz.repository.Entidades;
import sgcd.gov.mz.service.DecisaoService;
import sgcd.gov.mz.service.EntidadeService;

@Controller
public class DashboardController {

	
	
	@Autowired
	Decisoes decisoes;
	
	@Autowired
	private Entidades entidades;
	
	@Autowired
	private DecisaoService decisaoService;
	
	
	@GetMapping("/")
	public ModelAndView dashboard(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
		
		ModelAndView mv = new ModelAndView("Dashboard");
		mv.addObject("totalPendente", decisoes.countByPontoSituacao(PontoSituacao.PENDENTE));
		mv.addObject("totalEmProgresso", decisoes.countByPontoSituacao(PontoSituacao.EM_PROGRESSO));
		mv.addObject("totalConcluido", decisoes.countByPontoSituacao(PontoSituacao.CONCLUIDA));
		mv.addObject("totalAtrasado", decisoes.countByPontoSituacao(PontoSituacao.ATRASADA));
		mv.addObject("totalDecisoes", decisoes.count());
		mv.addObject("totalEntidades", entidades.count());
		
		 List<EstatisticaEntidadeDTO> estatisticas = decisaoService.carregarEstatisticas();
		 mv.addObject("estatisticas", estatisticas);
		 		
	      
		return mv;
	}
		
}
