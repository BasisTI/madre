import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ExamModel } from '../../models/subjects/exames-model';
import { Responsavel } from '../../models/subjects/responsavel-model';
import { SolicitacaoExame } from '../../models/subjects/solicitacao-exame';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { ResponsavelService } from '../../services/responsavel.service';
import { SolicitacaoExameService } from '../../services/solicitacao-exame.service';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';

@Component({
  selector: 'app-solicitar-exame',
  templateUrl: './solicitar-exame.component.html',
  styleUrls: ['./solicitar-exame.component.css']
})
export class SolicitarExameComponent implements OnInit {

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
    let solicitacaoExame = this.solicitarExame.value;

    let solicitacao: SolicitacaoExame = {
      infoClinica: solicitacaoExame.infoClinica,
      usoAntimicrobianos24h: solicitacaoExame.usoAntimicrobianos24h,
      pedidoPrimeiroExame: solicitacaoExame.pedidoPrimeiroExame
    };

    this.solicitacaoExameService.solicitarExame(solicitacao).subscribe();
  }

}
