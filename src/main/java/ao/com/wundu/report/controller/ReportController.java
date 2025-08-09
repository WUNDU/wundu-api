package ao.com.wundu.report.controller;

import ao.com.wundu.report.dtos.CategoryReportRequest;
import ao.com.wundu.report.dtos.CategoryReportResponse;
import ao.com.wundu.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reports", description = "Relatórios de gastos por categoria")
@RestController
@RequestMapping("api/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "Gastos de um usuário por categoria em um mês/ano")
    @PostMapping("/category")
    public ResponseEntity<CategoryReportResponse> getCategoryReport(@Valid @RequestBody CategoryReportRequest request) {
        CategoryReportResponse response = reportService.getCategorySpendingReport(request);
        return ResponseEntity.ok(response);
    }
}
