import { BreadcrumbService } from './../breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-prescricao',
  templateUrl: './prescricao.component.html',
  styleUrls: ['./prescricao.component.css']
})
export class PrescricaoComponent implements OnInit, OnDestroy {

    pacientes = [
        {   idade: '53', prontuario: '5299078',
        responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'João Pereira' },
        {   idade: '53', prontuario: '5299078',
        responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'José da Silva' },
        {   idade: '53', prontuario: '5299078',
        responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'Maria do Carmo' },
        {   idade: '53', prontuario: '5299078',
        responsavel: 'Mateus', dataAtendimento: '18/03/2019', nome: 'Pedro Silva' },


    ];

  constructor(private breadcrumbService: BreadcrumbService) {}


  ngOnInit() {
    this.breadcrumbService.setItems([{ label: 'Prescrição' }]);
  }

  ngOnDestroy() {
    this.breadcrumbService.reset();
  }

}
