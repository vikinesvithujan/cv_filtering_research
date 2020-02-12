import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { EducationalQualification } from '../models/educationalQualification.model';
import { Experience } from '../models/experience.model';
import { ProfessionalQualification } from '../models/professionalQualification.model';
import { Project } from '../models/project.model';
import { CandidateService } from '../services/candidate.service';
import { TechnicalSkill } from '../models/technicalSkill.model';
import { SoftSkill } from '../models/softSkill.model';

@Component({
  selector: 'app-view-candidate',
  templateUrl: './view-candidate.component.html',
  styleUrls: ['./view-candidate.component.css']
})
export class ViewCandidateComponent implements OnInit {

  infoForm: FormGroup;
  technicalSkills: TechnicalSkill[];
  softSkills: SoftSkill[];
  experiences: Experience[];
  educationalQualifications: EducationalQualification[];
  professionalQualifications: ProfessionalQualification[];
  projects: Project[];
  serverResponse: boolean;

  constructor(private router: Router, private route: ActivatedRoute, private candidateService: CandidateService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get("candidateId");
      if (id) this.getCandidateView(id);
      else this.back();
    });

    this.infoForm = new FormGroup({
      candidateId: new FormControl({ value: '', disabled: true }),
      firstName: new FormControl({ value: '', disabled: true }),
      lastName: new FormControl({ value: '', disabled: true }),
      gender: new FormControl({ value: '', disabled: true }),
      dateOfBirth: new FormControl({ value: '', disabled: true }),
      address: new FormControl({ value: '', disabled: true }),
      country: new FormControl({ value: '', disabled: true }),
      email: new FormControl({ value: '', disabled: true }),
      phone: new FormControl({ value: '', disabled: true }),
      nic: new FormControl({ value: '', disabled: true }),
      linkedIn: new FormControl({ value: '', disabled: true }),
    });

    this.serverResponse = false;
  }

  getCandidateView(candidateId: string): void {
    this.candidateService.getCandidateByID(candidateId).subscribe(res => {
      if (res && res.candidateId) {
        this.infoForm.setValue({
          candidateId: res.candidateId,
          firstName: res.firstName,
          lastName: res.lastName,
          gender: res.gender,
          dateOfBirth: res.dateOfBirth,
          address: res.address,
          country: res.country,
          email: res.email,
          phone: res.phone,
          nic: res.nic,
          linkedIn: res.linkedIn
        });
        this.technicalSkills = res.technicalSkills;
        this.softSkills = res.softSkills;
        this.experiences = res.workExperience;
        this.educationalQualifications = res.educationalQualifications;
        this.professionalQualifications = res.professionalQualifications;
        this.projects = res.projects;
        this.serverResponse = true;
      } else {
        this.back();
      }
    }, err => {
      console.log(err);
      this.back();
    });
  }

  back(): void {
    this.router.navigate(['admin']);
  }

}
