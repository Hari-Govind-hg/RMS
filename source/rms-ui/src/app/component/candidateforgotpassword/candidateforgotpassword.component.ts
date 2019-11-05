import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from '../HR/HRservice/loginservice';
import { UserService } from '../candidate/CandidateService/loginservice/user.service';
import { HttpClient } from '@angular/common/http';
import { map, first } from 'rxjs/operators';
import { User } from '../candidate/CandidateService/models';

@Component({
  selector: 'app-candidateforgotpassword',
  templateUrl: './candidateforgotpassword.component.html',
  styleUrls: ['./candidateforgotpassword.component.css']
})
export class CandidateforgotpasswordComponent implements OnInit {
  questions: string[] = ['What is the name of your first pet?','What is the color of your first car?','Who is your favourite sportsperson?'];
  loginForm: FormGroup;
  result:boolean;
  isFailed:boolean;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private alertService: AlertService,
    private http: HttpClient
    ) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      questionList: ['', Validators.required],
      answer: ['', Validators.required],
  });
  }

  onForgotPassword()
  {
    console.log(this.loginForm.value)
    let data=this.loginForm.value;
    
    this.userService.forgotPassword(this.loginForm.value)
          .pipe(first())
          .subscribe(
            
              data => {
                if(data==true)
                {
                  console.log("Inside data")
                  console.log(data);
                  this.router.navigate(['/resetpassword/'+this.loginForm.value.username]);
              }
              else
              this.isFailed=true;
            }
              ,
              error => {
                console.log("Inside error");
                console.log(error);
                this.isFailed=true;
              });
    
    // console.log(k);
    console.log("hi");

  }
  

}
