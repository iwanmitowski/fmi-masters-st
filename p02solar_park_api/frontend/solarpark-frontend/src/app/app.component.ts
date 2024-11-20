import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CustomerService } from './services/customer.service';
import { Customer } from './models/customer.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  public title = 'Solar Park';
  public customerCollection: Customer[] = [];
  public isEditFormVisible = false;
  public isCreateFormVisible = false;
  public selectedCustomer: Customer | null = null;
  public createdCustomer: Customer = {
    name: '',
    numberOfProjects: 0
  };
  
  private customerService = inject(CustomerService);

  public ngOnInit() {
    this.refreshCustomerCollection();
  }

  public processOnEdit(selectedElement: Customer) {
    this.isEditFormVisible = true;
    this.selectedCustomer = selectedElement;
  }

  public processOnCreate() {
    this.isCreateFormVisible = true;
  }

  public processOnDelete(customerId: any) {
    this.customerService.deleteCustomer(customerId)
    .subscribe((result: any) => {
      this.refreshCustomerCollection();
    })
  }

  public processOnChangeCustomerName(customerInput: string) {
    if (this.selectedCustomer) {
      this.selectedCustomer.name = customerInput;
    }
    console.log(this.selectedCustomer);
  }

  public processOnChangeCreateCustomerName(customerInput: string) {
    this.createdCustomer.name = customerInput;
  }

  public processOnChangeCreateNumberOfProjects(customerInput: number) {
    this.createdCustomer.numberOfProjects = customerInput;
  }


  public processOnSave() {
    this.customerService
    .updateCustomer(this.selectedCustomer!)
    .subscribe((result) => {
      console.log(result);
    });
    
    this.isEditFormVisible = false;
    this.selectedCustomer = null;
  }

  public processOnSaveCreate() {
    this.customerService
    .createCustomer(this.createdCustomer!)
    .subscribe((result) => {
      this.createdCustomer = {
        name: '',
        numberOfProjects: 0,
      }

      this.refreshCustomerCollection();

      console.log(result);
    });
    
    this.isEditFormVisible = false;
    this.selectedCustomer = null;
  }

  private refreshCustomerCollection(): void {
    this.customerService
    .getAllCustomers()
    .subscribe((result: any) => {
      this.customerCollection = result.data;
    })
  }

  public getTitle() {
    return 'Solar Park Title Function';
  }  
}
