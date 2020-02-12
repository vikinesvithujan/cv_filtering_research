import { Component, OnInit } from '@angular/core';
import { MockData } from '../data/mock-data';
import { Router } from '@angular/router';

@Component({
  selector: 'app-career',
  templateUrl: './career.component.html',
  styleUrls: ['./career.component.css']
})
export class CareerComponent implements OnInit {

  vacancies: string[];

  constructor(private router: Router) { }

  ngOnInit() {
    this.vacancies = MockData.availableVacancies;
  }

  selectVacancy(vacancy: string): void {
    vacancy = vacancy.replace(" ", "-");
    this.router.navigate(['/career/' + vacancy]);
  }

}
