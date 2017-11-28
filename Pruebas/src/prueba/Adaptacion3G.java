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

public class Adaptacion3G {
	
	private static final String sNeid = "NEID";
	private static final String sRNCID = "RNCID";
	private static final String sNODEBID = "NODEBID";
	private static final String sNODEBNAME = "NODEBNAME";
	private static final String sControllerName = "ControllerName";
	private static final String sCellId = "CELLID";
	
	
	private static TreeMap<String,String> arbolUCELLRncId;
	private static TreeMap<String,TreeMap<Integer,TreeMap<String,String>>> arbolUCELL_NODEB;
	public static void main(String[] args) {
		
		File carpetaEntrada = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_3G_BD\\");
		File carpetaSalida = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_3G_BD_PROCESADOS\\");
		
		
		// Creamos el arbol UCELL, NEID -> RNCID
		File fichero_UCELLRNCID = new File(carpetaEntrada, "U2GNCELL.txt");
		creaArbolUCELL_RNCID(fichero_UCELLRNCID);
		
		// Creamos el árbol UCELL, NEID -> CELLIDS -> CELLNAME, NODEBNAME 
		File ficheroUCELL = new File(carpetaEntrada,"UCELL.txt");
		creaArbolUCELL_UCELL(ficheroUCELL);
		
		// Añadimos al árbol UCELL, NEID -> CELLIDS -> CELLNAME, NODEBNAME, NODEBID
		File ficheroUCELL_ENODEB = new File(carpetaEntrada,"UNODEB.txt");
		creaArbolUCELL_UCELL_UNODEB(ficheroUCELL_ENODEB);
		
		// Escribimos el Arbol UCELL-RNCID
//		File ficheroArbolUCELL_RNCID = new File(carpetaSalida ,"arbolUCELLRNCID.txt");
//		escribeArbol_UCELL_RNCID(ficheroArbolUCELL_RNCID);
		
		// Escribimos el Arbol UCELL-NODEBNAME-CELLID
		File ficheroArbolUCELL_NODEBNAME_CELLID = new File(carpetaSalida,"arbol_UCELL_NEID_CELLID_CELLNAME_NODEBNAME_NODEBID.txt");
		escribeArbol_UCELL(ficheroArbolUCELL_NODEBNAME_CELLID);
		
						
		// Creamos el fichero UCELL
		File ficheroSalida_UCELL = new File(carpetaSalida,"UCELL.txt");
		escribeFicheroUCELL(ficheroUCELL,ficheroSalida_UCELL,retornaParametrosCabeceraUCELL(),retornaParametrosABuscarUCELL());
		
		// Creamos el fichero UCELLADAPTRACH
		File fichero_UCELLUCELLADAPTRACH = new File(carpetaEntrada,"UCELLADAPTRACH.txt");
		File ficheroSalida_UCELLADAPTRACH = new File(carpetaSalida,"UCELLADAPTRACH.txt");
		escribeFicheroUCELL_1(fichero_UCELLUCELLADAPTRACH,ficheroSalida_UCELLADAPTRACH, retornaParametrosCabeceraUCELLADAPTRACH(),retornaParametrosABuscarUCELLADAPTRACH());
		
		// Creamos el fichero UCELLALGOSWITCH
		File fichero_UCELLALGOSWITCH = new File(carpetaEntrada,"UCELLALGOSWITCH.txt");
		File ficheroSalida_UCELLALGOSWITCH = new File(carpetaSalida, "UCELLALGOSWITCH.txt");
		escribeFicheroUCELL_1(fichero_UCELLALGOSWITCH,ficheroSalida_UCELLALGOSWITCH,retornaParametrosCabeceraUCELLALGOSWITCH(),retornaParametrosABuscarUCELLALGOSWITCH());
		
		// Creamos el fichero UCELLCAC
		File fichero_UCELLCAC = new File(carpetaEntrada,"UCELLCAC.txt");
		File ficheroSalida_UCELLCAC = new File(carpetaSalida,"UCELLCAC.txt");
		escribeFicheroUCELL_1(fichero_UCELLCAC,ficheroSalida_UCELLCAC,retornaParametrosCabeceraUCELLCAC(),retornaParametrosABuscarUCELLCAC());
		
		// Creamos el fichero UCELLCOALGOENHPARA   NO ESTÁN DECLARADAS NI CABECERA NI PARAMETROS A BUSCAR
		File fichero_UCELLCOALGOENHPARA = new File(carpetaEntrada,"UCELLCOALGOENHPARA.txt");
		File ficheroSalida_UCELLCOALGOENHPARA = new File(carpetaSalida,"UCELLCOALGOENHPARA.txt");
		escribeFicheroUCELL_1(fichero_UCELLCOALGOENHPARA,ficheroSalida_UCELLCOALGOENHPARA,retornaParametrosCabeceraUCELLCOALGOENHPARA(),
				retornaParametrosABuscarUCELLCOALGOENHPARA());
		System.out.println("FIN");		
		
	}
	

	private static void creaArbolUCELL_RNCID(File ficheroRNCID) {
		arbolUCELLRncId = new TreeMap<String,String>();
		try (FileReader fr = new FileReader(ficheroRNCID);
			BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroUCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if(!arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])){
					arbolUCELLRncId.put(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)],
					aValoresParametros[mapaCabeceraFicheroUCELL.get(sRNCID)]);
				}
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Excepción File Not found en arbol UCELL 1");
		} catch (IOException e) {
			System.out.println("Excepción IO en arbol UCELL 1");
		}
	}
	
	private static void creaArbolUCELL_UCELL(File ficheroUCELL) {
		
		arbolUCELL_NODEB = new TreeMap<String,TreeMap<Integer,TreeMap<String,String>>>();
		try (FileReader fr = new FileReader(ficheroUCELL);
			BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroUCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if(!arbolUCELL_NODEB.containsKey(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])){
					arbolUCELL_NODEB.put(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)], new TreeMap<Integer,TreeMap<String,String>>());
				}
				if(!arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("CELLID")]))){
					arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
					.put(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("CELLID")]),new TreeMap<String,String>());
				}
				if(!arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
						.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("CELLID")]))
						.containsKey("CELLNAME")){
					arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
					.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("CELLID")]))
					.put("CELLNAME",aValoresParametros[mapaCabeceraFicheroUCELL.get("CELLNAME")]);
				}
				if(!arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
						.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("CELLID")]))
						.containsKey("NODEBNAME")){
					arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
					.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("CELLID")]))
					.put("NODEBNAME",aValoresParametros[mapaCabeceraFicheroUCELL.get("NODEBNAME")]);
				}
				
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Excepción File Not found en arbol UCELL_UCELL");
		} catch (IOException e) {
			System.out.println("Excepción IO en arbol UCELL_UCELL");
		}
		
	}
	
	private static void escribeArbol_UCELL_RNCID(File ficheroArbol_UCELLRNCID) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbol_UCELLRNCID))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"RNCID\"");
			bw.write(compositorCabecera.toString()+"\r\n");
			for (String sNeid : arbolUCELLRncId.keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(arbolUCELLRncId.get(sNeid));
					compositorValores.appendSeparator(",");
					bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
			}
		} catch (IOException e) {
			System.out.println("IO  Exception");
		}
		
	}
	
	private static void escribeArbol_UCELL(File ficheroArbolUCELL_NODEBNAME_CELLID){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbolUCELL_NODEBNAME_CELLID))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"CELLID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"CELLNAME\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"NODEBID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"NODEBNAME\"");
			bw.write(compositorCabecera.toString()+"\r\n");
			for (String sNeid : arbolUCELL_NODEB.keySet()) {
				for(Integer iCellid : arbolUCELL_NODEB.get(sNeid).keySet()){
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(iCellid);
					compositorValores.appendSeparator(",");
					for(String sParametro : arbolUCELL_NODEB.get(sNeid).get(iCellid).keySet()){
						compositorValores.append(arbolUCELL_NODEB.get(sNeid).get(iCellid).get(sParametro));
						compositorValores.appendSeparator(",");
					}
						bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
				}
			}
		} catch (IOException e) {
			System.out.println("IO  Exception en escribeArbol_UCELL");
		}
	}
	
	private static void creaArbolUCELL_UCELL_UNODEB(File fichero_UCELLUNODEB) {
		int i = 0;
		try (FileReader fr = new FileReader(fichero_UCELLUNODEB);
				BufferedReader br = new BufferedReader(fr)) {
				String sCabeceraFichero = br.readLine();
				TreeMap<String, Integer> mapaCabeceraFicheroUNODEB = retornaMapaCabecera(sCabeceraFichero);
				String sValoresParametros = br.readLine();
				while (sValoresParametros != null) {
					String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
					if(arbolUCELL_NODEB.containsKey(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)])){
						for(Integer iCellId : arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)]).keySet()){
							if((arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)]).get(iCellId).containsKey("NODEBNAME"))
									&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)]).get(iCellId).get("NODEBNAME")
											.equalsIgnoreCase(aValoresParametros[mapaCabeceraFicheroUNODEB.get("NODEBNAME")]))){			
								arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)])
								.get(iCellId)
								.put("NODEBID", aValoresParametros[mapaCabeceraFicheroUNODEB.get("NODEBID")]);
							}
						}
					}
					sValoresParametros = br.readLine();	
				}
			} catch (FileNotFoundException e) {
				System.out.println("Excepción File Not found en arbol UCELL 1");
			} catch (IOException e) {
				System.out.println("Excepción IO en arbol UCELL 1");
			}
		
	}
	
	private static void escribeFicheroUCELL(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sRncId = "---";
		String sNodeBId = "---";

		try (FileReader fr = new FileReader(ficheroEntrada_UCELL);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_UCELL))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFichero = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : aParametrosCabecera) {
				compositorCabecera.append("\""+sParametro+"\"");
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolParametroValor = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolParametroValor.put(sControllerName, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				arbolParametroValor.put(sCellId, aValoresParametros[mapaCabeceraFichero.get(sCellId)]);

				if (arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					sRncId = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
					arbolParametroValor.put("RNCID", sRncId);
				}
				if(arbolUCELL_NODEB.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
						&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])))
						&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).containsKey("NODEBNAME"))
						&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).containsKey("NODEBID"))
						&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBNAME")
								.equalsIgnoreCase(aValoresParametros[mapaCabeceraFichero.get("NODEBNAME")]))){
					sNodeBId = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBID");
					arbolParametroValor.put("NODEBID", sNodeBId);
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
			System.out.println("File not found exception en escribe fichero GSM");
		} catch (IOException e) {
			System.out.println("IO exception en escribe fichero GSM");
		}
		
	}
	
	private static void escribeFicheroUCELL_1(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sRncId = "---";
		String sNodeBId = "---";
		String sNodeBName = "---";
		String sCellName = "---";

		try (FileReader fr = new FileReader(ficheroEntrada_UCELL);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_UCELL))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFichero = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : aParametrosCabecera) {
				compositorCabecera.append("\""+sParametro+"\"");
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolParametroValor = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolParametroValor.put(sControllerName, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				arbolParametroValor.put(sCellId, aValoresParametros[mapaCabeceraFichero.get(sCellId)]);

				if (arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					sRncId = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
					arbolParametroValor.put("RNCID", sRncId);
				}
				if(arbolUCELL_NODEB.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
						&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])))){		
					sNodeBName = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBNAME");
					sNodeBId = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBID");
					sCellName = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("CELLNAME");
					arbolParametroValor.put("NODEBID", sNodeBId);
					arbolParametroValor.put("NODEBNAME", sNodeBName);
					arbolParametroValor.put("CELLNAME", sCellName);
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
			System.out.println("File not found exception en escribe fichero GSM");
		} catch (IOException e) {
			System.out.println("IO exception en escribe fichero GSM");
		}
		
	}
	
	private static void escribeFicheroUCELL_ALGOSWITCH(File fichero_UCELLALGOSWITCH, File ficheroSalida_UCELLALGOSWITCH,
			String[] aParametrosCabeceraUCELLALGOSWITCH, String[] aParametrosABuscarUCELLALGOSWITCH) {
		TreeMap<String, String> arbolParametroValor;
		String sRncId = "---";
		String sNodeBId = "---";
		String sNodeBName = "---";
		String sCellName = "---";

		try (FileReader fr = new FileReader(fichero_UCELLALGOSWITCH);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_UCELLALGOSWITCH))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFichero = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : aParametrosCabeceraUCELLALGOSWITCH) {
				compositorCabecera.append("\""+sParametro+"\"");
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolParametroValor = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolParametroValor.put(sControllerName, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				arbolParametroValor.put(sCellId, aValoresParametros[mapaCabeceraFichero.get(sCellId)]);

				if (arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					sRncId = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
					arbolParametroValor.put("RNCID", sRncId);
				}
				if(arbolUCELL_NODEB.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
						&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])))){		
					sNodeBName = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBNAME");
					sNodeBId = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBID");
					sCellName = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("CELLNAME");
					arbolParametroValor.put("NODEBID", sNodeBId);
					arbolParametroValor.put("NODEBNAME", sNodeBName);
					arbolParametroValor.put("CELLNAME", sCellName);
				}
					
				for (String sParametro : aParametrosABuscarUCELLALGOSWITCH) {
					if(mapaCabeceraFichero.containsKey(sParametro)){
						arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabeceraUCELLALGOSWITCH);
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found exception en escribe fichero GSM");
		} catch (IOException e) {
			System.out.println("IO exception en escribe fichero GSM");
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
	private static String[] retornaParametrosCabeceraRET(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraRETDEVICEDATA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraRETSUBUNIT(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELL(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","ACTSTATUS","BANDIND","BLKSTATUS","CCHCNOPINDEX","CELLHETFLAG","CFGRACIND","CIO","CNOPGRPINDEX",
				"DPGID","DSSFLAG","DSSSMALLCOVMAXTXPOWER","LAC","LOCELL","MAXTXPOWER","NEEDSELFPLANFLAG","NINSYNCIND","NOUTSYNCIND","PSCRAMBCODE","RAC","SAC","SN","SPGID","SRN","SSN","TCELL","TRLFAILURE",
				"TXDIVERSITYIND","UARFCNDOWNLINK","UARFCNUPLINK","UARFCNUPLINKIND","VPLIMITIND","Fecha", "Red", "ipEntrada","CELLCOVERAGETYPE","DLTPCPATTERN01COUNT","LOGICRNCID","PRIORITY","REMARK",
				"SPLITCELLIND","TIMER"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraU2GNCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLADAPTRACH(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","Fecha","Red","ipEntrada","OPTICONSTANTVALUE","OPTIPOWERRAMPSTEP","OPTIPREAMBLERETRANSMAX",
				"OPTISTARTLOADSTATE","OPTISTOPHYST","RANDOMACCESSCLEARTHD","RANDOMACCESSCONGESTTHD"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLALGOSWITCH(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","ADAPALGOSWITCH","BERATEREDUCEONFAIRNESSSWITCH","CELLCALLSHOCKSWITCH","CELLCAPACITYAUTOHANDLESWITCH",
				"CELLMOCNDEMARCSWITCH","CELLOMENHSWITCH","CELLSYSINFOENHSWITCH","CSRABCACOPTSWITCH","DLCMAVOIDCHGSCCODESWITCH","DLPWRLDCUESELSWITCH","HSPAENHSWITCH","HSPAPLUSSWITCH","NBMCACALGOSWITCH",
				"NBMCACALGOSWITCH2","NBMDLCACALGOSELSWITCH","NBMLDCALGOSWITCH","NBMLDCIRATUESELSWITCH","NBMLDCUESELSWITCH","NBMMACHSRESETALGOSELSWITCH","NBMSFLDCUESELSWITCH","NBMULCACALGOSELSWITCH","OFFLOADSWITCH",
				"RRCCACCHOICE","RRCCECODECACCHOICE","Fecha", "Red", "ipEntrada","DCHENHSWITCH","DEMARCPREEMPTSWITCH","DLSFADMALGOSWITCH","LOGICRNCID","NBMSEREXPCACALGOSWITCH"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLCAC(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","BACKGROUNDNOISE","BGNABNORMALTHD",
				"BGNADJUSTTIMELEN","BGNENDTIME","BGNEQUSERNUMTHD","BGNOPTSWITCH","BGNPERSISTSWITCH","BGNSTARTTIME","BGNSWITCH",
				"BGNUPDATETHD","CELLENVTYPE","CELLULEQUNUMCAPACITY","DEFPCPICHECNO","DLCCHLOADRSRVCOEFF","DLCELLTOTALTHD","DLCONVAMRTHD",
				"DLCONVNONAMRTHD","DLHOCECODERESVSF","DLHOTHD","DLHSUPARSVDFACTOR","DLMBMSRSVDFACTOR","DLNRTRRCCACCECODERESVSF",
				"DLOTHERRRCCACCECODERESVSF","DLOTHERTHD","DLRRCCECODERESVSF","DLTOTALEQUSERNUM","HSDPABEPBRTHD","HSDPAMAXGBPTHD",
				"HSDPASTRMPBRTHD","HSUPAEQUALPRIORITYUSERPBRTHD","HSUPAHIGHPRIORITYUSERPBRTHD","HSUPALOWPRIORITYUSERPBRTHD",
				"HSUPAMAXGBPTHD","LOADBALANCERATIO","MAXEFACHUSERNUM","MAXERACHUSERNUM","MAXHSDPAUSERNUM","MAXHSUPAUSERNUM",
				"MAXULTXPOWERFORBAC","MAXULTXPOWERFORCONV","MAXULTXPOWERFORINT","MAXULTXPOWERFORSTR","MTCHMAXPWR","MTCHMAXSF",
				"MTCHRSVPWR","MTCHRSVSF","NONHPWRFORGBPPREEMP","NRTRRCCACTHDOFFSET","OTHERRRCCACTHDOFFSET","ROTCONTROLTARGET",
				"RTRRCCACTHDOFFSET","TERMCONVUSINGHORESTHD","ULCCHLOADFACTOR","ULCELLTOTALTHD","ULHOCERESVSF","ULHSDPCCHRSVDFACTOR",
				"ULICLDCOPTSWITCH","ULNONCTRLTHDFORAMR","ULNONCTRLTHDFORHO","ULNONCTRLTHDFORNONAMR","ULNONCTRLTHDFOROTHER",
				"ULNRTRRCCACCERESVSF","ULOTHERRRCCACCERESVSF","ULRRCCERESVSF","ULTOTALEQUSERNUM","Fecha", "Red", "ipEntrada",
				"BGNFILTERCOEF","BGNOPTENHSWITCH","BGNULLOADTHD","CSRLMAXDLPWROFFSET","DEFAULTECNO","DLINTERFACTOR","DLOTHERTHDFORPT",
				"FREEUSERGBPRSVD","HHOPROCPCPREAMBLE","HHOPROCSRBDELAY","HSUPANONSERVINTERFEREFACTOR","IFFASTMULRLDLCECODERESVSF",
				"IFFASTMULRLDLTHD","IFFASTMULRLINITPWRPO","IFFASTMULRLULCERESVSF","LOGICRNCID","MAXUPAUSERNUMDYNADJFACTOR",
				"OFFSETECNO","PREAMBLEACKTHD","PTTPCPREAMBLE","PTTSRBDELAY","RRCPROCPCPREAMBLE","RRCPROCSRBDELAY","SHOINITPWRPO"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLCOALGOENHPARA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLCONNREDIR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLDRD(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLDYNSHUTDOWN(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLFRC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHOCOMM(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHSDPA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHSDPCCH(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHSUPA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTERFREQHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTERRATHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTERRATHONCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTRAFREQHO(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLLDM(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLLDR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLLICENSE(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLNFREQPRIOINFO(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLRLPWR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLSELRESEL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLURA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUDRD(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUEXT2GCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUEXT3GCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUHOCOMM(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTERFREQHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTERFREQNCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTERRATHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTRAFREQNCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULAC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULOCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULTECELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULTENCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUNODEBLDR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUNRNC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUPCPICH(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraURAC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosABuscarRET(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarRETDEVICEDATA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarRETSUBUNIT(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarU2GNCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELL(){
		String[] aParametrosCabecera={"ACTSTATUS","BANDIND","BLKSTATUS","CCHCNOPINDEX","CELLCOVERAGETYPE","CELLHETFLAG","CELLNAME","CFGRACIND","CIO","CNOPGRPINDEX","DLTPCPATTERN01COUNT",
				"DPGID","DSSFLAG","DSSSMALLCOVMAXTXPOWER","Fecha","LAC","LOCELL","LOGICRNCID","MAXTXPOWER","NEEDSELFPLANFLAG","NINSYNCIND","NODEBNAME","NOUTSYNCIND","PRIORITY","PSCRAMBCODE",
				"RAC","REMARK","Red","SAC","SN","SPGID","SPLITCELLIND","SRN","SSN","TCELL","TIMER","TRLFAILURE","TXDIVERSITYIND","UARFCNDOWNLINK","UARFCNUPLINK","UARFCNUPLINKIND","VPLIMITIND","ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLADAPTRACH(){
		String[] aParametrosCabecera={"NODEBNAME","CELLNAME","Fecha","Red","ipEntrada","OPTICONSTANTVALUE","OPTIPOWERRAMPSTEP","OPTIPREAMBLERETRANSMAX",
				"OPTISTARTLOADSTATE","OPTISTOPHYST","RANDOMACCESSCLEARTHD","RANDOMACCESSCONGESTTHD"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLALGOSWITCH(){
		String[] aParametrosCabecera={"ADAPALGOSWITCH","BERATEREDUCEONFAIRNESSSWITCH","CELLCALLSHOCKSWITCH","CELLCAPACITYAUTOHANDLESWITCH","CELLMOCNDEMARCSWITCH","CELLOMENHSWITCH","CELLSYSINFOENHSWITCH",
				"CSRABCACOPTSWITCH","DCHENHSWITCH","DEMARCPREEMPTSWITCH","DLCMAVOIDCHGSCCODESWITCH","DLPWRLDCUESELSWITCH","DLSFADMALGOSWITCH","Fecha","HSPAENHSWITCH","HSPAPLUSSWITCH","LOGICRNCID","NBMCACALGOSWITCH",
				"NBMCACALGOSWITCH2","NBMDLCACALGOSELSWITCH","NBMLDCALGOSWITCH","NBMLDCIRATUESELSWITCH","NBMLDCUESELSWITCH","NBMMACHSRESETALGOSELSWITCH","NBMSEREXPCACALGOSWITCH","NBMSFLDCUESELSWITCH",
				"NBMULCACALGOSELSWITCH","OFFLOADSWITCH","RRCCACCHOICE","RRCCECODECACCHOICE"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLCAC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLCOALGOENHPARA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLCONNREDIR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLDRD(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLDYNSHUTDOWN(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLFRC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLHOCOMM(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLHSDPA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLHSDPCCH(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLHSUPA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLINTERFREQHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLINTERRATHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLINTERRATHONCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLINTRAFREQHO(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLLDM(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLLDR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLLICENSE(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLNFREQPRIOINFO(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLRLPWR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLSELRESEL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLURA(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUDRD(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUEXT2GCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUEXT3GCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUHOCOMM(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUINTERFREQHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUINTERFREQNCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUINTERRATHOCOV(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUINTRAFREQNCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarULAC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarULOCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarULTECELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarULTENCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUNODEBLDR(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUNRNC(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUPCPICH(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarURAC(){
		String[] aParametrosCabecera={};
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
	
//	private static void comprobarCoincidencias(){
//		ArrayList<String> faltanEnSalidaYEstanEnLaEntrada = new ArrayList<String>();
//		ArrayList<String> faltanEnEntradaYEstanEnLaSalida = new ArrayList<String>();
//		String[] entrada ={"RNC_SOURCE","RNCID","CELLID","GSMCELLINDEX","BLINDHOFLAG","BLINDHOPRIO","NPRIOFLAG","NPRIO","CIOOFFSET","QOFFSET1SN","QRXLEVMIN","TPENALTYHCSRESELECT","TEMPOFFSET1","DRDECN0THRESHHOLD","SIB11IND","SIB12IND","MBDRFLAG","MBDRPRIO","SRVCCSWITCH","INTERRATADJSQHCS","NIRATOVERLAP","CELLNAME","GSMCELLNAME","CELLNAME-NCELLNAME"};
//		String[] salida ={"ACTSTATUS","BANDIND","BLKSTATUS","CCHCNOPINDEX","CELLCOVERAGETYPE","CELLHETFLAG","CELLID","CELLNAME","CFGRACIND","CIO","CNOPGRPINDEX","DLTPCPATTERN01COUNT","DPGID","DSSFLAG","DSSSMALLCOVMAXTXPOWER","Fecha","LAC","LOCELL","LOGICRNCID","MAXTXPOWER","NEEDSELFPLANFLAG","NEID","NINSYNCIND","NODEBNAME","NOUTSYNCIND","PRIORITY","PSCRAMBCODE","RAC","REMARK","Red","SAC","SN","SPGID","SPLITCELLIND","SRN","SSN","TCELL","TIMER","TRLFAILURE","TXDIVERSITYIND","UARFCNDOWNLINK","UARFCNUPLINK","UARFCNUPLINKIND","VPLIMITIND","ipEntrada"};
//		
//		for(String parametroEntrada : entrada){
//			boolean bandera=false;
//			for(String parametroEnSalida : salida){
//				if(parametroEntrada.equalsIgnoreCase(parametroEnSalida)){
//					bandera=true;
//				}	
//			}
//			if(bandera==false){
//				faltanEnEntradaYEstanEnLaSalida.add(parametroEntrada);
//			}
//		}
//		for(String parametroEnSalida : entrada){
//			boolean bandera=false;
//			for(String parametroEntrada : salida){
//				if(parametroEnSalida.equalsIgnoreCase(parametroEntrada)){
//					bandera=true;
//				}	
//			}
//			if(bandera==false){
//				faltanEnEntradaYEstanEnLaSalida.add(parametroEnSalida);
//			}
//		}
//		System.out.println("Los siguientes parámetros NO salida SÍ entrada: ");
//		for(String parametro : faltanEnSalidaYEstanEnLaEntrada){
//			System.out.println(parametro);
//		}
//		System.out.println("Los siguientes parámetros SÍ salida NO entrada: ");
//		for(String parametro : faltanEnEntradaYEstanEnLaSalida){
//			System.out.println(parametro);
//		}
//	}
//	
	
	
}
