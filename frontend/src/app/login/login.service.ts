import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable()
export class LoginService {

    private authUrl = '/api/authenticate';

    private logoutUrl = '/api/logout';

    constructor(private http: HttpClient, private router: Router) { }

    login(login: string, codigo: string): Observable<any>  {
        const credential = { login: login, codigo: codigo };
        localStorage.setItem('Logado', login);
        return this.http.post(this.authUrl, credential);
    }

    logout(): Observable<any>  {
         // this.cookieService.deleteAll();
        localStorage.removeItem('Logado')
        this.router.navigate(['/login']);
        return this.http.get(this.logoutUrl);
    }
}
