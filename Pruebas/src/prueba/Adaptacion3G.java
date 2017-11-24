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
	
	
	private static TreeMap<String,Integer> arbolGNCELL;
	private static TreeMap<String,TreeMap<Integer,String>> arbolControllerNameNodebNameNodebID;
	public static void main(String[] args) {
		
		File carpetaEntrada = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_3G_BD\\");
		File carpetaSalida = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_3G_BD_PROCESADOS\\");
		
		
		// Creamos arbol UCELL, NEID -> RNCID
		File fichero_UCELLRNCID = new File(carpetaEntrada + "\\U2GNCELL.txt");
		creaArbolUCELL_RNCID(fichero_UCELLRNCID);
		File ficheroArbolUCELL1 = new File(carpetaSalida + "\\arbolUCELLRNCID.txt");
		escribeArbol_UCELL_RNCID(ficheroArbolUCELL1);
		
		// Añadimos al árbol  NEID - NODEBNAME - NODEBID
		File fichero_UCELLUNODEB = new File(carpetaEntrada + "\\UNODEB.txt");
		creaArbol_UCELL_NODEB(fichero_UCELLUNODEB);
		File ficheroArbol_ENODEB = new File(carpetaSalida + "\\arbolUNODEB.txt");
		escribeArbol_UCELL_ENODEB(ficheroArbol_ENODEB);
		
		// Añadimos las celdas del fichero UCELL
//		File fichero_UCELL = new File(carpetaEntrada + "\\UCELL.txt");
//		creaArbolUCELL_UCELL(fichero_UCELL);
		
		File ficheroU2GNCELL = new File(carpetaEntrada,"U2GNCELL.txt");
		File ficheroSalida_U2GNCELL = new File(carpetaSalida,"U2GNCELL.txt");
		escribeFicheroU2GNCELL(ficheroSalida_U2GNCELL,ficheroSalida_U2GNCELL,retornaParametrosCabeceraU2GNCELL(),retornaParametrosABuscarU2GNCELL());
		System.out.println("FIN");		
		
	}
	
	private static void creaArbolUCELL_RNCID(File ficheroRNCID) {
		arbolGNCELL = new TreeMap<String,Integer>();
		try (FileReader fr = new FileReader(ficheroRNCID);
			BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroUCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if(!arbolGNCELL.containsKey(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])){
					arbolGNCELL.put(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)],
					UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get(sRNCID)]));
				}
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Excepción File Not found en arbol UCELL 1");
		} catch (IOException e) {
			System.out.println("Excepción IO en arbol UCELL 1");
		}
	}
	
	private static void escribeArbol_UCELL_RNCID(File ficheroArbol_UCELLRNCID) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbol_UCELLRNCID))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"RNCID\"");
			bw.write(compositorCabecera.toString()+"\r\n");
			for (String sNeid : arbolGNCELL.keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(arbolGNCELL.get(sNeid));
					compositorValores.appendSeparator(",");
					bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
			}
		} catch (IOException e) {
			System.out.println("IO  Exception");
		}
		
	}
	
	private static void creaArbol_UCELL_NODEB(File fichero_UCELLUNODEB) {
		arbolControllerNameNodebNameNodebID = new TreeMap<String,TreeMap<Integer,String>>();
		try (FileReader fr = new FileReader(fichero_UCELLUNODEB);
				BufferedReader br = new BufferedReader(fr)) {
				String sCabeceraFichero = br.readLine();
				TreeMap<String, Integer> mapaCabeceraFicheroUNODEB = retornaMapaCabecera(sCabeceraFichero);
				String sValoresParametros = br.readLine();
				while (sValoresParametros != null) {
					String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
					if(!arbolControllerNameNodebNameNodebID.containsKey(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)])){
						arbolControllerNameNodebNameNodebID.put(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)],
						new TreeMap<Integer,String>());
					}
					if(!arbolControllerNameNodebNameNodebID.get(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNODEBID)]))){
						arbolControllerNameNodebNameNodebID.get(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNeid)])
						.put(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNODEBID)]), 
								aValoresParametros[mapaCabeceraFicheroUNODEB.get(sNODEBNAME)]);
					}
					sValoresParametros = br.readLine();
				}
			} catch (FileNotFoundException e) {
				System.out.println("Excepción File Not found en arbol UCELL 1");
			} catch (IOException e) {
				System.out.println("Excepción IO en arbol UCELL 1");
			}
		
	}
	
	private static void escribeArbol_UCELL_ENODEB(File ficheroArbol_UCELLENODEB) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbol_UCELLENODEB))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"NODEBID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"NODEBNAME\"");
			bw.write(compositorCabecera.toString()+"\r\n");
			for (String sNeid : arbolControllerNameNodebNameNodebID.keySet()) {
				for(Integer iNodeBID : arbolControllerNameNodebNameNodebID.get(sNeid).keySet()){
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(iNodeBID);
					compositorValores.appendSeparator(",");
					compositorValores.append(arbolControllerNameNodebNameNodebID.get(sNeid).get(iNodeBID));
					compositorValores.appendSeparator(",");
					bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
				}
			}
		} catch (IOException e) {
			System.out.println("IO  Exception escribe arbol ENODEB");
		}
		
	}

	
	
	
	
	
	private static void escribeFicheroU2GNCELL(File ficheroSalida_U2GNCELL, File ficheroSalida_U2GNCELL2,
			String[] retornaParametrosCabeceraU2GNCELL, String[] retornaParametrosABuscarU2GNCELL) {
		// TODO Auto-generated method stub
		
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
	private static String[] retornaParametrosCabeceraU2GNCELL(){
		String[] aParametrosCabecera={"RNC_SOURCE","RNCID","CELLID","GSMCELLINDEX","BLINDHOFLAG","BLINDHOPRIO","NPRIOFLAG","NPRIO","CIOOFFSET","QOFFSET1SN","QRXLEVMIN","TPENALTYHCSRESELECT",
				"TEMPOFFSET1","DRDECN0THRESHHOLD","SIB11IND","SIB12IND","MBDRFLAG","MBDRPRIO","SRVCCSWITCH","INTERRATADJSQHCS","NIRATOVERLAP","CELLNAME","GSMCELLNAME","CELLNAME-NCELLNAME",
				"Fecha","Red","ipEntrada","HOPRIO",};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELL(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLADAPTRACH(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLALGOSWITCH(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLCAC(){
		String[] aParametrosCabecera={};
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
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLADAPTRACH(){
		String[] aParametrosCabecera={};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosABuscarUCELLALGOSWITCH(){
		String[] aParametrosCabecera={};
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
