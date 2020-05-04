import { Especialidade } from '@internacao/models/especialidade';
import { Component, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Equipe } from '@internacao/models/equipe';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { EquipeService } from './../../services/equipe.service';
import { EventEmitter } from '@angular/core';

@Component({
    selector: 'app-equipe',
    templateUrl: './equipe.component.html',
})
export class EquipeComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Equipe';
    @Input() public name = 'equipe';
    @Input() public especialidade: FormControl;
    @Output() public select = new EventEmitter();
    public equipes = new Array<Equipe>();

    constructor(private equipeService: EquipeService) {}

    ngOnInit(): void {
        this.equipeService
            .getEquipes(true, 'nome')
            .subscribe((equipes: Array<Equipe>) => (this.equipes = equipes));
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        if (!this.especialidade) {
            this.equipeService
                .getEquipesPorNome(evento.query, true, 'nome')
                .subscribe((equipes: Array<Equipe>) => (this.equipes = equipes));
            return;
        }

        this.equipeService
            .getEquipesPorEspecialidadeEPorNome(
                this.especialidade.value.id,
                evento.query,
                true,
                'nome',
            )
            .subscribe((equipes: Array<Equipe>) => (this.equipes = equipes));
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
