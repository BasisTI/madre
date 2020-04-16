import { PrescricaoMedicaDietaService } from './prescricao-medica-dieta.service';
import { BreadcrumbService } from '../../breadcrumb/breadcrumb.service';

import { Component, OnInit, OnDestroy} from '@angular/core';
import { FormGroup } from '@angular/forms';
import { LogoutDirective } from '@nuvem/angular-base';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-prescricao-medica-dieta',
    templateUrl: './prescricao-medica-dieta.component.html',
    styleUrls: ['./prescricao-medica-dieta.component.css']
})

export class PrescricaoMedicaDietaComponent implements OnInit, OnDestroy {

    paciente = {nome: ''};

    dieta: any;

    dietas: any[] = [];

    searchUrl = 'prescricao/api/prescricao-dieta';

    optItem = [
        { label: 'BRANDA', value: 'BRANDA' },
        { label: 'DIABETES', value: 'DIABETES' },
        { label: 'SEM LACTOSE', value: 'LACTOSE' },
        { label: 'DOENÇA CELIACA', value: 'DOENÇA CELÍACA' },
        { label: 'DIETA ZERO', value: 'DIETA ZERO' }
    ];


    descricao = new Map(this.optItem.map((s): [string, string] => [s.value, s.label]));

    constructor(
        private breadcrumbService: BreadcrumbService,
        private prescricaoMedicaDietaService: PrescricaoMedicaDietaService,
        private route: ActivatedRoute
    ) { }


    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Dieta', }
        ]);

        const codigoPaciente = this.route.snapshot.params['id'];

        if (codigoPaciente){
            this.carregarPaciente(codigoPaciente);
        }

        this.prescricaoMedicaDietaService.listar().subscribe(response => {
            response.map((item) => {
                // console.log(item);

                if (item.itemDieta) {
                    item.itemDieta.map((dieta) => {
                        this.dietas.push(dieta);

                    });
                }

            });
        });

        this.prescreverDieta();
    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaDietaService.buscarId(id)
        .subscribe(paciente => {
            this.paciente = paciente;
        });
    }


    prescreverDieta() {
        this.dieta = {
            descricao: "",
            quantidade: "",
            unidade: "",
            frequencia: "",
            tipoAprazamento: "",
            numeroVezes: "",
            observacao: "",
            bombaInfusao: ""
        };


    }

    adicionar(frm: FormGroup) {

        this.prescricaoMedicaDietaService.adicionar(this.dieta).subscribe(response => {
            frm.reset();

            this.prescreverDieta();
        });
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
