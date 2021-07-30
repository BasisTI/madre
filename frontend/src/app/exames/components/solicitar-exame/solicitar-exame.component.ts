import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ExamModel } from '../../models/subjects/exames-model';
import { ItemSolicitacaoExame } from '../../models/subjects/item-solicitacao-exame';
import { Responsavel } from '../../models/subjects/responsavel-model';
import { SolicitacaoExame } from '../../models/subjects/solicitacao-exame';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { ItemSolicitacaoExameService } from '../../services/item-solicitacao-exame.service';
import { ResponsavelService } from '../../services/responsavel.service';
import { SolicitacaoExameService } from '../../services/solicitacao-exame.service';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { ExamesComponent } from '../exames/exames.component';

@Component({
  selector: 'app-solicitar-exame',
  templateUrl: './solicitar-exame.component.html',
  styleUrls: ['./solicitar-exame.component.css']
})
export class SolicitarExameComponent implements OnInit {

  @ViewChild(ExamesComponent) appExames: ExamesComponent

  constructor(private fb: FormBuilder,
              private unidadeFuncionalService: UnidadeFuncionalService,
              private responsavelService: ResponsavelService,
              private solicitacaoExameService: SolicitacaoExameService) { }

  opcoesDeAntimicrobianos: boolean;
  opcoesDeExameOuComparativo: boolean;
  unidades: UnidadeFuncional[] = [];
  responsaveis: Responsavel[] = [];
  exames: ExamModel[] = [];

  solicitarExame = this.fb.group({
    infoClinica: [null, Validators.required],
    usoAntimicrobianos24h: [null, Validators.required],
    pedidoPrimeiroExame: [null, Validators.required],
  });


  ngOnInit(): void {
    this.unidadeFuncionalService.GetUnidades().subscribe((response) => {
      this.unidades = response;
    });

    this.responsavelService.getResponsavel().subscribe((response) => {
      this.responsaveis = response;
    });
  }

  valid(): boolean {
    return this.solicitarExame.valid;
  }

  cadastrar() {

    let itensSolicitacaoExame = this.appExames.pegarItensSolicitacaoExame();

    let solicitacaoExame = this.solicitarExame.value;

    let solicitacao: SolicitacaoExame = {
      infoClinica: solicitacaoExame.infoClinica,
      usoAntimicrobianos24h: solicitacaoExame.usoAntimicrobianos24h,
      pedidoPrimeiroExame: solicitacaoExame.pedidoPrimeiroExame,
      itemSolicitacao: itensSolicitacaoExame
    };

    console.log(solicitacao); 

    this.solicitacaoExameService.solicitarExame(solicitacao).subscribe();
  }

}
