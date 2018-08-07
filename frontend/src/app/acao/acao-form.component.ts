import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Acao } from './acao.model';
import { AcaoService } from './acao.service';

@Component({
  selector: 'jhi-acao-form',
  templateUrl: './acao-form.component.html'
})
export class AcaoFormComponent implements OnInit, OnDestroy {
  acao: Acao;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private acaoService: AcaoService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.acao = new Acao();
      if (params['id']) {
        this.isEdit = true;
        this.acaoService.find(params['id']).subscribe(acao => this.acao = acao);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Acaos', routerLink: '/acao' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.acao.id !== undefined) {
      this.subscribeToSaveResponse(this.acaoService.update(this.acao));
    } else {
      this.subscribeToSaveResponse(this.acaoService.create(this.acao));
    }
  }

  private subscribeToSaveResponse(result: Observable<Acao>) {
    result.subscribe((res: Acao) => {
      this.isSaving = false;
      this.router.navigate(['/acao']);
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
