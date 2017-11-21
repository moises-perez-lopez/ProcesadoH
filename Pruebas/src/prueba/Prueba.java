
package prueba;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.IS2.Nikola.UtilidadesTexto;
import org.apache.commons.lang3.text.StrBuilder;

public class Prueba {
	public final static String sNeid = "NEID";
	public final static String sCellIndex = "CELLID";
	public final static String sCellId = "CELLID";
	public final static String sCellName = "CELLNAME";
	public final static String sFecha = "Fecha";
	public final static String sEXT2GCellIndex = "EXT2GCELLID";
	public final static String sBTSId = "BTSID";
	public final static String sBTSNAME = "BTSNAME";
	public final static String sDeviceNo = "DEVICENO";
	public final static String sDeviceName = "DEVICENAME";
	public final static String sCellIndexSource = "SRC2GNCELLID";
	public final static String sCellIndexNeigh = "NBR2GNCELLID";
	public final static String sCELL2GBA1BCCH = "CELL2GBA1BCCH";
	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> mapaGCELLCellIdParametroValor = new TreeMap<String, TreeMap<Integer, TreeMap<String, String>>>();
	private static TreeMap<String, TreeMap<String, TreeMap<String, String>>> mapaGSMBTS = new TreeMap<String, TreeMap<String, TreeMap<String, String>>>();
	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> mapaGSMBTSRET = new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>>();

	public static void main(String[] args) {

		String sCarpetaEntradaT = "C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_2G_PRUEBA_BD\\";
		String sCarpetaSalidaT = "C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_2G_TXT\\";

		String sCarpetaEntradaCasa = "C:\\Users\\antonio\\Desktop\\IS2\\murdoc\\HuaweiParser\\SALIDA_2G\\";
		String sCarpetaSalidaCasa = "C:\\Users\\antonio\\Desktop\\IS2\\murdoc\\HuaweiParser\\SALIDA_2G_FICHEROS_SALIDA\\";

		// Creo mapa con fichero GCELL
		File ficheroGCELL = new File(sCarpetaEntradaT + "GCELL.txt");
		creaMapaGGCELL(ficheroGCELL);

		// Concateno el valor de BCCH a los registros del mapa GCELL
		File ficheroGCELLFreq = new File(sCarpetaEntradaT + "GCELLFREQ.txt");
		creaArbolGCELLconBCCH(ficheroGCELLFreq);

		// Añadimos al mapa GCELL nuevos registros desde el fichero GEXT2GCELL
		File ficheroGEXT2GCELL = new File(sCarpetaEntradaT + "GEXT2GCELL.txt");
		creaArbolGCELL_GEXT2GCELL(ficheroGEXT2GCELL);

		// Creamos el fichero GCELL con el arbol GCELL FINAL
		File ficheroGSM_GCELLFINAL = new File(sCarpetaSalidaT + "Arbol_GCELL_FINAL.txt");
		escribeArbolGCELLFINAL(ficheroGSM_GCELLFINAL);

		// Creamos el fichero GSM_BTS
		File ficheroGSM_BTS = new File(sCarpetaEntradaT + "BTS.txt");
		File ficheroArbolGSM_BTS = new File(sCarpetaSalidaT + "Arbol_BTS.txt");
		File ficheroSalida_GSM_BTS = new File(sCarpetaSalidaT + "BTS.txt");
		creaArbolGSMBTS(ficheroGSM_BTS);
		escribeArbolBTS(ficheroArbolGSM_BTS);
		escribeFicheroBTS(ficheroGSM_BTS, ficheroSalida_GSM_BTS);
		borraFichero(ficheroGSM_BTS);

		// Creamos el fichero GSM_BTSRET
		File ficheroGSM_BTSRET = new File(sCarpetaEntradaT + "BTSRET.txt");
		File ficheroSalida_GSM_BTSRET = new File(sCarpetaSalidaT + "BTSRET.txt");
		creaArbolGSM_BTSRET(ficheroGSM_BTSRET);
		escribeFicheroGSM_BTSRET(ficheroSalida_GSM_BTSRET);
		borraFichero(ficheroGSM_BTSRET);

		// Creamos el fichero GSM_BTSRETDEVICEDATA
		File ficheroGSM_BTSRETDEVICEDATA = new File(sCarpetaEntradaT + "BTSRETDEVICEDATA.txt");
		File ficheroSalida_GSM_BTSRETDEVICEDATA = new File(sCarpetaSalidaT + "BTSRETDEVICEDATA.txt");
		escribeFicheroGSM_BTSRETDEVICEDATA(crearArbolGSM_BTSRETDEVICEDATA(ficheroGSM_BTSRETDEVICEDATA),
				ficheroSalida_GSM_BTSRETDEVICEDATA);
		borraFichero(ficheroGSM_BTSRETDEVICEDATA);

		// Creamos el fichero GSM_BTSRETSUBUNIT
		File ficheroGSM_BTSRETSUBUNIT = new File(sCarpetaEntradaT + "BTSRETSUBUNIT.txt");
		File ficheroSalida_GSM_BTSRETSUBUNIT = new File(sCarpetaSalidaT + "BTSRETSUBUNIT.txt");
		escribeFicheroGSM_BTSRETSUBUNIT(crearArbolGSM_BTSRETSUBUNIT(ficheroGSM_BTSRETSUBUNIT),
				ficheroSalida_GSM_BTSRETSUBUNIT);
		borraFichero(ficheroGSM_BTSRETSUBUNIT);

		// Creamos el fichero G2GNCELL
		// File ficheroGSM_G2GNCELL = new File(sCarpetaEntradaT+"G2GNCELL.txt");
		// File ficheroSalida_GSM_G2GNCELL = new
		// File(sCarpetaSalidaT+"G2GNCELL.txt");
		// escribeFicheroGSM_G2GNCELL(ficheroGSM_G2GNCELL,ficheroSalida_GSM_G2GNCELL);
		// borraFichero(ficheroGSM_G2GNCELL);

		// Creamos el fichero GCELL2GBA1
		File ficheroGSM_GCELL2GBA1 = new File(sCarpetaEntradaT + "GCELL2GBA1.txt");
		File ficheroSalida_GSM_GCELL2GBA1 = new File(sCarpetaSalidaT + "GCELL2GBA1.txt");
		escribeFicheroGSM(ficheroGSM_GCELL2GBA1, ficheroSalida_GSM_GCELL2GBA1, retornaParametrosABuscarGCELL2GBA1(),
				retornaParametrosCabeceraGSM_GCELL2GBA1());

		// Creamos el fichero GCELLAMRQUL
		File ficheroGSM_GCELLAMRQUL = new File(sCarpetaEntradaT + "GCELLAMRQUL.txt");
		File ficheroSalida_GSM_GCELLAMRQUL = new File(sCarpetaSalidaT + "GCELLAMRQUL.txt");
		escribeFicheroGSM(ficheroGSM_GCELLAMRQUL, ficheroSalida_GSM_GCELLAMRQUL, retornaParametrosABuscarGCELLAMRQUL(),
				retornaParametrosCabeceraGSM_GCELLAMRQUL());

		// Creamos el fichero GCELLBASICPARA
		File ficheroGSM_GCELLBASICPARA = new File(sCarpetaEntradaT + "GCELLBASICPARA.txt");
		File ficheroSalida_GSM_GCELLBASICPARA = new File(sCarpetaSalidaT + "GCELLBASICPARA.txt");
		escribeFicheroGSM(ficheroGSM_GCELLBASICPARA, ficheroSalida_GSM_GCELLBASICPARA,
				retornaParametrosABuscarGCELLBASICPARA(), retornaParametrosCabeceraGSM_GCELLBASICPARA());

		// Creamos el fichero GCELLCCAD
		File ficheroGSM_GCELLCCAD = new File(sCarpetaEntradaT + "GCELLCCAD.txt");
		File ficheroSalida_GSM_GCELLCCAD = new File(sCarpetaSalidaT + "GCELLCCAD.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCAD, ficheroSalida_GSM_GCELLCCAD, retornaParametrosABuscarGCELLCCAD(),
				retornaParametrosCabeceraGSM_GCELLCCAD());

		// Creamos el fichero GCELLCCBASIC
		File ficheroGSM_GCELLCCBASIC = new File(sCarpetaEntradaT + "GCELLCCBASIC.txt");
		File ficheroSalida_GSM_GCELLCCBASIC = new File(sCarpetaSalidaT + "GCELLCCBASIC.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCBASIC, ficheroSalida_GSM_GCELLCCBASIC,
				retornaParametrosABuscarGCELLCCBASIC(), retornaParametrosCabeceraGSM_GCELLCCBASIC());

		// Creamos el fichero GCELLCCCH
		File ficheroGSM_GCELLCCCH = new File(sCarpetaEntradaT + "GCELLCCCH.txt");
		File ficheroSalida_GSM_GCELLCCCH = new File(sCarpetaSalidaT + "GCELLCCCH.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCCH, ficheroSalida_GSM_GCELLCCCH, retornaParametrosABuscarGCELLCCCH(),
				retornaParametrosCabeceraGSM_GCELLCCCH());

		// Creamos el fichero GCELLCCUTRANSYS
		File ficheroGSM_GCELLCCUTRANSYS = new File(sCarpetaEntradaT + "GCELLCCUTRANSYS.txt");
		File ficheroSalida_GSM_GCELLCCUTRANSYS = new File(sCarpetaSalidaT + "GCELLCCUTRANSYS.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCUTRANSYS, ficheroSalida_GSM_GCELLCCUTRANSYS,
				retornaParametrosABuscarGCELLCCUTRANSYS(), retornaParametrosCabeceraGSM_GCELLCCUTRANSYS());

		// Creamos el fichero GCELLCHMGAD
		File ficheroGSM_GCELLCHMGAD = new File(sCarpetaEntradaT + "GCELLCHMGAD.txt");
		File ficheroSalida_GSM_GCELLCHMGAD = new File(sCarpetaSalidaT + "GCELLCHMGAD.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCHMGAD, ficheroSalida_GSM_GCELLCHMGAD, retornaParametrosABuscarGCELLCHMGAD(),
				retornaParametrosCabeceraGSM_GCELLCHMGAD());

		// Creamos el fichero GCELLCHMGBASIC
		File ficheroGSM_GCELLCHMGBASIC = new File(sCarpetaEntradaT + "GCELLCHMGBASIC.txt");
		File ficheroSalida_GSM_GCELLCHMGBASIC = new File(sCarpetaSalidaT + "GCELLCHMGBASIC.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCHMGBASIC, ficheroSalida_GSM_GCELLCHMGBASIC,
				retornaParametrosABuscarGCELLCHMGBASIC(), retornaParametrosCabeceraGSM_GCELLCHMGBASIC());

		// Creamos el fichero GCELLFREQ
		File ficheroGSM_GCELLFREQ = new File(sCarpetaEntradaT + "GCELLFREQ.txt");
		File ficheorSalida_GSM_GCELLFREQ = new File(sCarpetaSalidaT + "GCELLFREQ.txt");
		escribeFicheroGSM(ficheroGSM_GCELLFREQ, ficheorSalida_GSM_GCELLFREQ, retornaParametrosABuscarGCELLFREQ(),
				retornaParametrosCabeceraGSM_GCELLFREQ());

		// Creamos el fichero GCELLGPRS
		File ficheroGSM_GCELLGPRS = new File(sCarpetaEntradaT + "GCELLGPRS.txt");
		File ficheroSalida_GSM_GCELLGPRS = new File(sCarpetaSalidaT + "GCELLGPRS.txt");
		escribeFicheroGSM(ficheroGSM_GCELLGPRS, ficheroSalida_GSM_GCELLGPRS, retornaParametrosABuscarGCELLGPRS(),
				retornaParametrosCabeceraGSM_GCELLGPRS());

		// Creamos el fichero GCELLHO2GBA2
		File ficheroGSM_GCELLHO2GBA2 = new File(sCarpetaEntradaT + "GCELLHO2GBA2.txt");
		File ficheroSalida_GSM_GCELLHO2GBA2 = new File(sCarpetaSalidaT + "GCELLHO2GBA2.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHO2GBA2, ficheroSalida_GSM_GCELLHO2GBA2,
				retornaParametrosABuscarGCELLHO2GBA2(), retornaParametrosCabeceraGSM_GCELLHO2GBA2());

		// Creamos el fichero GCELLHOAD
		File ficheroGSM_GCELLHOAD = new File(sCarpetaEntradaT + "GCELLHOAD.txt");
		File ficheroSalida_GSM_GCELLHOAD = new File(sCarpetaSalidaT + "GCELLHOAD.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOAD, ficheroSalida_GSM_GCELLHOAD, retornaParametrosABuscarGCELLHOAD(),
				retornaParametrosCabeceraGSM_GCELLHOAD());

		// Creamos el fichero GCELLHOBASIC
		File ficheroGSM_GCELLHOBASIC = new File(sCarpetaEntradaT + "GCELLHOBASIC.txt");
		File ficheroSalida_GSM_GCELLHOBASIC = new File(sCarpetaSalidaT + "GCELLHOBASIC.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOBASIC, ficheroSalida_GSM_GCELLHOBASIC,
				retornaParametrosABuscarGCELLHOBASIC(), retornaParametrosCabeceraGSM_GCELLHOBASIC());

		// Creamos el fichero GCELLHOCTRL
		File ficheroGSM_GCELLHOCTRL = new File(sCarpetaEntradaT + "GCELLHOCTRL.txt");
		File ficheroSalida_GSM_GCELLHOCTRL = new File(sCarpetaSalidaT + "GCELLHOCTRL.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOCTRL, ficheroSalida_GSM_GCELLHOCTRL, retornaParametrosABuscarGCELLHOCTRL(),
				retornaParametrosCabeceraGSM_GCCELHOCTRL());

		// Creamos el fichero GCELLHOEMG
		File ficheroGSM_GCELLHOEMG = new File(sCarpetaEntradaT + "GCELLHOEMG.txt");
		File ficheroSalida_GSM_GCELLHOEMG = new File(sCarpetaSalidaT + "GCELLHOEMG.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOEMG, ficheroSalida_GSM_GCELLHOEMG, retornaParametrosABuscarGCELLHOEMG(),
				retornaParametrosCabeceraGSM_GCELLHOEMG());

		// Creamos el fichero GCELLHOFITPEN
		File ficheroGSM_GCELLHOFITPEN = new File(sCarpetaEntradaT + "GCELLHOFITPEN.txt");
		File ficheroSalida_GSM_GCELLHOFITPEN = new File(sCarpetaSalidaT + "GCELLHOFITPEN.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOFITPEN, ficheroSalida_GSM_GCELLHOFITPEN,
				retornaParametrosABuscarGCELLHOFITPEN(), retornaParametrosCabeceraGSM_GCELLHOFITPEN());

		// Creamos el fichero GCELLHOUTRANFDD
		File ficheroGSM_GCELLHOUTRANFDD = new File(sCarpetaEntradaT + "GCELLHOUTRANFDD.txt");
		File ficheroSalida_GSM_GCELLHOUTRANFDD = new File(sCarpetaSalidaT + "GCELLHOUTRANFDD.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOUTRANFDD, ficheroSalida_GSM_GCELLHOUTRANFDD,
				retornaParametrosABuscarGCELLHOUTRANFDD(), retornaParametrosCabeceraGSM_GCELLHOUTRANFDD());

		// Creamos el fichero GCELLIDLEAD
		File ficheroGSM_GCELLIDLEAD = new File(sCarpetaEntradaT + "GCELLIDLEAD.txt");
		File ficheroSalida_GSM_GCELLIDLEAD = new File(sCarpetaSalidaT + "GCELLIDLEAD.txt");
		escribeFicheroGSM(ficheroGSM_GCELLIDLEAD, ficheroSalida_GSM_GCELLIDLEAD, retornaParametrosABuscarGCELLIDLEAD(),
				retornaParametrosCabeceraGSM_GCELLIDLEAD());

		// Creamos el fichero GCELLIDLEADBASIC
		File ficheroGSM_GCELLIDLEADBASIC = new File(sCarpetaEntradaT + "GCELLIDLEBASIC.txt");
		File ficheroSalida_GSM_GCELLIDLEADBASIC = new File(sCarpetaSalidaT + "GCELLIDLEBASIC.txt");
		escribeFicheroGSM(ficheroGSM_GCELLIDLEADBASIC, ficheroSalida_GSM_GCELLIDLEADBASIC,
				retornaParametrosABuscarGCELLIDLEADBASIC(), retornaParametrosCabeceraGSM_GCELLIDLEADBASIC());

		// Creamos el fichero GCELLMAGRP
		File ficheroGSM_GCELLMAGRP = new File(sCarpetaEntradaT + "GCELLMAGRP.txt");
		File ficheroSalida_GSM_GCELLMAGRP = new File(sCarpetaSalidaT + "GCELLMAGRP.txt");
		escribeFicheroGSM(ficheroGSM_GCELLMAGRP, ficheroSalida_GSM_GCELLMAGRP, retornaParametrosABuscarGCELLMAGRP(),
				retornaParametrosCabeceraGSM_GCELLMAGRP());
		
		// Creamos el fichero GCELLOTHEXT 
		File ficheroGSM_GCELLOTHEXT = new File(sCarpetaEntradaT + "GCELLOTHEXT.txt");
		File ficheroSalida_GSM_GCELLOTHEXT = new File(sCarpetaSalidaT + "GCELLOTHEXT.txt");
		escribeFicheroGSM(ficheroGSM_GCELLOTHEXT,ficheroSalida_GSM_GCELLOTHEXT,retornaParametrosABuscarGCELLOTHEXT(),
				retornaParametrosCabeceraGSM_GCELLOTHEXT());
		
		// Creamos el fichero GCELLPRIEUTRANSYS
		File ficheroGSM_GCELLPRIEUTRANSYS = new File(sCarpetaEntradaT + "GCELLPRIEUTRANSYS.txt");
		File ficheroSalida_GSM_GCELLPRIEUTRANSYS = new File(sCarpetaSalidaT + "GCELLPRIEUTRANSYS.txt");
		escribeFicheroGSM(ficheroGSM_GCELLPRIEUTRANSYS,ficheroSalida_GSM_GCELLPRIEUTRANSYS,retornaParametrosABuscarGCELLPRIEUTRANSYS(),
				retornaParametrosCabeceraGCELLPRIEUTRANSYS());
		
		// Creamos el fichero GCELLPSBASE
		File ficheroGSM_GCELLPSBASE = new File(sCarpetaEntradaT + "GCELLPSBASE.txt");
		File ficheroSalida_GSM_GCELLPSBASE = new File(sCarpetaSalidaT + "GCELLPSBASE.txt");
		escribeFicheroGSM(ficheroGSM_GCELLPSBASE,ficheroSalida_GSM_GCELLPSBASE,retornaParametrosABuscarGCELLPSBASE(),
				retornaParametrosCabeceraGCELLPSBASE());
		System.out.println("FIN");
	}



	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> creaMapaGGCELL(File ficheroGCELL) {
		TreeMap<String, String> arbolParametrosGCELL = retornaParametrosABuscarGCELL();
		try (FileReader fr = new FileReader(ficheroGCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])) {

					mapaGCELLCellIdParametroValor.put(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)],
							new TreeMap<Integer, TreeMap<String, String>>());
				}
				if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])
						.containsKey(UtilidadesTexto
								.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]))) {

					mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])
							.put(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]),
									new TreeMap<String, String>());
				}

				for (String sParametro : arbolParametrosGCELL.keySet()) {
					if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])
							.get(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]))
							.containsKey(arbolParametrosGCELL.get(sParametro))) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])
								.get(UtilidadesTexto
										.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]))
								.put(arbolParametrosGCELL.get(sParametro),
										aValoresParametros[mapaCabeceraFicheroGCELL.get(sParametro)]);

					}
				}

				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception MapaGCELL");
		} catch (IOException e) {
			System.out.println("IOException MapaGCELL");
		}
		return mapaGCELLCellIdParametroValor;
	}

	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> creaArbolGCELLconBCCH(
			File ficheroGCELLFreq) {
		try (FileReader fr = new FileReader(ficheroGCELLFreq); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroCELLFreq = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				String sBSC = aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sNeid)];
				for (String sNeid : mapaGCELLCellIdParametroValor.keySet()) {
					if (sNeid.equalsIgnoreCase(sBSC)) {
						for (Integer iCellIndex : mapaGCELLCellIdParametroValor.get(sNeid).keySet()) {
							if (iCellIndex == UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sCellId)])) {
								if (mapaGCELLCellIdParametroValor.get(sNeid).get(iCellIndex).get(sFecha)
										.equalsIgnoreCase(
												aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sFecha)])) {
									mapaGCELLCellIdParametroValor.get(sNeid).get(iCellIndex).put("BCCH",
											aValoresParametros[mapaCabeceraFicheroCELLFreq.get("FREQ1")]);
								}
							}
						}
					}
				}
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception GCELL con BCCH");
		} catch (IOException e) {
			System.out.println("IO Exception GCELL con BCCH");
		}
		return mapaGCELLCellIdParametroValor;
	}

	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> creaArbolGCELL_GEXT2GCELL(
			File ficheroGEXT2GCELL) {
		TreeMap<String, String> arbolParametrosGEXT2GCELL = retornaParametrosABuscarGEXT2GCELL();
		try (FileReader fr = new FileReader(ficheroGEXT2GCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGEXT2GCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sNeid)])) {
					mapaGCELLCellIdParametroValor.put(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sNeid)],
							new TreeMap<Integer, TreeMap<String, String>>());
				}
				if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(
								aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sEXT2GCellIndex)]))) {
					mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sNeid)])
							.put(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sEXT2GCellIndex)]),
									new TreeMap<String, String>());
				}

				for (String sParametro : arbolParametrosGEXT2GCELL.keySet()) {
					if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sEXT2GCellIndex)]))
							.containsKey(arbolParametrosGEXT2GCELL.get(sParametro))) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sEXT2GCellIndex)]))
								.put(arbolParametrosGEXT2GCELL.get(sParametro),
										aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sParametro)]);
					}
				}

				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception GCELL_GEXT2GCELL");
		} catch (IOException e) {
			System.out.println("IOException GCELL_GEXT2GCELL");
		}
		return mapaGCELLCellIdParametroValor;
	}

	private static TreeMap<String, TreeMap<String, TreeMap<String, String>>> creaArbolGSMBTS(File ficheroGSM_BTS) {
		try (FileReader fr = new FileReader(ficheroGSM_BTS); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMBTS = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)])) {
					mapaGSMBTS.put(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)],
							new TreeMap<String, TreeMap<String, String>>());
				}
				if (!mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])) {
					mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)]).put(
							aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)], new TreeMap<String, String>());
				}
				if (!mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSNAME)])) {
					mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSNAME)],
									aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sFecha)]);
				}
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not found creaArbolGSMBTS");
		} catch (IOException e) {
			System.out.println("IO Exception creaArbolGSMBTS");
		} catch (Exception e) {
			System.out.println("Otra excepción creaArbolGSMBTS");
		}
		return mapaGSMBTS;
	}

	private static void escribeFicheroBTS(File ficheroGSM_BTS, File ficheroSalida_GSM_BTS) {
		try (FileReader fr = new FileReader(ficheroGSM_BTS);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_BTS))) {
			StrBuilder creadorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_BTS()) {
				creadorCabecera.append("\"" + sParametro + "\"");
				creadorCabecera.appendSeparator(",");
			}
			bw.write(creadorCabecera.toString().substring(0, creadorCabecera.toString().length() - 1) + "\r\n");

			br.readLine();
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				StrBuilder creadorLineaValores = new StrBuilder();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, "\t");
				for (String sValorParametro : aValoresParametros) {
					creadorLineaValores.append(sValorParametro);
					creadorLineaValores.appendSeparator(",");

				}
				bw.write(creadorLineaValores.toString().substring(0, creadorLineaValores.toString().length() - 1)
						+ "\r\n");
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception escribeFicheroBTS");
		} catch (IOException e) {
			System.out.println("IOException escribeFicheroBTS");
		} catch (Exception e) {
			System.out.println("Otra excepción escribeFicheroBTS");
		}
	}

	private static void escribeArbolBTS(File ficheroArbolGSM_BTS) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbolGSM_BTS))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"BTSID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"Fecha\"" + "\r\n");
			for (String sNeid : mapaGSMBTS.keySet()) {
				for (String sBTSid : mapaGSMBTS.get(sNeid).keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(sBTSid);
					compositorValores.appendSeparator(",");
					compositorValores.append(mapaGSMBTS.get(sNeid).get(sBTSid) + "\r\n");
				}
			}
		} catch (IOException e) {
			System.out.println("IOException");
		}

	}

	private static void escribeArbolGCELLFINAL(File ficheroGSM_GCELLFINAL) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroGSM_GCELLFINAL))) {
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGCELL()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			for (String sNeid : mapaGCELLCellIdParametroValor.keySet()) {
				for (Integer iCellId : mapaGCELLCellIdParametroValor.get(sNeid).keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(iCellId);
					compositorValores.appendSeparator(",");
					for (String sParametro : retornaParametrosCabeceraGCELL()) {
						if ((!sParametro.equalsIgnoreCase("BSC")) && (!sParametro.equalsIgnoreCase("CELLINDEX"))) {
							compositorValores
									.append(mapaGCELLCellIdParametroValor.get(sNeid).get(iCellId).get(sParametro));
							compositorValores.appendSeparator(",");
						}
					}
					bw.write(compositorValores.toString().substring(0, compositorValores.size() - 1) + "\r\n");
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception GCELLFINAL");
		} catch (IOException e) {
			System.out.println("IOException GCELLFINAL");
		} catch (Exception e) {
			System.out.println("Otra excepción GCELLFINAL");
		}
	}

	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> creaArbolGSM_BTSRET(
			File ficheroGSM_BTSRET) {
		String[] aParametrosGSM_BTSRET = retornaParametrosABuscarGSMBTSRET();
		String sNombreBTS = "---";
		try (FileReader fr = new FileReader(ficheroGSM_BTSRET); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMBTSRET = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");

				if ((mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)]))
						&& (mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]))) {
					sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).firstKey();
				}

				if (!mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])) {
					mapaGSMBTSRET.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)],
							new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)]).put(
							aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)],
							new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).containsKey(sNombreBTS)) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
							.put(sNombreBTS, new TreeMap<String, TreeMap<String, TreeMap<String, String>>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).get(sNombreBTS)
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)],
									new TreeMap<String, TreeMap<String, String>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceName)])) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceName)],
									new TreeMap<String, String>());
				}
				for (String sParametro : aParametrosGSM_BTSRET) {
					if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceName)])
							.containsKey(sParametro)) {
						mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).get(sNombreBTS)
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceName)])
								.put(sParametro, aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sParametro)]);
					}
				}

				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception creaArbolGSM_BTSRET");
		} catch (IOException e) {
			System.out.println("IOException creaArbolGSM_BTSRET");
		} catch (Exception e) {
			System.out.println("Otra excepción creaArbolGSM_BTSRET");
		}
		return mapaGSMBTSRET;

	}

	private static void escribeFicheroGSM_BTSRET(File ficheroSalida_GSM_BTSRET) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_BTSRET))) {
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_BTSRET()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			for (String sNeid : mapaGSMBTSRET.keySet()) {
				for (String sBTSId : mapaGSMBTSRET.get(sNeid).keySet()) {
					for (String sBTSName : mapaGSMBTSRET.get(sNeid).get(sBTSId).keySet()) {
						for (String sDeviceNo : mapaGSMBTSRET.get(sNeid).get(sBTSId).get(sBTSName).keySet()) {
							for (String sDeviceName : mapaGSMBTSRET.get(sNeid).get(sBTSId).get(sBTSName).get(sDeviceNo)
									.keySet()) {
								StrBuilder compositorValores = new StrBuilder();
								compositorValores.append(sNeid);
								compositorValores.appendSeparator(",");
								compositorValores.append(sBTSId);
								compositorValores.appendSeparator(",");
								compositorValores.append(sBTSName);
								compositorValores.appendSeparator(",");
								compositorValores.append(sDeviceNo);
								compositorValores.appendSeparator(",");
								compositorValores.append(sDeviceName);
								compositorValores.appendSeparator(",");
								for (String sParametro : mapaGSMBTSRET.get(sNeid).get(sBTSId).get(sBTSName)
										.get(sDeviceNo).get(sDeviceName).keySet()) {
									compositorValores.append(mapaGSMBTSRET.get(sNeid).get(sBTSId).get(sBTSName)
											.get(sDeviceNo).get(sDeviceName).get(sParametro));
									compositorValores.appendSeparator(",");
								}
								bw.write(compositorValores.toString().substring(0, compositorValores.size() - 1)
										+ "\r\n");
							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception escribeFicheroGSM_BTSRET");
		} catch (IOException e) {
			System.out.println("IOException escribeFicheroGSM_BTSRET");
		} catch (Exception e) {
			System.out.println("Otra excepción escribeFicheroGSM_BTSRET");
		}

	}

	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> crearArbolGSM_BTSRETDEVICEDATA(
			File ficheroGSM_BTSRETDEVICEDATA) {
		TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> arbolBTSDEVICEDATA = new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>>();
		String[] aParametrosGSM_BTSRETDEVICEDATA = retornaParametrosABuscarGSMRETDEVICEDATA();
		String sNombreBTS = "---";
		String sDeviceName = "---";
		try (FileReader fr = new FileReader(ficheroGSM_BTSRETDEVICEDATA); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMBTSRETDEVICEDATA = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if ((mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)]))
						&& (mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]))) {

					sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).firstKey();

				}
				if (!arbolBTSDEVICEDATA
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])) {
					arbolBTSDEVICEDATA.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)],
							new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>());
				}
				if (!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])) {

					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)]).put(
							aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)],
							new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>());
				}
				if (!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
						.containsKey(sNombreBTS)) {

					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
							.put(sNombreBTS, new TreeMap<String, TreeMap<String, TreeMap<String, String>>>());
				}

				if ((mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)]))
						&& (mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]))
						&& (mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.containsKey(sNombreBTS))
						&& (mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.get(sNombreBTS).containsKey(
										aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)]))) {

					sDeviceName = mapaGSMBTSRET
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)]).firstKey();
				}

				if (!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])) {

					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).get(sNombreBTS)
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)],
									new TreeMap<String, TreeMap<String, String>>());

				}

				if (!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
						.containsKey(sDeviceName)) {

					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
							.put(sDeviceName, new TreeMap<String, String>());

				}
				for (String sParametro : aParametrosGSM_BTSRETDEVICEDATA) {
					if (!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
							.get(sDeviceName).containsKey(sParametro)) {
						arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.get(sNombreBTS)
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
								.get(sDeviceName).put(sParametro,
										aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sParametro)]);
					}
				}
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception crearArbolGSM_BTSRETDEVICEDATA");
		} catch (IOException e) {
			System.out.println("IOException crearArbolGSM_BTSRETDEVICEDATA");
		} catch (Exception e) {
			System.out.println("Otra excepción crearArbolGSM_BTSRETDEVICEDATA");
		}
		return arbolBTSDEVICEDATA;
	}

	private static void escribeFicheroGSM_BTSRETDEVICEDATA(
			TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> arbolGSMBTSRETDEVICE,
			File ficheroSalida_GSM_BRSRETDEVICEDATA) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_BRSRETDEVICEDATA))) {
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_BTSRETDEVICEDATA()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			for (String sNeid : arbolGSMBTSRETDEVICE.keySet()) {
				for (String sBTSId : arbolGSMBTSRETDEVICE.get(sNeid).keySet()) {
					for (String sBTSName : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).keySet()) {
						for (String sDeviceNo : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName).keySet()) {
							for (String sDeviceName : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName)
									.get(sDeviceNo).keySet()) {
								StrBuilder compositorValores = new StrBuilder();
								compositorValores.append(sNeid);
								compositorValores.appendSeparator(",");
								compositorValores.append(sBTSId);
								compositorValores.appendSeparator(",");
								compositorValores.append(sBTSName);
								compositorValores.appendSeparator(",");
								compositorValores.append(sDeviceNo);
								compositorValores.appendSeparator(",");
								compositorValores.append(sDeviceName);
								compositorValores.appendSeparator(",");
								for (String sParametro : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName)
										.get(sDeviceNo).get(sDeviceName).keySet()) {

									compositorValores.append(arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName)
											.get(sDeviceNo).get(sDeviceName).get(sParametro));
									compositorValores.appendSeparator(",");
								}
								bw.write(compositorValores.toString().substring(0, compositorValores.size() - 1)
										+ "\r\n");

							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception escribeFicheroGSM_BTSRETDEVICEDATA");
		} catch (IOException e) {
			System.out.println("IOException escribeFicheroGSM_BTSRETDEVICEDATA");
		} catch (Exception e) {
			System.out.println("Otra excepción escribeFicheroGSM_BTSRETDEVICEDATA");
		}
	}

	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> crearArbolGSM_BTSRETSUBUNIT(
			File ficheroGSM_BTSRETSUBUNIT) {
		TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> arbolBTSRETSUBUNIT = new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>>();
		String[] aParametrosGSM_BTSRETSUBUNIT = retornaParametrosABuscarGSMRETSUBUNIT();
		String sNombreBTS = "---";
		String sDeviceName = "---";
		try (FileReader fr = new FileReader(ficheroGSM_BTSRETSUBUNIT); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMBTSRETSUBUNIT = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if ((mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)]))
						&& (mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]))) {

					sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).firstKey();

				}
				if (!arbolBTSRETSUBUNIT
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])) {
					arbolBTSRETSUBUNIT.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)],
							new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>());
				}
				if (!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])) {

					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)]).put(
							aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)],
							new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>());
				}
				if (!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
						.containsKey(sNombreBTS)) {

					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
							.put(sNombreBTS, new TreeMap<String, TreeMap<String, TreeMap<String, String>>>());
				}

				if ((mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)]))
						&& (mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]))
						&& (mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.containsKey(sNombreBTS))
						&& (mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.get(sNombreBTS)
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)]))) {

					sDeviceName = mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)]).firstKey();
				}

				if (!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])) {

					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).get(sNombreBTS)
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)],
									new TreeMap<String, TreeMap<String, String>>());

				}

				if (!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
						.containsKey(sDeviceName)) {

					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
							.put(sDeviceName, new TreeMap<String, String>());

				}
				for (String sParametro : aParametrosGSM_BTSRETSUBUNIT) {
					if (!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
							.get(sDeviceName).containsKey(sParametro)) {
						arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.get(sNombreBTS)
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
								.get(sDeviceName).put(sParametro,
										aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sParametro)]);
					}
				}
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception crearArbolGSM_BTSRETSUBUNIT");
		} catch (IOException e) {
			System.out.println("IOException crearArbolGSM_BTSRETSUBUNIT");
		} catch (Exception e) {
			System.out.println("Otra excepción crearArbolGSM_BTSRETSUBUNIT");
		}
		return arbolBTSRETSUBUNIT;
	}

	private static void escribeFicheroGSM_BTSRETSUBUNIT(
			TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> arbolGSM_BTSRETSUBUNIT,
			File ficheroSalida_GSM_BTSRETSUBUNIT) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_BTSRETSUBUNIT))) {
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_BTSRETSUBUNIT()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			for (String sNeid : arbolGSM_BTSRETSUBUNIT.keySet()) {
				for (String sBTSId : arbolGSM_BTSRETSUBUNIT.get(sNeid).keySet()) {
					for (String sBTSName : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).keySet()) {
						for (String sDeviceNo : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName).keySet()) {
							for (String sDeviceName : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName)
									.get(sDeviceNo).keySet()) {
								StrBuilder compositorValores = new StrBuilder();
								compositorValores.append(sNeid);
								compositorValores.appendSeparator(",");
								compositorValores.append(sBTSId);
								compositorValores.appendSeparator(",");
								compositorValores.append(sBTSName);
								compositorValores.appendSeparator(",");
								compositorValores.append(sDeviceNo);
								compositorValores.appendSeparator(",");
								compositorValores.append(sDeviceName);
								compositorValores.appendSeparator(",");
								for (String sParametro : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName)
										.get(sDeviceNo).get(sDeviceName).keySet()) {

									compositorValores.append(arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName)
											.get(sDeviceNo).get(sDeviceName).get(sParametro));
									compositorValores.appendSeparator(",");
								}
								bw.write(compositorValores.toString().substring(0, compositorValores.size() - 1)
										+ "\r\n");

							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception escribeFicheroGSM_BTSRETSUBUNIT");
		} catch (IOException e) {
			System.out.println("IOException escribeFicheroGSM_BTSRETSUBUNIT");
		} catch (Exception e) {
			System.out.println("Otra excepción escribeFicheroGSM_BTSRETSUBUNIT");
		}

	}

	private static void escribeFicheroGSM_G2GNCELL(File ficheroGSM_G2GNCELL, File ficheroSalidaGSM_G2GNCELL) {
		TreeMap<String, String> arbolMapaValor = new TreeMap<String, String>();
		String[] aParametrosGSM_G2GNCELL = retornaParametrosABuscarG2GNCELL();
		String sRC2GNCELLNAME = "---";
		String sNRC2GNCELLNAME = "---";
		String sNCC = "---";
		String sBCC = "---";
		String sBCCH = "---";
		String NBR2GBCCH = "---";
		String NBR2GBCC = "---";
		String NBR2GNCC = "---";
		try (FileReader fr = new FileReader(ficheroGSM_G2GNCELL);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalidaGSM_G2GNCELL))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_G2GNCELL()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {

				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolMapaValor.put("BSC_SOURCE", aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)]);
				arbolMapaValor.put(sCellIndexSource,
						aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]);
				arbolMapaValor.put(sCellIndexNeigh,
						aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]);

				if (mapaGCELLCellIdParametroValor
						.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
						.containsKey(UtilidadesTexto.dameValorEntero(
								aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))) {
					sRC2GNCELLNAME = mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.get("CELLNAME");

					arbolMapaValor.put("SRC2GNCELLNAME", sRC2GNCELLNAME);
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.containsKey("NCC")) {
						sNCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
								.get("NCC");
						arbolMapaValor.put("NCC", sNCC);
					}
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.containsKey("BCC")) {
						sBCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
								.get("BCC");
						arbolMapaValor.put("BCC", sBCC);
					}
				}
				if (mapaGCELLCellIdParametroValor
						.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
						.get(UtilidadesTexto.dameValorEntero(
								aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
						.containsKey("BCCH")) {
					sBCCH = mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.get("BCCH");
					arbolMapaValor.put("BCCH", sBCCH);
				}

				if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(
								aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))) {
					sNRC2GNCELLNAME = mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.get("CELLNAME");
					arbolMapaValor.put("NBR2GNCELLNAME", sNRC2GNCELLNAME);
					if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.containsKey("NCC")) {
						NBR2GNCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
								.get("NCC");
						arbolMapaValor.put("NBR2GNCC", NBR2GNCC);
					}
					if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.containsKey("BCC")) {
						NBR2GBCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
								.get("BCC");
						arbolMapaValor.put("NBR2GBCC", NBR2GBCC);
					}
					if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.containsKey("BCCH")) {
						NBR2GNCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
								.get("BCCH");
						arbolMapaValor.put("NBR2GBCCH", NBR2GBCCH);
					}
				}

				for (String sParametro : aParametrosGSM_G2GNCELL) {
					if (!arbolMapaValor.containsKey(sParametro)) {
						arbolMapaValor.put(sParametro,
								aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolMapaValor, retornaParametrosCabeceraGSM_G2GNCELL());
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception crearArbolGSM_G2GNCELL");
		} catch (IOException e) {
			System.out.println("IOException crearArbolGSM_G2GNCELL");
		}
	}

	private static void escribirFichero(BufferedWriter bw, TreeMap<String, String> arbolParametroValor,
			String[] arrayCabecera) {

		StrBuilder compositorValores = new StrBuilder();
		for (String sParametro : arrayCabecera) {
			if (!arbolParametroValor.containsKey(sParametro)) {
				compositorValores.append("---");
				compositorValores.appendSeparator(",");
			} else {
				compositorValores.append(arbolParametroValor.get(sParametro));
				compositorValores.appendSeparator(",");
			}
		}
		try {
			bw.write(compositorValores.toString().substring(0, compositorValores.size() - 1) + "\r\n");
		} catch (IOException e) {
			System.out.println("Error en la escritura del fichero");
		}
	}

	private static void escribeFicheroGSM(File ficheroGSM_entrada, File ficheroSalida_GSM, String[] aParametrosABuscar,
			String[] aParametrosCabecera) {

		TreeMap<String, String> arbolParametroValor;
		String sNombreCelda = "---";
		String sCellIdentificador = "---";

		try (FileReader fr = new FileReader(ficheroGSM_entrada);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFichero = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : aParametrosCabecera) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolParametroValor = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolParametroValor.put("ControllerName", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				arbolParametroValor.put(sCellIndex, aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]);

				if (mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)].replaceAll("\"", ""))) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)].replaceAll("\"", ""))
							.containsKey(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))) {
						sNombreCelda = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)].replaceAll("\"", ""))
								.get(UtilidadesTexto
										.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))
								.get("CELLNAME");
						arbolParametroValor.put("CELLNAME", sNombreCelda);
					}
				}
				if (mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)].replaceAll("\"", ""))) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)].replaceAll("\"", ""))
							.containsKey(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))) {
						sCellIdentificador = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)].replaceAll("\"", ""))
								.get(UtilidadesTexto
										.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))
								.get("CELLID");
						arbolParametroValor.put("CI", sCellIdentificador);
					}
				}
				for (String sParametro : aParametrosABuscar) {
					if(mapaCabeceraFichero.containsKey(sParametro)){
						arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabecera);
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found exception en GCELL2GBA1");
		} catch (IOException e) {
			System.out.println("IO exception en GCELL2GBA1");
		}

	}

	private static void escribeFicheroGSM_GCELLAMRQUL(File ficheroGSM_GCELLAMRQUL, File ficheroSalida_GSM_GCELLAMRQUL) {
		TreeMap<String, String> arbolGSM_GCELLAMRQUL;
		String[] aParametrosGSM_GCELLAMRQUL = retornaParametrosABuscarGCELLAMRQUL();
		String sNombreCelda = "---";
		String sCellIdentificador = "---";
		try (FileReader fr = new FileReader(ficheroGSM_GCELLAMRQUL);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_GCELLAMRQUL))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGCELLAMRQUL = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_GCELLAMRQUL()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolGSM_GCELLAMRQUL = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolGSM_GCELLAMRQUL.put("ControllerName",
						aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)]);
				arbolGSM_GCELLAMRQUL.put(sCellIndex,
						aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]);
				if (mapaGCELLCellIdParametroValor.containsKey(
						aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)].replaceAll("\"", ""))) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)].replaceAll("\"", ""))
							.containsKey(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]))) {
						sNombreCelda = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)].replaceAll("\"", ""))
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]))
								.get(sCellName);
						arbolGSM_GCELLAMRQUL.put("CELLNAME", sNombreCelda);
					}
				}
				if (mapaGCELLCellIdParametroValor.containsKey(
						aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)].replaceAll("\"", ""))) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)].replaceAll("\"", ""))
							.containsKey(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]
											.replaceAll("\"", "")))) {
						sCellIdentificador = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)].replaceAll("\"", ""))
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]))
								.get("CELLID");
						arbolGSM_GCELLAMRQUL.put("CI", sCellIdentificador);
					}
				}
				for (String sParametro : aParametrosGSM_GCELLAMRQUL) {
					arbolGSM_GCELLAMRQUL.put(sParametro,
							aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sParametro)]);
				}
				escribirFichero(bw, arbolGSM_GCELLAMRQUL, retornaParametrosCabeceraGSM_GCELLAMRQUL());
				sValoresParametros = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void escribeFicheroGSM_GCELLBASICPARA(File ficheroGSM_GCELLBASICPARA,
			File ficheroSalida_GSM_GCELLBASICPARA) {
		TreeMap<String, String> arbolGSM_GCELLBASICPARA;
		String[] aParametrosGSM_GCELLBASCISPARA = retornaParametrosABuscarGCELLBASICPARA();
		String sNombreCelda = "---";
		String sCellIdentificador = "---";
		try (FileReader fr = new FileReader(ficheroGSM_GCELLBASICPARA);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_GCELLBASICPARA))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGCELLBASICPARA = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_GCELLBASICPARA()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolGSM_GCELLBASICPARA = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolGSM_GCELLBASICPARA.put("ControllerName",
						aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)]);
				arbolGSM_GCELLBASICPARA.put(sCellIndex,
						aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]);
				if (mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]))) {
						sNombreCelda = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]))
								.get("CELLNAME");
						arbolGSM_GCELLBASICPARA.put("CELLNAME", sNombreCelda);
					}
				}
				if (mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]))) {
						sCellIdentificador = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]))
								.get("CELLID");
						arbolGSM_GCELLBASICPARA.put("CI", sCellIdentificador);
					}
				}
				for (String sParametro : aParametrosGSM_GCELLBASCISPARA) {
					arbolGSM_GCELLBASICPARA.put(sParametro,
							aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sParametro)]);
				}
				escribirFichero(bw, arbolGSM_GCELLBASICPARA, retornaParametrosCabeceraGSM_GCELLBASICPARA());
				sValoresParametros = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static TreeMap<String, String> retornaParametrosABuscarGCELL() {
		TreeMap<String, String> arbolParametrosABuscar = new TreeMap<String, String>();
		arbolParametrosABuscar.put("CELLNAME", "CELLNAME");
		arbolParametrosABuscar.put("CI", "CELLID");
		arbolParametrosABuscar.put("NCC", "NCC");
		arbolParametrosABuscar.put("BCC", "BCC");
		arbolParametrosABuscar.put("LAC", "LAC");
		arbolParametrosABuscar.put("Fecha", "Fecha");
		arbolParametrosABuscar.put("Red", "Red");
		arbolParametrosABuscar.put("ipEntrada", "ipEntrada");
		return arbolParametrosABuscar;
	}

	private static TreeMap<String, String> retornaParametrosABuscarGEXT2GCELL() {
		TreeMap<String, String> aParametrosABuscar = new TreeMap<String, String>();
		aParametrosABuscar.put("EXT2GCELLNAME", "CELLNAME");
		aParametrosABuscar.put("CI", "CELLID");
		aParametrosABuscar.put("NCC", "NCC");
		aParametrosABuscar.put("BCC", "BCC");
		aParametrosABuscar.put("BCCH", "BCCH");
		aParametrosABuscar.put("LAC", "LAC");
		aParametrosABuscar.put("Fecha", "Fecha");
		aParametrosABuscar.put("Red", "Red");
		aParametrosABuscar.put("ipEntrada", "ipEntrada");
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGSMBTSRET() {
		String[] aParametrosABuscar = { "DEVICENO", "DEVICENAME", "CTRLPORTCN", "CTRLPORTNO", "CTRLPORTSN",
				"CTRLPORTSRN", "POLARTYPE", "RETTYPE", "SCENARIO", "SERIALNO", "VENDORCODE", "Fecha", "Red",
				"ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGSMRETDEVICEDATA() {
		String[] aParametrosABuscar = { "BAND1", "BAND2", "BAND3", "BAND4", "BEARING", "SUBUNITNO", "TILT", "SERIALNO",
				"Fecha", "Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGSMRETSUBUNIT() {
		String[] aParametrosABuscar = { "AER", "CELLID", "CONNCN1", "CONNCN2", "CONNPN1", "CONNPN2", "CONNSN1",
				"CONNSN2", "CONNSRN1", "CONNSRN2", "SUBUNITNO", "TILT", "Fecha", "Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarG2GNCELL() {
		String[] aParametrosABuscar = { "ADJHOOFFSET", "BETTERCELLLASTTIME", "BETTERCELLSTATTIME", "BQLASTTIME",
				"BQMARGIN", "BQNCELLABSTHRESSW", "BQSTATTIME", "CHAINNCELLTYPE", "COMPCOCELLLASTTIME",
				"COMPCOCELLSTARTHYST", "COMPCOCELLSTATTIME", "COMPCOCELLSTOPHYST", "DRHOLEVRANGE", "EDGEADJLASTTIME",
				"EDGEADJSTATTIME", "EDGEHOHYST", "EDOUTHOOFFSET", "Fecha", "GNCELLRANKPRI", "HCSLASTTIME",
				"HCSSTATTIME", "HOLASTTIME", "HOSTATICTIME", "HSRPNUSRNCTAG", "INTELEVHOHYST", "INTERCELLHYST",
				"ISCHAINNCELL", "LEVLAST", "LEVSTAT", "LOADHOPBGTMARGIN", "MINOFFSET", "NCELLPRI", "NCELLPUNEN",
				"NCELLPUNLEV", "NCELLPUNSTPTH", "NCELLPUNTM", "NCELLPWRCOMPVALUE", "NCELLTYPE", "PBGTLAST",
				"PBGTMARGIN", "PBGTSTAT", "Red", "SRCHOCTRLSWITCH", "TALASTTIME", "TASTATTIME", "ULBQLASTTIME",
				"ULBQSTATTIME", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELL2GBA1() {
		String[] aParametrosABuscar = { "CELL2GBA1BCCH", "CELL2GBA1OPTENHSW", "CELL2GBA1OPTSW", "CELL2GBA1TAG", "Fecha",
				"ITEM", "ITEMVALID", "Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLAMRQUL() {
		String[] aParametrosABuscar = { "DLQUALIMITAMRFR", "DLQUALIMITAMRHR", "RXLEVOFF", "RXQUAL1", "RXQUAL10",
				"RXQUAL11", "RXQUAL12", "RXQUAL2", "RXQUAL3", "RXQUAL4", "RXQUAL5", "RXQUAL6", "RXQUAL7", "RXQUAL8",
				"RXQUAL9", "ULQUALIMITAMRFR", "ULQUALIMITAMRHR", "Fecha", "Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLBASICPARA() {
		String[] aParametrosABuscar = { "BTSADJUST", "CALLRESTABDIS", "CELL8PSKPOWERLEVEL", "CELLSCENARIO", "CMEPRIOR",
				"DIRECTRYEN", "DIVERT16QAMDELAY", "DIVERT32QAMDELAY", "DIVERT8PSKDELAY", "DNPCEN", "DYNOPENTRXPOWER",
				"ENCRY", "ENCRYPTIONALGORITHM1ST", "ENCRYPTIONALGORITHM2ND", "ENCRYPTIONALGORITHM3RD",
				"ENCRYPTIONALGORITHM4TH", "ENCRYPTIONALGORITHM5TH", "ENCRYPTIONALGORITHM6TH", "ENCRYPTIONALGORITHM7TH",
				"FASTCALLTCHTHRESHOLD", "FRDLDTX", "FRULDTX", "ICBALLOW", "IMMASSCBB", "IMMASSEN", "LAYER", "LEVELRPT",
				"MAXTA", "MICCSWITCH", "NBAMRTFOSWITCH", "PDCH2SDEN", "POWERREDUCE16QAM", "POWERREDUCE32QAM",
				"RTPSWITCH", "RXMIN", "SDDYN", "SVHOCNGSTTHR", "TIMESLOTVOLADJALLOW", "UPPCEN", "Fecha", "Red",
				"ipEntrada", "COMPSWITCH", "DLCOMBFILTERSW", "GMSKDELAYDYNADJSW", "IMMTCHLOADTHRES",
				"PDCHTOSDCCHADJUSTPREFSW", "SIGDECODEENSWITCH", "SVHODTXDTCTIMER", "SVHOHODELAYTIMER", "ULROTSW",
				"UMAISSWITCH" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLCCBASIC() {
		String[] aParametrosABuscar = { "ACCCTRLEN", "AFRDSBLCNT", "AFRSAMULFRM", "AHRDSBLCNT", "AHRSAMULFRM",
				"ASSLOADJUDGEEN", "COMMACC", "DTLOADTHRED", "ECSC", "EMLPPEN", "ERGCALLDIS", "MBR", "MSMAXRETRAN",
				"PAGTIMES", "RACHBUSYTHRED", "REASSEN", "REPEATDLFASET", "REPEATDLFATHRED", "REPEATSASET", "RLT",
				"SAMULFRM", "SPECACC", "UMSFRBERTHRESH", "UMSFRLLRFACTOR", "UMSFRLLRTHRESH", "UMSFRSWITCH", "Fecha",
				"Red", "ipEntrada", "REPEATSADLTHD", "REPEATSAULTHD", "SATIMEROPTSW" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLCCAD() {
		String[] aParametrosABuscar = { "ASSQUELEN", "CELLSELECTAFTERCALLREL", "DNSENDSMDIS", "EMCPRILV",
				"FSTRTNTDDWITHOUTMR", "MAXTADROPCALLFILTER", "MAXTADROPCALLSWITCH", "MAXTADROPCALLTHRESHOLD", "POSSI13",
				"PREEMPTIONPERMIT", "REASSFREQBAND", "UPSENDSMDIS", "Fecha", "Red", "ipEntrada", "ASSRETRYMAX",
				"EMCALLDIRRETRYOPT", "MAXTADROPCALLOPTSW", "TAADJINTV", "TAADJOPTSW" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLCCCH() {
		String[] aParametrosABuscar = { "ABISFCEN", "CANPC", "CCCHLOADINDPRD", "CCCHLOADTHRES", "DYNCCCHSWITCH",
				"FMSMAXOPCC", "HRATESPT", "OVERLOADINTV", "PAGINGREORGLAGTM", "PAGINGREORGSTARTTHRD",
				"PAGINGREORGSTOPTHRD", "PAGINGREORGSW", "PCHMSGPRIORSW", "RACHLDAVERSLOT", "RACHLOADALM", "RFRESINDPRD",
				"Fecha", "Red", "ipEntrada", "INTACESCONGCTRLSW", "PAGINGOVLDPROCOPTSW" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLCCUTRANSYS() {
		String[] aParametrosABusar = { "BESTTDDCELLNUM", "CELL1800OFF", "CELL1800THRED", "CELL900OFF", "CELL900THRED",
				"FDDCELLOFF", "FDDCELLTHRED", "FDDFREQCNUM", "FDDQMIN", "FDDQMINOFFSET", "FDDQOFF", "FDDREP",
				"FDDRPTTHRESHOLD2ECNO", "FDDRPTTHRESHOLD2RSCP", "FDDRSCPMIN", "GSMFREQCNUM", "INVALBSICEN",
				"MEASURETYPE", "MSCVER", "POS2QUATER", "QCI", "QI", "QP", "QSEARCHC", "SCALEORDER", "SEARCH3G",
				"TDDCELLOFF", "TDDCELLRESELDIV", "TDDCELLTHRED", "TDDMIOPTIMIZEDALLOWED", "TDDMIPROHIBIT",
				"TDDSIOPTIMIZEDALLOWED", "Fecha", "Red", "ipEntrada", "EMRMSCAPIDESWITCH", "EMRMSCAPPROSWITCH",
				"FIRSTSI2QUATERMSGOPTSW", "POS2QUATER", "SI2QUATEROPTIMIZEDALLOWED" };
		return aParametrosABusar;
	}

	private static String[] retornaParametrosABuscarGCELLCHMGAD() {
		String[] aParametrosABuscar = { "AMRTCHHPRIORALLOW", "AMRTCHHPRIORLOAD", "TCHBUSYTHRES", "LOADSTATYPE", "Fecha",
				"Red", "ipEntrada", "AMRLOADOPTEN", "ASSOLRXLEVOFFSET", "BTRXPRIORITYSWITCH", "CELLOPPWRGRP",
				"CHALLOCATIONOPTSWITCH", "CHANALLOCBYUSRLOCINASS", "CHANALLOCBYUSRLOCINHO", "CHANINTERMESALLOW",
				"CHPWRINSUFFALLOWED", "CIRESTVALUE", "DLINTERFLEVLIMIT", "DLINTERFQUALLIMIT", "FREQLOADSHARETRAFFTHRSH",
				"FRHOREQREJWHENCELLCONGEST", "HALFRATEREPACKINGSWITCH", "HISPRIOALLOW", "HOOLRXLEVOFFSET",
				"HRIUOLDRATESELALLOW", "HSCSDBUSYTHRES", "IBCAAFRSOFTBLKTHRD", "IBCAAHRSOFTBLKTHRD", "IBCAALLOWED",
				"IBCAASSWAITMEASURERPTTIME", "IBCACALLINFOFILTERLEN", "IBCACALLSOFTBLOCKTHOFFSET",
				"IBCACALLTARGETCIROFFSET", "IBCACIRESTENHANCE", "IBCADLPATHLOSSOFF", "IBCAEHOASSWAITMEASURERPTTIME",
				"IBCAESTMTSCCOLLISIONALLOW", "IBCAFLEXTSCALLOWED", "IBCAFORCEDBTSSYNCALLOWED",
				"IBCAFREEBCCHCHANTHRSHOLD", "IBCAFREVLOPT", "IBCAFRSOFTBLKTHRD", "IBCAGETINTFSRCOPT",
				"IBCAHOASSWAITMEASURERPTTIME", "IBCAHOSOFTBLKTHRESHOLD", "IBCAHRSOFTBLKTHRD", "IBCAICDMRELEVOFFSET",
				"IBCAICDMSWITCH", "IBCAINITPCRXLEVDLOFFSET", "IBCAINITPCRXLEVULOFFSET", "IBCAINITPCRXQUALDLOFFSET",
				"IBCAINITPCRXQUALULOFFSET", "IBCAIUOPATHLOSSOFF", "IBCAMAIOUSMTD", "IBCAMAXINTFSRCNUM",
				"IBCANCELLPATHLOSSESTIMATE", "IBCANEWCALLCIROFFSET", "IBCANHOASSWAITMEASURERPTTIME",
				"IBCANONMEANCELLSTATNUM", "IBCAOPRREVISEFACTOR", "IBCAPATHLOSSOFF", "IBCAPDCHDYNTRANTMR",
				"IBCAPDDYNTRENHANCE", "IBCAPLFILTFACTOR", "IBCAQUEUEOPT", "IBCASCELLPATHLOSS", "IBCASOFTBLKSAICOFF",
				"IBCASOFTBLKSWITCH", "IBCASUBCHNHANDOVERALLOWED", "IBCATARGETCIRTHRSH", "IBCAUSEDIUOSUBLAY",
				"IBCAUSRDYNCMEASURENCELL", "IBCAWAFRSOFTBLKTHRD", "INNAMRTCHHPRIORLOAD", "INTERFPRIALLOW",
				"JUDGERXLEVWHENASSIGNHR", "LOADSHAREALLOW", "LOOSESDCCHLOADTHRED", "LOWRXLEVOLFORBIDOPTSW",
				"LOWRXLEVOLFORBIDSWITCH", "MCPACHAPPOPT", "MCPALOWTRAFFICTH", "MCPAOPTALG", "MINRXLEVWHENASSIGNHR",
				"MTSTURNOFFALG", "MTSTURNOFFHYST", "MTSTURNOFFTH", "OUTAMRTCHHPRIORLOAD", "PWRPRIORALLOW", "QLENSD",
				"QLENSI", "QTRUDNPWRLASTTIME", "QTRUDNPWRSTATTIME", "QTRUPWRSHARE", "QUALHOPRIALLOW", "SCENELOADTYPE",
				"SSLENSD", "SSLENSI", "TCHLOADOPTSWITCH", "TCHREQSUSPENDINTERVAL", "TCHTRIBUSYUNDERLAYTHR",
				"TCHTRICBUSYOVERLAYTHR", "TIGHTBCCHASSMAINBCCHLEV", "TIGHTBCCHASSMAINBCCHQUAL", "TIGHTSDCCHRXLEVTHRED",
				"TRXPRIALLOW", "TURNOFFLOADTYPE", "UPINTERFQUALLIMIT", "UPINTERLEVLIMIT", "UPRXLEVLASTTIME",
				"UPRXLEVSMOOTHPARA", "UPRXLEVSTATICTIME", "VAMOSAHSUSERDLSOFTBLOCKTHD", "WAITSDCCHIDLETIMER" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLCHMGBASIC() {
		String[] aParametrosABuscar = { "CELLMAXSD", "DIFFBANDSDCCHDYNADJ", "IDLESDTHRES", "SDCCHDYNADJTSNUM",
				"ENTCHADJALLOW", "Fecha", "Red", "ipEntrada", "ALLOWAMRHALFRATEUSERPERC", "ALLOWHALFRATEUSERPERC",
				"CHALLOCSTRATEGY", "DIFFBANDSDCCHUSINGOPTIMIZE", "DYNPBTSUPPORTED", "FACTORYMODE", "GRADEACCALLOW",
				"HIGHPRIUSERQUALFIRST", "IMMASSDIFFBANDALLOCTCHSW", "INTOCELLRESVCHANNUM", "MAINBCCHSDCCHNUM",
				"MINRESTIMETCH", "RSVCHMFORECNUM", "SDBACKTOTCHPUNISHSWITCH", "SDDYNADJRSVTCHNUM",
				"SDDYNADJRSVTCHSWITCH", "TIGHTBCCHSWITCH" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLFREQ() {
		String[] aParametrosABuscar = { "FREQ1", "FREQ2", "FREQ3", "FREQ4", "FREQ5", "FREQ6", "FREQ7", "FREQ8", "FREQ9",
				"FREQ10", "FREQ11", "FREQ12", "FREQ13", "FREQ14", "FREQ15", "FREQ16", "FREQ17", "FREQ18", "Fecha",
				"Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLGPRS() {
		String[] aParametrosABuscar = { "DLDCSPT", "DPIPARATRANMODE", "EDGE", "EGPRS2A", "GPRS", "NACCSPT", "NC2SPT",
				"PKTSI", "PSPAGINGCTRL", "RA", "SPTDPI", "SPTINTERRATINBSCPSHO", "SPTINTERRATOUTBSCPSHO",
				"SPTLTEINBSCPSHO", "SPTLTEOUTBSCPSHO", "SPTREDUCELATENCY", "SUPPORTDTM", "SUPPORTEDA", "Fecha", "Red",
				"ipEntrada", "ENACCSPT", "ENC2SPT" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLHO2GBA2() {
		String[] aParametrosABuscar = { "CELL2GBA2BCCH", "CELL2GBA2OPTSW", "CELL2GBA2TAG", "ITEM", "ITEMVALID", "Fecha",
				"Red", "ipEntrada", "CELL2GBA2OPTENHSW" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLHOAD() {
		String[] aParametrosABuscar = { "ABCDOWNQUALITY", "ABCUPQUALITY", "ABCWAITMAXTIME", "ACCTHRESLAYER",
				"ASSIGNBETTERCELLEN", "BANTIME", "CONTINTV", "HOTRYCNT", "INTERFEREDLEVOFF", "INTERFEREDLEVOFFOL",
				"INTERFEREDLQUALOFFOL", "INTERFEREDQUALOFF", "INTERFEREULEVOFF", "INTERFEREULEVOFFOL",
				"INTERFEREULQUALOFFOL", "INTERFEREUQUALOFF", "INTERFEROFFSWITCHOL", "KBIAS", "LAYHOLOADTH",
				"LOADACCTHRES", "LOADHOAD", "LOADHOHYSTADAPEN", "LOADHOPERIOD", "LOADHOSTEP", "LOADHOUSRRATIO",
				"LOADOFFSET", "MAXCNTNUM", "MAXRESEND", "OUTBSCLOADHOEN", "QCKSTATCNT", "QCKTIMETH", "QCKTRUECNT",
				"SDCCHWAITMREN", "SDCCHWAITMRTIMELEN", "SPEEDPUNISH", "SPEEDPUNISHT", "SYSFLOWLEV", "T3105",
				"TIGHTBCCHHOLOADTHRES", "TIGHTBCCHRXQUALTHRES", "TRIGTHRES", "TRIGTHRESLAYER", "Fecha", "Red",
				"ipEntrada" };

		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLHOBASIC() {
		String[] aParametrosABuscar = { "BQHOEN", "HOCTRLSWITCH", "HOTHRES", "INTERRATCELLRESELEN", "INTERRATINBSCHOEN",
				"INTERRATOUTBSCHOEN", "INTRACELLFHHOEN", "INTRACELLHOEN", "LEVHOEN", "LOADHOEN", "LTECELLRESELEN",
				"PBGTHOEN", "FRINGEHOEN", "ULEDGETHRES", "DLEDGETHRES", "F2HHOLOADSTFSWITCH", "AMRFULLTOHALFHOTHRESH",
				"AMRFULLTOHALFHOALLOW", "NOAMRFULLTOHALFHOALLOW", "HALFTOFULLHODURATION", "FULLTOHALFHOPERIOD",
				"FULLTOHALFHOLOADSTF", "INTERFHOEN", "Fecha", "Red", "ipEntrada", "AMRF2HHOQUALTHFINE",
				"AMRFULLTOHALFHOATCBADJSTEP", "AMRFULLTOHALFHOATCBTHRESH", "AMRFULLTOHALFHOPATHADJSTEP",
				"AMRFULLTOHALFHOPATHTHRESH", "AMRFULLTOHALFHOQUALTHRESH", "AMRH2FHOOPTSW", "AMRH2FHOQUALFINE",
				"AMRHALFTOFULLHOATCBTHRESH", "AMRHALFTOFULLHOPATHTHRESH", "AMRHALFTOFULLHOQUALALLOW",
				"AMRHALFTOFULLHOQUALTHRESH", "AMRHALFTOFULLHOTHRESH", "ATCBHOEN", "BADQUALHOOPTALLOW", "COBSCMSCADJEN",
				"CONHOEN", "DLEDGETHRES", "EDGEHOHYSTEN", "EDGELAST1", "EDGESTAT1", "EDOUTHOADEN", "FHGAINOFFSET",
				"FULLTOHALFHOATCBOFFSET", "FULLTOHALFHODURATION", "FULLTOHALFHOLASTTIME", "FULLTOHALFHOPATHOFFSET",
				"FULLTOHALFHOSTATTIME", "HALFTOFULLATCBOFFSET", "HALFTOFULLHOLASTTIME", "HALFTOFULLHOPATHOFFSET",
				"HALFTOFULLHOSTATTIME", "HOCDCMINDWPWR", "HOCDCMINUPPWR", "HOCDCOVERLODEHOEN", "HOPRIOMODEN",
				"INFHHOLAST", "INFHHOSTAT", "INHOF2HTH", "INHOH2FTH", "INTERHOOPTALLOW", "INTERRATDIFFPROCSW",
				"INTERRATIURGINBSCHOEN", "INTERRATIURGVOICECARRYEN", "INTRACELLFHOPTSWITCH", "INTRACELLSINUSEREN",
				"LEVHOHYST", "LOADHOEN", "LTESAILAC", "LTESAIMCC", "LTESAIMNC", "LTESAISAC", "MRINTRPLOPTSWITCH",
				"NOAMRF2HHOQUALTHFINE", "NOAMRFULLTOHALFHOATCBADJSTEP", "NOAMRFULLTOHALFHOATCBTHRESH",
				"NOAMRFULLTOHALFHOPATHADJSTEP", "NOAMRFULLTOHALFHOPATHTHRESH", "NOAMRFULLTOHALFHOQUALTHRESH",
				"NOAMRFULLTOHALFTHRESH", "NOAMRH2FHOQUALFINE", "NOAMRHALFTOFULLHOATCBTHRESH",
				"NOAMRHALFTOFULLHOPATHTHRESH", "NOAMRHALFTOFULLHOQUALALLOW", "NOAMRHALFTOFULLHOQUALTHRESH",
				"NOAMRHALFTOFULLTHRESH", "PBGTHOEN", "QCKMVHOEN", "QUICKHOEN", "QUICKPBGTHOEN", "RXQCKFALLHOEN",
				"SIGCHANHOEN", "SRVCCHOEN", "TAHOEN", "TIGHTBCCHHOLASTTIME", "TIGHTBCCHHOSTATTIME", "ULEDGETHRES",
				"ULMCRITOPTSW" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLHOCTRL() {
		String[] aParametrosABuscar = { "BSMSPWRLEV", "BTSMESRPTPREPROC", "CONTHOMININTV", "INRBSCSDHOEN",
				"MINPWRLEVDIRTRY", "MRPREPROCFREQ", "NEWURGHOMININTV", "PENALTYEN", "PRIMMESPPT", "SDHOMININTV",
				"TCHHOMININTV", "Fecha", "Red", "ipEntrada", "BCCHHOPHOCOMPOPT", "ULRXLEVBOUNDPROTECTIONSW" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLHOEMG() {
		String[] aParametrosABuscar = { "DLQUALIMIT", "FLTPARAA1", "FLTPARAA2", "FLTPARAA3", "FLTPARAA4", "FLTPARAA5",
				"FLTPARAA6", "FLTPARAA7", "FLTPARAA8", "FLTPARAB", "HOCTRLSWITCH", "NODLMRHOALLOWLIMIT", "NODLMRHOEN",
				"NODLMRHOLASTTIME", "NODLMRHOQUALLIMIT", "NODLMRHOSTATTIME", "TALIMIT", "ULQUALIMIT", "Fecha", "Red",
				"ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLHOFITPEN() {
		String[] aParametrosABuscar = { "CBSIGNLEN", "CBTRAFFLEN", "DATAQUAFLTLEN", "DATASTRFLTLEN", "DATASTRUPFLTLEN",
				"DTXMEASUSED", "FAILSIGSTRPUNISH", "HOCTRLSWITCH", "INITUPFILEN", "LOADHOPUNISHINHERITSWITCH",
				"MBSIGNLEN", "MBTRAFFLEN", "MRMISSCOUNT", "NCELLFLTLEN", "NRBSDCCHFFLEN", "NRBTCHFFLEN", "PENALTYTIMER",
				"RQSIGNLEN", "RQTRAFFLEN", "RSCPENALTYTIMER", "SIGQUAFLTLEN", "SIGSTRFLTLEN", "SIGSTRUPFLTLEN",
				"SSBQPUNISH", "SSTAPUNISH", "TAFLTLEN", "TIMEAMRFHPUNISH", "TIMEBQPUNISH", "TIMETAPUNISH",
				"UMPENALTYTIMER", "Fecha", "Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLHOUTRANFDD() {
		String[] aParametrosABuscar = { "BET3GHOEN", "HOECNOTH3G", "HOOPTSEL", "HOPRETH2G", "HORSCPTH3G", "Fecha",
				"Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLIDLEAD() {
		String[] aParametrosABuscar = { "ACS", "CMETO", "PT", "Fecha", "Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLIDLEADBASIC() {
		String[] aParammetrosABuscar = { "ATT", "BSAGBLKSRES", "BSPAMFRAMS", "CBA", "CBQ", "CRH", "CRO", "NCCPERMIT",
				"PI", "T3212", "TX", "Fecha", "Red", "ipEntrada" };
		return aParammetrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLMAGRP() {
		String[] aParametrosABuscar = {"ControllerName", "CELLNAME", "CELLID", "CI", "FREQ1", "FREQ2", "HOPINDEX",
				"HOPMODE", "HSN", "TSC", "FREQ3", "FREQ4", "FREQ5", "FREQ6", "FREQ7", "FREQ8", "FREQ9", "FREQ10",
				"Fecha", "Red", "ipEntrada"
		};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGCELLOTHEXT() {
		String[] aParametrosABuscar = {"CDRTTRYFBDTHRES","CELLCOVERAGETYPE","CELLOVERCVGRXLEVDLTH","CELLOVERCVGTALTH","CELLWEAKCVGRXLEVDLTH","CELLWEAKCVGTALTH",
				"DLFREQADJ","DRTAGCELLSEL","DRXEN","FERSTATTH1","FERSTATTH2","FERSTATTH3","FERSTATTH4","FERSTATTH5","FERSTATTH6","FERSTATTH7","FRAMEOFFSET","FREQADJ","FREQADJVALUE","HSCSDSCANPER",
				"HSCSDTRAFFSET","INTERFTHRES0","INTERFTHRES1","INTERFTHRES2","INTERFTHRES3","INTERFTHRES4","INTERFTHRES5","INTERPERIOD","IURGINFOCTRL","MAINBCCHPWDTACTCHEN","MAINBCCHPWRDTEN",
				"MAINBCCHPWRDTETIME","MAINBCCHPWRDTRANGE","MAINBCCHPWRDTSTIME","PCHOCMPCON","PODECTHRES","POERRTHRES","RESERVEDIDLECH","RFMAXPWRDEC","SDDROPSTATDLLEV","SDDROPSTATDLQUAL","SDDROPSTATULLEV",
				"SDDROPSTATULQUAL","TCHDROPSTATDLFER","TCHDROPSTATDLLEV","TCHDROPSTATDLQUAL","TCHDROPSTATULFER","TCHDROPSTATULLEV","TCHDROPSTATULQUAL","TFRMSTARTTIME","VQILOWTHRD","VSWRERRTHRES",
				"VSWRUNJUSTTHRES","Fecha","Red","ipEntrada","AUXTRXRSVSW","BTSGRPFLEXABISLDRACTION","CSUPDATAABNMLCHKSW","DRFUVWSRSMMODE","DRTAGCELLSEL","HOUMTSCMINQPOLICY","HSCSDREADJUSTMENTSW",
				"IBCAINTFPUNISHTHR","ICADPSCNIDENTOPTSW","INTFBANDENHANCESW","INTFFILTERPERIOD","INTFREPROTPERIOD","MTSPRITYPE","PREEMPTBBTHD","PSULFREQADJ","RELEASEBBTHD","TRXPOOLALLOCTAFTH",
				"TRXPOOLPMTTAFTH","TRXPOOLRELTAFTH"
				
		};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGCELLPRIEUTRANSYS() {
		String[] aParametrosABuscar = {"BESTEUTRANCELLNUM","EUTRANPRI","EUTRANQRXLEVMIN","FASTRETURNFILTERSW","FASTRETURNMEASSPT","GERANPRI","HPRIO",
				"QPEUTRAN","THREUTRANHIGH","THREUTRANLOW","THREUTRANRPT","THRGSMLOW","THRPRISEARCH","THRUTRANHIGH","THRUTRANLOW","TRESEL","UTRANPRI","UTRANQRXLEVMIN","Fecha","Red","ipEntrada",
				"EUTRANFREQCNUM","EUTRANRESELECTOPTSW","FDDFASTRETURNRSRPTH","FDDLTEOFFSET","SI2QUATEROPTFORLTESW","TDDFASTRETURNRSRPTH","TDDLTEOFFSET"
		};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGCELLPSBASE() {
		String[] aParametrosABuscar = {
				"ControllerName","CELLNAME","CELLID","CI","ACCBURST","BSCVMAX","BSSPAGINGCOORDINATION","CTRLACKTYPE","DRXTIMERMAX","EARLYTBFEST","EGPRS11BITCHANREQ",
				"EXTUTBFNODATA","INACTSCHPERIOD","NCO","NMO","PANDEC","PANINC","PANMAX","PANMAX","PSDTXLAOPTISWITCH","RACOLOR","SGSNR","SPGCCCCHSUP","T3168","T3192","UPDTXACKPERIOD",
				"Fecha","Red","ipEntrada","ACCTECHREQSW","EXTNMOISW"
		};
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosCabeceraGCELL() {
		String[] aParametrosABuscar = { "BSC", "CELLINDEX", "CELLNAME", "CELLID", "NCC", "BCC", "BCCH", "LAC", "Fecha",
				"Red", "ipEntrada" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosCabeceraGSM_BTS() {
		String[] aParametrosCabecera = { "ControllerName", "BTSNAME", "BTSID", "BTSDESC", "BTSTYPE", "ABISBYPASSMODE",
				"ACTSTATUS", "FLEXABISMODE", "HOSTTYPE", "INNBBULICSHAEN", "INTRABBPOOLSWITCH", "IPPHYTRANSTYPE",
				"ISCONFIGEDRING", "MAINBMPMODE", "MAINPORTNO", "MPMODE", "SEPERATEMODE", "SERVICEMODE", "SRANMODE",
				"TRANDETSWITCH", "TSASSIGNOPTISW", "WORKMODE", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;

	}

	private static String[] retornaParametrosCabeceraGSM_BTSRET() {
		String[] aParametrosCabecera = { "ControllerName", "BTSID", "BTSNAME", "DEVICE", "DEVICENAME", "CTRLPORTCN",
				"CTRLPORTNO", "CTRLPORTSN", "POLARTYPE", "RETTYPE", "SCENARIO", "SERIALNO", "VENDORCODE", "Fecha",
				"Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_BTSRETDEVICEDATA() {
		String[] aParametrosCabecera = { "ControllerName", "BTSID", "BTSNAME", "DEVICE", "DEVICENAME", "BAND1", "BAND2",
				"BAND3", "BAND4", "BEARING", "SUBUNITNO", "TILT", "SERIALNO", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_BTSRETSUBUNIT() {
		String[] aParametrosCabecera = { "ControllerName", "BTSID", "BTSNAME", "DEVICE", "DEVICENAME", "AER", "CELLID",
				"CONNCN1", "CONNCN2", "CONNPN1", "CONNPN2", "CONNSN1", "CONNSN2", "CONNSRN1", "CONNSRN2", "SUBUNITNO",
				"TILT", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_G2GNCELL() {
		String[] aParametrosCabecera = { "BSC_SOURCE", "SRC2GNCELLNAME", "NBR2GNCELLNAME", "INTERCELLHYST", "MINOFFSET",
				"PBGTMARGIN", "BQMARGIN", "ISCHAINNCELL", "NCELLTYPE", "INTELEVHOHYST", "DRHOLEVRANGE",
				"SRCHOCTRLSWITCH", "CHAINNCELLTYPE", "EDGEADJSTATTIME", "EDGEADJLASTTIME", "LEVSTAT", "LEVLAST",
				"PBGTSTAT", "PBGTLAST", "BETTERCELLSTATTIME", "BETTERCELLLASTTIME", "HOSTATICTIME", "HOLASTTIME",
				"HCSSTATTIME", "HCSLASTTIME", "BQSTATTIME", "BQLASTTIME", "TASTATTIME", "TALASTTIME", "ULBQSTATTIME",
				"ULBQLASTTIME", "LOADHOPBGTMARGIN", "NCELLPRI", "EDOUTHOOFFSET", "NCELLPUNEN", "NCELLPUNSTPTH",
				"NCELLPUNTM", "NCELLPUNLEV", "ADJHOOFFSET", "EDGEHOHYST", "NCC", "BCC", "BCCH", "NBR2GNCC", "NBR2GBCC",
				"NBR2GBCCH", "BQNCELLABSTHRESSW", "COMPCOCELLLASTTIME", "COMPCOCELLSTARTHYST", "COMPCOCELLSTATTIME",
				"COMPCOCELLSTOPHYST", "GNCELLRANKPRI", "HSRPNUSRNCTAG", "NCELLPWRCOMPVALUE", "Fecha", "Red",
				"ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELL2GBA1() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "CELL2GBA1BCCH",
				"CELL2GBA1OPTSW", "CELL2GBA1TAG", "ITEM", "ITEMVALID", "Fecha", "Red", "ipEntrada",
				"CELL2GBA1OPTENHSW" };

		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLAMRQUL() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "DLQUALIMITAMRFR",
				"DLQUALIMITAMRHR", "RXLEVOFF", "RXQUAL1", "RXQUAL10", "RXQUAL11", "RXQUAL12", "RXQUAL2", "RXQUAL3",
				"RXQUAL4", "RXQUAL5", "RXQUAL6", "RXQUAL7", "RXQUAL8", "RXQUAL9", "ULQUALIMITAMRFR", "ULQUALIMITAMRHR",
				"Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLBASICPARA() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "BTSADJUST", "CALLRESTABDIS",
				"CELL8PSKPOWERLEVEL", "CELLSCENARIO", "CMEPRIOR", "DIRECTRYEN", "DIVERT16QAMDELAY", "DIVERT32QAMDELAY",
				"DIVERT8PSKDELAY", "DNPCEN", "DYNOPENTRXPOWER", "ENCRY", "ENCRYPTIONALGORITHM1ST",
				"ENCRYPTIONALGORITHM2ND", "ENCRYPTIONALGORITHM3RD", "ENCRYPTIONALGORITHM4TH", "ENCRYPTIONALGORITHM5TH",
				"ENCRYPTIONALGORITHM6TH", "ENCRYPTIONALGORITHM7TH", "FASTCALLTCHTHRESHOLD", "FRDLDTX", "FRULDTX",
				"ICBALLOW", "IMMASSCBB", "IMMASSEN", "LAYER", "LEVELRPT", "MAXTA", "MICCSWITCH", "NBAMRTFOSWITCH",
				"PDCH2SDEN", "POWERREDUCE16QAM", "POWERREDUCE32QAM", "RTPSWITCH", "RXMIN", "SDDYN", "SVHOCNGSTTHR",
				"TIMESLOTVOLADJALLOW", "UPPCEN", "Fecha", "Red", "ipEntrada", "COMPSWITCH", "DLCOMBFILTERSW",
				"GMSKDELAYDYNADJSW", "IMMTCHLOADTHRES", "PDCHTOSDCCHADJUSTPREFSW", "SIGDECODEENSWITCH",
				"SVHODTXDTCTIMER", "SVHOHODELAYTIMER", "ULROTSW", "UMAISSWITCH" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLCCAD() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "ASSQUELEN",
				"CELLSELECTAFTERCALLREL", "DNSENDSMDIS", "EMCPRILV", "FSTRTNTDDWITHOUTMR", "MAXTADROPCALLFILTER",
				"MAXTADROPCALLSWITCH", "MAXTADROPCALLTHRESHOLD", "POSSI13", "PREEMPTIONPERMIT", "REASSFREQBAND",
				"UPSENDSMDIS", "Fecha", "Red", "ipEntrada", "ASSRETRYMAX", "EMCALLDIRRETRYOPT", "MAXTADROPCALLOPTSW",
				"TAADJINTV", "TAADJOPTSW" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLCCBASIC() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "ACCCTRLEN", "AFRDSBLCNT",
				"AFRSAMULFRM", "AHRDSBLCNT", "AHRSAMULFRM", "ASSLOADJUDGEEN", "COMMACC", "DTLOADTHRED", "ECSC",
				"EMLPPEN", "ERGCALLDIS", "MBR", "MSMAXRETRAN", "PAGTIMES", "RACHBUSYTHRED", "REASSEN", "REPEATDLFASET",
				"REPEATDLFATHRED", "REPEATSASET", "RLT", "SAMULFRM", "SPECACC", "UMSFRBERTHRESH", "UMSFRLLRFACTOR",
				"UMSFRLLRTHRESH", "UMSFRSWITCH", "Fecha", "Red", "ipEntrada", "REPEATSADLTHD", "REPEATSAULTHD",
				"SATIMEROPTSW" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLCCCH() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "ABISFCEN", "CANPC",
				"CCCHLOADINDPRD", "CCCHLOADTHRES", "DYNCCCHSWITCH", "FMSMAXOPCC", "HRATESPT", "OVERLOADINTV",
				"PAGINGREORGLAGTM", "PAGINGREORGSTARTTHRD", "PAGINGREORGSTOPTHRD", "PAGINGREORGSW", "PCHMSGPRIORSW",
				"RACHLDAVERSLOT", "RACHLOADALM", "RFRESINDPRD", "Fecha", "Red", "ipEntrada", "INTACESCONGCTRLSW",
				"PAGINGOVLDPROCOPTSW" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLCCUTRANSYS() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "BESTTDDCELLNUM", "CELL1800OFF",
				"CELL1800THRED", "CELL900OFF", "CELL900THRED", "FDDCELLOFF", "FDDCELLTHRED", "FDDFREQCNUM", "FDDQMIN",
				"FDDQMINOFFSET", "FDDQOFF", "FDDREP", "FDDRPTTHRESHOLD2ECNO", "FDDRPTTHRESHOLD2RSCP", "FDDRSCPMIN",
				"GSMFREQCNUM", "INVALBSICEN", "MEASURETYPE", "MSCVER", "POS2QUATER", "QCI", "QI", "QP", "QSEARCHC",
				"SCALEORDER", "SEARCH3G", "TDDCELLOFF", "TDDCELLRESELDIV", "TDDCELLTHRED", "TDDMIOPTIMIZEDALLOWED",
				"TDDMIPROHIBIT", "TDDSIOPTIMIZEDALLOWED", "Fecha", "Red", "ipEntrada", "EMRMSCAPIDESWITCH",
				"EMRMSCAPPROSWITCH", "FIRSTSI2QUATERMSGOPTSW", "POS2QUATER", "SI2QUATEROPTIMIZEDALLOWED" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLCHMGAD() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "AMRTCHHPRIORALLOW",
				"AMRTCHHPRIORLOAD", "TCHBUSYTHRES", "LOADSTATYPE", "Fecha", "Red", "ipEntrada", "AMRLOADOPTEN",
				"ASSOLRXLEVOFFSET", "BTRXPRIORITYSWITCH", "CELLOPPWRGRP", "CHALLOCATIONOPTSWITCH",
				"CHANALLOCBYUSRLOCINASS", "CHANALLOCBYUSRLOCINHO", "CHANINTERMESALLOW", "CHPWRINSUFFALLOWED",
				"CIRESTVALUE", "DLINTERFLEVLIMIT", "DLINTERFQUALLIMIT", "FREQLOADSHARETRAFFTHRSH",
				"FRHOREQREJWHENCELLCONGEST", "HALFRATEREPACKINGSWITCH", "HISPRIOALLOW", "HOOLRXLEVOFFSET",
				"HRIUOLDRATESELALLOW", "HSCSDBUSYTHRES", "IBCAAFRSOFTBLKTHRD", "IBCAAHRSOFTBLKTHRD", "IBCAALLOWED",
				"IBCAASSWAITMEASURERPTTIME", "IBCACALLINFOFILTERLEN", "IBCACALLSOFTBLOCKTHOFFSET",
				"IBCACALLTARGETCIROFFSET", "IBCACIRESTENHANCE", "IBCADLPATHLOSSOFF", "IBCAEHOASSWAITMEASURERPTTIME",
				"IBCAESTMTSCCOLLISIONALLOW", "IBCAFLEXTSCALLOWED", "IBCAFORCEDBTSSYNCALLOWED",
				"IBCAFREEBCCHCHANTHRSHOLD", "IBCAFREVLOPT", "IBCAFRSOFTBLKTHRD", "IBCAGETINTFSRCOPT",
				"IBCAHOASSWAITMEASURERPTTIME", "IBCAHOSOFTBLKTHRESHOLD", "IBCAHRSOFTBLKTHRD", "IBCAICDMRELEVOFFSET",
				"IBCAICDMSWITCH", "IBCAINITPCRXLEVDLOFFSET", "IBCAINITPCRXLEVULOFFSET", "IBCAINITPCRXQUALDLOFFSET",
				"IBCAINITPCRXQUALULOFFSET", "IBCAIUOPATHLOSSOFF", "IBCAMAIOUSMTD", "IBCAMAXINTFSRCNUM",
				"IBCANCELLPATHLOSSESTIMATE", "IBCANEWCALLCIROFFSET", "IBCANHOASSWAITMEASURERPTTIME",
				"IBCANONMEANCELLSTATNUM", "IBCAOPRREVISEFACTOR", "IBCAPATHLOSSOFF", "IBCAPDCHDYNTRANTMR",
				"IBCAPDDYNTRENHANCE", "IBCAPLFILTFACTOR", "IBCAQUEUEOPT", "IBCASCELLPATHLOSS", "IBCASOFTBLKSAICOFF",
				"IBCASOFTBLKSWITCH", "IBCASUBCHNHANDOVERALLOWED", "IBCATARGETCIRTHRSH", "IBCAUSEDIUOSUBLAY",
				"IBCAUSRDYNCMEASURENCELL", "IBCAWAFRSOFTBLKTHRD", "INNAMRTCHHPRIORLOAD", "INTERFPRIALLOW",
				"JUDGERXLEVWHENASSIGNHR", "LOADSHAREALLOW", "LOOSESDCCHLOADTHRED", "LOWRXLEVOLFORBIDOPTSW",
				"LOWRXLEVOLFORBIDSWITCH", "MCPACHAPPOPT", "MCPALOWTRAFFICTH", "MCPAOPTALG", "MINRXLEVWHENASSIGNHR",
				"MTSTURNOFFALG", "MTSTURNOFFHYST", "MTSTURNOFFTH", "OUTAMRTCHHPRIORLOAD", "PWRPRIORALLOW", "QLENSD",
				"QLENSI", "QTRUDNPWRLASTTIME", "QTRUDNPWRSTATTIME", "QTRUPWRSHARE", "QUALHOPRIALLOW", "SCENELOADTYPE",
				"SSLENSD", "SSLENSI", "TCHLOADOPTSWITCH", "TCHREQSUSPENDINTERVAL", "TCHTRIBUSYUNDERLAYTHR",
				"TCHTRICBUSYOVERLAYTHR", "TIGHTBCCHASSMAINBCCHLEV", "TIGHTBCCHASSMAINBCCHQUAL", "TIGHTSDCCHRXLEVTHRED",
				"TRXPRIALLOW", "TURNOFFLOADTYPE", "UPINTERFQUALLIMIT", "UPINTERLEVLIMIT", "UPRXLEVLASTTIME",
				"UPRXLEVSMOOTHPARA", "UPRXLEVSTATICTIME", "VAMOSAHSUSERDLSOFTBLOCKTHD", "WAITSDCCHIDLETIMER"

		};
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLCHMGBASIC() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "CELLMAXSD",
				"DIFFBANDSDCCHDYNADJ", "IDLESDTHRES", "SDCCHDYNADJTSNUM", "ENTCHADJALLOW", "Fecha", "Red", "ipEntrada",
				"ALLOWAMRHALFRATEUSERPERC", "ALLOWHALFRATEUSERPERC", "CHALLOCSTRATEGY", "DIFFBANDSDCCHUSINGOPTIMIZE",
				"DYNPBTSUPPORTED", "FACTORYMODE", "GRADEACCALLOW", "HIGHPRIUSERQUALFIRST", "IMMASSDIFFBANDALLOCTCHSW",
				"INTOCELLRESVCHANNUM", "MAINBCCHSDCCHNUM", "MINRESTIMETCH", "RSVCHMFORECNUM", "SDBACKTOTCHPUNISHSWITCH",
				"SDDYNADJRSVTCHNUM", "SDDYNADJRSVTCHSWITCH", "TIGHTBCCHSWITCH" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLFREQ() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "FREQ1", "FREQ2", "FREQ3",
				"FREQ4", "FREQ5", "FREQ6", "FREQ7", "FREQ8", "FREQ9", "FREQ10", "FREQ11", "FREQ12", "FREQ13", "FREQ14",
				"FREQ15", "FREQ16", "FREQ17", "FREQ18", "FREQ19", "FREQ20", "FREQ21", "FREQ22", "FREQ23", "FREQ24",
				"FREQ25", "FREQ26", "FREQ27", "FREQ28", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLGPRS() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "DLDCSPT", "DPIPARATRANMODE",
				"EDGE", "EGPRS2A", "GPRS", "NACCSPT", "NC2SPT", "PKTSI", "PSPAGINGCTRL", "RA", "SPTDPI",
				"SPTINTERRATINBSCPSHO", "SPTINTERRATOUTBSCPSHO", "SPTLTEINBSCPSHO", "SPTLTEOUTBSCPSHO",
				"SPTREDUCELATENCY", "SUPPORTDTM", "SUPPORTEDA", "Fecha", "Red", "ipEntrada", "ENACCSPT", "ENC2SPT" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLHO2GBA2() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "CELL2GBA2BCCH",
				"CELL2GBA2OPTSW", "CELL2GBA2TAG", "ITEM", "ITEMVALID", "Fecha", "Red", "ipEntrada",
				"CELL2GBA2OPTENHSW" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLHOAD() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "ABCDOWNQUALITY", "ABCUPQUALITY",
				"ABCWAITMAXTIME", "ACCTHRESLAYER", "ASSIGNBETTERCELLEN", "BANTIME", "CONTINTV", "HOTRYCNT",
				"INTERFEREDLEVOFF", "INTERFEREDLEVOFFOL", "INTERFEREDLQUALOFFOL", "INTERFEREDQUALOFF",
				"INTERFEREULEVOFF", "INTERFEREULEVOFFOL", "INTERFEREULQUALOFFOL", "INTERFEREUQUALOFF",
				"INTERFEROFFSWITCHOL", "KBIAS", "LAYHOLOADTH", "LOADACCTHRES", "LOADHOAD", "LOADHOHYSTADAPEN",
				"LOADHOPERIOD", "LOADHOSTEP", "LOADHOUSRRATIO", "LOADOFFSET", "MAXCNTNUM", "MAXRESEND",
				"OUTBSCLOADHOEN", "QCKSTATCNT", "QCKTIMETH", "QCKTRUECNT", "SDCCHWAITMREN", "SDCCHWAITMRTIMELEN",
				"SPEEDPUNISH", "SPEEDPUNISHT", "SYSFLOWLEV", "T3105", "TIGHTBCCHHOLOADTHRES", "TIGHTBCCHRXQUALTHRES",
				"TRIGTHRES", "TRIGTHRESLAYER", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLHOBASIC() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "BQHOEN", "HOCTRLSWITCH",
				"HOTHRES", "INTERRATCELLRESELEN", "INTERRATINBSCHOEN", "INTERRATOUTBSCHOEN", "INTRACELLFHHOEN",
				"INTRACELLHOEN", "LEVHOEN", "LOADHOEN", "LTECELLRESELEN", "PBGTHOEN", "FRINGEHOEN", "ULEDGETHRES",
				"DLEDGETHRES", "F2HHOLOADSTFSWITCH", "AMRFULLTOHALFHOTHRESH", "AMRFULLTOHALFHOALLOW",
				"NOAMRFULLTOHALFHOALLOW", "HALFTOFULLHODURATION", "FULLTOHALFHOPERIOD", "FULLTOHALFHOLOADSTF",
				"INTERFHOEN", "Fecha", "Red", "ipEntrada", "AMRF2HHOQUALTHFINE", "AMRFULLTOHALFHOATCBADJSTEP",
				"AMRFULLTOHALFHOATCBTHRESH", "AMRFULLTOHALFHOPATHADJSTEP", "AMRFULLTOHALFHOPATHTHRESH",
				"AMRFULLTOHALFHOQUALTHRESH", "AMRH2FHOOPTSW", "AMRH2FHOQUALFINE", "AMRHALFTOFULLHOATCBTHRESH",
				"AMRHALFTOFULLHOPATHTHRESH", "AMRHALFTOFULLHOQUALALLOW", "AMRHALFTOFULLHOQUALTHRESH",
				"AMRHALFTOFULLHOTHRESH", "ATCBHOEN", "BADQUALHOOPTALLOW", "COBSCMSCADJEN", "CONHOEN", "DLEDGETHRES",
				"EDGEHOHYSTEN", "EDGELAST1", "EDGESTAT1", "EDOUTHOADEN", "FHGAINOFFSET", "FULLTOHALFHOATCBOFFSET",
				"FULLTOHALFHODURATION", "FULLTOHALFHOLASTTIME", "FULLTOHALFHOPATHOFFSET", "FULLTOHALFHOSTATTIME",
				"HALFTOFULLATCBOFFSET", "HALFTOFULLHOLASTTIME", "HALFTOFULLHOPATHOFFSET", "HALFTOFULLHOSTATTIME",
				"HOCDCMINDWPWR", "HOCDCMINUPPWR", "HOCDCOVERLODEHOEN", "HOPRIOMODEN", "INFHHOLAST", "INFHHOSTAT",
				"INHOF2HTH", "INHOH2FTH", "INTERHOOPTALLOW", "INTERRATDIFFPROCSW", "INTERRATIURGINBSCHOEN",
				"INTERRATIURGVOICECARRYEN", "INTRACELLFHOPTSWITCH", "INTRACELLSINUSEREN", "LEVHOHYST", "LOADHOEN",
				"LTESAILAC", "LTESAIMCC", "LTESAIMNC", "LTESAISAC", "MRINTRPLOPTSWITCH", "NOAMRF2HHOQUALTHFINE",
				"NOAMRFULLTOHALFHOATCBADJSTEP", "NOAMRFULLTOHALFHOATCBTHRESH", "NOAMRFULLTOHALFHOPATHADJSTEP",
				"NOAMRFULLTOHALFHOPATHTHRESH", "NOAMRFULLTOHALFHOQUALTHRESH", "NOAMRFULLTOHALFTHRESH",
				"NOAMRH2FHOQUALFINE", "NOAMRHALFTOFULLHOATCBTHRESH", "NOAMRHALFTOFULLHOPATHTHRESH",
				"NOAMRHALFTOFULLHOQUALALLOW", "NOAMRHALFTOFULLHOQUALTHRESH", "NOAMRHALFTOFULLTHRESH", "PBGTHOEN",
				"QCKMVHOEN", "QUICKHOEN", "QUICKPBGTHOEN", "RXQCKFALLHOEN", "SIGCHANHOEN", "SRVCCHOEN", "TAHOEN",
				"TIGHTBCCHHOLASTTIME", "TIGHTBCCHHOSTATTIME", "ULEDGETHRES", "ULMCRITOPTSW"

		};
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCCELHOCTRL() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "BSMSPWRLEV", "BTSMESRPTPREPROC",
				"CONTHOMININTV", "INRBSCSDHOEN", "MINPWRLEVDIRTRY", "MRPREPROCFREQ", "NEWURGHOMININTV", "PENALTYEN",
				"PRIMMESPPT", "SDHOMININTV", "TCHHOMININTV", "Fecha", "Red", "ipEntrada", "BCCHHOPHOCOMPOPT",
				"ULRXLEVBOUNDPROTECTIONSW" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLHOEMG() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "DLQUALIMIT", "FLTPARAA1",
				"FLTPARAA2", "FLTPARAA3", "FLTPARAA4", "FLTPARAA5", "FLTPARAA6", "FLTPARAA7", "FLTPARAA8", "FLTPARAB",
				"HOCTRLSWITCH", "NODLMRHOALLOWLIMIT", "NODLMRHOEN", "NODLMRHOLASTTIME", "NODLMRHOQUALLIMIT",
				"NODLMRHOSTATTIME", "TALIMIT", "ULQUALIMIT", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLHOFITPEN() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "CBSIGNLEN", "CBTRAFFLEN",
				"DATAQUAFLTLEN", "DATASTRFLTLEN", "DATASTRUPFLTLEN", "DTXMEASUSED", "FAILSIGSTRPUNISH", "HOCTRLSWITCH",
				"INITUPFILEN", "LOADHOPUNISHINHERITSWITCH", "MBSIGNLEN", "MBTRAFFLEN", "MRMISSCOUNT", "NCELLFLTLEN",
				"NRBSDCCHFFLEN", "NRBTCHFFLEN", "PENALTYTIMER", "RQSIGNLEN", "RQTRAFFLEN", "RSCPENALTYTIMER",
				"SIGQUAFLTLEN", "SIGSTRFLTLEN", "SIGSTRUPFLTLEN", "SSBQPUNISH", "SSTAPUNISH", "TAFLTLEN",
				"TIMEAMRFHPUNISH", "TIMEBQPUNISH", "TIMETAPUNISH", "UMPENALTYTIMER", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLHOUTRANFDD() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "BET3GHOEN", "HOECNOTH3G",
				"HOOPTSEL", "HOPRETH2G", "HORSCPTH3G", "Fecha", "Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLIDLEAD() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "ACS", "CMETO", "PT", "Fecha",
				"Red", "ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLIDLEADBASIC() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "ATT", "BSAGBLKSRES",
				"BSPAMFRAMS", "CBA", "CBQ", "CRH", "CRO", "NCCPERMIT", "PI", "T3212", "TX", "Fecha", "Red",
				"ipEntrada" };
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_GCELLMAGRP() {
		String[] aParametrosCabecera = { "ControllerName", "CELLNAME", "CELLID", "CI", "FREQ1", "FREQ2", "HOPINDEX",
				"HOPMODE", "HSN", "TSC", "FREQ3", "FREQ4", "FREQ5", "FREQ6", "FREQ7", "FREQ8", "FREQ9", "FREQ10",
				"FREQ11", "FREQ12", "FREQ13", "FREQ14", "FREQ15", "FREQ16", "FREQ17", "FREQ18", "FREQ19", "FREQ20",
				"FREQ21", "FREQ22", "FREQ23", "FREQ24", "FREQ25", "FREQ26", "FREQ27", "FREQ28", "FREQ29", "FREQ30",
				"Fecha", "Red", "ipEntrada"

		};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGSM_GCELLOTHEXT() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","CDRTTRYFBDTHRES","CELLCOVERAGETYPE","CELLOVERCVGRXLEVDLTH","CELLOVERCVGTALTH","CELLWEAKCVGRXLEVDLTH","CELLWEAKCVGTALTH",
				"DLFREQADJ","DRTAGCELLSEL","DRXEN","FERSTATTH1","FERSTATTH2","FERSTATTH3","FERSTATTH4","FERSTATTH5","FERSTATTH6","FERSTATTH7","FRAMEOFFSET","FREQADJ","FREQADJVALUE","HSCSDSCANPER",
				"HSCSDTRAFFSET","INTERFTHRES0","INTERFTHRES1","INTERFTHRES2","INTERFTHRES3","INTERFTHRES4","INTERFTHRES5","INTERPERIOD","IURGINFOCTRL","MAINBCCHPWDTACTCHEN","MAINBCCHPWRDTEN",
				"MAINBCCHPWRDTETIME","MAINBCCHPWRDTRANGE","MAINBCCHPWRDTSTIME","PCHOCMPCON","PODECTHRES","POERRTHRES","RESERVEDIDLECH","RFMAXPWRDEC","SDDROPSTATDLLEV","SDDROPSTATDLQUAL","SDDROPSTATULLEV",
				"SDDROPSTATULQUAL","TCHDROPSTATDLFER","TCHDROPSTATDLLEV","TCHDROPSTATDLQUAL","TCHDROPSTATULFER","TCHDROPSTATULLEV","TCHDROPSTATULQUAL","TFRMSTARTTIME","VQILOWTHRD","VSWRERRTHRES",
				"VSWRUNJUSTTHRES","Fecha","Red","ipEntrada","AUXTRXRSVSW","BTSGRPFLEXABISLDRACTION","CSUPDATAABNMLCHKSW","DRFUVWSRSMMODE","DRTAGCELLSEL","HOUMTSCMINQPOLICY","HSCSDREADJUSTMENTSW",
				"IBCAINTFPUNISHTHR","ICADPSCNIDENTOPTSW","INTFBANDENHANCESW","INTFFILTERPERIOD","INTFREPROTPERIOD","MTSPRITYPE","PREEMPTBBTHD","PSULFREQADJ","RELEASEBBTHD","TRXPOOLALLOCTAFTH",
				"TRXPOOLPMTTAFTH","TRXPOOLRELTAFTH"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGCELLPRIEUTRANSYS() {
		String [] aParametrosCabecera= {"ControllerName","CELLNAME","CELLID","CI","BESTEUTRANCELLNUM","EUTRANPRI","EUTRANQRXLEVMIN","FASTRETURNFILTERSW","FASTRETURNMEASSPT","GERANPRI","HPRIO",
						"QPEUTRAN","THREUTRANHIGH","THREUTRANLOW","THREUTRANRPT","THRGSMLOW","THRPRISEARCH","THRUTRANHIGH","THRUTRANLOW","TRESEL","UTRANPRI","UTRANQRXLEVMIN","Fecha","Red","ipEntrada",
						"EUTRANFREQCNUM","EUTRANRESELECTOPTSW","FDDFASTRETURNRSRPTH","FDDLTEOFFSET","SI2QUATEROPTFORLTESW","TDDFASTRETURNRSRPTH","TDDLTEOFFSET"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGCELLPSBASE() {
		String [] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","ACCBURST","BSCVMAX","BSSPAGINGCOORDINATION","CTRLACKTYPE","DRXTIMERMAX","EARLYTBFEST","EGPRS11BITCHANREQ",
						"EXTUTBFNODATA","INACTSCHPERIOD","NCO","NMO","PANDEC","PANINC","PANMAX","PANMAX","PSDTXLAOPTISWITCH","RACOLOR","SGSNR","SPGCCCCHSUP","T3168","T3192","UPDTXACKPERIOD",
						"Fecha","Red","ipEntrada","ACCTECHREQSW","EXTNMOISW"};
		return aParametrosCabecera;
	}

	private static TreeMap<String, Integer> retornaMapaCabecera(String sCabeceraFichero) {
		TreeMap<String, Integer> mapaCabecera = new TreeMap<String, Integer>();
		String[] aParametrosCabecera = UtilidadesTexto.divideTextoEnTokens(sCabeceraFichero, ",\t");
		if ((aParametrosCabecera != null) && (aParametrosCabecera.length > 0)) {
			for (int iRecorreParametros = 0; iRecorreParametros < aParametrosCabecera.length; iRecorreParametros++) {
				if (!mapaCabecera.containsKey(aParametrosCabecera[iRecorreParametros])) {
					mapaCabecera.put(aParametrosCabecera[iRecorreParametros].replace("\"", ""), iRecorreParametros);
				}
			}
		}

		return mapaCabecera;
	}

	private static void borraFichero(File ficheroGSM_BTS) {
		// TODO Auto-generated method stub

	}

}