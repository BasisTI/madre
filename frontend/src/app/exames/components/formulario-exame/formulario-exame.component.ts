import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SituacaoExame } from "../../models/dropdowns/situacao.dropdown";
import { Amostra } from '../../models/subjects/amostra';
import { ExamModel } from '../../models/subjects/exames-model';
import { Material } from '../../models/subjects/material';
import { MaterialDeAnalise } from '../../models/subjects/material-de-analise';
import { MaterialDeExames } from '../../models/subjects/material-de-exames';
import { ExamesService } from '../../services/exames.service';
import { atividadeDropdown } from '../../models/dropdowns/atividade.dropdown';
import { naturezaDropdown } from '../../models/dropdowns/natureza.dropdown';
import { sumarioDropdown } from '../../models/dropdowns/sumario.dropdown';
import { unidadeTempoDropdown } from '../../models/dropdowns/unidadeDeTempo.dropdown';
import { MaterialDeAnaliseService } from '../../services/material-de-analise.service';
import { MaterialDeExamesService } from '../../services/material-de-exames.service';
@Component({
  selector: 'app-formulario-exame',
  templateUrl: './formulario-exame.component.html',
  styleUrls: ['./formulario-exame.component.css']
})
export class FormularioExameComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private exameService: ExamesService,
    private materialDeAnaliseService: MaterialDeAnaliseService,
    private materialDeExamesService: MaterialDeExamesService,
    ) { }

  materiais: Material[] = [];
  amostras: Amostra[] = [];

  teste: boolean;

  situacao = SituacaoExame;

  materialSelecionado: number;
  amostraSelecionada: number;

  cadastrarExame = this.fb.group({
    nome: [null, Validators.required],
    nomeUsual: [null, Validators.required],
    sigla: [null, Validators.required],
    ativo: [true, Validators.required],
    impressao: [null, Validators.required],
    consisteInterfaceamento: [null, Validators.required],
    anexaDocumentos: [null, Validators.required]
  });

  // Material
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
    analisadoPelaCII: [null, Validators.required], // faltou
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
    tempoJejum: [null, Validators.required], // aki
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
    permiteSolicitacaoPosAlta: [null, Validators.required], // opa
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


  valid(): boolean {
    if (this.cadastrarExame.valid && this.amostraSelecionada && this.materialSelecionado)
      return true;
    else
      return false;
  }

  limpar() {
    this.cadastrarExame.reset();
  }

  cadastrar() {
    let cadastroExame = this.cadastrarExame.value;

    let cadastro: ExamModel = {
      nome: cadastroExame.nome,
      nomeUsual: cadastroExame.nomeUsual,
      sigla: cadastroExame.sigla,
      materialExameId: this.materialSelecionado,
      amostraExameId: this.amostraSelecionada,
      ativo: cadastroExame.ativo,
      impressao: cadastroExame.impressao,
      consisteInterfaceamento: cadastroExame.consisteInterfaceamento,
      anexaDocumentos: cadastroExame.anexaDocumentos
    };

    this.exameService.cadastrarExame(cadastro).subscribe();
    this.cadastrarExame.reset();
  }

  validarMaterial(): boolean {
    return this.materialForm.valid;
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
    }

    this.materialDeExamesService.cadastrarMaterialDeExame(materialDeExames).subscribe();
    this.materialForm.reset();

  }

  limparMaterial() {
    this.materialForm.reset();
  }


  ngOnInit(): void {
    this.exameService.getMateriais().subscribe((response) => {
      this.materiais = response;
    });

    this.exameService.getAmostras().subscribe((response) => {
      this.amostras = response;
    });

    this.materialDeAnaliseService.pegarMaterial().subscribe((response) => {
      this.materiaisDeAnalise = response;
    })
  }

}