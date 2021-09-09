import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MaterialDeAnalise } from '../../models/subjects/material-de-analise';
import { MaterialDeExames } from '../../models/subjects/material-de-exames';
import { atividadeDropdown } from '../../models/dropdowns/atividade.dropdown';
import { naturezaDropdown } from '../../models/dropdowns/natureza.dropdown';
import { sumarioDropdown } from '../../models/dropdowns/sumario.dropdown';
import { unidadeTempoDropdown } from '../../models/dropdowns/unidadeDeTempo.dropdown';
import { MaterialDeAnaliseService } from '../../services/material-de-analise.service';
import { MaterialDeExamesService } from '../../services/material-de-exames.service';
import { AmostraDeMaterial } from '../../models/subjects/amostraDeMaterial'
import { Anticoagulante } from '../../models/subjects/anticoagulante';
import { Recipiente } from '../../models/subjects/recipiente';
import { origemDropdown } from '../../models/dropdowns/origem.dropdown';
import { ResponsavelDropdown } from '../../models/dropdowns/responsavel.dropdown';
import { UnidadeMedidaDropdown } from '../../models/dropdowns/unidadeMedida.dropdown';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
@Component({
  selector: 'app-material-exames',
  templateUrl: './material-exames.component.html',
  styleUrls: ['./material-exames.component.css']
})
export class MaterialExamesComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private materialDeAnaliseService: MaterialDeAnaliseService,
    private materialDeExamesService: MaterialDeExamesService,
    private unidadeDuncionalService: UnidadeFuncionalService,
    ) { }

  materialForm = this.fb.group({
    ativo: [null, Validators.required],
    npo: [null, Validators.required],
    jejum: [null, Validators.required],
    exigePreparo: [null, Validators.required],
    exigeDieta: [null, Validators.required],
    informaNumeroDeColetas: [null, Validators.required],
    geraItemDeSolicitacao: [null, Validators.required],
    exigeIntervaloDeColeta: [null, Validators.required],
    exigeRegiaoAnatomica: [null, Validators.required],
    ingestaoDeMedicamento: [null, Validators.required],
    dependenteDeExame: [null, Validators.required],
    analisadoPelaCII: [null, Validators.required],
    interesseDaCOMEDI: [null, Validators.required],
    exigeImpressao: [null, Validators.required],
    apareceResultado: [null, Validators.required],
    contaCelulas: [null, Validators.required],
    limiteDeSolicitacao: [null, Validators.required],
    formaDeRespiracao: [null, Validators.required],
    automatico: [null, Validators.required],
    exigeDadosComplementares: [null, Validators.required],
    natureza: [null, Validators.required],
    sumario: [null, Validators.required],
    tempoJejum: [null, Validators.required],
    intervaloMinimo: [null, Validators.required],
    unidadeDeTempo: [null, Validators.required],
    validade: [null, Validators.required],
    agendamentoMinimo: [null, Validators.required],
    tempoLimiteDaSolicitacao: [null, Validators.required],
    unidadeDeTempoDaSolicitacao: [null, Validators.required],
    numeroDeAmostras: [null, Validators.required],
    numeroDeAmostrasPadrao: [null, Validators.required],
    diasLimiteDefault: [null, Validators.required],
    tempoLimiteDefault: [null, Validators.required],
    numeroDeAmostrarPorIntervalo: [null, Validators.required],
    tempoLimiteDeAmostraPorIntervalo: [null, Validators.required],
    unidadeLimiteDeTempoDoPeriodo: [null, Validators.required],
    permiteSolicitacaoPosAlta: [null, Validators.required],
    tempoPermitidoParaSolicitarPosAlta: [null, Validators.required],
    tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras: [null, Validators.required],
    cartaDeColeta: [null, Validators.required],
    laboratorioTercerizado: [null, Validators.required],
    naoCancelaExameAposAlta: [null, Validators.required],
    materialId: [null, Validators.required],
  })

  atividade = atividadeDropdown;
  natureza = naturezaDropdown;
  sumario = sumarioDropdown;
  unidadeDeTempoOptions = unidadeTempoDropdown;
  materiaisDeAnalise: MaterialDeAnalise[] = [];

  ListaDeAmostras: AmostraDeMaterial[] = [];
  anticoagulantes: Anticoagulante[] = [];
  recipientes: Recipiente[] = [];
  unidadesFuncionais: UnidadeFuncional[] = [];
  responsaveis = ResponsavelDropdown;
  origens = origemDropdown;
  unidadeMedida = UnidadeMedidaDropdown;

  amostraForm = this.fb.group({
    id: [null],
    nome: [null],
    origem: [null, Validators.required],
    numeroDeAmostras: [null, Validators.required], 
    volumeDaAmostra: [null, Validators.required],
    unidadeDeMedida: [null, Validators.required],
    responsavel: [null, Validators.required],
    congelado: [null, Validators.required],
    unidadeFuncionalId: [null, Validators.required],
    amostraRecipienteId: [null, Validators.required],
    amostraRecipienteNome: [null],
    amostraAnticoagulanteId: [null, Validators.required],
    amostraAnticoagulanteNome: [null],
    amostraMaterialId: [null, Validators.required],
    amostraMaterialNome: [null],
  })

  booleanDropdown = [
    {
      label: "sim",
      value: "SIM"
    },
    {
      label: "não",
      value: "NAO"
    }
  ]

  validarMaterial(): boolean {
    return this.materialForm.valid;
  }

  validarAmostra(): boolean {
    return this.amostraForm.valid;
  }

  adicionarAmostra() {

    let amostra = this.amostraForm.value;

    let nomeMaterial = this.materiaisDeAnalise.find((elem)=> elem.id == amostra.amostraMaterialId);
    let nomeAnticoagulante = this.anticoagulantes.find((elem)=> elem.id == amostra.amostraAnticoagulanteId);
    let nomeRecipiente = this.recipientes.find((elem)=> elem.id == amostra.amostraRecipienteId);

    let amostraCompleta: AmostraDeMaterial = {
      nome: nomeMaterial.nome,
      origem: amostra.origem,
      numeroDeAmostras: amostra.numeroDeAmostras, 
      volumeDaAmostra: amostra.volumeDaAmostra,
      unidadeDeMedida: amostra.unidadeDeMedida,
      responsavel: amostra.responsavel,
      congelado: amostra.congelado,
      unidadeFuncionalId: amostra.unidadeFuncionalId,
      amostraRecipienteId: amostra.amostraRecipienteId,
      amostraRecipienteNome: nomeRecipiente.nome,
      amostraAnticoagulanteId: amostra.amostraAnticoagulanteId,
      amostraAnticoagulanteNome: nomeAnticoagulante.nome,
      amostraMaterialId: amostra.amostraMaterialId,
      amostraMaterialNome: nomeMaterial.nome,
    }

    this.ListaDeAmostras.push(amostraCompleta);
    this.amostraForm.reset();
  }

  limparAmostras() {
    this.ListaDeAmostras = [];
  }

  cadastrarMaterial() {
    let materialPreenchido = this.materialForm.value;

    let materialObjeto: MaterialDeAnalise = this.materiaisDeAnalise.find((elem) => elem.id = materialPreenchido.materialId)

    let materialDeExames: MaterialDeExames = {
      nome: materialObjeto.nome,
      ativo: materialPreenchido.ativo,
      npo: materialPreenchido.npo,
      jejum: materialPreenchido.jejum,
      exigePreparo: materialPreenchido.exigePreparo,
      exigeDieta: materialPreenchido.exigeDieta,
      informaNumeroDeColetas: materialPreenchido.informaNumeroDeColetas,
      geraItemDeSolicitacao: materialPreenchido.geraItemDeSolicitacao,
      exigeIntervaloDeColeta: materialPreenchido.exigeIntervaloDeColeta,
      exigeRegiaoAnatomica: materialPreenchido.exigeRegiaoAnatomica,
      ingestaoDeMedicamento: materialPreenchido.ingestaoDeMedicamento,
      dependenteDeExame: materialPreenchido.dependenteDeExame,
      analisadoPelaCII: materialPreenchido.analisadoPelaCII,
      interesseDaCOMEDI: materialPreenchido.interesseDaCOMEDI,
      exigeImpressao: materialPreenchido.exigeImpressao,
      apareceResultado: materialPreenchido.apareceResultado,
      contaCelulas: materialPreenchido.contaCelulas,
      limiteDeSolicitacao: materialPreenchido.limiteDeSolicitacao,
      formaDeRespiracao: materialPreenchido.formaDeRespiracao,
      automatico: materialPreenchido.automatico,
      exigeDadosComplementares: materialPreenchido.exigeDadosComplementares,
      natureza: materialPreenchido.natureza,
      sumario: materialPreenchido.sumario,
      tempoJejum: materialPreenchido.tempoJejum,
      intervaloMinimo: materialPreenchido.intervaloMinimo,
      unidadeDeTempo: materialPreenchido.unidadeDeTempo,
      validade: materialPreenchido.validade,
      agendamentoMinimo: materialPreenchido.agendamentoMinimo,
      tempoLimiteDaSolicitacao: materialPreenchido.tempoLimiteDaSolicitacao,
      unidadeDeTempoDaSolicitacao: materialPreenchido.unidadeDeTempoDaSolicitacao,
      numeroDeAmostras: materialPreenchido.numeroDeAmostras,
      numeroDeAmostrasPadrao: materialPreenchido.numeroDeAmostrasPadrao,
      diasLimiteDefault: materialPreenchido.diasLimiteDefault,
      tempoLimiteDefault: materialPreenchido.tempoLimiteDefault,
      numeroDeAmostrarPorIntervalo: materialPreenchido.numeroDeAmostrarPorIntervalo,
      tempoLimiteDeAmostraPorIntervalo: materialPreenchido.tempoLimiteDeAmostraPorIntervalo,
      unidadeLimiteDeTempoDoPeriodo: materialPreenchido.unidadeLimiteDeTempoDoPeriodo,
      permiteSolicitacaoPosAlta: materialPreenchido.permiteSolicitacaoPosAlta,
      tempoPermitidoParaSolicitarPosAlta: materialPreenchido.tempoPermitidoParaSolicitarPosAlta,
      tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras: materialPreenchido.tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras,
      cartaDeColeta: materialPreenchido.cartaDeColeta,
      laboratoriaTercerizado: materialPreenchido.laboratoriaTercerizado,
      naoCancelaExamaAposAlta: materialPreenchido.naoCancelaExamaAposAlta,
      materialId: materialPreenchido.materialId,
      materialNome: materialObjeto.nome,
      amostras: this.ListaDeAmostras,
    }

    this.materialDeExamesService.cadastrarMaterialDeExame(materialDeExames).subscribe();
    this.materialForm.reset();
    this.ListaDeAmostras = [];

  }

  limparMaterial() {
    this.materialForm.reset();
  }

  ngOnInit(): void {
    this.materialDeAnaliseService.pegarMaterial().subscribe((response) => {
      this.materiaisDeAnalise = response;
    })
    this.materialDeExamesService.pegarAnticoagulantes().subscribe((response) => {
      this.anticoagulantes = response;
    })
    this.materialDeExamesService.pegarRecipientes().subscribe((response) => {
      this.recipientes = response;
    })
    this.unidadeDuncionalService.getUnidades().subscribe((response) => {
      this.unidadesFuncionais = response;
    })
  }

}
