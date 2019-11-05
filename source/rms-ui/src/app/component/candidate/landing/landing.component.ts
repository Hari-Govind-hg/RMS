import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { NavService } from '../../nav/nav.service';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../CandidateService/models/user';
import { Subscription } from 'rxjs/internal/Subscription';
import { CandidateService } from '../CandidateService/candidate.service';
import { HttpClient } from '@angular/common/http';
import { Candidate } from '../CandidateService/models/candidate';


@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit,OnDestroy {
  currentUserSubscription: Subscription;
  currentUser:any;
  jobList:any;
  appliedJobList:any;
  isApplied:boolean;
  candidateSubscription:Subscription;
  dataList:any;
  candidate:any;
  appliedStatus:boolean;
  cJob:any;
  status:any;
  lastDate:Date;
  timeOfClosing:Date;
  constructor(private authenticationService: AuthenticationService,
    public nav: NavService,private router:Router,private route:ActivatedRoute,private candidateService:CandidateService,private http:HttpClient) 
    { 
      this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
      console.log(this.currentUser);
  });
}



  ngOnInit() {
    this.nav.show();
    this.onViewAllJob();
  }


  onViewAllJob(){
    this.candidateSubscription= this.candidateService.getAllJobsCandidate()
    .subscribe((res:any[])=>{
      console.log(res);
      let temp = JSON.parse(JSON.stringify(res));
      let today = new Date();
      temp.forEach(job=>{
        let timeOfClosing = job.jApplicationCloseDate.substring(0, 10);
          let timeOfClosingYear = timeOfClosing.substring(0,4);
          let timeOfClosingMonth = timeOfClosing.substring(5,7);
          let month:number =parseInt(timeOfClosingMonth);
          timeOfClosingMonth = month-1;
          let timeOfClosingDay = timeOfClosing.substring(8,10);
          timeOfClosing = (timeOfClosingYear+"-"+timeOfClosingMonth+"-"+timeOfClosingDay);
          this.lastDate = new Date(timeOfClosingYear,timeOfClosingMonth,timeOfClosingDay);
          job.lastDateToApply = this.lastDate;
          console.log(this.lastDate.getTime());
          console.log(today.getTime());
          console.log(this.lastDate.getTime()-today.getTime());
          if((this.lastDate.getTime()-today.getTime())<=86400000){
            job.applyFast = true;
          }
          else{
            job.applyFast = false;
          }
      })
      this.jobList = temp;
  });
  }

  onViewJobHandler(jId){
    this.jobList.forEach(job => {
      if(job.jId==jId){
        this.cJob=job;
      }
    });
  }
  
async onApplyHandler(jId){
    console.log("inside applyhandler");
    let name = this.currentUser.principal.username;
    console.log(name);
    let res= await this.candidateService.getCandidate(name);
    this.candidate = res;
    console.log(this.candidate);

    this.candidateSubscription= this.candidateService.applyForJobCandidate(this.candidate.cId,jId)
    .subscribe((res:any[])=>{
      console.log(res);
      // console.log(res.status);
      if(res.status=="Success"){
        this.appliedStatus=true;
      }
      else if(res.status){
        this.appliedStatus=false;
      }
  });
  }
  async showJobByPreference(name){
    let res= await this.candidateService.getCandidate(name);
    this.candidate = res;
    this.jobList=null;
    this.candidateSubscription= this.candidateService.getJobsBySkillCandidate(this.candidate.cId)
    .subscribe((res:any[])=>{
      console.log(res);
      let temp = JSON.parse(JSON.stringify(res));
      let today = new Date();
      temp.forEach(job=>{
        let timeOfClosing = job.jApplicationCloseDate.substring(0, 10);
          let timeOfClosingYear = timeOfClosing.substring(0,4);
          let timeOfClosingMonth = timeOfClosing.substring(5,7);
          let month:number =parseInt(timeOfClosingMonth);
          timeOfClosingMonth = month-1;
          let timeOfClosingDay = timeOfClosing.substring(8,10);
          timeOfClosing = (timeOfClosingYear+"-"+timeOfClosingMonth+"-"+timeOfClosingDay);
          this.lastDate = new Date(timeOfClosingYear,timeOfClosingMonth,timeOfClosingDay);
          job.lastDateToApply = this.lastDate;
          if((this.lastDate.getTime()-today.getTime())<=86400000){
            job.applyFast = true;
          }
          else{
            job.applyFast = false;
          }
      })
      this.jobList = temp;
  });

  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.currentUserSubscription.unsubscribe();
}

}
