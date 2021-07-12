package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.service.dto.MedicamentoDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportarMedicamentoService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Medicamento> ListaMedicamentos;

    @Autowired
    private MedicamentoService medicamentoService;

    public ExportarMedicamentoService(List<Medicamento> listaMedicamentos) {
        ListaMedicamentos = listaMedicamentos;

    }

    private void escreverHeaderRow() {
        Row row = sheet.createRow(0);
        Cell cell;

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setFontHeight(12);
        estilo.setFont(fonte);
        estilo.getVerticalAlignment();

        ArrayList<String> listaHeader = new ArrayList<>();
        listaHeader.add("Nome");
        listaHeader.add("Descrição");
        listaHeader.add("Concentração");
        listaHeader.add("Unidade");
        listaHeader.add("Apresentação");
        listaHeader.add("Situação");

        for(int i=0; i<=listaHeader.size()-1; i++){
            cell = row.createCell(i);
            cell.setCellValue(listaHeader.get(i));
            cell.setCellStyle(estilo);
        }

    }

    private void escreverDataRows() {
        int rowCount = 1;
        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setFontHeight(12);
        estilo.setFont(fonte);

        String medicamentoQuery = "select * from medicamento";
        int medicamentoSize = medicamentoService.listarTodos().size();
        Pageable medicamentos = PageRequest.of(0, medicamentoSize);

        for (MedicamentoDTO medicamento : medicamentoService.search(medicamentoQuery, medicamentos)) {
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
            cell.setCellValue(medicamento.getUnidadeId().getNome());
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue(medicamento.getApresentacaoId().getNome());
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(4);

            cell = row.createCell(5);
            cell.setCellValue(medicamento.isAtivo() ? "Ativo" : "Inativo");
            cell.setCellStyle(estilo);
            sheet.autoSizeColumn(5);
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Medicamentos");
        response.setContentType("application/octet-stream");
        escreverHeaderRow();
        escreverDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
