package com.diplomado.diplomado.categoria;

import com.diplomado.diplomado.config.JwtConfig;
import com.diplomado.diplomado.utils.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/categoria")
public class CategoriaController {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
    private final CategoriaService categoriaService;
    private final JwtConfig jwtConfig;

    @Autowired
    public CategoriaController(CategoriaService categoriaService, JwtConfig jwtConfig) {
        this.categoriaService = categoriaService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<CategoriaDto>> crearCategoria(
            @RequestBody CategoriaDto categoriaDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear categoria");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear categoria: {}", username);
        CategoriaDto nuevaCategoria = categoriaService.crearCategoria(categoriaDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Categoria creada exitosamente", nuevaCategoria));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<CategoriaDto>>> obtenerTodasLasCategorias(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener categorias");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener categorias: {}", username);
        List<CategoriaDto> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(new ResponseDto<>(true, "Categorias obtenidas exitosamente", categorias));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CategoriaDto>> obtenerCategoriaPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener categoria");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener categoria con ID: {}", id);
        CategoriaDto categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Categoria obtenida exitosamente", categoria));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<CategoriaDto>> actualizarCategoria(
            @PathVariable Integer id,
            @RequestBody CategoriaDto categoriaDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar categoria");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar categoria con ID: {}", id);
        CategoriaDto categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Categoria actualizada exitosamente", categoriaActualizada));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarCategoria(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar categoria");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar categoria con ID: {}", id);
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Categoria eliminada exitosamente", null));
    }
}
