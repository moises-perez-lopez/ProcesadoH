
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
	public final static String sBSC = "NEID";
	public final static String sCellId = "CELLID";
	public final static String sFecha = "Fecha";
	public final static String sBTSId = "BTSID";
	public final static String sBTSNAME = "BTSNAME";
	public final static String sDeviceNo = "DEVICENO";
	public final static String sCellIdSource = "SRC2GNCELLID";
	public final static String sCellIdNeigh = "NBR2GNCELLID";
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
		creaArbolGSMBTS(ficheroGSM_BTS);
		escribeArbolBTS(ficheroArbolGSM_BTS);
		escribeFicheroBTS(ficheroGSM_BTS);
		borraFichero(ficheroGSM_BTS);

		// Creamos el fichero GSM_BTSRET
		File ficheroGSM_BTSRET = new File(sCarpetaEntradaT + "BTSRET.txt");
		File ficheroSalida_GSM_BTSRET = new File(sCarpetaSalidaT + "BTSRET.txt");
		creaArbolGSM_BTSRET(ficheroGSM_BTSRET);
		escribeFicheroGSM_BTSRET(ficheroSalida_GSM_BTSRET);
		borraFichero(ficheroGSM_BTSRET);

		// Creamos el fichero GSM_BTSRETDEVICEDATA
		File ficheroGSM_BTSRETDEVICEDATA = new File(sCarpetaEntradaT+"BTSRETDEVICEDATA.txt");
		File ficheroSalida_GSM_BTSRETDEVICEDATA = new File(sCarpetaSalidaT+"BTSRETDEVICEDATA.txt");
		escribeFicheroGSM_BTSRETDEVICEDATA(crearArbolGSM_BTSRETDEVICEDATA(ficheroGSM_BTSRETDEVICEDATA),ficheroSalida_GSM_BTSRETDEVICEDATA);
		borraFichero(ficheroGSM_BTSRETDEVICEDATA);

		// Creamos el fichero GSM_BTSRETSUBUNIT
		File ficheroGSM_BTSRETSUBUNIT = new File(sCarpetaEntradaT+"BTSRETSUBUNIT.txt");
		File ficheroSalida_GSM_BTSRETSUBUNIT = new File(sCarpetaSalidaT+"BTSRETSUBUNIT.txt");
		escribeFicheroGSM_BTSRETSUBUNIT(crearArbolGSM_BTSRETSUBUNIT(ficheroGSM_BTSRETSUBUNIT),ficheroSalida_GSM_BTSRETSUBUNIT);
				
		// Creamos el fichero G2GNCELL
		File ficheroGSM_G2GNCELL = new File(sCarpetaEntradaT+"G2GNCELL.txt");
		File ficheroSalida_GSM_G2GNCELL = new File(sCarpetaSalidaT+"G2GNCELL.txt");
//		crearArbolGSM_G2GNCELLAux(ficheroGSM_G2GNCELL);
		escribirFicheroGSM_G2GNCELL(creaArbolGSM_G2GNCELL(ficheroGSM_G2GNCELL),ficheroSalida_GSM_G2GNCELL);
		
		
		System.out.println("FIN");
	}

	
	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> creaMapaGGCELL(File ficheroGCELL) {
		String[] aParametrosGCELL = retornaParametrosABuscarGCELL();
		try (FileReader fr = new FileReader(ficheroGCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGCELL.get(sBSC)])) {
					mapaGCELLCellIdParametroValor.put(aValoresParametros[mapaCabeceraFicheroGCELL.get(sBSC)],
							new TreeMap<Integer, TreeMap<String, String>>());
				}
				if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sBSC)])
						.containsKey(UtilidadesTexto
								.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellId)]))) {
					mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sBSC)]).put(
							UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellId)]),
							new TreeMap<String, String>());
				}
				for (String sParametro : aParametrosGCELL) {
					if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sBSC)])
							.get(UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellId)]))
							.containsKey(sParametro)) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGCELL.get(sBSC)])
								.get(UtilidadesTexto
										.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGCELL.get(sCellId)]))
								.put(sParametro, aValoresParametros[mapaCabeceraFicheroGCELL.get(sParametro)]);
					}
				}
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception MapaGCELL");
		} catch (IOException e) {
			System.out.println("IOException MapaGCELL");
		} catch (Exception e) {
			System.out.println("Otra excepción MapaGCELL");
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
				for (String sNeid : mapaGCELLCellIdParametroValor.keySet()) {
					if (sNeid.equalsIgnoreCase(aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sBSC)])) {
						for (Integer iCellId : mapaGCELLCellIdParametroValor.get(sNeid).keySet()) {
							if (iCellId == UtilidadesTexto
									.dameValorEntero(aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sCellId)])) {
								if (mapaGCELLCellIdParametroValor.get(sNeid).get(iCellId).get(sFecha).equalsIgnoreCase(
										aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sFecha)])) {
									mapaGCELLCellIdParametroValor.get(sNeid).get(iCellId).put("BCCH",
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
		} catch (Exception e){
			System.out.println("Otra excepción GCELL con BCCH");
		}
		return mapaGCELLCellIdParametroValor;
	}

	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> creaArbolGCELL_GEXT2GCELL(
			File ficheroGEXT2GCELL) {
		String[] aParametrosGEXT2GCELL = retornaParametrosABuscarGEXT2GCELL();
		try (FileReader fr = new FileReader(ficheroGEXT2GCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGEXT2GCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sBSC)])) {
					mapaGCELLCellIdParametroValor.put(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sBSC)],
							new TreeMap<Integer, TreeMap<String, String>>());
				}
				if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sBSC)])
						.containsKey(UtilidadesTexto.dameValorEntero(
								aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get("EXT2GCELLID")]))) {
					mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sBSC)])
							.put(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get("EXT2GCELLID")]),
									new TreeMap<String, String>());
				}
				for (String sParametro : aParametrosGEXT2GCELL) {
					if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sBSC)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get("EXT2GCELLID")]))
							.containsKey(sParametro)) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sBSC)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get("EXT2GCELLID")]))
								.put(sParametro, aValoresParametros[mapaCabeceraFicheroGEXT2GCELL.get(sParametro)]);
					}
				}
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception GCELL_GEXT2GCELL");
		} catch (IOException e) {
			System.out.println("IOException GCELL_GEXT2GCELL");
		} catch (Exception e) {
			System.out.println("Otra excepción GCELL_GEXT2GCELL");
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
				if (!mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBSC)])) {
					mapaGSMBTS.put(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBSC)],
							new TreeMap<String, TreeMap<String, String>>());
				}
				if (!mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBSC)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])) {
					mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBSC)]).put(
							aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)], new TreeMap<String, String>());
				}
				if (!mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSNAME)])) {
					mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBSC)])
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

	private static void escribeFicheroBTS(File ficheroGSM_BTS) {
		File fichero_Salida_BTS = new File(
				"C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_2G_TXT\\BTS.txt");
		try (FileReader fr = new FileReader(ficheroGSM_BTS);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(fichero_Salida_BTS))) {
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
			bw.write("\"NEID\",\"BTSID\",\"Fecha\"\r\n");
			for (String sNeid : mapaGSMBTS.keySet()) {
				for (String sBTSid : mapaGSMBTS.get(sNeid).keySet()) {
					bw.write(sNeid + "," + sBTSid + "," + mapaGSMBTS.get(sNeid).get(sBTSid) + "\r\n");
				}
			}
		} catch (IOException e) {
			System.out.println("IOException");
		}

	}

	private static void escribeArbolGCELLFINAL(File ficheroGSM_GCELLFINAL) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroGSM_GCELLFINAL))) {

			for (String sParametro : retornaParametrosCabeceraGCELL()) {
				bw.write(sParametro + "\t");
			}

			bw.write("\r\n");
			for (String sNeid : mapaGCELLCellIdParametroValor.keySet()) {
				for (Integer iCellId : mapaGCELLCellIdParametroValor.get(sNeid).keySet()) {
					bw.write(sNeid + "\t" + iCellId + "\t");
					for (String sParametro : mapaGCELLCellIdParametroValor.get(sNeid).get(iCellId).keySet()) {
						bw.write(mapaGCELLCellIdParametroValor.get(sNeid).get(iCellId).get(sParametro) + "\t");
					}
					bw.write("\r\n");
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
				
				if ((mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)]))
						&& (mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]))) {
					sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).firstKey();
				}
				
				if (!mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])) {
					mapaGSMBTSRET.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)],
							new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)], new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>>());
				}
				if(!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
							.containsKey(sNombreBTS)){
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
								.put(sNombreBTS,new TreeMap<String, TreeMap<String, TreeMap<String, String>>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
						.get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
							.get(sNombreBTS)
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)], new TreeMap<String, TreeMap<String, String>>());
				}
				if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
						.get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get("DEVICENAME")])) {
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)]).get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
							.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get("DEVICENAME")],
									new TreeMap<String, String>());
				}
				for (String sParametro : aParametrosGSM_BTSRET) {
					if (!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
							.get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get("DEVICENAME")])
							.containsKey(sParametro)) {
						mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBSC)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
								.get(sNombreBTS)
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get("DEVICENAME")])
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
				if ((mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)]))
						&& (mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]))) {
					
					sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]).firstKey();
				
				}
				if(!arbolBTSDEVICEDATA.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])){
					arbolBTSDEVICEDATA.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>>());
				}
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
					.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])){
					
						arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
						.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>());
				}
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
						.containsKey(sNombreBTS)){
					
					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
					.put(sNombreBTS, new TreeMap<String,TreeMap<String, TreeMap<String,String>>>());
				}
				
				if((mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.containsKey(sNombreBTS))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
								.get(sNombreBTS).containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)]))){
					
					sDeviceName=mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
							.get(sNombreBTS).get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)]).firstKey();
				}
				
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
						.get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])){
					
					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
					.get(sNombreBTS)
					.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)], new TreeMap<String,TreeMap<String,String>>());
					
				}
				
				if(!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
						.get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
						.containsKey(sDeviceName)){
				
					arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
					.get(sNombreBTS)
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
					.put(sDeviceName, new TreeMap<String,String>());
					
				}
				for (String sParametro : aParametrosGSM_BTSRETDEVICEDATA) {
					if (!arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBTSId)])
							.get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sDeviceNo)])
							.get(sDeviceName)
							.containsKey(sParametro)) {
						arbolBTSDEVICEDATA.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETDEVICEDATA.get(sBSC)])
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
				if ((mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)]))
						&& (mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]))) {
					
					sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]).firstKey();
				
				}
				if(!arbolBTSRETSUBUNIT.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])){
					arbolBTSRETSUBUNIT.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>>());
				}
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
					.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])){
					
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
						.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)], new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>());
				}
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
						.containsKey(sNombreBTS)){
					
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
					.put(sNombreBTS, new TreeMap<String,TreeMap<String, TreeMap<String,String>>>());
				}
				
				if((mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
								.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)]))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.containsKey(sNombreBTS))
						&&(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
								.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
								.get(sNombreBTS).containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)]))){
					
					sDeviceName=mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
							.get(sNombreBTS).get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)]).firstKey();
				}
				
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
						.get(sNombreBTS)
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])){
					
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
					.get(sNombreBTS)
					.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)], new TreeMap<String,TreeMap<String,String>>());
					
				}
				
				if(!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
						.get(sNombreBTS)
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
						.containsKey(sDeviceName)){
				
					arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
					.get(sNombreBTS)
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
					.put(sDeviceName, new TreeMap<String,String>());
					
				}
				for (String sParametro : aParametrosGSM_BTSRETSUBUNIT) {
					if (!arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBTSId)])
							.get(sNombreBTS)
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sDeviceNo)])
							.get(sDeviceName)
							.containsKey(sParametro)) {
						arbolBTSRETSUBUNIT.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRETSUBUNIT.get(sBSC)])
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
	
	private static TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>>> crearArbolGSM_G2GNCELLAux(File ficheroGSM_G2GNCELL) {
		TreeMap <String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>> arbolG2GNCELLSource = new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>();
		TreeMap <String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>> arbolG2GNCELLNeigh = new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>();
		String[] aParametrosGSM_G2GNCELLSource = retornaParametrosABuscarG2GNCELLAux();
		String[] aParametrosGSM_G2GNCELLNeigh = retornaParametrosABuscarG2GNCELLAux();
		String sNombreCellNameSource;
		String sNombreCellNameNeigh;
		String sNCCSource;
		String sBCCSource;
		String sBCCHSource;
		String sNCCNeigh;
		String sBCCNeigh;
		String sBCCHNeigh;
		int i =0;
		try (FileReader fr = new FileReader(ficheroGSM_G2GNCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				sNombreCellNameSource = retornaCellNameSource(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);
				sNombreCellNameNeigh = retornaCellNameNeigh(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);	
				sNCCSource = retornaNCCSource(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);
				sBCCSource = retornaBCCSource(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);
				sBCCHSource = retornaBCCHSource(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);
				sNCCNeigh = retornaNCCNeigh(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);
				sBCCNeigh = retornaBCCNeigh(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);
				sBCCHNeigh = retornaBCCHNeigh(aValoresParametros,mapaCabeceraFicheroGSMG2GNCELL);
				if(!arbolG2GNCELLSource.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])){
					arbolG2GNCELLSource.put(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)], new TreeMap<String,TreeMap<String,TreeMap<String,String>>>());
				}
				if(!arbolG2GNCELLNeigh.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])){
					arbolG2GNCELLNeigh.put(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)], new TreeMap<String,TreeMap<String,TreeMap<String,String>>>());
				}
				if(!arbolG2GNCELLSource.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
					.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])){
					arbolG2GNCELLSource.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.put(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)], new TreeMap<String,TreeMap<String,String>>());
				}
				if(!arbolG2GNCELLNeigh.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)])){
					arbolG2GNCELLNeigh.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
							.put(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)], new TreeMap<String,TreeMap<String,String>>());
				}
				if(!arbolG2GNCELLSource.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])
						.containsKey(sNombreCellNameSource)){
						arbolG2GNCELLSource.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])
							.put(sNombreCellNameSource, new TreeMap<String,String>());
				}
				if(!arbolG2GNCELLNeigh.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)])
						.containsKey(sNombreCellNameNeigh)){
					arbolG2GNCELLNeigh.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)])
							.put(sNombreCellNameNeigh, new TreeMap<String,String>());
				}
				arbolG2GNCELLSource = anadirValoresParametrosSource(arbolG2GNCELLSource,sNombreCellNameSource,sNCCSource,sBCCSource,sBCCHSource);
				arbolG2GNCELLNeigh = anadirValoresParametrosNeigh(arbolG2GNCELLNeigh,sNombreCellNameNeigh,sNCCNeigh,sBCCHNeigh,sBCCHNeigh);
					i++;	
				System.out.println(i);
				sValoresParametros = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception crearArbolGSM_G2GNCELL");
		} catch (IOException e) {
			System.out.println("IOException crearArbolGSM_G2GNCELL");
		} 
		return null;
	}
	
	private static TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>> creaArbolGSM_G2GNCELL(File ficheroGSM_G2GNCELL) {
		TreeMap <String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>> arbolG2GNCELL = new TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>();
		TreeMap<String,String> arbolParametros = new TreeMap<String,String>();
		String[] aParametrosGSM_G2GNCELL = retornaParametrosABuscarG2GNCELL();
		int i =0;
		try (FileReader fr = new FileReader(ficheroGSM_G2GNCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if(!arbolG2GNCELL.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])){
					arbolG2GNCELL.put(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)], new TreeMap<String,TreeMap<String,TreeMap<String,String>>>());
				}
				if(!arbolG2GNCELL.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
					.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])){
					arbolG2GNCELL.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.put(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)], new TreeMap<String,TreeMap<String,String>>());
				}
				if(!arbolG2GNCELL.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)])){
						arbolG2GNCELL.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])
							.put(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)], new TreeMap<String,String>());
					}
				for (String sParametro : aParametrosGSM_G2GNCELL){
					if(!arbolParametros.containsKey(sParametro)){
						arbolParametros.put(sParametro, aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sParametro)]);
					}
				}
				
				//aqui añadir al arbol parametros, los valores que debería tener NCC, BCC, BCCH, NGB2NCC, NGBC2BCC, NGB2BCCH. Antes de añadirlo al mapa total.
				arbolG2GNCELL.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)])
						.putAll(arbolParametros);
				sValoresParametros = br.readLine();
				i++;
				System.out.println(i);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception crearArbolGSM_G2GNCELL");
		} catch (IOException e) {
			System.out.println("IOException crearArbolGSM_G2GNCELL");
		} 
		return arbolG2GNCELL;
	}
	
	private static void escribirFicheroGSM_G2GNCELL(TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>> arbolGSM_G2GNCELL, File ficheroSalida_GSM_G2GNCELL) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_G2GNCELL))) {
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_G2GNCELL()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			for (String sNeid : arbolGSM_G2GNCELL.keySet()) {
				for (String sCellIdSource : arbolGSM_G2GNCELL.get(sNeid).keySet()) {
					for(String sCellIdNeigh : arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).keySet()){
						StrBuilder compositorValores = new StrBuilder();
						compositorValores.append(sNeid);
						compositorValores.appendSeparator(",");
						compositorValores.append(sCellIdSource);
						compositorValores.appendSeparator(",");
						compositorValores.append(sCellIdNeigh);
						compositorValores.appendSeparator(",");
						for (String sParametro : arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).get(sCellIdNeigh).keySet()) {					
							compositorValores.append(arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).get(sCellIdNeigh).get(sParametro));
							compositorValores.appendSeparator(",");
						}
						bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
								
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception escribeFicheroGSM_G2GNCELL");
		} catch (IOException e) {
			System.out.println("IOException escribeFicheroGSM_G2GNCELL");
		} catch (Exception e) {
			System.out.println("Otra excepción escribeFicheroGSM_G2GNCELL");
		}
		
	}
	
	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>> anadirValoresParametrosSource(
			TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>> arbolG2GNCELL,
			String sNombreCellNameSource, String sNCCSource, String sBCCSource, String sBCCHSource) {
		for(String sNeid : arbolG2GNCELL.keySet()){
			for(String sCellId : arbolG2GNCELL.get(sNeid).keySet()){
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).containsKey(sNombreCellNameSource)){
					arbolG2GNCELL.get(sNeid).get(sCellId).put(sNombreCellNameSource,new TreeMap<String,String>());
				}
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).containsKey("NCC")){
					arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).put("NCC", sNCCSource);
				}
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).containsKey("BCC")){
					arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).put("BCC", sBCCSource);
				}
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).containsKey("BCCH")){
					arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).put("BCCH", sBCCHSource);
				}
			}
		}
		return arbolG2GNCELL;
	}
	
	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>> anadirValoresParametrosNeigh(
			TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String, String>>>> arbolG2GNCELL,
			String sNombreCellNameSource, String sNCCSource, String sBCCSource, String sBCCHSource) {
		for(String sNeid : arbolG2GNCELL.keySet()){
			for(String sCellId : arbolG2GNCELL.get(sNeid).keySet()){
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).containsKey(sNombreCellNameSource)){
					arbolG2GNCELL.get(sNeid).get(sCellId).put(sNombreCellNameSource,new TreeMap<String,String>());
				}
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).containsKey("NBR2GNCC")){
					arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).put("NBR2GNCC", sNCCSource);
				}
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).containsKey("NBR2GBCC")){
					arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).put("NBR2GBCC", sBCCSource);
				}
				if(!arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).containsKey("NBR2GBCCH")){
					arbolG2GNCELL.get(sNeid).get(sCellId).get(sNombreCellNameSource).put("NBR2GBCCH", sBCCHSource);
				}
			}
		}
		return arbolG2GNCELL;
	}

	private static String retornaCellNameSource(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		String sNombreCellNameSource = "---";
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&& (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])))) {
			
			sNombreCellNameSource = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
					.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])).firstKey();
		
		}
		return sNombreCellNameSource;
	}
	
	private static String retornaCellNameNeigh(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		String sNombreCellNameNeigh ="---";
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&& (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)])))) {
			
			sNombreCellNameNeigh = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)])
					.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)])).firstKey();
		
		}
		return sNombreCellNameNeigh;
	}
	
	private static String retornaNCCSource(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		ArrayList<String> aValoresParametrosRetorno = new ArrayList<String>();
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)]).containsKey("NCC"))){
			String sNCC = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)]).get("NCC");
			return sNCC;
		}
		return "---";
	}
	
	private static String retornaBCCSource(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)]).containsKey("BCC"))){
			String sBCC = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)]).get("BCC");
			return sBCC;
		}
		return "---";
	}
	
	private static String retornaBCCHSource(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)]).containsKey("BCCH"))){
			String sBCCH = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)]).get("BCCH");
			return sBCCH;
		}
		return "---";
	}
	
	private static String retornaNCCNeigh(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)]).containsKey("NCC"))){
			String sNCC = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)]).get("NCC");
			return sNCC;
		}
		return "---";
	}
	
	private static String retornaBCCNeigh(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)]).containsKey("BCC"))){
			String sBCC = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)]).get("BCC");
			return sBCC;
		}
		return "---";
	}
	
	private static String retornaBCCHNeigh(String[] aValoresParametros,
			TreeMap<String, Integer> mapaCabeceraFicheroGSMG2GNCELL) {
		if ((mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdSource)])))
				&&(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)]).containsKey("BCCH"))){
			String sBCCH = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sBSC)]).get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCellIdNeigh)]).get("BCCH");
			return sBCCH;
		}
		return "---";
	}


	private static void escribeFicheroGSM_G2GNCELL(TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,TreeMap<String,String>>>>>> arbolGSM_G2GNCELL, File ficheroSalida_GSM_G2GNCELL) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_G2GNCELL))) {
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraGSM_G2GNCELL()) {
				compositorCabecera.append(sParametro);
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0,compositorCabecera.size()-1)+"\r\n");
			for (String sNeid : arbolGSM_G2GNCELL.keySet()) {
				for (String sCellIdSource : arbolGSM_G2GNCELL.get(sNeid).keySet()) {
					for (String sCellNameSource : arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).keySet()) {
						for (String sCellIdNeigh : arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).get(sCellNameSource).keySet()) {
							for (String sCellNameNeigh : arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).get(sCellNameSource).get(sCellIdNeigh)
									.keySet()) {
								StrBuilder compositorValores = new StrBuilder();
								compositorValores.append(sNeid);
								compositorValores.appendSeparator(",");
								compositorValores.append(sCellIdSource);
								compositorValores.appendSeparator(",");
								compositorValores.append(sCellNameSource);
								compositorValores.appendSeparator(",");
								compositorValores.append(sCellIdNeigh);
								compositorValores.appendSeparator(",");
								compositorValores.append(sCellNameNeigh);
								compositorValores.appendSeparator(",");
								for (String sParametro : arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).get(sCellNameSource)
										.get(sCellIdNeigh).get(sCellNameNeigh).keySet()) {
									compositorValores.append(arbolGSM_G2GNCELL.get(sNeid).get(sCellIdSource).get(sCellNameSource).get(sCellIdNeigh).get(sCellNameNeigh).get(sParametro));
									compositorValores.appendSeparator(",");
								}
								bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
								
							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception escribeFicheroGSM_G2GNCELL");
		} catch (IOException e) {
			System.out.println("IOException escribeFicheroGSM_G2GNCELL");
		} catch (Exception e) {
			System.out.println("Otra excepción escribeFicheroGSM_G2GNCELL");
		}
		
	}

	
	private static String[] retornaParametrosABuscarGCELL() {
		String[] aParametrosABuscar = { "CELLNAME", "CI", "BCC", "NCC", "LAC", "Fecha" };
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarGEXT2GCELL() {
		String[] aParametrosABuscar = { "EXT2GCELLNAME", "CI", "BCC", "NCC", "BCCH", "LAC", "Fecha" };
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
	
	private static String[] retornaParametrosABuscarG2GNCELLAux() {
		String[] aParametrosABuscar = {"NCC","BCC","BCCH"};
		return aParametrosABuscar;
	}

	private static String[] retornaParametrosABuscarG2GNCELL() {
		String[] aParametrosABuscar = {	"ADJHOOFFSET","BETTERCELLLASTTIME","BETTERCELLSTATTIME","BQLASTTIME","BQMARGIN","BQNCELLABSTHRESSW","BQSTATTIME","CHAINNCELLTYPE","COMPCOCELLLASTTIME",
				"COMPCOCELLSTARTHYST","COMPCOCELLSTATTIME","COMPCOCELLSTOPHYST","DRHOLEVRANGE","EDGEADJLASTTIME","EDGEADJSTATTIME","EDGEHOHYST","EDOUTHOOFFSET","Fecha","GNCELLRANKPRI","HCSLASTTIME",
				"HCSSTATTIME","HOLASTTIME","HOSTATICTIME","HSRPNUSRNCTAG","INTELEVHOHYST","INTERCELLHYST","ISCHAINNCELL","LEVLAST","LEVSTAT","LOADHOPBGTMARGIN","MINOFFSET","NBR2GNCELLID","NCELLPRI",
				"NCELLPUNEN","NCELLPUNLEV","NCELLPUNSTPTH","NCELLPUNTM","NCELLPWRCOMPVALUE","NCELLTYPE","PBGTLAST","PBGTMARGIN","PBGTSTAT","Red","SRCHOCTRLSWITCH","TALASTTIME",
				"TASTATTIME","ULBQLASTTIME","ULBQSTATTIME","ipEntrada"};
		return aParametrosABuscar;
	}


	private static String[] retornaParametrosCabeceraGCELL() {
		String[] aParametrosABuscar = { "BSC", "CELLID", "BCC", "BCCH", "CELLNAME", "Fecha", "LAC", "NCC" };
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