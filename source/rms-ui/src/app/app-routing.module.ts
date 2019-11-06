import { JobComponent } from './component/HR/job/job.component';
import { AddjobComponent } from './component/HR/addjob/addjob.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { JobdetailComponent } from './component/HR/jobdetail/jobdetail.component';
import { LoginComponent } from './component/HR/login/login.component';
import { RegisterComponent } from './component/HR/register';
import { AuthGuard } from './component/HR/HRservice/helper';
import { HomeImageComponent } from './component/landingPage/home-image/home-image.component';
import { LoginLandingComponent } from './component/landingPage/login-landing/login-landing.component';
import { LandingComponent } from './component/candidate/landing/landing.component';
import { ProfileComponent } from './component/candidate/profile/profile.component';
import { ViewjobsComponent } from './component/candidate/viewjobs/viewjobs.component';
import { AboutComponent } from './component/about/about.component';
import { CandidateLoginComponent } from './component/candidate/candidate-login/candidate-login.component';
import { CandidateRegisterComponent } from './component/candidate/candidate-register/candidate-register.component';
import { CandidateRegisterDetailsComponent } from './component/candidate/candidate-register/candidate-register-details/candidate-register-details.component';
import { CandidateService } from './component/candidate/CandidateService/candidate.service';
import { JobServiceService } from './component/HR/HRservice/job-service.service';
import { ContactComponent } from './component/contactus/contact/contact.component';
import { ReplyComponent } from './component/contactus/reply/reply.component';
import { QuerylistComponent } from './component/contactus/query-list/query-list.component';
import { CandidateforgotpasswordComponent } from './component/candidateforgotpassword/candidateforgotpassword.component';
import { CandidateResetpasswordComponent } from './component/candidate/candidate-resetpassword/candidate-resetpassword.component';

const routes: Routes = [
  {path:'', component:HomeImageComponent},
  {path: 'login',component: LoginLandingComponent },
  {path:'hrlogin',component:LoginComponent},
  {path : 'jobs', component : JobComponent,canActivate:  [JobServiceService]},
  {path : 'jobs/addjob', component : AddjobComponent},
  {path : 'jobs/:id', component : JobdetailComponent},
  { path: 'register', component: RegisterComponent },
  {path : 'landing', component : LandingComponent,canActivate: [CandidateService]},
  {path:'profile',component:ProfileComponent},
  {path:'listjobs',component:ViewjobsComponent},
  {path: 'about', component:AboutComponent},
  {path: 'contact', component:ContactComponent},
  {path:'reply',component:ReplyComponent},
  {path:'querylist',component:QuerylistComponent},
  {path:'candidatelogin',component:CandidateLoginComponent},
  {path:'candidateRegister',component:CandidateRegisterComponent},
  {path:'forgotpassword',component:CandidateforgotpasswordComponent},
  {path:'registerdetails',component:CandidateRegisterDetailsComponent},
  {path:'resetpassword/:id',component:CandidateResetpasswordComponent},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
