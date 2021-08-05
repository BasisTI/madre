package br.com.basis.consulta.service;

import br.com.basis.consulta.domain.Emergencia;
import br.com.basis.consulta.repository.EmergenciaRepository;
import br.com.basis.consulta.service.dto.EmergenciaDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ExportarEmergenciaService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private final EmergenciaService emergenciaService;
    private final EmergenciaRepository emergenciaRepository;

    private void escreverHeaderRow() {
        Row row = sheet.createRow(0);
        Cell cell;

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setFontName("Sans Serif");
        estilo.setFont(fonte);
        estilo.getVerticalAlignment();

        ArrayList<String> listaHeader = new ArrayList<>();
        listaHeader.add("Número");
        listaHeader.add("Grade");
        listaHeader.add("Data/Hora");
        listaHeader.add("Sala");
        listaHeader.add("Turno");
        listaHeader.add("Pagador");
        listaHeader.add("Clínica central");
        listaHeader.add("Justificativa");
        listaHeader.add("Observações");
        listaHeader.add("Paciente ID");

        for (int i = 0; i <= listaHeader.size() -1; i++) {
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


    private void escreverDataRows() {
        int rowCount = 1;
        CellStyle estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setFontHeight(12);
        fonte.setFontName("Arial");
        estilo.setFont(fonte);
        String emergenciaQuery = "select * from emergencia";

        int contaEmergencias = emergenciaRepository.countAllByIdIsNotNull().intValue();

        Pageable medicamentos = PageRequest.of(0, 10);

        for (Emergencia emergencia : emergenciaService.buscaEmergencias(medicamentos)){
            Row row = sheet.createRow(rowCount++);

            escreverColunas(row, estilo, 0, emergencia.getGrade().toString());
            escreverColunas(row, estilo, 1, emergencia.getDataHoraDaConsulta().toString());
            escreverColunas(row, estilo, 2, emergencia.getNumeroSala());
            escreverColunas(row, estilo, 3, emergencia.getTurno().name());
            escreverColunas(row, estilo, 4, emergencia.getTipoPagador().name());
            escreverColunas(row, estilo, 5, emergencia.getClinicaCentralId().toString());
            escreverColunas(row, estilo, 6, emergencia.getJustificativa());
            escreverColunas(row,estilo, 7, emergencia.getObservacoes());
            escreverColunas(row, estilo, 8, emergencia.getPacienteId().toString());

        }
    }


    public void exportar(HttpServletResponse response) throws IOException {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Emergências");
        response.setContentType("application/octet-stream");
        escreverHeaderRow();
        escreverDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
