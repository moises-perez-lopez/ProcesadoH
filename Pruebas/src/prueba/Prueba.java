
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
		File ficheroGCELL = new File(sCarpetaEntradaCasa + "GCELL.txt");
		creaMapaGGCELL(ficheroGCELL);

		// Concateno el valor de BCCH a los registros del mapa GCELL
		File ficheroGCELLFreq = new File(sCarpetaEntradaCasa + "GCELLFREQ.txt");
		creaArbolGCELLconBCCH(ficheroGCELLFreq);

		// Añadimos al mapa GCELL nuevos registros desde el fichero GEXT2GCELL
		File ficheroGEXT2GCELL = new File(sCarpetaEntradaCasa + "GEXT2GCELL.txt");
		creaArbolGCELL_GEXT2GCELL(ficheroGEXT2GCELL);

		// Creamos el fichero GCELL con el arbol GCELL FINAL
		File ficheroGSM_GCELLFINAL = new File(sCarpetaSalidaCasa + "Arbol_GCELL_FINAL.txt");
		escribeArbolGCELLFINAL(ficheroGSM_GCELLFINAL);

		// Creamos el fichero GSM_BTS
		File ficheroGSM_BTS = new File(sCarpetaEntradaCasa + "BTS.txt");
		File ficheroArbolGSM_BTS = new File(sCarpetaSalidaCasa + "Arbol_BTS.txt");
		File ficheroSalida_GSM_BTS = new File(sCarpetaSalidaCasa + "BTS.txt");
		creaArbolGSMBTS(ficheroGSM_BTS);
		escribeArbolBTS(ficheroArbolGSM_BTS);
		escribeFicheroBTS(ficheroGSM_BTS,ficheroSalida_GSM_BTS);
		borraFichero(ficheroGSM_BTS);

		// Creamos el fichero GSM_BTSRET
		File ficheroGSM_BTSRET = new File(sCarpetaEntradaCasa + "BTSRET.txt");
		File ficheroSalida_GSM_BTSRET = new File(sCarpetaSalidaCasa + "BTSRET.txt");
		creaArbolGSM_BTSRET(ficheroGSM_BTSRET);
		escribeFicheroGSM_BTSRET(ficheroSalida_GSM_BTSRET);
		borraFichero(ficheroGSM_BTSRET);

		// Creamos el fichero GSM_BTSRETDEVICEDATA
		File ficheroGSM_BTSRETDEVICEDATA = new File(sCarpetaEntradaCasa+"BTSRETDEVICEDATA.txt");
		File ficheroSalida_GSM_BTSRETDEVICEDATA = new File(sCarpetaSalidaCasa+"BTSRETDEVICEDATA.txt");
		escribeFicheroGSM_BTSRETDEVICEDATA(crearArbolGSM_BTSRETDEVICEDATA(ficheroGSM_BTSRETDEVICEDATA),ficheroSalida_GSM_BTSRETDEVICEDATA);
		borraFichero(ficheroGSM_BTSRETDEVICEDATA);

		// Creamos el fichero GSM_BTSRETSUBUNIT
		File ficheroGSM_BTSRETSUBUNIT = new File(sCarpetaEntradaCasa+"BTSRETSUBUNIT.txt");
		File ficheroSalida_GSM_BTSRETSUBUNIT = new File(sCarpetaSalidaCasa+"BTSRETSUBUNIT.txt");
		escribeFicheroGSM_BTSRETSUBUNIT(crearArbolGSM_BTSRETSUBUNIT(ficheroGSM_BTSRETSUBUNIT),ficheroSalida_GSM_BTSRETSUBUNIT);
		borraFichero(ficheroGSM_BTSRETSUBUNIT);
				
		// Creamos el fichero G2GNCELL
//		File ficheroGSM_G2GNCELL = new File(sCarpetaEntradaCasa+"G2GNCELL.txt");
//		File ficheroSalida_GSM_G2GNCELL = new File(sCarpetaSalidaCasa+"G2GNCELL.txt");
//		escribeFicheroGSM_G2GNCELL(ficheroGSM_G2GNCELL,ficheroSalida_GSM_G2GNCELL);
//		borraFichero(ficheroGSM_G2GNCELL);
		
		// Creamos el fichero GCELL2GBA1
		File ficheroGSM_GCELL2GBA1 = new File(sCarpetaEntradaCasa+"GCELL2GBA1.txt");
		File ficheroSalida_GSM_GCELL2GBA1 = new File(sCarpetaSalidaCasa+"GCELL2GBA1.txt");
		escribeFicheroGSM_GCELL2GBA1(ficheroGSM_GCELL2GBA1,ficheroSalida_GSM_GCELL2GBA1);
		
		// Creamos el fichero GCELLAMRQUL
		File ficheroGSM_GCELLAMRQUL = new File(sCarpetaEntradaCasa+"GCELLAMRQUL.txt");
		File ficheroSalida_GSM_GCELLAMRQUL = new File(sCarpetaSalidaCasa+"GCELLAMRQUL.txt");
		escribeFicheroGSM_GCELLAMRQUL(ficheroGSM_GCELLAMRQUL,ficheroSalida_GSM_GCELLAMRQUL);
		
		// Creamos el fichero GCELLBASICPARA 
		File ficheroGSM_GCELLBASICPARA = new File(sCarpetaEntradaCasa+"GCELLBASICPARA.txt");
		File ficheroSalida_GSM_GCELLBASICPARA = new File(sCarpetaEntradaCasa+"GCELLBASICPARA.txt");
		escribeFicheroGSM_GCELLBASICPARA(ficheroGSM_GCELLBASICPARA,ficheroSalida_GSM_GCELLBASICPARA);
		System.out.println("FIN");
	}

	
	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> creaMapaGGCELL(File ficheroGCELL) {
		TreeMap<String,String> arbolParametrosGCELL = retornaParametrosABuscarGCELL();
		try (FileReader fr = new FileReader(ficheroGCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])) {
					
					mapaGCELLCellIdParametroValor.put(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)],
							new TreeMap<Integer, TreeMap<String,String>>());
				}
				if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])
						.containsKey(UtilidadesTexto
								.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]))) {
					
					mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)]).put(
							UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]),
							new TreeMap<String,String>());
				}
				
				for (String sParametro : arbolParametrosGCELL.keySet()) {
					if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])
							.get(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]))
							.containsKey(arbolParametrosGCELL.get(sParametro))) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sNeid)])
								.get(UtilidadesTexto
										.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellIndex)]))
								.put(arbolParametrosGCELL.get(sParametro), aValoresParametros[mapaCabeceraFicheroGCELL.get(sParametro)]);
						
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
					if(sNeid.equalsIgnoreCase(sBSC)){
						for (Integer iCellIndex : mapaGCELLCellIdParametroValor.get(sNeid).keySet()) {
							if (iCellIndex == UtilidadesTexto
								.dameValorEntero(aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sCellId)])) {
									if(mapaGCELLCellIdParametroValor.get(sNeid).get(iCellIndex).get(sFecha).equalsIgnoreCase(aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sFecha)])){
											mapaGCELLCellIdParametroValor.get(sNeid).get(iCellIndex).put("BCCH", aValoresParametros[mapaCabeceraFicheroCELLFreq.get("FREQ1")]);			
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
		TreeMap<String,String> arbolParametrosGEXT2GCELL = retornaParametrosABuscarGEXT2GCELL();
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
							.get(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sEXT2GCellIndex)]))
							.containsKey(arbolParametrosGEXT2GCELL.get(sParametro))) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sNeid)])
								.get(UtilidadesTexto
										.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sEXT2GCellIndex)]))
								.put(arbolParametrosGEXT2GCELL.get(sParametro), aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sParametro)]);
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
		} catch (Exception e){
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
			compositorCabecera.append("\"Fecha\""+"\r\n");
			for (String sNeid : mapaGSMBTS.keySet()) {
				for (String sBTSid : mapaGSMBTS.get(sNeid).keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(sBTSid);
					compositorValores.appendSeparator(",");
					compositorValores.append(mapaGSMBTS.get(sNeid).get(sBTSid)+"\r\n");
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
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			for (String sNeid : mapaGCELLCellIdParametroValor.keySet()) {
				for (Integer iCellId : mapaGCELLCellIdParametroValor.get(sNeid).keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(iCellId);
					compositorValores.appendSeparator(",");
					for (String sParametro : retornaParametrosCabeceraGCELL()) {
						if((!sParametro.equalsIgnoreCase("BSC"))&&(!sParametro.equalsIgnoreCase("CELLINDEX"))){
							compositorValores.append(mapaGCELLCellIdParametroValor.get(sNeid).get(iCellId).get(sParametro));
							compositorValores.appendSeparator(",");
						}
					}
					bw.write(compositorValores.toString().substring(0,compositorValores.size()-1)+"\r\n");
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
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)], new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>());
				}
				if(!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
							.containsKey(sNombreBTS)){
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
								.put(sNombreBTS,new TreeMap<String, TreeMap<String, TreeMap<String, String>>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
						.get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
							.get(sNombreBTS)
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)], new TreeMap<String, TreeMap<String, String>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
						.get(sNombreBTS)
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
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
							.get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceName)])
							.containsKey(sParametro)) {
						mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
								.get(sNombreBTS)
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
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
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
									compositorValores.append(mapaGSMBTSRET.get(sNeid).get(sBTSId).get(sBTSName).get(sDeviceNo).get(sDeviceName).get(sParametro));
									compositorValores.appendSeparator(",");
								}
								bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
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

	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> crearArbolGSM_BTSRETDEVICEDATA(File ficheroGSM_BTSRETDEVICEDATA) {
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
				if(!arbolBTSDEVICEDATA.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])){
					arbolBTSDEVICEDATA.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>>());
				}
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
					.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])){
					
						arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>());
				}
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
						.containsKey(sNombreBTS)){
					
					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
					.put(sNombreBTS, new TreeMap<String,TreeMap<String, TreeMap<String,String>>>());
				}
				
				if((mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.containsKey(sNombreBTS))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.get(sNombreBTS).containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)]))){
					
					sDeviceName=mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
							.get(sNombreBTS).get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)]).firstKey();
				}
				
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
						.get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])){
					
					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
					.get(sNombreBTS)
					.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)], new TreeMap<String,TreeMap<String,String>>());
					
				}
				
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
						.get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
						.containsKey(sDeviceName)){
				
					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
					.get(sNombreBTS)
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
					.put(sDeviceName, new TreeMap<String,String>());
					
				}
				for (String sParametro : aParametrosGSM_BTSRETDEVICEDATA) {
					if (!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
							.get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
							.get(sDeviceName)
							.containsKey(sParametro)) {
						arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.get(sNombreBTS)
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
								.get(sDeviceName)
								.put(sParametro, aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sParametro)]);
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
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			for (String sNeid : arbolGSMBTSRETDEVICE.keySet()) {
				for (String sBTSId : arbolGSMBTSRETDEVICE.get(sNeid).keySet()) {
					for (String sBTSName : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).keySet()) {
						for (String sDeviceNo : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName).keySet()) {
							for (String sDeviceName : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName).get(sDeviceNo)
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
								for (String sParametro : arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName)
										.get(sDeviceNo).get(sDeviceName).keySet()) {
								
									compositorValores.append(arbolGSMBTSRETDEVICE.get(sNeid).get(sBTSId).get(sBTSName).get(sDeviceNo).get(sDeviceName).get(sParametro));
									compositorValores.appendSeparator(",");
								}
								bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
								
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
	
	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>> crearArbolGSM_BTSRETSUBUNIT(File ficheroGSM_BTSRETSUBUNIT) {
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
				if(!arbolBTSRETSUBUNIT.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])){
					arbolBTSRETSUBUNIT.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>>());
				}
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
					.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])){
					
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>());
				}
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
						.containsKey(sNombreBTS)){
					
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
					.put(sNombreBTS, new TreeMap<String,TreeMap<String, TreeMap<String,String>>>());
				}
				
				if((mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.containsKey(sNombreBTS))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.get(sNombreBTS).containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)]))){
					
					sDeviceName=mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
							.get(sNombreBTS).get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)]).firstKey();
				}
				
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
						.get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])){
					
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
					.get(sNombreBTS)
					.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)], new TreeMap<String,TreeMap<String,String>>());
					
				}
				
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
						.get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
						.containsKey(sDeviceName)){
				
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
					.get(sNombreBTS)
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
					.put(sDeviceName, new TreeMap<String,String>());
					
				}
				for (String sParametro : aParametrosGSM_BTSRETSUBUNIT) {
					if (!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
							.get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
							.get(sDeviceName)
							.containsKey(sParametro)) {
						arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.get(sNombreBTS)
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
								.get(sDeviceName)
								.put(sParametro, aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sParametro)]);
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
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			for (String sNeid : arbolGSM_BTSRETSUBUNIT.keySet()) {
				for (String sBTSId : arbolGSM_BTSRETSUBUNIT.get(sNeid).keySet()) {
					for (String sBTSName : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).keySet()) {
						for (String sDeviceNo : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName).keySet()) {
							for (String sDeviceName : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName).get(sDeviceNo)
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
								for (String sParametro : arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName)
										.get(sDeviceNo).get(sDeviceName).keySet()) {
								
									compositorValores.append(arbolGSM_BTSRETSUBUNIT.get(sNeid).get(sBTSId).get(sBTSName).get(sDeviceNo).get(sDeviceName).get(sParametro));
									compositorValores.appendSeparator(",");
								}
								bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
								
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
		TreeMap <String,String> arbolMapaValor = new TreeMap<String,String>();
		String[] aParametrosGSM_G2GNCELL = retornaParametrosABuscarG2GNCELL();
		String sRC2GNCELLNAME = "---";
		String sNRC2GNCELLNAME = "---";
		String sNCC = "---";
		String sBCC = "---";
		String sBCCH = "---";
		String NBR2GBCCH = "---";
		String NBR2GBCC = "---";
		String NBR2GNCC = "---";
		try (FileReader fr = new FileReader(ficheroGSM_G2GNCELL); BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalidaGSM_G2GNCELL)) ){
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_G2GNCELL()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolMapaValor.put("BSC_SOURCE", aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)]);
				arbolMapaValor.put(sCellIndexSource, aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]);
				arbolMapaValor.put(sCellIndexNeigh, aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]);
								
				if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))){
					sRC2GNCELLNAME=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.get("CELLNAME");
					
					arbolMapaValor.put("SRC2GNCELLNAME", sRC2GNCELLNAME);
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.containsKey("NCC")){
						sNCC=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.get("NCC");
						arbolMapaValor.put("NCC",sNCC);
					}	
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.containsKey("BCC")){
						sBCC=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.get("BCC");
						arbolMapaValor.put("BCC",sBCC);
					}
				}
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.containsKey("BCCH")){
						sBCCH=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)].replaceAll("\"", ""))
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexSource)]))
							.get("BCCH");
						arbolMapaValor.put("BCCH",sBCCH);
					}

				
				if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))){
					sNRC2GNCELLNAME=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.get("CELLNAME");
						arbolMapaValor.put("NBR2GNCELLNAME", sNRC2GNCELLNAME);
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.containsKey("NCC")){
						NBR2GNCC=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
								.get("NCC");
						arbolMapaValor.put("NBR2GNCC", NBR2GNCC);
					}
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.containsKey("BCC")){
						NBR2GBCC=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
									.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
									.get("BCC");
						arbolMapaValor.put("NBR2GBCC", NBR2GBCC);
					}
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
							.containsKey("BCCH")){
						NBR2GNCC=mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIndexNeigh)]))
								.get("BCCH");
						arbolMapaValor.put("NBR2GBCCH", NBR2GBCCH);
					}
				}
				
				for (String sParametro : aParametrosGSM_G2GNCELL){
					if(!arbolMapaValor.containsKey(sParametro)){
						arbolMapaValor.put(sParametro, aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sParametro)]);
					}
				}
				escribirFichero(bw,arbolMapaValor,retornaParametrosCabeceraGSM_G2GNCELL());
				sValoresParametros = br.readLine();	
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception crearArbolGSM_G2GNCELL");
		} catch (IOException e) {
			System.out.println("IOException crearArbolGSM_G2GNCELL");
		} 
	}
	
	private static void escribirFichero(BufferedWriter bw , TreeMap<String,String> arbolGSM_G2GNCELL,String[] arrayCabecera) {
		
			StrBuilder compositorValores = new StrBuilder();
			for(String sParametro : arrayCabecera){
					compositorValores.append(arbolGSM_G2GNCELL.get(sParametro));
					compositorValores.appendSeparator(",");
			}
		try {
				bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
			} catch (IOException e) {
				System.out.println("Error en la escritura del fichero");
			}		
	}

	private static void escribeFicheroGSM_GCELL2GBA1(File ficheroGSM_GCELL2GBA1, File ficheroSalida_GSM_GCELL2GBA1) {		
		TreeMap<String,String> arbolGSM_GCELL2GBA1;
		String[] aParametrosGSM_GCELL2GBA1 = retornaParametrosABuscarGCELL2GBA1();
		String sNombreCelda = "---";
		String sCellIdentificador = "---";

		try (FileReader fr = new FileReader(ficheroGSM_GCELL2GBA1); BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_GCELL2GBA1))){
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMGCELL2GBA1 = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_GCELL2GBA1()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolGSM_GCELL2GBA1 = new TreeMap<String,String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolGSM_GCELL2GBA1.put("ControllerName",aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sNeid)]);
				arbolGSM_GCELL2GBA1.put(sCellIndex, aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sCellIndex)]);
				
				if(mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sNeid)])){
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sCellIndex)]))){
						sNombreCelda= mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sCellIndex)])).get("CELLNAME");
						arbolGSM_GCELL2GBA1.put("CELLNAME", sNombreCelda);
					}		
				}
				if(mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sNeid)])){
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sCellIndex)]))){
						sCellIdentificador = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sCellIndex)])).get("CELLID");
						arbolGSM_GCELL2GBA1.put("CI", sCellIdentificador);
					}			
				}	
				for(String sParametro : aParametrosGSM_GCELL2GBA1){
					arbolGSM_GCELL2GBA1.put(sParametro, aValoresParametros[mapaCabeceraFicheroGSMGCELL2GBA1.get(sParametro)]);
				}
				escribirFichero(bw,arbolGSM_GCELL2GBA1,retornaParametrosCabeceraGSM_GCELL2GBA1());
				sValoresParametros = br.readLine();
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception en GCELL2GBA1");
		} catch (IOException e) {
			System.out.println("IO exception en GCELL2GBA1");
		}
		
	}
	private static void escribeFicheroGSM_GCELLAMRQUL(File ficheroGSM_GCELLAMRQUL, File ficheroSalida_GSM_GCELLAMRQUL) {
		TreeMap<String,String> arbolGSM_GCELLAMRQUL;
		String[] aParametrosGSM_GCELLAMRQUL = retornaParametrosABuscarGCELLAMRQUL();
		String sNombreCelda = "---";
		String sCellIdentificador = "---";
		try (FileReader fr = new FileReader(ficheroGSM_GCELLAMRQUL); BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_GCELLAMRQUL))){
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGCELLAMRQUL = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_GCELLAMRQUL()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolGSM_GCELLAMRQUL = new TreeMap<String,String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolGSM_GCELLAMRQUL.put("ControllerName",aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)]);
				arbolGSM_GCELLAMRQUL.put(sCellIndex, aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]);
				if(mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)])){
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]))){
						sNombreCelda= mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)])).get("CELLNAME");
						arbolGSM_GCELLAMRQUL.put("CELLNAME", sNombreCelda);
					}		
				}
				if(mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)])){
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)]))){
						sCellIdentificador = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sCellIndex)])).get("CELLID");
						arbolGSM_GCELLAMRQUL.put("CI", sCellIdentificador);
					}			
				}	
				for(String sParametro : aParametrosGSM_GCELLAMRQUL){
					arbolGSM_GCELLAMRQUL.put(sParametro, aValoresParametros[mapaCabeceraFicheroGCELLAMRQUL.get(sParametro)]);
				}
				escribirFichero(bw,arbolGSM_GCELLAMRQUL,retornaParametrosCabeceraGSM_GCELLAMRQUL());
				sValoresParametros = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void escribeFicheroGSM_GCELLBASICPARA(File ficheroGSM_GCELLBASICPARA,
			File ficheroSalida_GSM_GCELLBASICPARA) {
		TreeMap<String,String> arbolGSM_GCELLBASICPARA;
		String[] aParametrosGSM_GCELLBASCISPARA = retornaParametrosABuscarGCELLBASICPARA();
		String sNombreCelda = "---";
		String sCellIdentificador = "---";
		try (FileReader fr = new FileReader(ficheroGSM_GCELLBASICPARA); BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_GCELLBASICPARA))){
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGCELLBASICPARA = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_GCELLBASICPARA()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolGSM_GCELLBASICPARA = new TreeMap<String,String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolGSM_GCELLBASICPARA.put("ControllerName",aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)]);
				arbolGSM_GCELLBASICPARA.put(sCellIndex, aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]);
				if(mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])){
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]))){
						sNombreCelda= mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)])).get("CELLNAME");
						arbolGSM_GCELLBASICPARA.put("CELLNAME", sNombreCelda);
					}		
				}
				if(mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])){
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)]))){
						sCellIdentificador = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sCellIndex)])).get("CELLID");
						arbolGSM_GCELLBASICPARA.put("CI", sCellIdentificador);
					}			
				}	
				for(String sParametro : aParametrosGSM_GCELLBASCISPARA){
					arbolGSM_GCELLBASICPARA.put(sParametro, aValoresParametros[mapaCabeceraFicheroGCELLBASICPARA.get(sParametro)]);
				}
				escribirFichero(bw,arbolGSM_GCELLBASICPARA,retornaParametrosCabeceraGSM_GCELLAMRQUL());
				sValoresParametros = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static TreeMap<String,String> retornaParametrosABuscarGCELL() {
		TreeMap<String,String> arbolParametrosABuscar = new TreeMap<String,String>();
		arbolParametrosABuscar.put("CELLNAME","CELLNAME");
		arbolParametrosABuscar.put("CI","CELLID");
		arbolParametrosABuscar.put("NCC","NCC");
		arbolParametrosABuscar.put("BCC","BCC");
		arbolParametrosABuscar.put("LAC","LAC");
		arbolParametrosABuscar.put("Fecha","Fecha");
		arbolParametrosABuscar.put("Red","Red");
		arbolParametrosABuscar.put("ipEntrada","ipEntrada");
		return arbolParametrosABuscar;
	}

	private static TreeMap<String,String> retornaParametrosABuscarGEXT2GCELL() {
		TreeMap<String,String> aParametrosABuscar = new TreeMap<String,String>();
		aParametrosABuscar.put("EXT2GCELLNAME","CELLNAME");
		aParametrosABuscar.put("CI","CELLID");
		aParametrosABuscar.put("NCC","NCC");
		aParametrosABuscar.put("BCC","BCC");
		aParametrosABuscar.put("BCCH","BCCH");
		aParametrosABuscar.put("LAC","LAC");
		aParametrosABuscar.put("Fecha","Fecha");
		aParametrosABuscar.put("Red","Red");
		aParametrosABuscar.put("ipEntrada","ipEntrada");
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
		String[] aParametrosABuscar = {"AER","CELLID","CONNCN1","CONNCN2","CONNPN1","CONNPN2","CONNSN1","CONNSN2","CONNSRN1",
				"CONNSRN2","SUBUNITNO", "TILT", "Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarG2GNCELL() {
		String[] aParametrosABuscar = {	"ADJHOOFFSET","BETTERCELLLASTTIME","BETTERCELLSTATTIME","BQLASTTIME","BQMARGIN","BQNCELLABSTHRESSW","BQSTATTIME","CHAINNCELLTYPE","COMPCOCELLLASTTIME",
				"COMPCOCELLSTARTHYST","COMPCOCELLSTATTIME","COMPCOCELLSTOPHYST","DRHOLEVRANGE","EDGEADJLASTTIME","EDGEADJSTATTIME","EDGEHOHYST","EDOUTHOOFFSET","Fecha","GNCELLRANKPRI","HCSLASTTIME",
				"HCSSTATTIME","HOLASTTIME","HOSTATICTIME","HSRPNUSRNCTAG","INTELEVHOHYST","INTERCELLHYST","ISCHAINNCELL","LEVLAST","LEVSTAT","LOADHOPBGTMARGIN","MINOFFSET","NCELLPRI",
				"NCELLPUNEN","NCELLPUNLEV","NCELLPUNSTPTH","NCELLPUNTM","NCELLPWRCOMPVALUE","NCELLTYPE","PBGTLAST","PBGTMARGIN","PBGTSTAT","Red","SRCHOCTRLSWITCH","TALASTTIME",
				"TASTATTIME","ULBQLASTTIME","ULBQSTATTIME","ipEntrada"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGCELL2GBA1() {
		String[] aParametrosABuscar = {"CELL2GBA1BCCH","CELL2GBA1OPTENHSW","CELL2GBA1OPTSW","CELL2GBA1TAG","Fecha","ITEM","ITEMVALID","Red","ipEntrada"};
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGCELLAMRQUL() {
		String[] aParametrosABuscar = {"DLQUALIMITAMRFR","DLQUALIMITAMRHR","RXLEVOFF","RXQUAL1","RXQUAL10","RXQUAL11","RXQUAL12","RXQUAL2",
				"RXQUAL3","RXQUAL4","RXQUAL5","RXQUAL6","RXQUAL7","RXQUAL8","RXQUAL9","ULQUALIMITAMRFR","ULQUALIMITAMRHR","Fecha","Red",
				"ipEntrada"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGCELLBASICPARA() {
		String[] aParametrosABuscar ={"BTSADJUST","CALLRESTABDIS","CELL8PSKPOWERLEVEL","CELLSCENARIO","CMEPRIOR","DIRECTRYEN","DIVERT16QAMDELAY",
				"DIVERT32QAMDELAY","DIVERT8PSKDELAY","DNPCEN","DYNOPENTRXPOWER","ENCRY","ENCRYPTIONALGORITHM1ST","ENCRYPTIONALGORITHM2ND",
				"ENCRYPTIONALGORITHM3RD","ENCRYPTIONALGORITHM4TH","ENCRYPTIONALGORITHM5TH","ENCRYPTIONALGORITHM6TH","ENCRYPTIONALGORITHM7TH",
				"FASTCALLTCHTHRESHOLD","FRDLDTX","FRULDTX","ICBALLOW","IMMASSCBB","IMMASSEN","LAYER","LEVELRPT","MAXTA","MICCSWITCH",
				"NBAMRTFOSWITCH","PDCH2SDEN","POWERREDUCE16QAM","POWERREDUCE32QAM","RTPSWITCH","RXMIN","SDDYN","SVHOCNGSTTHR","TIMESLOTVOLADJALLOW",
				"UPPCEN","Fecha","Red","ipEntrada","COMPSWITCH","DLCOMBFILTERSW","GMSKDELAYDYNADJSW","IMMTCHLOADTHRES","PDCHTOSDCCHADJUSTPREFSW",
				"SIGDECODEENSWITCH","SVHODTXDTCTIMER","SVHOHODELAYTIMER","ULROTSW","UMAISSWITCH"};
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosCabeceraGCELL() {
		String[] aParametrosABuscar = { "BSC", "CELLINDEX", "CELLNAME", "CELLID", "NCC", "BCC" ,"BCCH", "LAC", "Fecha", "Red","ipEntrada" };	
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
		String[] aParametrosCabecera = { "ControllerName", "BTSID", "BTSNAME", "DEVICE", "DEVICENAME", "BAND1","BAND2", "BAND3", "BAND4",
				"BEARING","SUBUNITNO","TILT", "SERIALNO", "Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;	
	}
	
	private static String[] retornaParametrosCabeceraGSM_BTSRETSUBUNIT(){
		String[] aParametrosCabecera = {"ControllerName","BTSID","BTSNAME","DEVICE","DEVICENAME","AER","CELLID","CONNCN1","CONNCN2","CONNPN1",
				"CONNPN2","CONNSN1","CONNSN2","CONNSRN1","CONNSRN2","SUBUNITNO","TILT","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGSM_G2GNCELL() {
		String[] aParametrosCabecera = {"BSC_SOURCE","SRC2GNCELLNAME","NBR2GNCELLNAME","INTERCELLHYST","MINOFFSET","PBGTMARGIN","BQMARGIN","ISCHAINNCELL","NCELLTYPE","INTELEVHOHYST","DRHOLEVRANGE",
						"SRCHOCTRLSWITCH","CHAINNCELLTYPE","EDGEADJSTATTIME","EDGEADJLASTTIME","LEVSTAT","LEVLAST","PBGTSTAT","PBGTLAST","BETTERCELLSTATTIME","BETTERCELLLASTTIME","HOSTATICTIME","HOLASTTIME",
						"HCSSTATTIME","HCSLASTTIME","BQSTATTIME","BQLASTTIME","TASTATTIME","TALASTTIME","ULBQSTATTIME","ULBQLASTTIME","LOADHOPBGTMARGIN","NCELLPRI","EDOUTHOOFFSET","NCELLPUNEN",
						"NCELLPUNSTPTH","NCELLPUNTM","NCELLPUNLEV","ADJHOOFFSET","EDGEHOHYST","NCC","BCC","BCCH","NBR2GNCC","NBR2GBCC","NBR2GBCCH","BQNCELLABSTHRESSW","COMPCOCELLLASTTIME",
						"COMPCOCELLSTARTHYST","COMPCOCELLSTATTIME","COMPCOCELLSTOPHYST","GNCELLRANKPRI","HSRPNUSRNCTAG","NCELLPWRCOMPVALUE","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGSM_GCELL2GBA1() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","CELL2GBA1BCCH","CELL2GBA1OPTSW","CELL2GBA1TAG","ITEM",
				"ITEMVALID","Fecha","Red","ipEntrada","CELL2GBA1OPTENHSW"};

		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraGSM_GCELLAMRQUL() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","DLQUALIMITAMRFR","DLQUALIMITAMRHR","RXLEVOFF", "RXQUAL1",
						"RXQUAL10","RXQUAL11","RXQUAL12","RXQUAL2","RXQUAL3","RXQUAL4","RXQUAL5","RXQUAL6","RXQUAL7","RXQUAL8","RXQUAL9",
						"ULQUALIMITAMRFR","ULQUALIMITAMRHR","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGSM_GCELLBASICPARA() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","BTSADJUST","CALLRESTABDIS","CELL8PSKPOWERLEVEL","CELLSCENARIO","CMEPRIOR","DIRECTRYEN","DIVERT16QAMDELAY",
				"DIVERT32QAMDELAY","DIVERT8PSKDELAY","DNPCEN","DYNOPENTRXPOWER","ENCRY","ENCRYPTIONALGORITHM1ST","ENCRYPTIONALGORITHM2ND",
				"ENCRYPTIONALGORITHM3RD","ENCRYPTIONALGORITHM4TH","ENCRYPTIONALGORITHM5TH","ENCRYPTIONALGORITHM6TH","ENCRYPTIONALGORITHM7TH",
				"FASTCALLTCHTHRESHOLD","FRDLDTX","FRULDTX","ICBALLOW","IMMASSCBB","IMMASSEN","LAYER","LEVELRPT","MAXTA","MICCSWITCH",
				"NBAMRTFOSWITCH","PDCH2SDEN","POWERREDUCE16QAM","POWERREDUCE32QAM","RTPSWITCH","RXMIN","SDDYN","SVHOCNGSTTHR","TIMESLOTVOLADJALLOW",
				"UPPCEN","Fecha","Red","ipEntrada","COMPSWITCH","DLCOMBFILTERSW","GMSKDELAYDYNADJSW","IMMTCHLOADTHRES","PDCHTOSDCCHADJUSTPREFSW",
				"SIGDECODEENSWITCH","SVHODTXDTCTIMER","SVHOHODELAYTIMER","ULROTSW","UMAISSWITCH"};
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