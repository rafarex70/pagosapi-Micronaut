package com.example.repos;


import com.example.domain.Pago;
import com.example.model.BalanceDTO;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findAllOrderByFechaDesc();
}
