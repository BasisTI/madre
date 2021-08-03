package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.repository.MedicamentoRepository;
import br.com.basis.madre.farmacia.service.dto.MedicamentoDTO;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class ExportarMedicamentoService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private final MedicamentoService medicamentoService;
    private final MedicamentoRepository medicamentoRepository;

    private void escreverHeaderRow() {
        Row row = sheet.createRow(0);
        Cell cell;

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setFontHeight(12);
        fonte.setFontName("Sans Serif");
        estilo.setFont(fonte);
        estilo.getVerticalAlignment();

        ArrayList<String> listaHeader = new ArrayList<>();
        listaHeader.add("Nome");
        listaHeader.add("Descrição");
        listaHeader.add("Concentração");
        listaHeader.add("Unidade");
        listaHeader.add("Apresentação");
        listaHeader.add("Situação");

        for (int i = 0; i <= listaHeader.size() - 1; i++) {
            cell = row.createCell(i);
            cell.setCellValue(listaHeader.get(i));
            cell.setCellStyle(estilo);
        }

    }

    private void escreverColunas(Row rowCell, CellStyle cellStyle, int column, String value) {
        Cell cell = rowCell.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        sheet.autoSizeColumn(column);
    }
    private void escreverDescricao(Row rowCell, CellStyle cellStyle, int column, String value) {
        Cell cell = rowCell.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        sheet.setDefaultColumnWidth(65);
    }

    private void escreverDataRows() {
        int rowCount = 1;
        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setFontHeight(12);
        fonte.setFontName("Arial");
        estilo.setFont(fonte);
        String medicamentoQuery = "select * from medicamento where ativo = true";

        int contaMedicamentos = medicamentoRepository.countByAtivoIsTrue().intValue();

        Pageable medicamentos = PageRequest.of(0, contaMedicamentos);

        for (MedicamentoDTO medicamento : medicamentoService.search(medicamentoQuery, medicamentos)) {
            Row row = sheet.createRow(rowCount++);

            escreverColunas(row, estilo, 0, medicamento.getNome());
            escreverDescricao(row, estilo, 1, medicamento.getDescricao());
            escreverColunas(row, estilo, 2, medicamento.getConcentracao());
            escreverColunas(row, estilo, 3, medicamento.getUnidadeId().getNome());
            escreverColunas(row, estilo, 4, medicamento.getApresentacaoId().getNome());
            escreverColunas(row, estilo, 5, medicamento.isAtivo() ? "Ativo" : "Inativo");
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
