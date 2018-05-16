import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Especialidade } from './especialidade.model';
import { EspecialidadeService } from './especialidade.service';

@Component({
  selector: 'jhi-especialidade-detail',
  templateUrl: './especialidade-detail.component.html'
})
export class EspecialidadeDetailComponent implements OnInit, OnDestroy {

  especialidade: Especialidade;
  private subscription: Subscription;

  constructor(
    private especialidadeService: EspecialidadeService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Especialidades', routerLink: '/especialidade' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.especialidadeService.find(id).subscribe((especialidade) => {
      this.especialidade = especialidade;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
