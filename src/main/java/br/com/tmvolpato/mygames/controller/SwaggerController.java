package br.com.tmvolpato.mygames.controller;

import br.com.tmvolpato.mygames.common.web.util.Mappings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller of documentation of application.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */

@Controller
@RequestMapping(Mappings.DOCUMENTATION_API)
public class SwaggerController {

    @GetMapping
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}
