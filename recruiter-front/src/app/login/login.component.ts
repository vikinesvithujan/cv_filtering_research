import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginStatus: boolean;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  get i() { return this.loginForm.controls; }

  onLogin(): void {
    if(this.loginForm.valid) {
      if(this.authService.login(this.loginForm.value.username, this.loginForm.value.password)) {
        this.router.navigate(['admin']);
      } else {
        this.loginForm.reset();
        this.loginStatus = true;
      }
    }
  }

}
