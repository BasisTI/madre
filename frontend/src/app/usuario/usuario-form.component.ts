import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Usuario } from './usuario.model';
import { UsuarioService } from './usuario.service';
import { MultiSelectModule } from 'primeng/multiselect';
import {  } from '../rel-usuario-unidadehospitalar';// falta fazer primeiro do rel_usu....

@Component({
  selector: 'jhi-usuario-form',
  templateUrl: './usuario-form.component.html'
})
export class UsuarioFormComponent implements OnInit, OnDestroy {
  usuario: Usuario;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private usuarioService: UsuarioService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.usuario = new Usuario();
      this.usuario.ativo = true;
      if (params['id']) {
        this.isEdit = true;
        this.usuarioService.find(params['id']).subscribe(usuario => this.usuario = usuario);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Usuarios', routerLink: '/usuario' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.usuario.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioService.update(this.usuario));
    } else {
      this.subscribeToSaveResponse(this.usuarioService.create(this.usuario));
    }
  }

  private subscribeToSaveResponse(result: Observable<Usuario>) {
    result.subscribe((res: Usuario) => {
      this.isSaving = false;
      this.router.navigate(['/usuario']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
      this.addErrorMessage(res);
    });
  }

  private addConfirmationMessage() {
    if (this.isEdit) {
      this.pageNotificationService.addUpdateMsg();
    } else {
      this.pageNotificationService.addCreateMsg();
    }
  }
  private addErrorMessage(res: Response) {
    if (res.headers.toJSON()['x-cadastrosbasicosapp-errorexists'] != null) {
      this.pageNotificationService.addErrorMessage('Registro já cadastrado!');
    } else {
      this.pageNotificationService.addErrorMessage('Dados inválidos!');
    }
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
    this.breadcrumbService.reset();
  }
}
