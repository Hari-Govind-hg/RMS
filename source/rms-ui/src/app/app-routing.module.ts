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
import { JobdetailsComponent } from './component/candidate/jobdetails/jobdetails.component';

const routes: Routes = [
  {path:'', component:HomeImageComponent},
  {path: 'login',component: LoginLandingComponent },
  {path:'hrlogin',component:LoginComponent},
  {path : 'jobs', component : JobComponent,canActivate: [AuthGuard]},
  {path : 'jobs/addjob', component : AddjobComponent},
  {path : 'jobs/:id', component : JobdetailComponent},
  { path: 'register', component: RegisterComponent },
  {path : 'landing', component : LandingComponent},
  {path:'profile',component:ProfileComponent},
  {path:'listjobs',component:ViewjobsComponent},
  {path:'jobdetails',component:JobdetailsComponent},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
