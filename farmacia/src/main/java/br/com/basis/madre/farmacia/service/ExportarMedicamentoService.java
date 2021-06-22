package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.Medicamento;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExportarMedicamentoService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Medicamento> ListaMedicamentos;

    public ExportarMedicamentoService(List<Medicamento> listaMedicamentos) {
        ListaMedicamentos = listaMedicamentos;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Medicamentos");
    }

    private void escreverHeaderRow() {
        Row row = sheet.createRow(0);

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setFontHeight(12);
        estilo.setFont(fonte);
        estilo.getVerticalAlignment();

        Cell cell = row.createCell(0);
        cell.setCellValue("Nome");
        cell.setCellStyle(estilo);

        cell = row.createCell(1);
        cell.setCellValue("Descrição");
        cell.setCellStyle(estilo);

        cell = row.createCell(2);
        cell.setCellValue("Concentração");
        cell.setCellStyle(estilo);

        cell = row.createCell(3);
        cell.setCellValue("Unidade");
        cell.setCellStyle(estilo);

        cell = row.createCell(4);
        cell.setCellValue("Apresentação");
        cell.setCellStyle(estilo);

        cell = row.createCell(5);
        cell.setCellValue("Situação");
        cell.setCellStyle(estilo);

    }

    private void escreverDataRows() {
        int rowCount = 1;
        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setFontHeight(12);
        estilo.setFont(fonte);

        for (Medicamento medicamento : ListaMedicamentos) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(medicamento.getNome());
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue(medicamento.getDescricao());
            cell.setCellStyle(estilo);
            sheet.setDefaultColumnWidth(65);

            cell = row.createCell(2);
            cell.setCellValue(medicamento.getConcentracao());
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue(medicamento.getUnidade().getNome());
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue(medicamento.getApresentacao().getNome());
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(4);

            cell = row.createCell(5);
            cell.setCellValue(medicamento.getAtivo() ? "Ativo" : "Inativo");
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(5);
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escreverHeaderRow();
        escreverDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
