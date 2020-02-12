import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { AuthGuardService } from './services/auth-guard.service';
import { ViewCandidateComponent } from './view-candidate/view-candidate.component';
import { AboutComponent } from './about/about.component';
import { CareerComponent } from './career/career.component';
import { SelectCareerComponent } from './select-career/select-career.component';


const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'about', component: AboutComponent },
  { path: 'career', component: CareerComponent },
  { path: 'career/:name', component: SelectCareerComponent },
  { path: 'career/:name/apply', component: UserComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuardService] },
  { path: 'admin/view-candidate/:candidateId', component: ViewCandidateComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
