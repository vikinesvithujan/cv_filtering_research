import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { Experience } from '../models/experience.model';
import { EducationalQualification } from '../models/educationalQualification.model';
import { ProfessionalQualification } from '../models/professionalQualification.model';
import { Project } from '../models/project.model';
import { CandidateService } from '../services/candidate.service';
import { ActivatedRoute } from '@angular/router';
import { TechnicalSkill } from '../models/technicalSkill.model';
import { SoftSkill } from '../models/softSkill.model';

declare var $: any;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  name: string;
  infoForm: FormGroup;
  technicalSkillForm: FormGroup;
  softSkillForm: FormGroup;
  experienceForm: FormGroup;
  educationalQualificationForm: FormGroup;
  professionalQualificationForm: FormGroup;
  projectForm: FormGroup;

  technicalSkills: TechnicalSkill[];
  softSkills: SoftSkill[];
  experiences: Experience[];
  educationalQualifications: EducationalQualification[];
  professionalQualifications: ProfessionalQualification[];
  projects: Project[];

  constructor(private candidateService: CandidateService, private location: Location, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.name = params.get("name");
      if (this.name) {
        this.name = params.get("name").replace("-", " ");
      }
      else this.back();
    });

    this.infoForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
      dateOfBirth: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phone: new FormControl('', [Validators.required]),
      nic: new FormControl('', [Validators.required]),
      linkedIn: new FormControl('', [Validators.required])
    });

    this.technicalSkillForm = new FormGroup({
      skill: new FormControl('', [Validators.required]),
      rate: new FormControl('', [Validators.required])
    });

    this.softSkillForm = new FormGroup({
      skill: new FormControl('', [Validators.required]),
      rate: new FormControl('', [Validators.required])
    });

    this.experienceForm = new FormGroup({
      company: new FormControl('', [Validators.required]),
      designation: new FormControl('', [Validators.required]),
      duration: new FormControl('', [Validators.required])
    });

    this.educationalQualificationForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      institute: new FormControl('', [Validators.required]),
      certificate: new FormControl('', [Validators.required]),
      gpa: new FormControl('', [Validators.required])
    });

    this.professionalQualificationForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      institute: new FormControl('', [Validators.required]),
      level: new FormControl('', [Validators.required]),
    });

    this.projectForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      technologies: new FormControl('', [Validators.required]),
    });

    this.init();
  }

  init(): void {
    this.technicalSkills = [];
    this.softSkills = [];
    this.experiences = [];
    this.educationalQualifications = [];
    this.professionalQualifications = [];
    this.projects = [];
  }

  get i() { return this.infoForm.controls; }
  get ts() { return this.technicalSkillForm.controls; }
  get ss() { return this.softSkillForm.controls; }
  get ex() { return this.experienceForm.controls; }
  get eq() { return this.educationalQualificationForm.controls; }
  get pq() { return this.professionalQualificationForm.controls; }
  get p() { return this.projectForm.controls; }

  openTechnicalSkillModal(): void {
    $('#technicalSkillModal').modal('show');
  }

  addTechnicalSkill(s: TechnicalSkill): void {
    if (this.technicalSkillForm.valid) {
      this.technicalSkills.push(s);
      this.technicalSkillForm.reset();
      $('#technicalSkillModal').modal('hide');
    }
  }

  deleteTechnicalSkill(index: number): void {
    this.technicalSkills.splice(index, 1);
  }

  openSoftSkillModal(): void {
    $('#softSkillModal').modal('show');
  }

  addSoftSkill(s: SoftSkill): void {
    if (this.softSkillForm.valid) {
      this.softSkills.push(s);
      this.softSkillForm.reset();
      $('#softSkillModal').modal('hide');
    }
  }

  deleteSoftSkill(index: number): void {
    this.softSkills.splice(index, 1);
  }

  openExperienceModal(): void {
    $('#experienceModal').modal('show');
  }

  addExperience(e: Experience): void {
    if (this.experienceForm.valid) {
      this.experiences.push(e);
      this.experienceForm.reset();
      $('#experienceModal').modal('hide');
    }
  }

  deleteExperience(index: number): void {
    this.experiences.splice(index, 1);
  }

  openEducationalQualificationModal(): void {
    $('#educationalQualificationModal').modal('show');
  }

  addEducationalQualification(e: EducationalQualification): void {
    if (this.educationalQualificationForm.valid) {
      this.educationalQualifications.push(e);
      this.educationalQualificationForm.reset();
      $('#educationalQualificationModal').modal('hide');
    }
  }

  deleteEducationalQualification(index: number): void {
    this.educationalQualifications.splice(index, 1);
  }

  openProfessionalQualificationModal(): void {
    $('#professionalQualificationModal').modal('show');
  }

  addProfessionalQualification(e: ProfessionalQualification): void {
    if (this.professionalQualificationForm.valid) {
      this.professionalQualifications.push(e);
      this.professionalQualificationForm.reset();
      $('#professionalQualificationModal').modal('hide');
    }
  }

  deleteProfessionalQualification(index: number): void {
    this.professionalQualifications.splice(index, 1);
  }

  openProjectModal(): void {
    $('#projectModal').modal('show');
  }

  addProject(p: Project): void {
    if (this.projectForm.valid) {
      this.projects.push(p);
      this.projectForm.reset();
      $('#projectModal').modal('hide');
    }
  }

  deleteProject(index: number): void {
    this.projects.splice(index, 1);
  }

  onSubmit(): void {
    if (this.infoForm.valid) {
      let candidate = this.infoForm.value;
      candidate.position = this.name.replace(" ", "-");
      candidate.technicalSkills = this.technicalSkills;
      candidate.softSkills = this.softSkills;
      candidate.educationalQualifications = this.educationalQualifications;
      candidate.professionalQualifications = this.professionalQualifications;
      candidate.projects = this.projects;
      candidate.workExperience = this.experiences;
      this.candidateService.addCandidate(candidate).subscribe(res => {
        $('#successModal').modal('show');
        this.infoForm.reset();
        this.init();
        $("html, body").animate({ scrollTop: 0 }, "slow");
      }, err => {
        $('#errorModal').modal('show');
        console.log(err);
        $("html, body").animate({ scrollTop: 0 }, "slow");
      });
    }
  }

  onReset(): void {
    this.infoForm.reset();
    this.init();
  }

  back(): void {
    this.location.back();
  }

}
