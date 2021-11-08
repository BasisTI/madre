import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AuthenticationService } from "@nuvem/angular-base";
import { PageNotificationService } from "@nuvem/primeng-components";
import { environment } from "src/environments/environment";
import { Usuario } from "../usuario/usuario";


@Injectable()
export class AuthService {

    public static PREFIX_ROLE = "ROLE_MADRE_";

    constructor(private authService: AuthenticationService<Usuario>,
        private pageNotificationService: PageNotificationService,
        private http: HttpClient) {

    }

    isAuthenticated(): boolean{
        return this.authService.isAuthenticated();
    }

    possuiRole(role: string): boolean {
        if(!this.authService.getUser()){
            return false;
        }
        for (let permissao of this.authService.getUser().roles) {
            if (role === permissao) {
                return true;
            }
        }
        return false;
    }

    possuiAlgumaRoles(role: string[]): boolean {
        if(!this.authService.getUser()){
            return false;
        }
        for (let permissao of this.authService.getUser().roles) {
            for (let index = 0; index < role.length; index++) {
                const element = role[index];
                if (element === permissao) {
                    return true;
                }
            }
        }
        return false;
    }

    getRoles(){
        return this.authService.getUserDetails();
    }

}
