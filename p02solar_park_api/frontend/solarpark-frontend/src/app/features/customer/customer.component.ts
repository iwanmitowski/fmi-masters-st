import { Component, inject } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Customer } from "../../models/customer.model";
import { CustomerService } from "../../services/customer.service";
import { DataGridComponent, DataGridHeader } from "../../components/data-grid/data-grid.component";
import { Router } from "@angular/router";

@Component({
  standalone: true,
  imports: [FormsModule, DataGridComponent],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css'
})

export class CustomerPage {
  public dataGridMapping: DataGridHeader[] = [
    { column: 'Customer Name', value: 'name' },
    { column: 'N projects', value: 'numberOfProjects'}, ];

  public customerCollection: Customer[] = [];
  public isEditFormVisible = false;
  public isCreateFormVisible = false;
  public selectedCustomer: Customer | null = null;
  public createdCustomer: Customer = {
    name: '',
    numberOfProjects: 0
  };

  private customerService = inject(CustomerService);
  private router = inject(Router)

  public ngOnInit() {
    this.refreshCustomerCollection();
  }

  public processOnNavigate(customer: Customer) {
    this.router.navigateByUrl(`/customers/${customer.id}/projects`)
  }

  public processOnEdit(selectedElement: Customer) {
    this.isEditFormVisible = true;
    this.selectedCustomer = selectedElement;
  }

  public processOnCreate() {
    this.isCreateFormVisible = true;
  }

  public processOnDelete(customer: any) {
    this.customerService.deleteCustomer(customer.id)
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
}
