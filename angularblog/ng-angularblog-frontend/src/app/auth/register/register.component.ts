import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {RegisterPayload} from '../register-payload';
import {AuthServiceService} from '../auth-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  registerPayload: RegisterPayload;

  constructor(private formBuilder: FormBuilder, private authService: AuthServiceService ) {
    this.registerForm = this.formBuilder.group(
      {
        username : '',
        email : '',
        password: '',
        confirmPassword: ''
      });
    this.registerPayload = {
       username: '',
       email : '',
       password: '',
       confirmPassword: ''
    };
  }

  ngOnInit() {
  }

  OnSubmit() {
    this.registerPayload.username = this.registerForm.get('username').value;
    this.registerPayload.email = this.registerForm.get('email').value;
    this.registerPayload.password = this.registerForm.get('password').value;
    this.registerPayload.confirmPassword = this.registerForm.get('confirmPassword').value;

    this.authService.register(this.registerPayload).subscribe( data => {
        console.log('registration success !!');
      },
      error => {
        console.log('error registering the user !!');
      });
}

}
