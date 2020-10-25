import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {PackageInfo} from "../domain/packageInfo";
import {PackageInfoService} from "../service/package-info.service";

@Component({
  selector: 'us-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  message: string = undefined;
  packageInfo: PackageInfo = new PackageInfo();

  constructor(private packageInfoService: PackageInfoService) {
  }

  ngOnInit() {
  }

  save(form: NgForm) {
    this.packageInfoService.save(this.packageInfo).subscribe(it => {
      this.message = "done";
      this.packageInfo = new PackageInfo();
    }, error => {
      this.message = "error";
    });
  }
}
