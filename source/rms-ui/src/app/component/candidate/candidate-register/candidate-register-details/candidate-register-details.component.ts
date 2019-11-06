import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { User } from '../../CandidateService/models/user';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CandidateService } from '../../CandidateService/candidate.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-candidate-register-details',
  templateUrl: './candidate-register-details.component.html',
  styleUrls: ['./candidate-register-details.component.css']
})
export class CandidateRegisterDetailsComponent implements OnInit {
  currentUserSubscription: Subscription;
  currentUser:User
  candidateDetailsForm:FormGroup;
  isSaved:boolean;
  fullName:string
  skills: string[] = ['Java', 'HTML/CSS', 'JavaScript', 'Spring', 'Angular', 'SQL','Networking','ML','C++','C'];
  
  
  
  constructor(private candidateService:CandidateService,private router:Router) { 

    this.candidateDetailsForm=new FormGroup({
      cName:new FormControl('',[Validators.required]),
      skillList:new FormControl('',[Validators.required]),
      cPhone:new FormControl('',Validators.required),
      cExperience:new FormControl('',Validators.required),
      cEmail:new FormControl('',Validators.required)
    });
  }

  
  async onAddCandidateDetails(){
    console.log(this.candidateDetailsForm.value);
    let res:any = await this.candidateService.createCandidate(this.candidateDetailsForm.value);
    console.log(res);
    if(res){
      this.isSaved=true;
      this.router.navigate(['/landing']);
    }
  }
  

  ngOnInit() {
    this.fullName=localStorage.getItem('fullName')
  }

  ngOnDestroy()
  {
    localStorage.removeItem('fullName')
  }

}
