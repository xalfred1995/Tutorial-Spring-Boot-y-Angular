package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    List<Loan> findAll(Specification<Loan> spec);

    /**
     * Obtiene el número de préstamos para un cliente específico en un rango de fechas.
     *
     * @param clientId   El ID del cliente para el cual se cuentan los préstamos.
     * @param startDate  La fecha de inicio del rango.
     * @param endDate    La fecha de fin del rango.
     * @return El número de préstamos para el cliente en el rango especificado.
     */
    @Query("SELECT COUNT(1) FROM Loan l WHERE l.client.id = :clientId AND l.startDate <= :endDate AND l.endDate >= :startDate")
    int loanCountByClient(@Param("clientId") Long clientId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * Verifica si un juego ha sido prestado en un rango de fechas.
     *
     * @param gameId     El ID del juego que se desea verificar si está prestado.
     * @param startDate  La fecha de inicio del rango.
     * @param endDate    La fecha de fin del rango.
     * @return Devuelve 1 si el juego está prestado en el rango especificado, de lo contrario, devuelve 0.
     */
    @Query("SELECT COUNT(1) > 0 FROM Loan l WHERE l.game.id = :gameId AND l.startDate <= :endDate AND l.endDate >= :startDate")
    boolean isGameLoaned(@Param("gameId") Long gameId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
