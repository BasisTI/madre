import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Anexo } from './anexo.model';
import { AnexoService } from './anexo.service';

@Component({
  selector: 'jhi-anexo-detail',
  templateUrl: './anexo-detail.component.html'
})
export class AnexoDetailComponent implements OnInit, OnDestroy {

  anexo: Anexo;
  private subscription: Subscription;

  constructor(
    private anexoService: AnexoService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Anexos', routerLink: '/anexo' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.anexoService.find(id).subscribe((anexo) => {
      this.anexo = anexo;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
