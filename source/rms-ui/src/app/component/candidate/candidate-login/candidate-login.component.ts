import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../../authentication/authentication.service';
import { AlertService } from '../CandidateService/loginservice/alert.service';
import { first } from 'rxjs/operators';
import { CandidateService } from '../CandidateService/candidate.service';

@Component({
  selector: 'app-candidate-login',
  templateUrl: './candidate-login.component.html',
  styleUrls: ['./candidate-login.component.css']
})
export class CandidateLoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  candidate:any;
  isFailed:boolean = false;

  constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private authenticationService: AuthenticationService,
      private alertService: AlertService,
      private candidateService:CandidateService
  ) {
      // redirect to home if already logged in
      if (this.authenticationService.currentUserValue) { 
          this.router.navigate(['/landing']);
      }
  }

  ngOnInit() {
      this.loginForm = this.formBuilder.group({
          username: ['', Validators.required],
          password: ['', Validators.required]
      });

      // get return url from route parameters or default to '/'
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/landing';
     
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onLogin() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.loginForm.invalid) {
          return;
      }

      this.loading = true;
      this.authenticationService.Candidatelogin(this.f.username.value, this.f.password.value)
          .pipe(first())
          .subscribe(
              async data => {
                let userObj = JSON.parse(JSON.stringify(data));
                
                if(userObj.authorities[0].authority=="ROLE_CANDIDATE")
                {
                   
                  let res= await this.candidateService.getCandidate(userObj.principal.username);
                  this.candidate = res;
                  
                  if(this.candidate==null)
                  this.router.navigate(['/registerdetails']);
                  else
                  this.router.navigate(['/landing']);
                  

                }
                else
                this.alertService.error("Login Failed")
                this.loading = false;
                  
              },
              error => {
                
                this.loading=false;
                this.isFailed=true;
              });
  }
}
