import { Component, OnInit } from '@angular/core';
import { Responsavel } from '../../models/subjects/responsavel-model';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { ResponsavelService } from '../../services/responsavel.service';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';

@Component({
  selector: 'app-solicitar-exame',
  templateUrl: './solicitar-exame.component.html',
  styleUrls: ['./solicitar-exame.component.css']
})
export class SolicitarExameComponent implements OnInit {

  constructor(private unidadeFuncionalService: UnidadeFuncionalService,
              private responsavelService: ResponsavelService) { }

  opcoesDeAntimicrobianos: string;
  opcoesDeExameOuComparativo: string;
  unidades: UnidadeFuncional[] = [];
  responsaveis: Responsavel[] = [];


  ngOnInit(): void {
    this.unidadeFuncionalService.GetUnidades().subscribe((response) => {
      this.unidades = response;
    });

    this.responsavelService.getResponsavel().subscribe((response) => {
      this.responsaveis = response;
    });
  }

}
