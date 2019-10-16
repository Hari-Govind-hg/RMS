import { JobServiceService } from '../../job-service.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-addjob',
  templateUrl: './addjob.component.html',
  styleUrls: ['./addjob.component.css']
})
export class AddjobComponent implements OnInit {

  //Create the formGroup
  jobPostingForm:FormGroup;
  isSaved:boolean=false;

  constructor(private rmsService:JobServiceService) {

    this.jobPostingForm=new FormGroup({
      jobId:new FormControl('',[Validators.required]),
      designation:new FormControl('',Validators.required),
      jobRole:new FormControl('',Validators.required),
      jobDescription:new FormControl('',Validators.required),
      employmentType:new FormControl('',Validators.required),
      salary:new FormControl('',Validators.required),
      location:new FormControl('',Validators.required),
    });
   }

   async onAddJobHandler(){
     console.log(this.jobPostingForm.value);
     let res:any = await this.rmsService.createJob(this.jobPostingForm.value);
     console.log(res);
     if(res){
       this.isSaved=true;
     }
   }
  ngOnInit() {
  }

}
