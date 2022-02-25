package com.adriano.gerenciaplanilhas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.adriano.model.Perfil;
import com.adriano.repositotory.PerfilRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil/banco")
@AllArgsConstructor
public class GerenciadorPerfil {

    private PerfilRepository rPerfil;

    @GetMapping("/toplanilha")
    public void salvarPerfilPlanilha() throws IOException {

        Workbook wb = new XSSFWorkbook();
        List<Perfil> perfils = rPerfil.findByOrderByNumeroSeguidorDesc();

        Sheet sheet = wb.createSheet("perfil");

        for (int i = 0; i < perfils.size(); i++) {
           
            // Create a row and put some cells in it. Rows are 0 based.
            Row row = sheet.createRow(i);

            // Create a cell and put a value in it.
            row.createCell(0).setCellValue(perfils.get(i).getUsername());
            row.createCell(1).setCellValue(perfils.get(i).getSenha());
            row.createCell(2).setCellValue(perfils.get(i).getNome());
            row.createCell(3).setCellValue(perfils.get(i).getSobreNome());
            row.createCell(4).setCellValue(perfils.get(i).getDispositivo());
            row.createCell(5).setCellValue(perfils.get(i).getDataCriacao());
            row.createCell(6).setCellValue(perfils.get(i).getDataCadastro());
            row.createCell(7).setCellValue(perfils.get(i).getDataBloqueio());
            row.createCell(8).setCellValue(perfils.get(i).getDataInicioTrabalho());
            row.createCell(9).setCellValue(perfils.get(i).getNumeroSeguidor());
            row.createCell(10).setCellValue(perfils.get(i).getNumeroSeguindo());
            row.createCell(11).setCellValue(perfils.get(i).getGenero());
            row.createCell(12).setCellValue(perfils.get(i).getQualidade());
            row.createCell(13).setCellValue(perfils.get(i).getEmail().getEmail());
            row.createCell(14).setCellValue(perfils.get(i).getStatus().getStatus());
            

        }

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("src/main/resources/perfils.xlsx")) {
            wb.write(fileOut);
        }

    }

}
