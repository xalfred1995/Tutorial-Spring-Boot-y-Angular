import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { Loan } from './model/Loan';
import { LoanPage } from './model/LoanPage';
import { LOAN_DATA } from './model/mock-loans';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class LoanService {

    constructor(
      private http: HttpClient
    ) { }

    getLoans(pageable: Pageable, gameTitle?: string, clientName?: string, date?: Date): Observable<LoanPage> {
      return this.http.post<LoanPage>(this.composeFindUrl(gameTitle, clientName, date),{pageable:pageable});
    }

    saveLoan(loan: Loan): Observable<void> {
      let url = 'http://localhost:8080/loan';
      return this.http.put<void>(url, loan).pipe(
        catchError(this.handleError)
      );
    }

    deleteLoan(idLoan : number): Observable<void> {
      return this.http.delete<void>('http://localhost:8080/loan/'+idLoan);
    }
    
    getAllLoans(pageable: Pageable): Observable<LoanPage> {
      return this.http.post<LoanPage>('http://localhost:8080/loan', {pageable:pageable});
    }

    private composeFindUrl(gameTitle?: string, clientName?: string, date?: Date): string {
      let params = '';

      if (gameTitle != null) {
        if (params != '') params += "&";
          params += `gameTitle=${gameTitle}`;
      }

      if (clientName != null) {
          if (params != '') params += '&';
          params += `clientName=${clientName}`;
      }

      if (date != null) {
        if (params != '') params += '&';
        params += `date=${date.toISOString()}`;
      }

      let url = 'http://localhost:8080/loan';

      if (params == '') return url;
      else return url + '?'+params;
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Error del lado del cliente
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Error del lado del servidor
      if (error.status === 500 && error.error) {
        // Aquí asumimos que el mensaje de error está en el cuerpo de la respuesta
        errorMessage = error.error;
      } else {
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
    }
    return throwError(errorMessage);
  }
}
