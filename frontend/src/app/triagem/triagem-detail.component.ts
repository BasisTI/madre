import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Triagem } from './triagem.model';
import { TriagemService } from './triagem.service';

@Component({
  selector: 'jhi-triagem-detail',
  templateUrl: './triagem-detail.component.html'
})
export class TriagemDetailComponent implements OnInit, OnDestroy {

  triagem: Triagem;
  private subscription: Subscription;

  constructor(
    private triagemService: TriagemService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Triagems', routerLink: '/triagem' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.triagemService.find(id).subscribe((triagem) => {
      this.triagem = triagem;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
