import { OPCOES_DE_TIPO_DE_ENDERECO } from './../../models/dropdowns/opcoes-de-tipo-de-endereco';
import { DatatableClickEvent, BreadcrumbService } from '@nuvem/primeng-components';
import { Component, Input } from "@angular/core";
import { FormBuilder, Validators, FormArray } from "@angular/forms";
import { CepService } from "./cep.service";
import { UfService } from "../municipio/uf.service";
import { MunicipioService } from "../municipio/municipio.service";
import { UF } from "../../models/dropdowns/types/uf";
import { MunicipioUF } from "../../models/dropdowns/types/municipio-uf";
import { CEP } from "./cep.model";


@Component({
    selector: 'paciente-form-endereco',
    templateUrl: './paciente-form-endereco.component.html',
})
export class PacienteEnderecoFormComponent {

    @Input()
    public enderecos: any =  FormArray;

    public controle: boolean;

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
                    this.controle = true;
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
        this.controle = false;
        this.endereco.reset();
      }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
