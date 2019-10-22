import { JobServiceService } from '../HRservice/job-service.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from "@angular/router";

@Component({
  selector: 'app-addjob',
  templateUrl: './addjob.component.html',
  styleUrls: ['./addjob.component.css']
})
export class AddjobComponent implements OnInit {

  //Create the formGroup
  jobPostingForm:FormGroup;
  isSaved:boolean=false;
  skills: string[] = ['Java', 'HTML/CSS', 'JavaScript', 'Spring', 'Angular', 'SQL','Networking','ML','C++','C'];

  constructor(private rmsService:JobServiceService,private router:Router) {

    this.jobPostingForm=new FormGroup({
      jTitle:new FormControl('',[Validators.required]),
      skillList:new FormControl('',[Validators.required]),
      jDescription:new FormControl('',Validators.required),
      jSalary:new FormControl('',Validators.required),
      jRequiredExperience:new FormControl('',Validators.required),
      jApplicationCloseDate:new FormControl('',Validators.required)
      // jOrganisation:new FormControl('',Validators.required),
    });
   }

   async onAddJobHandler(){
     console.log(this.jobPostingForm.value);
     let res:any = await this.rmsService.createJob(this.jobPostingForm.value);
     console.log(res);
     if(res){
       this.isSaved=true;
       this.router.navigate(['/jobs']);
     }
   }
  ngOnInit() {
  }

}
