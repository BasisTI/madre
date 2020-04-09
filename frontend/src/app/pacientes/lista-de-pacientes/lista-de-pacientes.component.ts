import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';

import { BreadcrumbService } from '../../breadcrumb/breadcrumb.service';
import { PacientesService } from '../pacientes.service';

@Component({
  selector: 'app-lista-de-pacientes',
  templateUrl: './lista-de-pacientes.component.html',
})
export class ListaDePacientesComponent implements OnInit, OnDestroy, AfterViewInit {
  searchUrl = '';
  paginaDePacientes;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private pacientesService: PacientesService,
  ) {}

  ngOnInit(): void {
    this.breadcrumbService.setItems([{ label: 'Pacientes' }]);
  }

  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }

  ngAfterViewInit(): void {
    this.pacientesService.getListaDePacientes().then((response) => {
      this.paginaDePacientes = response.content;
    });
  }
}
