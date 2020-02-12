import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { MockData } from '../data/mock-data';

@Component({
  selector: 'app-select-career',
  templateUrl: './select-career.component.html',
  styleUrls: ['./select-career.component.css']
})
export class SelectCareerComponent implements OnInit {

  name: string;
  requirements: string[];
  responsibilities: string[];
  constructor(private route: ActivatedRoute, private location: Location, private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.name = params.get("name");
      if (this.name) {
        this.name = params.get("name").replace("-", " ");
        const n = this.name.replace(" ", "").trim();
        this.requirements = MockData.requirements[n];
        this.responsibilities = MockData.responsibilities[n];
      }
      else this.back();
    });
  }

  back(): void {
    this.location.back();
  }

  apply(): void {
    this.router.navigate(['/career/' + this.name.replace(" ", "-") + '/apply']);
  }

}
