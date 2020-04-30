import { EquipeService } from './../../services/equipe.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { Equipe } from '@internacao/models/equipe';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';

@Component({
    selector: 'app-equipe',
    templateUrl: './equipe.component.html',
})
export class EquipeComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Equipe';
    @Input() public name = 'equipe';
    public equipes = new Array<Equipe>();

    constructor(private equipeService: EquipeService) {}

    ngOnInit(): void {
        this.equipeService
            .getEquipes(true, 'nome')
            .subscribe((equipes: Array<Equipe>) => (this.equipes = equipes));
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.equipeService
            .getEquipesPorNome(evento.query, true, 'nome')
            .subscribe((equipes: Array<Equipe>) => (this.equipes = equipes));
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
