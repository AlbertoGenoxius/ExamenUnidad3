package mx.edu.utez.examen.service;

import mx.edu.utez.examen.dto.CreateCompraDTO;
import mx.edu.utez.examen.model.Compra;
import mx.edu.utez.examen.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CompraService {
    CompraRepository compraRepository;
    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }
    public Map<String, Object> createCompra(CreateCompraDTO createCompraDTO){
        Map<String, Object> respuesta = new HashMap<>();
        if(compraRepository.existsByClienteEmail(createCompraDTO.getClienteEmail())){
            respuesta.put("mensaje", "El email del cliente ya está registrado en el sistema");
            respuesta.put("code", 400);
            return respuesta;
        }
        if(createCompraDTO.getMontoTotal() <= 0){
            respuesta.put("mensaje", "El monto total debe ser mayor a 0");
            respuesta.put("code", 400);
            return respuesta;
        }
        LocalDate fechaCreacion = createCompraDTO.getFechaCreacion();
        LocalDate hoy = LocalDate.now();
        if(fechaCreacion.isAfter(hoy)){
            respuesta.put("mensaje", "La fecha de creación no puede ser futura");
            respuesta.put("code", 400);
            return respuesta;
        }
        Compra unaCompra = new Compra();
        unaCompra.setMontoTotal(createCompraDTO.getMontoTotal());
        unaCompra.setClienteEmail(createCompraDTO.getClienteEmail());
        unaCompra.setFechaCreacion(createCompraDTO.getFechaCreacion());
        compraRepository.save(unaCompra);
        respuesta.put("mensaje", "Compra creada correctamente");
        respuesta.put("compra", unaCompra);
        respuesta.put("code", 201);
        return respuesta;
    }
    public Map<String, Object> getCompraById(Integer id){
        Map<String, Object> mapResponse = new HashMap<>();
        Optional<Compra> optCompra = compraRepository.findById(id);
        if(optCompra.isPresent()){
            Compra compra = optCompra.get();
            mapResponse.put("compra", compra);
            mapResponse.put("mensaje", "Compra encontrada correctamente");
            mapResponse.put("code", 200);
        }else{
            mapResponse.put("mensaje", "Compra no encontrada");
            mapResponse.put("code", 404);
        }
        return mapResponse;
    }
    public Map<String, Object> getCompraByEmail(String clienteEmail){
        Map<String, Object> mapResponse = new HashMap<>();
        Optional<Compra> optCompra = compraRepository.findByClienteEmail(clienteEmail);
        if(optCompra.isPresent()){
            Compra compra = optCompra.get();
            mapResponse.put("compra", compra);
            mapResponse.put("mensaje", "Compra encontrada por email del cliente");
            mapResponse.put("code", 200);
        }else{
            mapResponse.put("mensaje", "No se encontró compra con ese email del cliente");
            mapResponse.put("code", 404);
        }
        return mapResponse;
    }
    public Map<String, Object> deleteCompra(Integer id){
        Map<String, Object> mapResponse = new HashMap<>();
        Optional<Compra> optCompra = compraRepository.findById(id);
        if(optCompra.isPresent()){
            Compra unaCompra = optCompra.get();
            compraRepository.delete(unaCompra);
            mapResponse.put("compra", unaCompra);
            mapResponse.put("mensaje", "Compra eliminada correctamente");
            mapResponse.put("code", 200);
        }else{
            mapResponse.put("mensaje", "Compra no encontrada");
            mapResponse.put("code", 404);
        }
        return mapResponse;
    }
}
