import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AlertService, AuthenticationService } from '../HRservice/loginservice';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    isFailed:boolean = false;
    navObject:NavComponent;
    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertService
    ) {
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) { 
            this.router.navigate(['/jobs']);
        }
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/jobs';
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    onLogin() {
        this.submitted = true;
        console.log("Inside onLogin UI");

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.loading = true;
        this.authenticationService.Hrlogin(this.f.username.value, this.f.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    let userObj = JSON.parse(JSON.stringify(data));
                    console.log(userObj);
                    this.isFailed=false;
                    if(userObj.authorities[0].authority==="ROLE_HR")
                    {
                    this.router.navigate([this.returnUrl]);
                }
                else
                {
                this.alertService.error("Login Failed");
                    this.loading = false;
                }
                },
                error => {
                    console.log("Inside error")
                    this.loading=false;
                    this.isFailed=true;
                });
    }
}
