import { Component, Input, Output, EventEmitter } from "@angular/core";
import { provideValueAccessor } from "@nuvem/angular-base";
import { FieldComponent } from '@nuvem/primeng-components';

@Component({
    selector: "[nDropdown]",
    templateUrl: "./dropdown.component.html",
    providers: [provideValueAccessor(DropdownComponent)],
})
export class DropdownComponent extends FieldComponent<any> {

    @Input()
    options: string;

    @Input()
    filter: boolean = false;

    @Input()
    optionLabel: string;

    @Output()
    onChange: EventEmitter<any> = new EventEmitter<any>();

    onChangeEvent(event) {
        this.onChange.emit(event);
    }

}
