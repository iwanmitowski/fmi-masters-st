import { Component, inject, OnInit } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot } from "@angular/router";
import { CustomerService } from "../../services/customer.service";
import { DataGridComponent } from "../../components/data-grid/data-grid.component";
import { FormsModule } from "@angular/forms";
import { ProjectService } from "../../services/project.service";

export type DataGridHeader = {
  column: string,
  value: string,
}

@Component({
  standalone: true,
  templateUrl: './project.component.html',
  styleUrl: './project.component.css',
  imports: [FormsModule, DataGridComponent]
})

export class ProjectPage implements OnInit {
  private activeRouter = inject(ActivatedRoute)
  private customerService = inject(CustomerService);
  private projectService = inject(ProjectService);

  public dataGridMapping: DataGridHeader[] = [
    { column: 'Project Name', value: 'name' },
    { column: 'Project Cost', value: 'cost'}, ];

  public projectCollection = [];
  public state = {
    isCreateProjectVisible: false,
  }
  public project = {
    name: '',
    cost: 0,
    customerId: -1,
  }

  public ngOnInit(): void {
    this.refreshProjectCollection();
  }

  public processOnSave() {
    this.projectService
    .createProject(this.project)
    .subscribe((result) => {
      this.refreshProjectCollection();
      console.log(result);
    });
    this.state.isCreateProjectVisible = false;
  }

  private refreshProjectCollection(): void {
    const customerId = this.activeRouter.snapshot.paramMap.get('id');
    if (customerId) {
        this.project.customerId = +customerId;

        this.customerService.getCustomerProjects(+customerId)
        .subscribe((response: any) => {
          this.projectCollection = response.data;
        })
    }
  }
}
