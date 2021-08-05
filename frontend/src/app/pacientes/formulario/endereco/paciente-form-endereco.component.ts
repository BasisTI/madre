import { OPCOES_DE_TIPO_DE_ENDERECO } from './../../models/dropdowns/opcoes-de-tipo-de-endereco';
import { DatatableClickEvent, BreadcrumbService, DatatableComponent } from '@nuvem/primeng-components';
import { Component, Input, ViewChild } from "@angular/core";
import { FormBuilder, Validators, FormArray } from "@angular/forms";
import { CepService } from "./cep.service";
import { UfService } from "../municipio/uf.service";
import { MunicipioService } from "../municipio/municipio.service";
import { UF } from "../../models/dropdowns/types/uf";
import { MunicipioUF } from "../../models/dropdowns/types/municipio-uf";
import { CEP } from "./cep.model";
import { element } from 'protractor';


@Component({
    selector: 'paciente-form-endereco',
    templateUrl: './paciente-form-endereco.component.html'
})
export class PacienteEnderecoFormComponent {

    @ViewChild('datatable') table: DatatableComponent;

    @Input() public enderecos: any = [];

    public validaEndereco: boolean = false;
    
    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_ENDERECO;

    ufs: UF[] = [];
    municipios: MunicipioUF[] = [];
    ceps: CEP[] = [];

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
        this.endereco.controls.municipioId.setValue(null);
        this.municipioService
            .getListaDeMunicipiosUF(this.endereco.value.uf.id, '')
            .subscribe((res) => (this.municipios = res));
    }

    searchUnidade(event) {
        this.municipioService
            .getListaDeMunicipiosUF(this.endereco.value.uf.id, event.query)
            .subscribe((res) => {
                this.municipios = res;
            });
    }

    adicionarEnderecoALista() {
        if (this.endereco.valid) {
            let endr = {
                ...this.endereco.value,
                id: this.enderecos.value?.length
            }
            
            this.enderecos.value.push(endr);
            this.endereco = this.fb.group({
                id: this.enderecos.lenght,
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

            this.endereco.reset();
            this.table.refresh();
        }
    }

    datatableClick(event: DatatableClickEvent) {
        if (event.selection) {
            switch (event.button) {
                case "edit":
                    this.validaEndereco = true;
                    this.endereco.patchValue(event.selection);
                    break;
                case "delete":
                    let enderecos = this.enderecos.value.filter((e) => e.id != event.selection.id)
                    this.enderecos.setValue(enderecos);
                    break;
            }
            this.table.refresh();
        }
    }

    atualizarEdicao(): void {
        if (this.endereco.valid) {
            let endr = {
                ...this.endereco.value
            };
        
            let enderecos = this.enderecos.value.map((element) => {
                if(element.id = endr.id){
                    return endr;
                }
                return element;
            });
            
            this.enderecos.setValue(enderecos);
            this.validaEndereco = false;
            this.endereco.reset();
        }
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
