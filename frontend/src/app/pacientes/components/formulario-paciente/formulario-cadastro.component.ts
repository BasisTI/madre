import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { FormBuilder, Validators, FormGroup, AbstractControl, FormArray } from '@angular/forms';

@Component({
    selector: 'app-formulario-cadastro',
    templateUrl: './formulario-cadastro.component.html',
    styleUrls: ['./formulario-cadastro.component.scss'],
})
export class FormularioCadastroComponent implements OnInit, OnDestroy {
    formularioDeCadastro = this.fb.group({
        dadosPessoais: this.fb.group({
            nome: ['', Validators.required],
            nomeSocial: [''],
            sexo: ['', Validators.required],
            raca: ['', Validators.required],
            etnia: ['', Validators.required],
            estadoCivil: ['', Validators.required],
            prontuarioDaMae: [''],
            nomeDaMae: ['', Validators.required],
            nomeDoPai: ['', Validators.required],
            dataDeNascimento: ['', Validators.required],
            horaDoNascimento: [''],
            nacionalidade: ['', Validators.required],
            naturalidade: ['', Validators.required],
            grauDeInstrucao: ['', Validators.required],
            ocupacao: [''],
            religiao: [''],
            email: ['', Validators.maxLength(254)],
        }),
        telefones: this.fb.array([
            // tipo: ['', Validators.required],
            // telefone: ['', Validators.required],
            // observacao: [''],
            // DDD: ['', Validators.required],
        ]),
        enderecos: this.fb.group({
            municipio: ['', Validators.required],
            cep: ['', Validators.required],
            uf: [''],
            logradouro: ['', Validators.required],
            numero: ['', Validators.required],
            complemento: [''],
            bairro: ['', Validators.required],
            tipo: ['', Validators.required],
            correspondencia: ['', Validators.required],
        }),
        responsavel: this.fb.group(
            {
                nomeDoResponsavel: ['', [this.customRequired]],
                grauDeParentesco: ['', [this.customRequired]],
                ddd: ['', [this.customRequired]],
                telefone: ['', [this.customRequired]],
                observacao: ['', [this.customRequired]],
            },
            { updateOn: 'blur', validators: this.validateGroup },
        ),
        certidao: this.fb.group({
            registroDeNascimento: [''],
            tipoCertidao: [''],
            nomeDoCartorio: [''],
            livro: [''],
            folhas: [''],
            termo: [''],
            dataDeEmissao: [''],
            numeroDaDN: [''],
        }),
        documentos: this.fb.group(
            {
                numeroIdentidade: ['', [this.customRequired1]],
                orgaoEmissor: ['', [this.customRequired1]],
                uf: ['', [this.customRequired1]],
                dataDeEmissao: ['', [this.customRequired1]],
                cpf: [''],
                pisPasep: [''],
                cnh: [''],
                validadeCNH: ['', this.customRequiredCNH1],
            },
            { updateOn: 'blur', validators: [this.validateGroup1, this.validateGroupCNH1] },
        ),
        cartaoSUS: this.fb.group({
            numero: ['', [Validators.required, this.validarNumero]],
            justificativa: [''],
            motivoCadastro: [''],
            docReferencia: [''],
            cartaoNacional: ['', [this.validarNumero]],
            dataDeEntrada: [''],
            dataDeNaturalizacao: [''],
            portaria: [''],
        }),
        observacao: this.fb.group({
            valor: [],
        }),
    });

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Cadastro de Paciente' },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    cadastrar() {
        console.log();
    }

    customRequired(control: AbstractControl): { [key: string]: boolean } | null {
        if (
            control.parent &&
            (control.parent.get('nomeDoResponsavel').value ||
                control.parent.get('observacao').value) &&
            !control.value
        ) {
            return { required: true };
        }

        return null;
    }

    validateGroup(group: FormGroup): { [key: string]: boolean } | null {
        if (group.get('nomeDoResponsavel').value || group.get('observacao').value) {
            group.markAsDirty();
            return { required: true };
        }

        return null;
    }

    customRequired1(control: AbstractControl): { [key: string]: boolean } | null {
        if (control.parent && control.parent.get('numeroIdentidade').value && !control.value) {
            return { required: true };
        } else {
            return null;
        }
    }

    validateGroup1(group: FormGroup): { [key: string]: boolean } | null {
        if (group.get('numeroIdentidade').value) {
            group.markAsDirty();
            return { required: true };
        } else {
            return null;
        }
    }

    validateGroupCNH1(group: FormGroup): { [key: string]: boolean } | null {
        if (group.get('cnh').value) {
            group.markAsDirty();
            return { required: true };
        }

        return null;
    }

    customRequiredCNH1(control: AbstractControl): { [key: string]: boolean } | null {
        if (control.parent && control.parent.get('cnh').value && !control.value) {
            return { required: true };
        }

        return null;
    }

    /**
     * fé em deus
     */
    validarNumero(control: AbstractControl) {
        let cns = control.value;
        cns = cns.replace(/\D/g, '');

        if (cns.length !== 15) {
            return { customCns: true };
        }

        const soma =
            cns
                .split('')
                .reduce(
                    (somaParcial: number, atual: string, posicao: number) =>
                        somaParcial + parseInt(atual, 10) * (15 - posicao),
                    0,
                ) % 11;

        return soma % 11 === 0 ? null : { customCns: true };
    }
}
