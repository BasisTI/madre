import { OPCOES_DE_TIPO_DE_ENDERECO } from './../../models/dropdowns/opcoes-de-tipo-de-endereco';
import { DatatableClickEvent, BreadcrumbService } from '@nuvem/primeng-components';
import { Component, Input, Renderer2 } from "@angular/core";
import { FormBuilder, Validators, FormArray } from "@angular/forms";
import { CepService } from "./cep.service";
import { UfService } from "../municipio/uf.service";
import { MunicipioService } from "../municipio/municipio.service";
import { UF } from "../../models/dropdowns/types/uf";
import { Municipio } from "../../models/dropdowns/types/municipio";
import { CEP } from "./cep.model";
import { Pagination } from './../../../shared/pagination';


@Component({
    selector: 'paciente-form-endereco',
    templateUrl: './paciente-form-endereco.component.html',
})
export class PacienteEnderecoFormComponent {

    @Input()
    public enderecos: any =  FormArray;

    public enderecoValido: boolean = false;
    public correspondencia: boolean = true;

    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_ENDERECO;

    ufs: UF[] = [];
    municipios: Municipio[] = [];
    ceps: CEP[] = [];
    pagination: Pagination<any, any>;
    isListen: boolean = false;
    pagesGet: Array<number> = [];

    endereco = this.fb.group({
        id: [null],
        cep: [null],
        logradouro: [null],
        numero: [null],
        complemento: [null],
        bairro: [null],
        correspondencia: [null],
        tipoDoEndereco: [null],
        municipioId: [null],
        uf: [null],
        indice: [null],
    });

    constructor(
        private renderer: Renderer2,
        private fb: FormBuilder,
        public municipioService: MunicipioService,
        public ufService: UfService,
        public cepService: CepService,
        private breadcrumbService: BreadcrumbService
    ) { }

    ngOnInit() {
        this.ufService.getListaDeUF().subscribe((res) => (this.ufs = res));
    }

    consultaCEP(event) {
        const cep = this.endereco.value.cep;
        if (cep != null && cep !== '') {
            this.cepService.buscarCEP(cep)
                .subscribe(dados => this.populaDadosForm(dados));
        }
    }

    populaDadosForm(dados) {
        this.endereco.patchValue({
            logradouro: dados.logradouro,
            bairro: dados.bairro,
        });
        const municipioId = dados.municipioId;
        if (municipioId != null && municipioId !== '') {
            this.municipioService.getMunicipioById(municipioId).subscribe(dados => this.populaMunicipio(dados));
        }
        console.log(dados);
    }

    populaMunicipio(dados) {
        this.endereco.patchValue({
            municipioId: dados
        });
        const ufId = dados.ufId;
        if (ufId != null && ufId != '') {
            this.ufService.getUfById(ufId).subscribe(dados => this.populaUF(dados));
        }
    }

    populaUF(dados) {
        this.endereco.patchValue({
            uf: dados
        });
        console.log(dados);
    }

    aoSelecionarUF() {
        this.resetPagesGet();
        this.endereco.controls.municipioId.setValue(null);
        this.municipioService
            .pesquisaMunicipios(this.endereco.value.uf.id, '', 0)
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
                    .pesquisaMunicipios(this.endereco.value.uf.id, this.endereco.value.municipioId ? this.endereco.value.municipioId.nome ? this.endereco.value.municipioId.nome : this.endereco.value.municipioId : '', page)
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
                            const input = <HTMLElement>document.body.querySelector('p-autocomplete[formcontrolname="municipioId"] .p-component.ng-star-inserted.p-autocomplete-dd-input');
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
            const panel = <HTMLElement>document.body.querySelector('p-autocomplete[formcontrolname="municipioId"] .p-autocomplete-panel.p-component');
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
        let nome: string = this.endereco.value.municipioId ? this.endereco.value.municipioId.nome ? this.endereco.value.municipioId.nome : this.endereco.value.municipioId : '';
        if(this.pagination !== null && this.municipios !== null && this.pagination.params.ufId == this.endereco.value.uf.id && this.pagination.params.nome == nome){
            page = this.pagination.nextPage;
        }else{
            this.resetPagesGet();
        }
        return page;
    }

    resetPagesGet(){
        this.pagesGet = [];
        this.isListen = false;
    }

    adicionarEnderecoALista() {
        if (this.endereco.valid) {
            this.endereco.patchValue({indice: this.enderecos.length});
            this.enderecos.push(this.endereco);
            this.endereco = this.fb.group({
                id: [null],
                cep: [null],
                logradouro: [null],
                numero: [null],
                complemento: [null],
                bairro: [null],
                correspondencia: [null],
                tipoDoEndereco: [null],
                municipioId: [null],
                uf: [null],
                indice: [null],
            });
        }
        this.endereco.reset();
    }

    datatableClick(event: DatatableClickEvent) {
        if (event.selection) {
            switch (event.button) {
                case "edit":
                    this.enderecoValido = true;
                    this.endereco.patchValue(this.enderecos.controls[event.selection.indice].value);
                    break;
                case "delete":
                    this.enderecos.removeAt(event.selection.indice);
                    var i = 0;
                    this.enderecos.controls.forEach((atual) => atual.patchValue({indice: i++}));
                    break;
            }
        }
    }

    atualizarEdicao(): void {
        let atual = this.endereco.value;
        this.enderecos.controls[atual.indice].patchValue(atual);
        this.enderecoValido = false;
        this.endereco.reset();
      }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
