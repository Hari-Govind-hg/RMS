import { Component, OnInit, Directive } from '@angular/core';
import { Subscription, Observable } from 'rxjs';
import { JobServiceService } from '../HRservice/job-service.service';
import { AuthenticationService, UserService } from '../HRservice/loginservice';
import { User } from '../HRservice/models';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
@Directive({
  exportAs: '{{job.jobId}}'
})
export class JobComponent implements OnInit {
  jobList: any[];
  myControl: FormGroup;
  jobSubscription: Subscription;
  currentUser: User;
  jobId: String;
  myExperienceControl: FormGroup;
  myExperienceSkillControl:FormGroup;
  currentUserSubscription: Subscription;
  isLoggedIn$: Observable<boolean>;
  duplicateJobData: any;
  scheduledJob: any;
  scheduleTest: FormGroup;
  isDeleted: boolean;
  isSaved: boolean;
  jobData: any;
  skill: String;
  experience: any;
  isEmpty: boolean;
  date: any;
  search: any;
  count: any;
  lastDate:Date;

  constructor(private jobService: JobServiceService, private authenticationService: AuthenticationService,
    private userService: UserService, private router: Router, private route: ActivatedRoute) {
    const _jobId = this.route.snapshot.params.id;
    this.search = false;
    this.count = 0;
    this.myControl = new FormGroup({
      skill: new FormControl('')
    });
    this.myExperienceControl = new FormGroup({
      experience: new FormControl('')
    });
    this.myExperienceSkillControl= new FormGroup({
      skill: new FormControl(''),
      experience: new FormControl('')
    });
    this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
    this.scheduleTest = new FormGroup({
      date: new FormControl('')
    });
  }

  ngOnInit() {
    
    this.isLoggedIn$ = this.authenticationService.loggedIn;
    

    // Calls all the jobs in the db
    this.allJobs();

    const _jobId = this.route.snapshot.params.id;
    
  }


  // Calls all the jobs in the db
  allJobs(){
    this.jobSubscription = this.jobService.getJobs()
      .subscribe((res: any[]) => {
        
        let temp = JSON.parse(JSON.stringify(res));
        let today = new Date();
        temp.forEach(job => {
          if(job.jInterviewDate==null){
          job.isScheduled = false;
           
          }
          else{
            job.isScheduled = true;
            
          }
          let timeOfClosing = job.jApplicationCloseDate.substring(0, 10);
          let timeOfClosingYear = timeOfClosing.substring(0,4);
          let timeOfClosingMonth = timeOfClosing.substring(5,7);
          let month:number =parseInt(timeOfClosingMonth);
          timeOfClosingMonth = month-1;
          let timeOfClosingDay = timeOfClosing.substring(8,10);
          timeOfClosing = (timeOfClosingYear+"-"+timeOfClosingMonth+"-"+timeOfClosingDay);
          this.lastDate = new Date(timeOfClosingYear,timeOfClosingMonth,timeOfClosingDay);
          job.lastDateToApply = this.lastDate;
          job.isValidToSchedule = false;
          
         if(today.getTime()>this.lastDate.getTime())
         {
           
           job.isValidToSchedule = true;
         }
         else{
          
           job.isValidToSchedule = false;
         } 

        });
        this.jobList = temp;
      });
  }

  onAdvancedButtonClick() {
    this.count = this.count + 1;

    if (this.count % 2 != 0) {
      this.search = true;
    }
    else {
      this.search = false;
      this.allJobs();
    }
  }

  onEditHandler(id) {
    this.jobSubscription = this.jobService.getJobById(id)
      .subscribe((res: any) => {
        
        let job = JSON.parse(JSON.stringify(res));
        if(job.jInterviewDate==null){
          job.isScheduled = false;
           
          }
          else{
            job.isScheduled = true;
            
          }
          let timeOfClosing = job.jApplicationCloseDate.substring(0, 10);
          job.lastDateToApply = timeOfClosing;
        this.duplicateJobData = job;
        
        if (this.duplicateJobData.jAppliedCandidateList == "") {
          this.isEmpty = true;
        }
        else {
          this.isEmpty = false;
        }
      });
  }

  onInteviewScheduler(jId) {
    this.jobSubscription = this.jobService.getJobById(jId)
      .subscribe((res: any) => {
        
        this.scheduledJob = res;

        let temp = JSON.parse(JSON.stringify(res))
        temp.jInterviewDate = this.scheduleTest.value.date;

        


        this.jobSubscription = this.jobService.scheduleInterview(temp)
          .subscribe((res: any) => {
           
            this.scheduledJob = res;
          });

      });



  }

  ongetAppliedCandidatesList(id) {
    this.jobSubscription = this.jobService.getJobById(id)
      .subscribe((res: any) => {
        
        let job = JSON.parse(JSON.stringify(res));
        if(job.jInterviewDate==null){
          job.isScheduled = false;
            
          }
          else{
            job.isScheduled = true;
            
          }
          let timeOfClosing = job.jApplicationCloseDate.substring(0, 10);
          job.lastDateToApply = timeOfClosing;
        this.duplicateJobData = job;
        
        if (this.duplicateJobData.jAppliedCandidateList == "") {
          
          this.isEmpty = true;
        }
        else {
          
          this.isEmpty = false;
        }
      });
  }

  onSearchHandler(searchForm) {
    
    this.skill = searchForm.value.skill;
    if (this.skill == "") {
      this.allJobs();
    }
    else {
      this.jobSubscription = this.jobService.getJobBySkill(this.skill)
        .subscribe((res: any[]) => {
          
          this.jobList = res;
        });
    }
  }


  onSkillExperienceSearchHandler(searchForm) {
    
    this.experience = searchForm.value.experience;
    this.skill = searchForm.value.skill;
    if (this.experience == "" && this.skill == "") {
      this.allJobs();
    }
    else {
      if (this.experience == "") {
        this.jobSubscription = this.jobService.getJobBySkill(this.skill)
          .subscribe((res: any[]) => {
            
            this.jobList = res;
          });
      }

      else {
        if (this.skill == "") {
          this.jobSubscription = this.jobService.searchJobsByExperience(this.experience)
            .subscribe((res: any[]) => {
             
              this.jobList = res;
            });
        }

        else {
          this.jobSubscription = this.jobService.searchJobsBySkillExperience(this.skill, this.experience)
            .subscribe((res: any[]) => {
              
              this.jobList = res;
            });
        }
      }
    }
  }

  onExperienceSearchHandler(searchForm) {
    
    this.experience = searchForm.value.experience;
    if (this.experience == "") {
      this.allJobs();
    }
    else {
      this.jobSubscription = this.jobService.searchJobsByExperience(this.experience)
        .subscribe((res: any[]) => {
          
          this.jobList = res;
        });
    }
  }

  async onUpdateHandler(formData) {
    
    var obj = formData.value;
    obj.jId = this.jobId;

    //use promise based submission
    let res = await this.jobService.updateJob(this.duplicateJobData);

    const _jobId = this.route.snapshot.params.id;
    

    if (res) {
      this.isSaved = true;
      
      this.allJobs();
    }
  }

  async onDeleteHandler(id) {
    
    let res = await this.jobService.deleteJob(id);
    if (res) {
      this.isDeleted = true;
      this.allJobs();
    }
  }


  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.currentUserSubscription.unsubscribe();
    this.isLoggedIn$ = this.authenticationService.loggedIn;
    
  }
}