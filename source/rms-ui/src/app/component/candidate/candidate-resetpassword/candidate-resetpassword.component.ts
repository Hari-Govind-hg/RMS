import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService, AlertService } from '../CandidateService/loginservice';
import { HttpClient } from '@angular/common/http';
import { first } from 'rxjs/operators';
import { User } from '../CandidateService/models/user';



@Component({
  selector: 'app-candidate-resetpassword',
  templateUrl: './candidate-resetpassword.component.html',
  styleUrls: ['./candidate-resetpassword.component.css']
})
export class CandidateResetpasswordComponent implements OnInit {
  loginForm: FormGroup;
  uname:string;
  isMismatch:boolean;
  
  

   constructor(private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private alertService: AlertService,
    private http: HttpClient,
    private route: ActivatedRoute

    ) {
      this.uname= this.route.snapshot.params.id;
    }

    ngOnInit() {
      console.log(this.uname);
      this.loginForm = this.formBuilder.group({
        password: ['', Validators.required],
        confirmpassword: ['', Validators.required],
        
       
    });
    }

    onResetPassword()
    {
    
    // this.user.username=this.uname
    
    if(this.loginForm.value.password===this.loginForm.value.confirmpassword)

      
{

    this.userService.resetPassword(this.loginForm.value,this.uname)
    .pipe(first())
    .subscribe(
        data => {
            console.log("Inside data")
            console.log(data)
            if(data==true)
            this.router.navigate(['/candidatelogin']);
            else
            this.isMismatch=true;
        },
        error => {
          console.log("Inside error")
          console.log(error)
        });

// console.log(k);
console.log("hi");

}
else
{
this.isMismatch=true;
}

}

}
