import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Triagem } from './triagem.model';
import { TriagemService } from './triagem.service';

@Component({
  selector: 'jhi-triagem-form',
  templateUrl: './triagem-form.component.html'
})
export class TriagemFormComponent implements OnInit, OnDestroy {
  triagem: Triagem;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private triagemService: TriagemService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.triagem = new Triagem();
      if (params['id']) {
        this.isEdit = true;
        this.triagemService.find(params['id']).subscribe(triagem => this.triagem = triagem);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Triagems', routerLink: '/triagem' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.triagem.id !== undefined) {
      this.subscribeToSaveResponse(this.triagemService.update(this.triagem));
    } else {
      this.subscribeToSaveResponse(this.triagemService.create(this.triagem));
    }
  }

  private subscribeToSaveResponse(result: Observable<Triagem>) {
    result.subscribe((res: Triagem) => {
      this.isSaving = false;
      this.router.navigate(['/triagem']);
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
