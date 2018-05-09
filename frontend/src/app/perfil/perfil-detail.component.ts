import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Perfil } from './perfil.model';
import { PerfilService } from './perfil.service';

@Component({
  selector: 'jhi-perfil-detail',
  templateUrl: './perfil-detail.component.html'
})
export class PerfilDetailComponent implements OnInit, OnDestroy {

  perfil: Perfil;
  private subscription: Subscription;

  constructor(
    private perfilService: PerfilService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Perfils', routerLink: '/perfil' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.perfilService.find(id).subscribe((perfil) => {
      this.perfil = perfil;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
