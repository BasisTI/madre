import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Anexo } from './anexo.model';
import { AnexoService } from './anexo.service';

@Component({
  selector: 'jhi-anexo-form',
  templateUrl: './anexo-form.component.html'
})
export class AnexoFormComponent implements OnInit, OnDestroy {
  anexo: Anexo;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private anexoService: AnexoService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.anexo = new Anexo();
      if (params['id']) {
        this.isEdit = true;
        this.anexoService.find(params['id']).subscribe(anexo => this.anexo = anexo);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Anexos', routerLink: '/anexo' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.anexo.id !== undefined) {
      this.subscribeToSaveResponse(this.anexoService.update(this.anexo));
    } else {
      this.subscribeToSaveResponse(this.anexoService.create(this.anexo));
    }
  }

  private subscribeToSaveResponse(result: Observable<Anexo>) {
    result.subscribe((res: Anexo) => {
      this.isSaving = false;
      this.router.navigate(['/anexo']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
    });
  }

  private addConfirmationMessage() {
    if (this.isEdit) {
      this.pageNotificationService.addUpdateMsg();
    } else {
      this.pageNotificationService.addCreateMsg();
    }
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
    this.breadcrumbService.reset();
  }
}
