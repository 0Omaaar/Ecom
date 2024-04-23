import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss'
})
export class OrdersComponent {

  orders: any;

  constructor(private adminService: AdminService, 
    private snackBar: MatSnackBar
  ){}

  ngOnInit(): void{
    this.getPlacedOrders();
  }

  getPlacedOrders(): void{
    this.adminService.getPlacedOrders().subscribe( res => {
      this.orders = res;
    })
  }

  changeOrderStatus(orderId, status){
    this.adminService.changeOrderStatus(orderId, status).subscribe( res => {
      if (res.id != null){
        this.snackBar.open("Order Status Changed Successfully !", 'Close', {duration: 5000});
        this.getPlacedOrders();
      }else{
        this.snackBar.open("Something Went Wrong During Status Change", 'Close', {duration: 5000});
      }
    })
  }


}
