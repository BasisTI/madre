import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Paciente } from './paciente.model';
import { PacienteService } from './paciente.service';

@Component({
  selector: 'jhi-paciente-detail',
  templateUrl: './paciente-detail.component.html'
})
export class PacienteDetailComponent implements OnInit, OnDestroy {

  paciente: Paciente;
  private subscription: Subscription;

  constructor(
    private pacienteService: PacienteService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Pacientes', routerLink: '/paciente' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.pacienteService.find(id).subscribe((paciente) => {
      this.paciente = paciente;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
