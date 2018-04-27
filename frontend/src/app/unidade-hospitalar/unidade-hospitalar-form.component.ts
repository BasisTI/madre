import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { UnidadeHospitalar } from './unidade-hospitalar.model';
import { UnidadeHospitalarService } from './unidade-hospitalar.service';

@Component({
  selector: 'jhi-unidade-hospitalar-form',
  templateUrl: './unidade-hospitalar-form.component.html'
})
export class UnidadeHospitalarFormComponent implements OnInit, OnDestroy {
  unidadeHospitalar: UnidadeHospitalar;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private unidadeHospitalarService: UnidadeHospitalarService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.unidadeHospitalar = new UnidadeHospitalar();
      if (params['id']) {
        this.isEdit = true;
        this.unidadeHospitalarService.find(params['id']).subscribe(unidadeHospitalar => this.unidadeHospitalar = unidadeHospitalar);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Unidade Hospitalars', routerLink: '/unidadeHospitalar' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.unidadeHospitalar.id !== undefined) {
      this.subscribeToSaveResponse(this.unidadeHospitalarService.update(this.unidadeHospitalar));
    } else {
      this.subscribeToSaveResponse(this.unidadeHospitalarService.create(this.unidadeHospitalar));
    }
  }

  private subscribeToSaveResponse(result: Observable<UnidadeHospitalar>) {
    result.subscribe((res: UnidadeHospitalar) => {
      this.isSaving = false;
      this.router.navigate(['/unidadeHospitalar']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
      this.addErrorMessage();
    });
  }

  private addConfirmationMessage() {
    if (this.isEdit) {
      this.pageNotificationService.addUpdateMsg();
    } else {
      this.pageNotificationService.addCreateMsg();
    }
  }
  private addErrorMessage() {
    this.pageNotificationService.addErrorMessage("Dados inválidos", "Erro");
  }


  ngOnDestroy() {
    this.routeSub.unsubscribe();
    this.breadcrumbService.reset();
  }
  

}
