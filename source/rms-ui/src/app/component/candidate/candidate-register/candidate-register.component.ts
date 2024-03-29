import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { UserService, AuthenticationService } from '../CandidateService/loginservice';
import { AlertService } from '../../HR/HRservice/loginservice';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-candidate-register',
  templateUrl: './candidate-register.component.html',
  styleUrls: ['./candidate-register.component.css']
})
export class CandidateRegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  isDuplicateUser:boolean;
  questions: string[] = ['What is the name of your first pet?','What is the color of your first car?','Who is your favourite sportsperson?'];

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private authenticationService: AuthenticationService,
      private userService: UserService,
      private alertService: AlertService
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
          questionList: ['', Validators.required],
          answer: ['', Validators.required],
          password: ['', [Validators.required, Validators.minLength(6)]],
      });

      
  }

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  onSubmit() {
      this.submitted = true;
      

      localStorage.setItem('fullName',this.registerForm.value.firstName+" "+this.registerForm.value.lastName)

      // stop here if form is invalid
      if (this.registerForm.invalid) {
          
          return;
      }
    
      this.loading = true;
      this.userService.register(this.registerForm.value)
          .pipe(first())
          .subscribe(
              data => {
                  
                  this.alertService.success('Registration successful', true);
                  this.router.navigate(['/candidatelogin']);
              },
              error => {

                  this.isDuplicateUser=true
                  this.alertService.error('Registration Failed',error);
                  this.loading = false;
              });
  }

}
