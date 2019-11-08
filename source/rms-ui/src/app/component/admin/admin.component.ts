import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import {UserService, AuthenticationService } from '../HR/HRservice/loginservice';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  success:boolean;
  isUnAuthorized:boolean;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private authenticationService: AuthenticationService,
      private userService: UserService
  ) { 
      // redirect to home if already logged in
      if (this.authenticationService.currentUserValue) { 
          this.router.navigate(['/jobs']);
      }
  }

  ngOnInit() {
      this.registerForm = this.formBuilder.group({
          firstName: ['', Validators.required],
          lastName: ['', Validators.required],
          username: ['', Validators.required],
          admincode: ['', Validators.required],
          password: ['', [Validators.required, Validators.minLength(6)]]
      });
  }

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  onSubmit() {
      this.submitted = true;
    
      // stop here if form is invalid
      if (this.registerForm.invalid) {
          
          return;
      }
      this.loading = true;
      if(this.registerForm.value.admincode=="8_HG-NG-AP-HR-RS-SV-VVS-PT@1207"){
        this.isUnAuthorized=false;
        this.userService.register(this.registerForm.value)
        .pipe(first())
        .subscribe(
            data => {
                this.success=true;
                this.loading = false;
            },
            error => {
                this.loading = false;
                this.success=false;
            });
      }
      else if(this.registerForm.value.admincode!="8_HG-NG-AP-HR-RS-SV-VVS-PT@1207"){
        this.isUnAuthorized=true;
        this.loading = false;
      }
  }

}
