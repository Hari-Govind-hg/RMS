import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { NavService } from '../../nav/nav.service';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../CandidateService/models/user';
import { Subscription } from 'rxjs/internal/Subscription';
import { CandidateService } from '../CandidateService/candidate.service';
import { HttpClient } from '@angular/common/http';
import { Candidate } from '../CandidateService/models/candidate';
// import { Candidate } from '../CandidateService/models/candidate';


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
  cJob:any;
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
    // this.dataList=localStorage.getItem(currentUser);
    // console.log(this.dataList);
  }


  onViewAllJob(){
    this.candidateSubscription= this.candidateService.getAllJobsCandidate()
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
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

    // this.getCandidate();
    this.candidateSubscription= this.candidateService.applyForJobCandidate(this.candidate.cId,jId)
    .subscribe((res:any[])=>{
      console.log(res);
      // if(res){
        
      //   this.appliedJobList=res;
      // }
  });
  }
  async showJobByPreference(name){
    let res= await this.candidateService.getCandidate(name);
    this.candidate = res;
    this.jobList=null;
    this.candidateSubscription= this.candidateService.getJobsBySkillCandidate(this.candidate.cId)
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
  });

  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.currentUserSubscription.unsubscribe();
}

}
