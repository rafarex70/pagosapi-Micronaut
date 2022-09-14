package com.example.repos;

import com.example.domain.Persona;
import com.example.model.PagosPersonaDTO;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    @Query("select coalesce(sum(importe),0) as importe ,pe.name as name " +
            "from Persona pe left join Pago pa on pa.persona_id=pe.id " +
            "group by pe.id")
    @Join(value = "Pago", alias = "pa")
    public List<PagosPersonaDTO> balancePagos();
}
