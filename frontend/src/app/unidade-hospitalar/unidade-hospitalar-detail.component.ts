import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { UnidadeHospitalar } from './unidade-hospitalar.model';
import { UnidadeHospitalarService } from './unidade-hospitalar.service';

@Component({
  selector: 'jhi-unidade-hospitalar-detail',
  templateUrl: './unidade-hospitalar-detail.component.html'
})
export class UnidadeHospitalarDetailComponent implements OnInit, OnDestroy {

  unidadeHospitalar: UnidadeHospitalar;
  private subscription: Subscription;

  constructor(
    private unidadeHospitalarService: UnidadeHospitalarService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Unidade Hospitalars', routerLink: '/unidadeHospitalar' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.unidadeHospitalarService.find(id).subscribe((unidadeHospitalar) => {
      this.unidadeHospitalar = unidadeHospitalar;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
