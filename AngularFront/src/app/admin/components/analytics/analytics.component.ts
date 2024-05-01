import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrl: './analytics.component.scss'
})
export class AnalyticsComponent {
  constructor(private adminService: AdminService){}
  data: any;

  ngOnInit(){
    this.adminService.getAnalytics().subscribe( res => {
      this.data = res;
      console.log(this.data);
    })
  }
}
