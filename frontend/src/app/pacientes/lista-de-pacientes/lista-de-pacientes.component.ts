import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';

import { BreadcrumbService } from '../../breadcrumb/breadcrumb.service';
import { PacientesService } from '../pacientes.service';
import { PacienteSummary } from '../models/paciente.summary';

@Component({
  selector: 'app-lista-de-pacientes',
  templateUrl: './lista-de-pacientes.component.html',
})
export class ListaDePacientesComponent implements OnInit, OnDestroy {
  pacientes: PacienteSummary[];

  constructor(private breadcrumbService: BreadcrumbService, private service: PacientesService) {}

  ngOnInit(): void {
    this.breadcrumbService.setItems([{ label: 'Pacientes' }]);

    this.service.getListaDePacientes().subscribe((dados) => (this.pacientes = dados.content));
  }

  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }
}
