import { LoanPage } from "./LoanPage";

export const LOAN_DATA: LoanPage = {
    content: [
        { id: 1, game:{ id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, client:{id: 1, name: 'Alfredo'}, startDate: new Date('2024-04-30'), endDate: new Date('2024-05-03') },
        { id: 2, game:{ id: 2, title: 'Juego 2', age: 6, category: { id: 2, name: 'Categoría 2' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, client:{id: 2, name: 'Jaume'}, startDate: new Date('2024-04-30'), endDate: new Date('2024-05-03') },
        { id: 3, game:{ id: 3, title: 'Juego 3', age: 6, category: { id: 3, name: 'Categoría 3' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, client:{id: 3, name: 'Oscar'}, startDate: new Date('2024-04-30'), endDate: new Date('2024-05-03') },
        { id: 4, game:{ id: 4, title: 'Juego 4', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, client:{id: 4, name: 'Pepe'}, startDate: new Date('2024-04-30'), endDate: new Date('2024-05-03') },
        { id: 5, game:{ id: 5, title: 'Juego 5', age: 6, category: { id: 2, name: 'Categoría 2' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, client:{id: 5, name: 'Jose'}, startDate: new Date('2024-04-30'), endDate: new Date('2024-05-03') },
        { id: 6, game:{ id: 6, title: 'Juego 6', age: 6, category: { id: 3, name: 'Categoría 3' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, client:{id: 6, name: 'Pepito'}, startDate: new Date('2024-04-30'), endDate: new Date('2024-05-03') },
    ],  
    pageable : {
        pageSize: 5,
        pageNumber: 0,
        sort: [
            {property: "id", direction: "ASC"}
        ]
    },
    totalElements: 6
}