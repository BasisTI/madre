import { ItemDiagnostico } from './models/itemDiagnostico';
import { CID } from '@internacao/models/cid';
import { FormBuilder, Validators } from '@angular/forms';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { DiagnosticoService } from './diagnostico.service';

@Component({
    selector: 'app-diagnostico',
    templateUrl: './diagnostico.component.html',
    styleUrls: ['./diagnostico.component.css']
})
export class DiagnosticoComponent implements OnInit, OnDestroy {

    paciente: {}

    public itensDiagnostico: ItemDiagnostico[] = []

    prescricaoDiagnostico = this.fb.group({
        idPaciente: [null, Validators.required],
        nome: [null],
        dataPrescricao: [new Date()],
        tipo: 'DIAGNOSTICO',
        observacao: [null]
    });

    itemPrescricaoDiagnostico = this.fb.group({
        idCid: [null, Validators.required],
        complemento: [null],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private fb: FormBuilder,
        private prescricaoMedicaService: PrescricaoMedicaService,
        private diagnosticoService: DiagnosticoService,
        private router: Router
    ) { }

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Diagnóstico' }
        ]);

        const codigoPaciente = this.route.snapshot.params['id'];

        if (codigoPaciente) {
            this.carregarPaciente(codigoPaciente);
        }


    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id)
            .subscribe(paciente => {

                this.paciente = paciente;
                this.prescricaoDiagnostico.patchValue({ idPaciente: paciente.id, nome: paciente.nome });

            });
    }

    incluirItem() {
        if (this.itemPrescricaoDiagnostico.valid) {

            this.itensDiagnostico.push(this.itemPrescricaoDiagnostico.value);
            this.itemPrescricaoDiagnostico.reset();
        }

    }

    prescrever() {
        const prescricao = this.prescricaoDiagnostico.value;

        const prescricaoDiagnostico = Object.assign({}, prescricao, {
            itens: this.itensDiagnostico
        });

        prescricaoDiagnostico.itens = prescricaoDiagnostico.itens.map(item => {
            if (item.idCid?.id) {
                item.idCid = item.idCid.id;
            }
            return item;

        });


        if (this.itensDiagnostico.length == 0) {
            this.prescricaoDiagnostico.invalid
        }

        this.diagnosticoService.prescreverDiagnostico(prescricaoDiagnostico).subscribe(
            (resposta) => {
                this.router.navigate(['/prescricao-medica/lista/', prescricaoDiagnostico.idPaciente]);
                this.itemPrescricaoDiagnostico.reset();
                return resposta;
            },
            (erro) => {
                return erro;
            },
        );


    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }


}
