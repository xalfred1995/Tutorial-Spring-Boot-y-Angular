package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    GameService gameService;

    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto, String titleGame, String clientName, Date date) {

        Specification<Loan> spec = Specification.where(null);

        if (clientName != null && !clientName.isEmpty()) {
            SearchCriteria clientCriteria = new SearchCriteria("client.name", ":", clientName);
            LoanSpecification clientSpec = new LoanSpecification(clientCriteria);
            spec = spec.and(clientSpec);
        }

        if (titleGame != null && !titleGame.isEmpty()) {
            SearchCriteria gameCriteria = new SearchCriteria("game.title", ":", titleGame);
            LoanSpecification gameSpec = new LoanSpecification(gameCriteria);
            spec = spec.and(gameSpec);
        }

        if (date != null) {

            SearchCriteria startDateCriteria = new SearchCriteria("startDate", "<=", date);
            LoanSpecification startDateSpec = new LoanSpecification(startDateCriteria);

            SearchCriteria endDateCriteria = new SearchCriteria("endDate", ">=", date);
            LoanSpecification endDateSpec = new LoanSpecification(endDateCriteria);

            spec = spec.and(startDateSpec).and(endDateSpec);
        }

        Pageable pageable = dto.getPageable().getPageable();
        return loanRepository.findAll(spec, pageable);

    }

    @Override
    public void save(LoanDto dto) throws Exception {
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();
        Long loanPeriod = endDate.getTime() - startDate.getTime();

        //Como getTime nos devuelve el tiempo en milisegundos hay que pasarlo a días
        loanPeriod = loanPeriod / (1000 * 60 * 60 * 24);

        //Validación de fechas
        if (endDate.before(startDate)) {
            throw new Exception("La fecha final del préstamo tiene que ser mayor que la de inicio");
        }

        //Validación para que un préstamo no supere los 14 días
        if (loanPeriod > 14) {
            throw new Exception("El préstamo no puede superar los 14 días");
        }

        //Validación para que el cliente no supere los prestamos maximos permitidos
        int loanCountByClient = loanRepository.loanCountByClient(dto.getClient().getId(), dto.getStartDate(), dto.getEndDate());
        if (loanCountByClient >= 1) {
            throw new Exception("El cliente ha alcanzado el máximo de préstamos en las fechas seleccionadas");
        }

        //Validación para ver si un juego está alquilado en ese periodo de tiempo
        boolean isGameLoaned = loanRepository.isGameLoaned(dto.getGame().getId(), dto.getStartDate(), dto.getEndDate());
        if (isGameLoaned) {
            throw new Exception("Este juego ya ha sido prestado en las fechas seleccionadas");
        }

        Loan loan = new Loan();
        BeanUtils.copyProperties(dto, loan, "id", "client", "game");
        loan.setClient(clientService.get(dto.getClient().getId()));
        loan.setGame(gameService.get(dto.getGame().getId()));

        System.out.println("DATOS PRÉSTAMO: " + loan);

        this.loanRepository.save(loan);
    }

    @Override
    public void delete(Long id) {
        this.loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

}
