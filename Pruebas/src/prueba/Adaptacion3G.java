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

	public static void main(String[] args) {
		
		File carpetaEntrada = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser");
		File carpetaSalida = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser");
		
		File ficheroU2GNCELL = new File(carpetaEntrada,"U2GNCELL.txt");
		File ficheroSalida_U2GNCELL = new File(carpetaSalida,"U2GNCELL.txt");
		escribeFicheroU2GNCELL(ficheroSalida_U2GNCELL,ficheroSalida_U2GNCELL,retornaParametrosCabeceraU2GNCELL(),retornaParametrosABuscarU2GNCELL());
		comprobarCoincidencias();
		
		
		
		
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
	
	private static void comprobarCoincidencias(){
		ArrayList<String> faltanEnSalidaYEstanEnLaEntrada = new ArrayList<String>();
		ArrayList<String> faltanEnEntradaYEstanEnLaSalida = new ArrayList<String>();
		String[] entrada ={"RNC_SOURCE","RNCID","CELLID","GSMCELLINDEX","BLINDHOFLAG","BLINDHOPRIO","NPRIOFLAG","NPRIO","CIOOFFSET","QOFFSET1SN","QRXLEVMIN","TPENALTYHCSRESELECT","TEMPOFFSET1","DRDECN0THRESHHOLD","SIB11IND","SIB12IND","MBDRFLAG","MBDRPRIO","SRVCCSWITCH","INTERRATADJSQHCS","NIRATOVERLAP","CELLNAME","GSMCELLNAME","CELLNAME-NCELLNAME"};
		String[] salida ={"ACTSTATUS","BANDIND","BLKSTATUS","CCHCNOPINDEX","CELLCOVERAGETYPE","CELLHETFLAG","CELLID","CELLNAME","CFGRACIND","CIO","CNOPGRPINDEX","DLTPCPATTERN01COUNT","DPGID","DSSFLAG","DSSSMALLCOVMAXTXPOWER","Fecha","LAC","LOCELL","LOGICRNCID","MAXTXPOWER","NEEDSELFPLANFLAG","NEID","NINSYNCIND","NODEBNAME","NOUTSYNCIND","PRIORITY","PSCRAMBCODE","RAC","REMARK","Red","SAC","SN","SPGID","SPLITCELLIND","SRN","SSN","TCELL","TIMER","TRLFAILURE","TXDIVERSITYIND","UARFCNDOWNLINK","UARFCNUPLINK","UARFCNUPLINKIND","VPLIMITIND","ipEntrada"};
		
		for(String parametroEntrada : entrada){
			boolean bandera=false;
			for(String parametroEnSalida : salida){
				if(parametroEntrada.equalsIgnoreCase(parametroEnSalida)){
					bandera=true;
				}	
			}
			if(bandera==false){
				faltanEnEntradaYEstanEnLaSalida.add(parametroEntrada);
			}
		}
		for(String parametroEnSalida : entrada){
			boolean bandera=false;
			for(String parametroEntrada : salida){
				if(parametroEnSalida.equalsIgnoreCase(parametroEntrada)){
					bandera=true;
				}	
			}
			if(bandera==false){
				faltanEnEntradaYEstanEnLaSalida.add(parametroEnSalida);
			}
		}
		System.out.println("Los siguientes parámetros NO salida SÍ entrada: ");
		for(String parametro : faltanEnSalidaYEstanEnLaEntrada){
			System.out.println(parametro);
		}
		System.out.println("Los siguientes parámetros SÍ salida NO entrada: ");
		for(String parametro : faltanEnEntradaYEstanEnLaSalida){
			System.out.println(parametro);
		}
	}
	
	
}
