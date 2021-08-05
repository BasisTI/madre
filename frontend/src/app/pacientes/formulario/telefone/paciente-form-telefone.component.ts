import { DDDService } from './ddd.service';
import { OPCOES_DE_TIPO_DE_TELEFONE } from './../../models/dropdowns/opcoes-de-tipo-de-telefone';
import { DatatableClickEvent, BreadcrumbService, DatatableComponent } from '@nuvem/primeng-components';
import { Component, Input, ViewChild , ChangeDetectorRef, OnInit} from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { DDD } from '../../models/dropdowns/types/DDD';

@Component({
    selector: 'paciente-form-telefone',
    templateUrl: './paciente-form-telefone.component.html',
})
export class PacienteTelefoneFormComponent implements OnInit {

    @ViewChild('datatable') table: DatatableComponent;
    
    @Input() telefones: any = [];

    public validaTelefone: boolean;

    public listaDDD: DDD[] = [];

    public opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    public tipoDeTelefoneSelecionado: string;

    public tipoMascara: string = "9999-9999"; 

    public telefone = this.fb.group({
        id: this.telefones.length,
        ddd:[null, Validators.required],
        numero: [null, Validators.required],
        tipo: [null, Validators.required],
        observacao: [null],
        indice: [null]
    });

    constructor(
        private fb: FormBuilder, 
        private breadcrumbService: BreadcrumbService, 
        public dddService: DDDService, 
        private changeDetectorRef: ChangeDetectorRef
    ) {}

    ngOnInit() {
        this.dddService.all().subscribe((result) => {
            result.forEach(element => {
                this.listaDDD.push(element);
            });
        });
    }

    adicionarTelefoneALista() {
        if (this.telefone.valid) {
            let fone = {
                ...this.telefone.value, 
                id: this.telefones.value?.length,
                ddd: this.telefone.get('ddd').value.valor
            };
            
            this.telefones.value.push(fone);
            this.telefone = this.fb.group({
                id: this.telefones.length,
                ddd: [null],
                numero: [null],
                tipo: [null],
                observacao: [null],
                indice: [null]
            });
            this.telefone.reset();
            this.table.refresh();
        }
    }

    datatableClick(event: DatatableClickEvent) {
        if (event.selection) {
            switch (event.button) {
                case "edit":
                    this.validaTelefone = true;
                    this.telefone.patchValue(event.selection);
                    
                    break;
                case "delete":
                    let telefones = this.telefones.value.filter((e) => e.id != event.selection.id)
                    this.telefones.setValue(telefones);
                    break;
            }
            this.table.refresh();
        }
    }

    atualizarEdicao(): void {
        if (this.telefone.valid) {
            let fone = {
                ...this.telefone.value, 
                ddd: this.telefone.get('ddd').value.valor
            };
    
            let telefones = this.telefones.value.map((element) => {
                if (element.id == fone.id) {
                    return fone;
                }
                return element;
            });
    
            this.telefones.setValue(telefones);
            this.validaTelefone = false;
            this.telefone.reset();
        }
    }

    tipoTelefone(event){
        this.tipoDeTelefoneSelecionado = event.value;
        if(this.tipoDeTelefoneSelecionado === 'CELULAR'){
            this.tipoMascara = "9 9999-9999";
        }
        else{
            this.tipoMascara = "9999-9999";
        }

        this.changeDetectorRef.detectChanges();
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}