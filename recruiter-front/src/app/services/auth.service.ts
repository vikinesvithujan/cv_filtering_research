import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) { }

  isAuthenticated(): boolean {
    return localStorage.getItem('isAuthenticate') !== null && localStorage.getItem('isAuthenticate') === "true";
  }

  login(username: string, password: string): boolean {
    localStorage.setItem('isAuthenticate', "false");
    if(username === "admin" && password === "12345") {
      localStorage.setItem('isAuthenticate', "true");
      return true;
    }
    return false;
  }

  logout(): void {
    localStorage.setItem('isAuthenticate', "false");
    this.router.navigate(['login']);
  }
}
