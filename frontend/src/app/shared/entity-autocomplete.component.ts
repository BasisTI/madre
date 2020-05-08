import { FormGroup } from '@angular/forms';

export interface EntityAutoComplete {
    parentFormGroup: FormGroup;
    required: boolean;
    label: string;
    name: string;

    aoDigitar(evento: { originalEvent: any; query: string }): void;
    aoDesfocar(): void;
}
