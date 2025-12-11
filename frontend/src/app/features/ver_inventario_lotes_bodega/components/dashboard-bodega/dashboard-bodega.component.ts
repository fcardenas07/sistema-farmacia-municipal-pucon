import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard-bodega',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-bodega.component.html',
  styleUrls: ['./dashboard-bodega.component.css']
})
export class DashboardBodegaComponent implements AfterViewInit {

  ngAfterViewInit(): void {
    this.renderChart1();
    this.renderChart2();
    this.renderChart3();
  }

  // ================================
  // Gráfico BAR (Mensual)
  // ================================
  renderChart1(): void {
    new Chart('chart1', {
      type: 'bar',
      data: {
        labels: ['Dañado', 'Error Inv.', 'Robo', 'Recal'],
        datasets: [
          {
            label: 'Mermas',
            data: [12, 5, 2, 3],
            backgroundColor: [
              '#6366f1', // Indigo 500
              '#60a5fa', // Blue 400
              '#34d399', // Emerald 400
              '#fbbf24'  // Amber 400
            ],
            borderRadius: 6
          }
        ]
      },
      options: {
        responsive: true,
        animation: { duration: 1000, easing: 'easeOutQuart' },
        plugins: { legend: { display: false } }
      }
    });
  }

  // ================================
  // Gráfico PIE (Tipo de merma)
  // ================================
  renderChart2(): void {
    new Chart('chart2', {
      type: 'pie',
      data: {
        labels: ['Dañado', 'Error Inv.', 'Robo'],
        datasets: [
          {
            data: [12, 5, 2],
            backgroundColor: [
              '#6366f1',
              '#60a5fa',
              '#34d399'
            ]
          }
        ]
      },
      options: {
        responsive: true,
        animation: { duration: 900 }
      }
    });
  }

  // ================================
  // Gráfico HORIZONTAL BAR
  // ================================
  renderChart3(): void {
    new Chart('chart3', {
      type: 'bar',
      data: {
        labels: ['Ibuprofeno 400mg'],
        datasets: [
          {
            label: 'Reposición',
            data: [45],
            backgroundColor: '#60a5fa',
            borderRadius: 6
          }
        ]
      },
      options: {
        indexAxis: 'y',
        responsive: true,
        animation: { duration: 900 },
        plugins: { legend: { display: false } }
      }
    });
  }

}
