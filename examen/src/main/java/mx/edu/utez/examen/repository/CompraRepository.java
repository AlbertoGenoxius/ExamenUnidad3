package mx.edu.utez.examen.repository;

import mx.edu.utez.examen.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    Optional<Compra> findByClienteEmail(String clienteEmail);
    boolean existsByClienteEmail(String clienteEmail);
}
