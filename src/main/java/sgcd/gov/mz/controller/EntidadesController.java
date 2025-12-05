/**
 * 
 */
package sgcd.gov.mz.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sgcd.gov.mz.controller.page.PageWrapper;
import sgcd.gov.mz.model.Entidade;
import sgcd.gov.mz.repository.Entidades;
import sgcd.gov.mz.repository.filter.EntidadeFilter;
import sgcd.gov.mz.security.UsuarioSistema;
import sgcd.gov.mz.service.EntidadeService;
import sgcd.gov.mz.service.exception.ImpossivelExcluirEntidadeException;

/**
 * @author Lee dos Santos
 *
 */
@Controller
@RequestMapping("/entidades")
public class EntidadesController {
	
	@Autowired
	private Entidades entidades;
	
	@Autowired
	private EntidadeService service;
	
	
	@RequestMapping("/nova")
	public ModelAndView nova(Entidade entidade) {
		ModelAndView mv = new ModelAndView("entidade/CadastroEntidade");
		
		return mv;
	}
	
	@RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Entidade entidade, BindingResult result, Model model,@AuthenticationPrincipal UsuarioSistema usuarioSistema, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(entidade);
		}
		entidade.setDataCriacao(LocalDateTime.now());
		entidade.setDataActualizacao(LocalDateTime.now());
		entidade.setUsuarioCriacao(usuarioSistema.getUsuario());
		entidade.setUsuarioActualizacao(usuarioSistema.getUsuario());
		service.salvar(entidade);
		attributes.addFlashAttribute("mensagem", "Entidade salva com sucesso!");
		return new ModelAndView("redirect:/entidades/nova");
	}
	
	@GetMapping
	public ModelAndView pesquisar(EntidadeFilter entidadeFilter, BindingResult result,
	        @PageableDefault(size = 10) Pageable pageable,
	        HttpServletRequest httpServletRequest) {

	    Page<Entidade> entidadesFiltradas = entidades.filtrar(entidadeFilter, pageable);

	    
	    ModelAndView mv = new ModelAndView("entidade/PesquisaEntidades");

	    PageWrapper<Entidade> paginaWrapper = new PageWrapper<>(entidadesFiltradas, httpServletRequest);
	    mv.addObject("pagina", paginaWrapper);

	    return mv;
	}



	
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Entidade entidade) {
		try {
			service.excluir(entidade);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Entidade entidade) {
		ModelAndView mv = nova(entidade);
		mv.addObject(entidade);
		return mv;
	}
	

}
