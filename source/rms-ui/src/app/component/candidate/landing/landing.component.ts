import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
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
export class LandingComponent implements OnInit, OnDestroy {
  currentUserSubscription: Subscription;
  currentUser: any;
  jobList: any;
  appliedJobList: any;
  isApplied: boolean;
  candidateSubscription: any;
  dataList: any;
  duplicateCandidateData: any;
  candidate: any;
  appliedStatus: boolean;
  cJob: any;
  status: any;
  lastDate: Date;
  timeOfClosing: Date;
  loading:boolean;
  isLoading:boolean;
  constructor(private authenticationService: AuthenticationService, private router: Router, private route: ActivatedRoute, private candidateService: CandidateService, private http: HttpClient) {
    this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
  }



  ngOnInit() {
    this.onViewAllJob();
    this.onViewAppliedJob();
  }

  onViewAllJob() {
    this.isLoading=true;
    this.candidateSubscription = this.candidateService.getAllJobsCandidate()
      .subscribe((res: any[]) => {
        let temp = JSON.parse(JSON.stringify(res));
        let today = new Date();
        temp.forEach(job => {
          let timeOfClosing = job.jApplicationCloseDate.substring(0, 10);
          let timeOfClosingYear = timeOfClosing.substring(0, 4);
          let timeOfClosingMonth = timeOfClosing.substring(5, 7);
          let month: number = parseInt(timeOfClosingMonth);
          timeOfClosingMonth = month - 1;
          let timeOfClosingDay = timeOfClosing.substring(8, 10);
          timeOfClosing = (timeOfClosingYear + "-" + timeOfClosingMonth + "-" + timeOfClosingDay);
          this.lastDate = new Date(timeOfClosingYear, timeOfClosingMonth, timeOfClosingDay);
          job.lastDateToApply = this.lastDate;
          if ((this.lastDate.getTime() - today.getTime()) <= 86400000) {
            job.applyFast = true;
          }
          else {
            job.applyFast = false;
          }
          if(job.jInterviewDate!=null){
            job.isClosed=true;
          }
          else if(job.jInterviewDate==null){
            job.isClosed=false;
          }
        })
        this.jobList = temp;
        this.isLoading=false
      });
      this.onViewAppliedJob();
  }
 async onViewAppliedJob(){
    let name = this.currentUser.principal.username;
    let res = await this.candidateService.getCandidate(name);
    this.candidate = res;
    
    this.candidateSubscription=this.candidateService.getJobsAppliedByCandidate(this.candidate.cId)
    .subscribe((res: any[]) => {
      let temp = JSON.parse(JSON.stringify(res));
      this.appliedJobList=temp;
      
    });
  }

  async onEditProfileHandler(name) {
    let res = await this.candidateService.getCandidate(name);
    this.duplicateCandidateData=res;
  }

  onViewJobHandler(jId) {
    this.jobList.forEach(job => {
      if (job.jId == jId) {
        this.cJob = job;
      }
    });
  }

  async onUpdateHandler(formData) {
    var obj = formData.value;

    //use promise based submission
    let res = await this.candidateService.updateCandidate(this.duplicateCandidateData);
  }

  async onApplyHandler(jId) {
    this.loading = true;
    let name = this.currentUser.principal.username;
    let res = await this.candidateService.getCandidate(name);
    this.candidate = res;
    this.candidateSubscription = this.candidateService.applyForJobCandidate(this.candidate.cId, jId)
      .subscribe((res: any[]) => {
        let userObj = JSON.parse(JSON.stringify(res));
        if (userObj.status == "Success") {
          this.loading = false;
          this.appliedStatus = true;
        }
        else if (userObj.status=="Failed") {
          this.loading = false;
          this.appliedStatus = false;
        }
      });
      this.onViewAppliedJob();
  }


  async showJobByPreference(name) {
    let res = await this.candidateService.getCandidate(name);
    this.candidate = res;
    this.jobList = null;
    this.candidateSubscription = this.candidateService.getJobsBySkillCandidate(this.candidate.cId)
      .subscribe((res: any[]) => {
        let temp = JSON.parse(JSON.stringify(res));
        let today = new Date();
        temp.forEach(job => {
          let timeOfClosing = job.jApplicationCloseDate.substring(0, 10);
          let timeOfClosingYear = timeOfClosing.substring(0, 4);
          let timeOfClosingMonth = timeOfClosing.substring(5, 7);
          let month: number = parseInt(timeOfClosingMonth);
          timeOfClosingMonth = month - 1;
          let timeOfClosingDay = timeOfClosing.substring(8, 10);
          timeOfClosing = (timeOfClosingYear + "-" + timeOfClosingMonth + "-" + timeOfClosingDay);
          this.lastDate = new Date(timeOfClosingYear, timeOfClosingMonth, timeOfClosingDay);
          job.lastDateToApply = this.lastDate;
          if ((this.lastDate.getTime() - today.getTime()) <= 86400000) {
            job.applyFast = true;
          }
          else {
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