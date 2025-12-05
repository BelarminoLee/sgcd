/**
 * 
 */
package sgcd.gov.mz.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sgcd.gov.mz.controller.page.PageWrapper;
import sgcd.gov.mz.model.Decisao;
import sgcd.gov.mz.model.PontoSituacao;
import sgcd.gov.mz.repository.Decisoes;
import sgcd.gov.mz.repository.Entidades;
import sgcd.gov.mz.repository.filter.DecisaoFilter;
import sgcd.gov.mz.security.UsuarioSistema;
import sgcd.gov.mz.service.DecisaoService;
import sgcd.gov.mz.service.exception.ImpossivelExcluirEntidadeException;

/**
 * @author Lee dos Santos
 *
 */
@Controller
@RequestMapping("/decisoes")
public class DecisoesController {
	
	@Autowired
	private Decisoes decisoes;
	
	@Autowired
	private DecisaoService service;
	
	@Autowired
	private Entidades entidades;
	
	
	@RequestMapping("/nova")
	public ModelAndView nova(Decisao decisao) {
		ModelAndView mv = new ModelAndView("decisao/CadastroDecisao");
		mv.addObject("decisao", decisao); // IMPORTANTE
		mv.addObject("pontosSituacoes", PontoSituacao.values());
		mv.addObject("entidades", entidades.findAll());
	  
		return mv;
	}
	
	@PostMapping({ "/nova", "{\\+d}" })
	public ModelAndView salvar(@Valid Decisao decisao, BindingResult result,
	        Model model, @AuthenticationPrincipal UsuarioSistema usuarioSistema, RedirectAttributes attributes,
	        HttpServletRequest req) {

	    if (result.hasErrors()) {
	        return nova(decisao);
	    }

	   
	    if (decisao.getCodigo() != null) {
	        // Edição - buscar a entidade existente para evitar inserir duplicado
	    	Decisao decisaoExistente = service.buscarPorId(decisao.getCodigo());
	    	    // Atualizar os campos que podem ser alterados
	            decisaoExistente.setAmbito(decisao.getAmbito());
	            decisaoExistente.setDescricaoDecisao(decisao.getDescricaoDecisao());
	            decisaoExistente.setDataDecisao(decisao.getDataDecisao());
	            decisaoExistente.setPrazo(decisao.getPrazo());
	            decisaoExistente.setEntidade(decisao.getEntidade());
	            decisaoExistente.setPontosFocais(decisao.getPontosFocais());
	            decisaoExistente.setProgresso(decisao.getProgresso());
	            decisaoExistente.setObservacao(decisao.getObservacao());
	            decisaoExistente.setDataActualizacao(LocalDateTime.now());
	            decisaoExistente.setUsuarioActualizacao(usuarioSistema.getUsuario());
	            decisaoExistente.setPontoSituacao(decisao.getPontoSituacao());
	            // NÃO altera idDecisao nem dataCriacao, pontoSituacao, usuarioCriacao

	            service.salvar(decisaoExistente);
	        } else {
	            // Novo registro
	            service.gerarCodigo(decisao);
	            decisao.setPontoSituacao(PontoSituacao.PENDENTE);
	            decisao.setDataCriacao(LocalDateTime.now());
	            decisao.setDataActualizacao(LocalDateTime.now());
	            decisao.setUsuarioCriacao(usuarioSistema.getUsuario());
	            decisao.setUsuarioActualizacao(usuarioSistema.getUsuario());
	    service.salvar(decisao);
	        }

	        attributes.addFlashAttribute("mensagem", "Decisão salva com sucesso!");
	        return new ModelAndView("redirect:/decisoes/nova");
	    }
	//@GetMapping
	public String pesquisar(DecisaoFilter filtro, Model model) {
	    List<Decisao> resultados = decisoes.filtrar(filtro);
	    model.addAttribute("decisoes", resultados);
	    model.addAttribute("filtro", filtro);
	    System.out.println("Lista total"+resultados.size());
	    return "decisao/PesquisaDecisoes";
	}
	
	//@GetMapping
	public String pesquisarComPaginacao(@RequestParam(required = false, defaultValue = "") String termo,
	                                    @PageableDefault(size = 10, sort = "dataDecisao", direction = Sort.Direction.DESC) Pageable pageable,
	                                    Model model) {

	    List<Decisao> pagina = service.pesquisarTodosCampos(termo);
	
	    model.addAttribute("decisoes", pagina);
	    model.addAttribute("termo", termo); // manter o termo no input
	    return "decisao/PesquisaDecisoes";
	}
	
	
	  @GetMapping
	  public ModelAndView pesquisar(DecisaoFilter decisaoFilter,
	  BindingResult result,
	  
	  @PageableDefault(size = 10) Pageable pageable, HttpServletRequest
	  httpServletRequest) { ModelAndView mv = new
	  ModelAndView("decisao/PesquisaDecisoes");
	  
	  mv.addObject("pontosSituacoes", PontoSituacao.values());
	  mv.addObject("entidades", entidades.findAll());
	  
	  PageWrapper<Decisao> paginaWrapper = new
	  PageWrapper<>(decisoes.filtrar(decisaoFilter, pageable), httpServletRequest);
	  mv.addObject("decisao", decisoes);
	  mv.addObject("pagina", paginaWrapper);
	  
	  return mv; }
	 
	@GetMapping({ "/{codigo}" })
	public ModelAndView editar(@PathVariable Long codigo) {
		Decisao decisao = decisoes.buscarComResponsaveis(codigo);
		ModelAndView mv = nova(decisao);
		mv.addObject(decisao);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Decisao decisao) {
		try {
			service.excluir(decisao);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	
	@GetMapping("/modal")
	public String listarPorSituacao(@RequestParam String filtroSituacao, Model model){ 
	    DecisaoFilter filtro = new DecisaoFilter();
	    filtro.setPontoSituacao(PontoSituacao.valueOf(filtroSituacao));

	    List<Decisao> resultados = decisoes.filtrar(filtro);
	    model.addAttribute("decisoes", resultados);

	    return "decisao/fragmentoLista :: listaDecisoes";
}
	@GetMapping("/decisoes/por-status")
	@ResponseBody
	public List<Decisao> listarPorStatus(@RequestParam("status") PontoSituacao status) {
	    return decisoes.findByPontoSituacao(status);
	}

}

