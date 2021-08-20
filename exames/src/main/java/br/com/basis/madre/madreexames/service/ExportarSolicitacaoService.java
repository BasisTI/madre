package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.SolicitacaoExame;
import br.com.basis.madre.madreexames.repository.SolicitacaoExameRepository;
import br.com.basis.madre.madreexames.service.dto.SolicitacaoExameDTO;
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
public class ExportarSolicitacaoService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private final SolicitacaoExameService solicitacaoExameService;
    private final SolicitacaoExameRepository solicitacaoExameRepository;

    private void escreverHeaderRow() {
        Row row = sheet.createRow(0);
        Cell cell;

        CellStyle  estilo = workbook.createCellStyle();
        XSSFFont fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setFontHeight(12);
        fonte.setFontName("Sans Serif");
        estilo.setFont(fonte);
        estilo.getVerticalAlignment();

        ArrayList<String> listaHeader = new ArrayList<>();
        listaHeader.add("Código");
        listaHeader.add("Info. Clínicas");
        listaHeader.add("Uso Antimicrobianos 24h");
        listaHeader.add("Pedido primeiro exame");

        for (int i = 0; i <= listaHeader.size() - 1; i++) {
            cell = row.createCell(i);
            cell.setCellValue(listaHeader.get(i));
            cell.setCellStyle(estilo);
        }
    }

    private void escreverColunas(Row rowCell, CellStyle cellStyle, int column, String value){
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

        int contaSolicitacoes = solicitacaoExameRepository.countAllByIdIsNotNull().intValue();

        Pageable solicitacoes = PageRequest.of(0, contaSolicitacoes);

        for (SolicitacaoExameDTO solicitacao : solicitacaoExameService.buscarTodasSolicitacoes(solicitacoes)) {
            Row row = sheet.createRow(rowCount++);

            escreverColunas(row, estilo, 0, solicitacao.getId().toString());
            escreverColunas(row, estilo, 1, solicitacao.getInfoClinica());
            escreverColunas(row, estilo, 2, solicitacao.isUsoAntimicrobianos24h() ? "Sim" : "Não");
            escreverColunas(row, estilo, 3, solicitacao.isPedidoPrimeiroExame() ? "1º Exame" : "Comparativo");
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Solicitações");
        response.setContentType("application/octet-stream");
        escreverHeaderRow();
        escreverDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
