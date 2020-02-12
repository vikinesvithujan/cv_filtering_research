import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { CandidateService } from '../services/candidate.service';
import { Candidate } from '../models/candidate.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MockData } from '../data/mock-data';

declare var $: any;

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  candidates: Candidate[];
  addSkillForm: FormGroup;
  skills: string[];
  position: any;
  vacancies: string[];

  constructor(private authService: AuthService, private candidateService: CandidateService) { }

  ngOnInit() {
    this.getAllCandidates();
    this.addSkillForm = new FormGroup({
      skill: new FormControl('', [Validators.required])
    });
    this.skills = [];
    this.vacancies = MockData.availableVacancies;
    this.position = 'all';
  }

  get f() { return this.addSkillForm.controls; }

  getAllCandidates(): void {
    this.candidateService.getCandidateList().subscribe(res => {
      this.candidates = res;
    }, err => {
      console.log(err);
    });
  }

  logout(): void {
    this.authService.logout();
  }

  getSkillModal(): void {
    $('#addSkillModal').modal('show');
  }

  addSkill(value: any): void {
    if (this.addSkillForm.valid && this.skills.indexOf(value.skill) < 0) {
      this.skills.push(value.skill);
    }
    this.addSkillForm.reset();
    $('#addSkillModal').modal('hide');
  }

  deleteSkill(index: number): void {
    this.skills.splice(index, 1);
  }

  filterBySkill(): void {
    this.candidates = null;
    let data = {
      skills: this.skills,
      position: this.position.replace(" ", "-")
    };
    this.candidateService.getCandidateBySkill(data).subscribe(res => {
      this.candidates = res;
    }, err => {
      console.log(err);
      this.getAllCandidates();
    });
  }

}
