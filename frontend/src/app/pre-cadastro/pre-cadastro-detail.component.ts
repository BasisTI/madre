import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PreCadastro } from './pre-cadastro.model';
import { PreCadastroService } from './pre-cadastro.service';

@Component({
  selector: 'jhi-pre-cadastro-detail',
  templateUrl: './pre-cadastro-detail.component.html'
})
export class PreCadastroDetailComponent implements OnInit, OnDestroy {

  preCadastro: PreCadastro;
  private subscription: Subscription;

  constructor(
    private preCadastroService: PreCadastroService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Pre Cadastros', routerLink: '/preCadastro' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.preCadastroService.find(id).subscribe((preCadastro) => {
      this.preCadastro = preCadastro;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
