package co.com.maocq.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	private File file;
	private Workbook workbook;
	private Sheet sheet;

	public ExcelUtil(File file) {
		this.file = file;
	}

	public ExcelUtil getLibro() throws IOException {

		FileInputStream fileInput = new FileInputStream(file);

		String extension = getExtension(file.getPath());
		if (extension.equalsIgnoreCase("xls")) {
			workbook = new HSSFWorkbook(fileInput);
		} else if (extension.equalsIgnoreCase("xlsx")) {
			workbook = new XSSFWorkbook(fileInput);
		} else {
			// TODO: Lanzar exepción
			workbook = null;
		}
		return this;
	}

	public ExcelUtil getHoja(int hoja) {
		sheet = workbook.getSheetAt(hoja);
		return this;
	}

	public void recorrerHoja(ExcelIterador ei, int celdas) {

		int ultimaFila = sheet.getLastRowNum();
		for (int i = 1; i <= ultimaFila; i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				break;

			// Convertir celdas a formato String
			for (int cell = 0; cell <= celdas; cell++)
				if (row.getCell(cell) != null)
					row.getCell(cell).setCellType(Cell.CELL_TYPE_STRING);

			String[] valoresIteracion = new String[celdas];
			for (int cell = 0; cell <= celdas; cell++) {
				if (row.getCell(cell) == null)
					break;
				valoresIteracion[cell] = row.getCell(cell).toString();
			}

			ei.iteracion(valoresIteracion, i == sheet.getLastRowNum() ? -1 : i);

		}
	}

	public String[][] getMatriz() {
		String[][] matriz = new String[sheet.getLastRowNum() + 1][3];

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);

			for (int j = 0; j < row.getLastCellNum(); j++) {
				System.out.println("Fila " + i + " Columna" + j);
				matriz[i][j] = row.getCell(j).toString();
			}
		}
		return matriz;
	}

	public int getUltimaFila() {
		return sheet.getLastRowNum();
	}

	private String getExtension(String filename) {
		int index = filename.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return filename.substring(index + 1);
		}
	}

}
