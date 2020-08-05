import { Component, Input } from "@angular/core";
import { provideValueAccessor } from "@nuvem/angular-base";
import { FieldComponent } from "@nuvem/primeng-components";

@Component({
    selector: "[nInputMask]",
    templateUrl: "./input-mask.component.html",
    providers: [ provideValueAccessor(InputMaskComponent) ],
})
export class InputMaskComponent extends FieldComponent<string> {

    @Input()
    mask: string;

    @Input()
    placeholder: string;

    @Input()
    tooltip: string;

}
