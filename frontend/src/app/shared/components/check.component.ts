import { Component, Input } from "@angular/core";
import { provideValueAccessor } from "@nuvem/angular-base";
import { FieldComponent } from "@nuvem/primeng-components";

@Component({
    selector: "[nCheck]",
    templateUrl: "./check.component.html",
    providers: [ provideValueAccessor(CheckComponent) ],
})
export class CheckComponent extends FieldComponent<string> {

}
