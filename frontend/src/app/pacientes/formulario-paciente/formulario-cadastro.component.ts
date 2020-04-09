import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';

@Component({
  selector: 'app-formulario-cadastro',
  templateUrl: './formulario-cadastro.component.html',
})
export class FormularioCadastroComponent implements OnInit, OnDestroy {
  constructor(private breadcrumbService: BreadcrumbService) {}

  ngOnInit(): void {
    this.breadcrumbService.setItems([
      { label: 'Pacientes', routerLink: 'pacientes' },
      { label: 'Cadastro de Paciente' },
    ]);
  }

  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }
}
