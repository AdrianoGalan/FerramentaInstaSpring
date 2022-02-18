package com.adriano.utilitario;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.adriano.model.Sobrenomes;
import com.adriano.repositotory.SobreNomeRepository;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import lombok.AllArgsConstructor;
import lombok.Cleanup;

@RestController
@RequestMapping("/api/sobrenome/banco")
@AllArgsConstructor
public class gerenciadorSobreNomes {


    private SobreNomeRepository rSobreNome;

    @GetMapping
    public void salvarNomes() throws IOException {

        @Cleanup
        FileInputStream file = new FileInputStream("src/main/resources/sobrenome.xlsx");

        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        List<Row> rows = (List<Row>) toList(sheet.iterator());

        rows.remove(0);

        for (Row row : rows) {
            List<Cell> cells = (List<Cell>) toList(row.cellIterator());
            try {
                

                Sobrenomes sobrenome = new Sobrenomes();
                sobrenome.setSobrenome(cells.get(0).getStringCellValue());
              
    
                rSobreNome.save(sobrenome);

            } catch (Exception e) {
                System.out.println("erro " + e.toString());
            }
           
        }

        System.out.println("nomes Inseridos");

    }

    private List<?> toList(Iterator<?> iterator) {
        return IteratorUtils.toList(iterator);
    }

}
