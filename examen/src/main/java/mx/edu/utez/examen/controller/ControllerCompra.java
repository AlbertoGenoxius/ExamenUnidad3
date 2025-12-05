package mx.edu.utez.examen.controller;

import mx.edu.utez.examen.dto.CreateCompraDTO;
import mx.edu.utez.examen.service.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/apis/compra")
@CrossOrigin(origins = "*")
public class ControllerCompra {
    CompraService compraService;
    public ControllerCompra(CompraService compraService) {
        this.compraService = compraService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        Map<String, Object> respuesta = compraService.getCompraById(id);
        int code = (int) respuesta.get("code");
        return new ResponseEntity<>(respuesta, code == 404 ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @GetMapping("/email/{clienteEmail}")
    public ResponseEntity<Object> getByEmail(@PathVariable String clienteEmail) {
        Map<String, Object> respuesta = compraService.getCompraByEmail(clienteEmail);
        int code = (int) respuesta.get("code");
        return new ResponseEntity<>(respuesta, code == 404 ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody CreateCompraDTO createCompraDTO){
        Map<String, Object> respuesta = compraService.createCompra(createCompraDTO);
        int code = (int) respuesta.get("code");
        System.out.println(createCompraDTO.getMontoTotal());
        System.out.println(createCompraDTO.getClienteEmail());
        System.out.println(createCompraDTO.getFechaCreacion());
        return new ResponseEntity<>(respuesta,
                code == 201 ? HttpStatus.CREATED :
                        code == 400 ? HttpStatus.BAD_REQUEST :
                                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        Map<String, Object> respuesta = compraService.deleteCompra(id);
        int code = (int) respuesta.get("code");
        return new ResponseEntity<>(respuesta, code == 404 ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
