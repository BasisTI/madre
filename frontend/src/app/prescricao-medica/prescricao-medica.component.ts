import { BreadcrumbService } from './../breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
    selector: 'app-prescricao-medica',
    templateUrl: './prescricao-medica.component.html',
    styleUrls: ['./prescricao-medica.component.css']
})
export class PrescricaoMedicaComponent implements OnInit, OnDestroy {

    pacientes: any[];

    constructor(private breadcrumbService: BreadcrumbService) { }


    ngOnInit(
    ) {
        this.pacientes = [
            {
                idade: '54', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'João Pereira'
            },
            {
                idade: '61', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'José da Silva'
            },
            {
                idade: '53', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'Maria do Carmo'
            },
            {
                idade: '66', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'Pedro Silva'
            },
            {
                idade: '54', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'João Pereira'
            },
            {
                idade: '61', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'José da Silva'
            },
            {
                idade: '53', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'Maria do Carmo'
            },
            {
                idade: '66', prontuario: '5299078',
                responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'Pedro Silva'
            },
        ];
        this.breadcrumbService.setItems([{ label: 'Prescrição Médica' }]);
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
