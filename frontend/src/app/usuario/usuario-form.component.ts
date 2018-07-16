import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem, ListboxModule } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Usuario } from './usuario.model';
import { UsuarioService } from './usuario.service';
import { MultiSelectModule } from 'primeng/multiselect';

import { Perfil, PerfilService } from '../perfil';
import { Especialidade, EspecialidadeService } from '../especialidade';
import { ResponseWrapper } from '../shared';

@Component({
  selector: 'jhi-usuario-form',
  templateUrl: './usuario-form.component.html'
})
export class UsuarioFormComponent implements OnInit, OnDestroy {

  perfils: Perfil[];
  esconde: boolean;
  especialidades: Especialidade[];
  myEspecialidade: Especialidade;
  myPerfil: Perfil;
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
    private perfilService: PerfilService,
    private especialidadeService: EspecialidadeService,
  ) {}

  ngOnInit() {
    this.esconde = true;
    this.myPerfil = new Perfil();
    this.isSaving = false;
    this.perfilService.query().subscribe((res: ResponseWrapper) => {
      this.perfils = res.json;
    });
    this.especialidadeService.query().subscribe((res: ResponseWrapper) => {
      this.especialidades = res.json;
    });
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.usuario = new Usuario();
      this.usuario.ativo = true;
      if (params['id']) {
        this.isEdit = true;
        this.esconde = false;
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
    if (res.headers.toJSON()['x-cadastrosbasicosapp-erroremailexists'] == "Email already in use") {
      this.pageNotificationService.addErrorMessage('Email já cadastrado!');
    } else if (res.headers.toJSON()['x-cadastrosbasicosapp-errorloginexists'] == "Login already in use") {
      this.pageNotificationService.addErrorMessage('Login já cadastrado!');
    } else {
      this.pageNotificationService.addErrorMessage('Dados inválidos!');
    }
  
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
    this.breadcrumbService.reset();
  }
  trocaPerfil(){
    this.myPerfil = this.usuario.perfil;
    if (this.myPerfil.nomePerfil == 'Médico'){
      this.esconde = false;
    }else {
      this.esconde = true;
      this.usuario.especialidade = null;
    }
  }
}
