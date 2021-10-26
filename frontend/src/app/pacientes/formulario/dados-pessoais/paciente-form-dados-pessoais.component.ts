import { UfService } from './uf.service';
import { Municipio } from "../../models/dropdowns/types/municipio";
import { Pagination } from './../../../shared/pagination';
import { UF } from './../../models/dropdowns/types/uf';
import { AfterViewInit, Component, Input, OnInit, Renderer2 } from "@angular/core";
import { FormGroup } from "@angular/forms";

import * as moment from 'moment';

import { OPCOES_DE_SEXO } from './../../models/dropdowns/opcoes-de-sexo';
import { OPCOES_DE_GRAU_DE_INSTRUCAO } from './../../models/dropdowns/opcoes-de-grau-de-instrucao';

import { RacaService } from "./raca.service";
import { EtniaService } from "./etnia.service";
import { EstadoCivilService } from "./estado-civil.service";
import { NacionalidadeService } from "./nacionalidade.service";
import { OcupacaoService } from "./ocupacao.service";
import { ReligiaoService } from "./religiao.service";
import { DateComponent } from '@fullcalendar/core';
import { MunicipioService } from "../municipio/municipio.service";

import { FiltroMunicipioModel } from './../../models/municipio.filtro.model';


@Component({
    selector: 'paciente-form-dados-pessoais',
    templateUrl: './paciente-form-dados-pessoais.component.html',
    styleUrls: ['paciente-form-dados-pessoais.component.css'],
})
export class PacienteDadosPessoaisFormComponent implements OnInit {
    @Input()
    public formGroup: FormGroup;

    opcoesDeSexo = OPCOES_DE_SEXO;
    opcoesDeGrauDeInstrucao = OPCOES_DE_GRAU_DE_INSTRUCAO;
    idade = '';
    uf = '';

    ufs: UF[] = [];
    municipios: Municipio[] = [];
    filtroMunicipioModel: FiltroMunicipioModel;
    pagination: Pagination<any, any>;
    isListen: boolean = false;
    pagesGet: Array<number> = [];

    constructor(
        private renderer: Renderer2,
        public racaService: RacaService,
        public etniaService: EtniaService,
        public estadoCivilService: EstadoCivilService,
        public nacionalidadeService: NacionalidadeService,
        public ufService: UfService,
        public ocupacaoService: OcupacaoService,
        public religiaoService: ReligiaoService,
        public municipioService: MunicipioService,
    ) {
    }

    ngOnInit() {
        this.ufService.getListaDeUF().subscribe((res) => (this.ufs = res));


            if(this.formGroup.get('etniaId').value != null){
                this.etniaService.find(this.formGroup.get('etniaId').value).subscribe(res => {
                    this.formGroup.patchValue({etniaId: res});
                });
            }

            if(this.formGroup.get('racaId').value != null){
                this.racaService.find(this.formGroup.get('racaId').value).subscribe(res => {
                    this.formGroup.patchValue({racaId: res});
                });
            }

            if(this.formGroup.get('estadoCivilId').value != null){
                this.estadoCivilService.find(this.formGroup.get('estadoCivilId').value).subscribe(res => {
                    this.formGroup.patchValue({estadoCivilId: res});
                });
            }

        if (this.formGroup.get('racaId').value != null) {
            this.racaService.find(this.formGroup.get('racaId').value).subscribe((res) => {
                this.formGroup.patchValue({ racaId: res });
            });
        }

        if (this.formGroup.get('estadoCivilId').value != null) {
            this.estadoCivilService
                .find(this.formGroup.get('estadoCivilId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ estadoCivilId: res });
                });
        }

        if (this.formGroup.get('horaDeNascimento').value != null) {
            var date = new Date(this.formGroup.get('horaDeNascimento').value);
            this.formGroup.patchValue({
                horaDeNascimento: new Date(this.formGroup.get('horaDeNascimento').value),
            });
        }

        if(this.formGroup.get('naturalidadeId').value != null){
            this.municipioService.find(this.formGroup.get('naturalidadeId').value).subscribe(res => {
                this.formGroup.patchValue({naturalidadeId: res});
            });
        }

        if (this.formGroup.get('ufId').value != null) {
            this.ufService.find(this.formGroup.get('ufId').value).subscribe((res) => {
                this.formGroup.patchValue({ ufId: res });
            });
        }

        if (this.formGroup.get('naturalidadeId').value != null) {
            this.municipioService
                .find(this.formGroup.get('naturalidadeId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ naturalidadeId: res });
                });
        }

        if (this.formGroup.get('ocupacaoId').value != null) {
            this.ocupacaoService.find(this.formGroup.get('ocupacaoId').value).subscribe((res) => {
                this.formGroup.patchValue({ ocupacaoId: res });
            });
        }

        if (this.formGroup.get('religiaoId').value != null) {
            this.religiaoService.find(this.formGroup.get('religiaoId').value).subscribe((res) => {
                this.formGroup.patchValue({ religiaoId: res });
            });
        }

        this.aoSelecionarDataDeNascimento();
    }

    aoSelecionarDataDeNascimento() {
        const { dataDeNascimento } = this.formGroup.value;

        if (dataDeNascimento) {
            const idade = moment().diff(moment(dataDeNascimento), 'years');

            if (idade === 0) {
                this.idade = 'Menos de 1 ano';
                return;
            }

            this.idade = String(idade);

            return;
        }

        this.idade = '';
    }


    aoSelecionarUF() {
       this.formGroup.controls.naturalidadeId.setValue(null);
        this.municipioService
            .pesquisaMunicipios(this.formGroup.value.ufId.id, '', 0)
            .subscribe((res) => {
                this.pagination = res;
                this.municipios = res.content;
                if(! this.isListen){
                    this.listenScrollPanel();
                }
            });
    }

    searchUnidade(event) {

        const page = this.getPage();

        if(page !== null && !this.pagesGet.includes(page)){
            //adiciona a pagina no array pagesGet para evitar requests multiplos para a mesma pagina;
            this.pagesGet.push(page);

            //busca os dados do municipio pela pagina: page
            this.municipioService
                .pesquisaMunicipios(this.formGroup.value.ufId.id, this.formGroup.value.naturalidadeId ? this.formGroup.value.naturalidadeId.nome ? this.formGroup.value.naturalidadeId.nome : this.formGroup.value.naturalidadeId : '', page)
                .subscribe((res) => {
                    this.pagination = res;
                    if(this.pagination.currentPage > 0){
                        //caso seja retornado a currentPage > 0, os dados retornados serão adicionado à naturalidades
                        this.pagination.content.forEach((v) => {
                            if(this.municipios.filter(e => e.id === v.id).length === 0){
                                this.municipios.push(v)
                            }
                        });
                        //Gera um evento click no input do autocomplete para atualizar os dados na lista do dropdown
                        const input = <HTMLElement>document.body.querySelector(".p-component.ng-star-inserted.p-autocomplete-dd-input");
                        input.click();
                    }else{
                        //caso seja retornado a currentPage = 0, naturalidades irá conter apenas os dados dessa página
                        this.municipios = res.content;
                    }
                    if(! this.isListen){
                        this.listenScrollPanel();
                    }
                });
        }
    }

    listenScrollPanel() {
        setTimeout(() => {
            const panel = <HTMLElement>document.body.querySelector(".p-autocomplete-panel.p-component");
            if (panel) {
              this.renderer.listen(panel, 'scroll', event => {
                if ((event.target.scrollHeight - event.target.clientHeight) === event.target.scrollTop) {
                    //chama o método searchUnidade() para buscar a próxima página quando o scroll do autocomplete chegar ao fim
                    this.searchUnidade(null);
                }
              });
              this.isListen = true;
            }
        }, 1000);
    }

    getPage(){
        var page = 0;
        let nome: string = this.formGroup.value.naturalidadeId ? this.formGroup.value.naturalidadeId.nome ? this.formGroup.value.naturalidadeId.nome : this.formGroup.value.naturalidadeId : '';
        if(this.pagination !== null && this.municipios !== null && this.pagination.params.ufId == this.formGroup.value.ufId.id && this.pagination.params.nome == nome){
            page = this.pagination.nextPage;
        }else{
            this.resetPagesGet();
        }
        return page;
    }

    onHideAutocomplete(){
        this.isListen = false;
        this.pagesGet = [];
    }

    resetPagesGet(){
        this.pagesGet = [];
    }
}
