import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { VinculoModel } from '../../models/vinculo-model';
import { VinculoService } from '../../services/vinculo.service';

@Component({
  selector: 'app-vinculos',
  templateUrl: './vinculos.component.html',
  styleUrls: ['./vinculos.component.css']
})
export class VinculosComponent implements OnInit {

  formVinculo = this.fb.group({
    id: [''],
    codigo: ['', Validators.required],
    descricao: ['', Validators.required],
    situacao: ['', Validators.required],
    infDependente: [''],
    exTermino: [''],
    geraMatricula: [''],
    matricula:[''],
    exCentroAtividade: [''],
    exOcupacao: [''],
    transfereStarh: [''],
    permiteDuplos: [''],
    exCpfRg: [''],
    gestapDesempenho: [''],
    emiteContrato: [''],
    numerosDeDiasAdmissao: [''],
    tituloMasculino: [''],
    tituloFeminino: [''],
  });

  constructor(
    private fb: FormBuilder,
    private vinculoService: VinculoService,
    ) { }

  situacaoDoServidor = OPCOES_DE_SITUACOES;

  obrigadorio = "";

  ngOnInit(): void {
  }

  valid(): boolean {
    return this.formVinculo.valid;
  }

  submit() {
    const vinc = this.formVinculo.value;
    const vinculo: VinculoModel = {
      id: vinc.id,
      codigo: vinc.codigo,
      descricao: vinc.descricao,
      situacao: vinc.situacao,
      infDependente: vinc.infDependente,
      exTermino: vinc.exTermino,
      geraMatricula: vinc.geraMatricula,
      matricula: vinc.matricula,
      exCentroAtividade: vinc.exCentroAtividade,
      exOcupacao: vinc.exOcupacao,
      transfereStarh: vinc.transfereStarh,
      permiteDuplos: vinc.permiteDuplos,
      exCpfRg: vinc.exCpfRg,
      gestapDesempenho: vinc.gestapDesempenho,
      emiteContrato: vinc.emiteContrato,
      numerosDeDiasAdmissao: vinc.numerosDeDiasAdmissao,
      tituloMasculino: vinc.tituloMasculino,
      tituloFeminino: vinc.tituloFeminino,
    };
    
    if (this.formVinculo.value.id != 0) {
      this.vinculoService.alterarVinculo(vinculo).subscribe((e) => {
        this.formVinculo.reset();
      });
    } else {
      this.vinculoService.cadastrarVinculo(vinculo).subscribe((e) => {
        this.formVinculo.reset();
      });
    } 
  }

  limpar() {
    this.formVinculo.controls.matricula.setValue(null)
  }

}
