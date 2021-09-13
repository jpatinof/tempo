package com.b2bsg.common.file.excel;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.util.NumericUtils;
import com.b2bsg.common.util.StringUtils;
import com.cromasoft.cromaflow.common.constants.ErrorKeys;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Clase que contiene todos las propiedades de ExcelUtils para la manipulacion de
 * archivos en formato ecel
 *
 * @author Edgar Prieto
 */
public class ExcelUtils
{
	/**
	 * Retorna el valor de cell.
	 *
	 * @param ar_row correspondiente al valor del tipo de objeto Row
	 * @param ai_cell correspondiente al valor del tipo de objeto int
	 * @return el valor de cell
	 */
	public static Cell getCell(Row ar_row, int ai_cell)
	{
		Cell lc_cell;

		lc_cell = null;

		if(ar_row != null)
		{
			lc_cell = ar_row.getCell(ai_cell);

			if(lc_cell == null)
				lc_cell = ar_row.createCell(ai_cell);
		}

		return lc_cell;
	}

	/**
	 * Retorna el valor de row.
	 *
	 * @param as_sheet correspondiente al valor del tipo de objeto Sheet
	 * @param ai_row correspondiente al valor del tipo de objeto int
	 * @return el valor de row
	 */
	public static Row getRow(Sheet as_sheet, int ai_row)
	{
		Row lr_row;

		lr_row = null;

		if(as_sheet != null)
		{
			lr_row = as_sheet.getRow(ai_row);

			if(lr_row == null)
				lr_row = as_sheet.createRow(ai_row);
		}

		return lr_row;
	}

	/**
	 * Retorna el valor de sheet.
	 *
	 * @param aw_workbook correspondiente al valor del tipo de objeto Workbook
	 * @param as_sheetName correspondiente al valor del tipo de objeto String
	 * @return el valor de sheet
	 */
	public static Sheet getSheet(Workbook aw_workbook, String as_sheetName)
	{
		Sheet ls_sheet;

		ls_sheet = null;

		if(aw_workbook != null)
		{
			ls_sheet = aw_workbook.getSheet(as_sheetName);

			if(ls_sheet == null)
				ls_sheet = aw_workbook.createSheet(as_sheetName);
		}

		return ls_sheet;
	}

	/**
	 * Valida la propiedad valid row.
	 *
	 * @param ar_row correspondiente al valor del tipo de objeto Row
	 * @param ai_lastCell correspondiente al valor del tipo de objeto int
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid row
	 */
	public static boolean isValidRow(Row ar_row, int ai_lastCell)
	{
		boolean lb_validRow;

		lb_validRow = false;

		if(ar_row != null)
		{
			int li_i;

			li_i = 0;

			while((li_i < ai_lastCell) && !lb_validRow)
			{
				Cell lc_cel;

				lc_cel = ar_row.getCell(li_i);

				if(lc_cel != null)
				{
					int    li_cellType;
					String ls_celValue;

					li_cellType     = lc_cel.getCellType();
					ls_celValue     = null;

					switch(li_cellType)
					{
						case Cell.CELL_TYPE_STRING:
							ls_celValue = StringUtils.getString(lc_cel.getStringCellValue());

							break;

						case Cell.CELL_TYPE_NUMERIC:
							ls_celValue = StringUtils.getString(lc_cel.getNumericCellValue());

							break;

						default:
							ls_celValue = StringUtils.getString(lc_cel.getStringCellValue());

							break;
					}

					lb_validRow = StringUtils.isValidString(ls_celValue);
				}

				li_i++;
			}
		}

		return lb_validRow;
	}

	/**
	 * Valida la propiedad xlsx.
	 *
	 * @param aw_workbook correspondiente al valor del tipo de objeto Workbook
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en xlsx
	 */
	public static boolean isXlsx(Workbook aw_workbook)
	{
		return (aw_workbook != null) && aw_workbook instanceof XSSFWorkbook;
	}

	/**
	 * Retorna el valor del objeto de int.
	 *
	 * @param ad_width correspondiente al valor del tipo de objeto double
	 * @return devuelve el valor de int
	 */
	public static int calculateColumnWidth(double ad_width)
	{
		double ld_return;

		if(ad_width > 254d)
			ld_return = 65280d;

		if(ad_width > 1d)
			ld_return = 450d + (30d * Math.floor((ad_width) / 5d)) + ((ad_width - 1d) * 250d);
		else
			ld_return = 450d;

		return NumericUtils.getInt(ld_return);
	}

	/**
	 * Crea celda.
	 *
	 * @param ar_fila correspondiente al valor del tipo de objeto Row
	 * @param ai_numeroCelda correspondiente al valor del tipo de objeto int
	 * @param valor correspondiente al valor del tipo de objeto String
	 * @param acs_estilo correspondiente al valor del tipo de objeto CellStyle
	 */
	public static void creaCelda(Row ar_fila, int ai_numeroCelda, String valor, CellStyle acs_estilo)
	{
		final Cell lc_celda = ar_fila.createCell(ai_numeroCelda);
		lc_celda.setCellValue(valor);

		if(acs_estilo != null)
			lc_celda.setCellStyle(acs_estilo);
	}

	/**
	 * Crea celda.
	 *
	 * @param ar_fila correspondiente al valor del tipo de objeto Row
	 * @param ai_numeroCelda correspondiente al valor del tipo de objeto int
	 * @param valor correspondiente al valor del tipo de objeto double
	 * @param acs_estilo correspondiente al valor del tipo de objeto CellStyle
	 */
	public static void creaCelda(Row ar_fila, int ai_numeroCelda, double valor, CellStyle acs_estilo)
	{
		creaCelda(ar_fila, ai_numeroCelda, StringUtils.getString(valor), acs_estilo);
	}

	/**
	 * Retorna el valor del objeto de Map con los estilos de la hoda
	 *
	 * @param lw_workbook correspondiente al valor del tipo de objeto Workbook
	 * @return devuelve el valor de Map
	 */
	public static Map<String, CellStyle> createStyles(Workbook lw_workbook)
	{
		return createStyles(lw_workbook, false);
	}

	/**
	 * Retorna el valor del objeto de Map con los estilos de la hoda
	 *
	 * @param lw_workbook correspondiente al valor del tipo de objeto Workbook
	 * @param ab_lockedCells indica si se deben bloquear las celdas
	 * @return devuelve el valor de Map
	 */
	public static Map<String, CellStyle> createStyles(Workbook lw_workbook, boolean ab_lockedCells)
	{
		Map<String, CellStyle> lmscs_styles;

		lmscs_styles = new HashMap<String, CellStyle>();

		if(lw_workbook != null)
		{
			{
				CellStyle lcs_style;
				Font      lf_font;

				lf_font       = lw_workbook.createFont();
				lcs_style     = lw_workbook.createCellStyle();

				lf_font.setFontHeightInPoints((short)8);
				lf_font.setColor(IndexedColors.WHITE.getIndex());
				lf_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				lf_font.setFontName("Arial");

				lcs_style.setAlignment(CellStyle.ALIGN_CENTER);
				lcs_style.setBorderBottom((short)1);
				lcs_style.setBorderLeft((short)1);
				lcs_style.setBorderRight((short)1);
				lcs_style.setBorderTop((short)1);
				lcs_style.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
				lcs_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				lcs_style.setFont(lf_font);
				lcs_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				lcs_style.setLocked(ab_lockedCells);

				lmscs_styles.put("title", lcs_style);
			}

			{
				CellStyle lcs_style;
				Font      lf_font;

				lf_font       = lw_workbook.createFont();
				lcs_style     = lw_workbook.createCellStyle();

				lf_font.setFontHeightInPoints((short)8);
				lf_font.setColor(IndexedColors.BLACK.getIndex());
				lf_font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				lf_font.setFontName("Arial");

				lcs_style.setAlignment(CellStyle.ALIGN_LEFT);
				lcs_style.setBorderBottom((short)1);
				lcs_style.setBorderLeft((short)1);
				lcs_style.setBorderRight((short)1);
				lcs_style.setBorderTop((short)1);
				lcs_style.setFont(lf_font);
				lcs_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				lcs_style.setLocked(ab_lockedCells);

				lmscs_styles.put("normal", lcs_style);
			}

			{
				CellStyle lcs_style;
				Font      lf_font;

				CreationHelper createHelper = lw_workbook.getCreationHelper();

				lf_font       = lw_workbook.createFont();
				lcs_style     = lw_workbook.createCellStyle();

				lf_font.setFontHeightInPoints((short)8);
				lf_font.setColor(IndexedColors.BLACK.getIndex());
				lf_font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				lf_font.setFontName("Arial");

				lcs_style.setAlignment(CellStyle.ALIGN_LEFT);
				lcs_style.setBorderBottom((short)1);
				lcs_style.setBorderLeft((short)1);
				lcs_style.setBorderRight((short)1);
				lcs_style.setBorderTop((short)1);
				lcs_style.setFont(lf_font);
				lcs_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				lcs_style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));
				lcs_style.setLocked(ab_lockedCells);

				lmscs_styles.put("date", lcs_style);
			}
		}

		return lmscs_styles;
	}

	/**
	 * Retorna el valor del objeto de Workbook.
	 *
	 * @param ab_xlsx correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Workbook
	 * @see Workbook
	 */
	public static Workbook createWorkbook(boolean ab_xlsx)
	{
		return ab_xlsx ? new XSSFWorkbook() : new HSSFWorkbook();
	}

	/**
	 * Retorna el valor del objeto de Workbook.
	 *
	 * @param ab_xlsx correspondiente al valor del tipo de objeto boolean
	 * @param ac_template correspondiente al valor del tipo de objeto byte[]
	 * @return devuelve el valor de Workbook
	 * @throws IOException Señala que se ha producido una excepción de E / S.
	 * @see Workbook
	 */
	public static Workbook createWorkbook(boolean ab_xlsx, byte[] ac_template)
	    throws IOException
	{
		Workbook lw_return;

		if(ac_template != null)
			lw_return = ab_xlsx ? new XSSFWorkbook(new ByteArrayInputStream(ac_template))
				                : new HSSFWorkbook(new ByteArrayInputStream(ac_template));
		else
			lw_return = createWorkbook(ab_xlsx);

		return lw_return;
	}

	/**
	 * Retorna el valor del objeto de Date.
	 *
	 * @param ar_registro correspondiente al valor del tipo de objeto Row
	 * @param ai_fila correspondiente al valor del tipo de objeto int
	 * @param ai_celda correspondiente al valor del tipo de objeto int
	 * @param as_nombreCelda correspondiente al valor del tipo de objeto String
	 * @param ab_requerido correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Date
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public static Date validarDateCeldaExcel(
	    Row ar_registro, int ai_fila, int ai_celda, String as_nombreCelda, boolean ab_requerido
	)
	    throws B2BException
	{
		Cell lc_tmp;
		Date ld_date;

		lc_tmp      = ar_registro.getCell(ai_celda);
		ld_date     = null;

		if(lc_tmp != null)
		{
			int li_cellType;

			li_cellType = lc_tmp.getCellType();

			if((li_cellType == Cell.CELL_TYPE_NUMERIC) || (li_cellType == Cell.CELL_TYPE_STRING))
				ld_date = lc_tmp.getDateCellValue();
		}

		if((ld_date == null) && ab_requerido)
			throw new B2BException(
			    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " no debe estar vacio."
			);

		return ld_date;
	}

	/**
	 * Retorna el valor del objeto de Double.
	 *
	 * @param ar_registro correspondiente al valor del tipo de objeto Row
	 * @param ai_fila correspondiente al valor del tipo de objeto int
	 * @param ai_celda correspondiente al valor del tipo de objeto int
	 * @param as_nombreCelda correspondiente al valor del tipo de objeto String
	 * @param ab_requerido correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Double
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public static Double validarDoubleCeldaExcel(
	    Row ar_registro, int ai_fila, int ai_celda, String as_nombreCelda, boolean ab_requerido
	)
	    throws B2BException
	{
		return validarDoubleCeldaExcel(ar_registro, ai_fila, ai_celda, as_nombreCelda, ab_requerido, false);
	}

	/**
	 * Retorna el valor del objeto de Double.
	 *
	 * @param ar_registro correspondiente al valor del tipo de objeto Row
	 * @param ai_fila correspondiente al valor del tipo de objeto int
	 * @param ai_celda correspondiente al valor del tipo de objeto int
	 * @param as_nombreCelda correspondiente al valor del tipo de objeto String
	 * @param ab_requerido correspondiente al valor del tipo de objeto boolean
	 * @param ab_validValue correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Double
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public static Double validarDoubleCeldaExcel(
	    Row ar_registro, int ai_fila, int ai_celda, String as_nombreCelda, boolean ab_requerido, boolean ab_validValue
	)
	    throws B2BException
	{
		Cell   lc_tmp;
		Double ll_tmp;

		lc_tmp     = ar_registro.getCell(ai_celda);
		ll_tmp     = new Double(0);

		if(lc_tmp != null)
		{
			if(lc_tmp.getCellType() == Cell.CELL_TYPE_NUMERIC)
				ll_tmp = NumericUtils.getDoubleWrapper(lc_tmp.getNumericCellValue());
			else
				throw new B2BException(
				    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " debe ser numérico."
				);

			if(ll_tmp != null)
			{
				double ld_validValue;

				ld_validValue = ab_validValue ? 0.0D : 1D;

				if(!NumericUtils.isValidDouble(ll_tmp, ld_validValue))
					throw new B2BException(
					    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " debe ser numérico, mayor "
					    + (ab_validValue ? "o igual " : "") + "a cero (0) no negativo."
					);
			}
			else if(ab_requerido)
				throw new B2BException(
				    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " no debe estar vacio."
				);
		}
		else if(ab_requerido)
			throw new B2BException(
			    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " no debe estar vacio."
			);

		return ll_tmp;
	}

	/**
	 * Retorna el valor del objeto de Long.
	 *
	 * @param ar_registro correspondiente al valor del tipo de objeto Row
	 * @param ai_fila correspondiente al valor del tipo de objeto int
	 * @param ai_celda correspondiente al valor del tipo de objeto int
	 * @param as_nombreCelda correspondiente al valor del tipo de objeto String
	 * @param ab_requerido correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Long
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see Long
	 */
	public static Long validarLongCeldaExcel(
	    Row ar_registro, int ai_fila, int ai_celda, String as_nombreCelda, boolean ab_requerido
	)
	    throws B2BException
	{
		return validarLongCeldaExcel(ar_registro, ai_fila, ai_celda, 1L, as_nombreCelda, ab_requerido);
	}

	/**
	 * Retorna el valor del objeto de Long.
	 *
	 * @param ar_registro correspondiente al valor del tipo de objeto Row
	 * @param ai_fila correspondiente al valor del tipo de objeto int
	 * @param ai_celda correspondiente al valor del tipo de objeto int
	 * @param al_valorAValidar correspondiente al valor a validar del tipo de objeto int
	 * @param as_nombreCelda correspondiente al valor del tipo de objeto String
	 * @param ab_requerido correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Long
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see Long
	 */
	public static Long validarLongCeldaExcel(
	    Row ar_registro, int ai_fila, int ai_celda, long al_valorAValidar, String as_nombreCelda, boolean ab_requerido
	)
	    throws B2BException
	{
		Cell lc_tmp;
		Long ll_tmp;

		lc_tmp     = ar_registro.getCell(ai_celda);
		ll_tmp     = NumericUtils.getLongWrapper(0L);

		if(lc_tmp != null)
		{
			if(lc_tmp.getCellType() == Cell.CELL_TYPE_NUMERIC)
				ll_tmp = NumericUtils.getLongWrapper(lc_tmp.getNumericCellValue());
			else if(lc_tmp.getCellType() == Cell.CELL_TYPE_STRING)
				ll_tmp = NumericUtils.getLongWrapper(lc_tmp.getStringCellValue());

			if(ll_tmp != null)
			{
				if(!NumericUtils.isValidLong(ll_tmp, al_valorAValidar))
				{
					Object[] aoa_messageArgs = new String[2];

					aoa_messageArgs[0]     = as_nombreCelda;
					aoa_messageArgs[1]     = StringUtils.getString(ai_fila);

					if(al_valorAValidar == 0L)
						throw new B2BException(ErrorKeys.ERROR_CAMPO_FILA_CERO_IGUAL, aoa_messageArgs);
					else
						throw new B2BException(ErrorKeys.ERROR_CAMPO_FILA_CERO, aoa_messageArgs);
				}
			}
			else if(ab_requerido)
			{
				Object[] aoa_messageArgs = new String[2];

				aoa_messageArgs[0]     = as_nombreCelda;
				aoa_messageArgs[1]     = StringUtils.getString(ai_fila);

				throw new B2BException(ErrorKeys.ERROR_CAMPO_FILA_VACIO, aoa_messageArgs);
			}
		}
		else if(ab_requerido)
		{
			Object[] aoa_messageArgs = new String[2];

			aoa_messageArgs[0]     = as_nombreCelda;
			aoa_messageArgs[1]     = StringUtils.getString(ai_fila);

			throw new B2BException(ErrorKeys.ERROR_CAMPO_FILA_VACIO, aoa_messageArgs);
		}

		return ll_tmp;
	}

	/**
	 * Retorna el valor del objeto de Double.
	 *
	 * @param ar_registro correspondiente al valor del tipo de objeto Row
	 * @param ai_fila correspondiente al valor del tipo de objeto int
	 * @param ai_celda correspondiente al valor del tipo de objeto int
	 * @param as_nombreCelda correspondiente al valor del tipo de objeto String
	 * @param ab_requerido correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Double
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see Double
	 */
	public static Double validarPorcentaje(
	    Row ar_registro, int ai_fila, int ai_celda, String as_nombreCelda, boolean ab_requerido
	)
	    throws B2BException
	{
		Cell   lc_tmp;
		Double ll_tmp;

		lc_tmp     = ar_registro.getCell(ai_celda);
		ll_tmp     = null;

		if(lc_tmp != null)
		{
			if(lc_tmp.getCellType() == Cell.CELL_TYPE_NUMERIC)
				ll_tmp = NumericUtils.getDoubleWrapper(lc_tmp.getNumericCellValue());
			else
				throw new B2BException(
				    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " debe ser numérico."
				);

			if(ll_tmp == null)
				throw new B2BException(
				    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila
				    + " debe ser numérico, mayor a cero (0)."
				);
			else if(ab_requerido)
				throw new B2BException(
				    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " no debe estar vacio."
				);

			if((NumericUtils.getDouble(ll_tmp) == 0.0) || (NumericUtils.getDouble(ll_tmp) > 100))
				throw new B2BException("El valor del campo  " + as_nombreCelda + "  debe estar entre 0 y 100.");
		}
		else if(ab_requerido)
			throw new B2BException(
			    "El campo " + as_nombreCelda + " en la fila Nº " + ai_fila + " no debe estar vacio."
			);

		return ll_tmp;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param ar_registro correspondiente al valor del tipo de objeto Row
	 * @param ai_fila correspondiente al valor del tipo de objeto int
	 * @param ai_celda correspondiente al valor del tipo de objeto int
	 * @param as_nombreCelda correspondiente al valor del tipo de objeto String
	 * @param ab_requerido correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de String
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public static String validarStringCeldaExcel(
	    Row ar_registro, int ai_fila, int ai_celda, String as_nombreCelda, boolean ab_requerido
	)
	    throws B2BException
	{
		Cell   lc_tmp;
		String ls_tmp;

		lc_tmp     = ar_registro.getCell(ai_celda);
		ls_tmp     = null;

		if(lc_tmp != null)
		{
			if(lc_tmp.getCellType() == Cell.CELL_TYPE_NUMERIC)
				ls_tmp = StringUtils.getString(lc_tmp.getNumericCellValue());
			else if(lc_tmp.getCellType() == Cell.CELL_TYPE_STRING)
				ls_tmp = StringUtils.getString(lc_tmp.getStringCellValue());

			if(!StringUtils.isValidString(ls_tmp) && ab_requerido)
			{
				Object[] aoa_messageArgs = new String[2];

				aoa_messageArgs[0]     = as_nombreCelda;
				aoa_messageArgs[1]     = StringUtils.getString(ai_fila);

				throw new B2BException(ErrorKeys.ERROR_CAMPO_FILA_VACIO, aoa_messageArgs);
			}
		}
		else if(ab_requerido)
		{
			Object[] aoa_messageArgs = new String[2];

			aoa_messageArgs[0]     = as_nombreCelda;
			aoa_messageArgs[1]     = StringUtils.getString(ai_fila);

			throw new B2BException(ErrorKeys.ERROR_CAMPO_FILA_VACIO, aoa_messageArgs);
		}

		return ls_tmp;
	}

	/**
	 * Write.
	 *
	 * @param aw_workbook correspondiente al valor del tipo de objeto Workbook
	 * @param aos_excel correspondiente al valor del tipo de objeto OutputStream
	 * @throws IOException Señala que se ha producido una excepción de E / S.
	 */
	public static void write(Workbook aw_workbook, OutputStream aos_excel)
	    throws IOException
	{
		if((aw_workbook != null) && (aos_excel != null))
		{
			aw_workbook.write(aos_excel);

			aos_excel.flush();
			aos_excel.close();
		}
	}

	/**
	 * Auto ajustar tamano columnas.
	 *
	 * @param aw_libroTrabajo de aw libro trabajo
	 */
	public static void autoAjustarTamanoColumnas(Workbook aw_libroTrabajo)
	{
		if(aw_libroTrabajo != null)
		{
			int li_numeroPaginas;

			li_numeroPaginas = aw_libroTrabajo.getNumberOfSheets();

			for(int i = 0; i < li_numeroPaginas; i++)
			{
				Sheet ls_pagina;

				ls_pagina = aw_libroTrabajo.getSheetAt(i);

				if((ls_pagina != null) && (ls_pagina.getPhysicalNumberOfRows() > 0))
				{
					Row lr_fila;

					lr_fila = ls_pagina.getRow(ls_pagina.getFirstRowNum());

					if(lr_fila != null)
					{
						Iterator<Cell> lic_iteradorCelda;

						lic_iteradorCelda = lr_fila.cellIterator();

						while(lic_iteradorCelda.hasNext())
						{
							Cell lc_celda;
							int  li_indexColumna;

							lc_celda            = lic_iteradorCelda.next();
							li_indexColumna     = lc_celda.getColumnIndex();

							ls_pagina.autoSizeColumn(li_indexColumna);
						}
					}
				}
			}
		}
	}
}
