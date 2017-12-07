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
	private static TreeMap<Integer,String> arbolUCELL_NODEBID_NODEBNAME;
	private static TreeMap<String,TreeMap<Integer,TreeMap<String,String>>> arbolUCELL_NEID_LTECI_EUTRANCI = new TreeMap<String,TreeMap<Integer,TreeMap<String,String>>>();;
	private static TreeMap<String,TreeMap<Integer,TreeMap<String,String>>> arbolUCELL_Definiciones2G;
	
	public static void main(String[] args) {
		
		File carpetaEntrada = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_3G_BD\\");
		File carpetaSalida = new File("C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_3G_BD_PROCESADOS\\");
		
		
		// Creamos el arbol UCELL, NEID -> RNCID
		System.out.println("Creamos el arbol UCELL, NEID -> RNCID");
		File fichero_UCELLRNCID = new File(carpetaEntrada, "U2GNCELL.txt");
		creaArbolUCELL_RNCID(fichero_UCELLRNCID);
		
		// Creamos el árbol UCELL, NEID -> CELLIDS -> CELLNAME, NODEBNAME 
		System.out.println("Creamos el árbol UCELL, NEID -> CELLIDS -> CELLNAME, NODEBNAME");
		File ficheroUCELL = new File(carpetaEntrada,"UCELL.txt");
		creaArbolUCELL_UCELL(ficheroUCELL);
		
		// Añadimos al árbol UCELL, NEID -> CELLIDS -> CELLNAME, NODEBNAME, NODEBID
		System.out.println("Añadimos al árbol UCELL, NEID -> CELLIDS -> CELLNAME, NODEBNAME, NODEBID");
		File ficheroUCELL_ENODEB = new File(carpetaEntrada,"UNODEB.txt");
		creaArbolUCELL_UCELL_UNODEB(ficheroUCELL_ENODEB);
		
		// Creamos el arbol de definiciones de vecinas 2G
		System.out.println("Creamos el árbol NEID-GSMCELLINDEX-GSMCELLNAME");
		File ficheroUCELL_Definiciones2G = new File(carpetaEntrada,"UEXT2GCELL.txt");
		creaArbolUCELL_NEID_GSMCELLINDEX_GSMCELLNAME(ficheroUCELL_Definiciones2G);
		
		// Escribimos el arbol UNODEBID-UNODEBNAME
		File fiheroArbol_ENODEBID_ENODEBNAME = new File(carpetaSalida,"arbolENODEBID_ENODEBNAME.txt");
//		escribeArbol_UNODEBID_UNODEBNAME(fiheroArbol_ENODEBID_ENODEBNAME);
		
		// Escribimos el Arbol UCELL-RNCID
		File ficheroArbolUCELL_RNCID = new File(carpetaSalida ,"arbolUCELLRNCID.txt");
//		escribeArbol_UCELL_RNCID(ficheroArbolUCELL_RNCID);
		
		// Escribimos el Arbol UCELL-NODEBNAME-CELLID
		System.out.println("Escribimos el Arbol UCELL-NODEBNAME-CELLID");
		File ficheroArbolUCELL_NODEBNAME_CELLID = new File(carpetaSalida,"arbol_UCELL_NEID_CELLID_CELLNAME_NODEBNAME_NODEBID.txt");
//		escribeArbol_UCELL(ficheroArbolUCELL_NODEBNAME_CELLID);
		
		
		// Creamos el fichero RET
		System.out.println("Creamos el fichero RET");
		File fichero_UCELLRET = new File(carpetaEntrada,"RET.txt");
		File ficheroSalida_UCELLRET = new File(carpetaSalida,"RET.txt");
		escribeFicheroUCELL_Vacio(fichero_UCELLRET,ficheroSalida_UCELLRET,retornaParametrosCabeceraRET(),retornaParametrosABuscarRET());
		
		// Creamos el fichero RETDEVICEDATA
		System.out.println("Creamos el fichero RETDEVICEDATA");
		File fichero_UCELLRETDEVICEDATA = new File(carpetaEntrada,"RETDEVICEDATA.txt");
		File ficheroSalida_UCELLRETDEVICEDATA = new File(carpetaSalida,"RETDEVICEDATA.txt");
		escribeFicheroUCELL_Vacio(fichero_UCELLRETDEVICEDATA,ficheroSalida_UCELLRETDEVICEDATA,retornaParametrosCabeceraRETDEVICEDATA(),retornaParametrosABuscarRETDEVICEDATA());
				
				// Creamos el fichero RET
		System.out.println("Creamos el fichero RETSUBUNIT");
		File fichero_UCELRETSUBUNIT = new File(carpetaEntrada,"RETSUBUNIT.txt");
		File ficheroSalida_UCELLRETSUBUNIT = new File(carpetaSalida,"RETSUBUNIT.txt");
		escribeFicheroUCELL_Vacio(fichero_UCELRETSUBUNIT,ficheroSalida_UCELLRETSUBUNIT,retornaParametrosCabeceraRETSUBUNIT(),retornaParametrosABuscarRETSUBUNIT());
		
		
		// Creamos el fichero UCELL
		System.out.println("Creamos el fichero UCELL");
		File ficheroSalida_UCELL = new File(carpetaSalida,"UCELL.txt");
		escribeFicheroUCELL(ficheroUCELL,ficheroSalida_UCELL,retornaParametrosCabeceraUCELL(),retornaParametrosABuscarUCELL());
		
		
		// Creamos el fichero U2GNCELL
		System.out.println("Creamos el fichero U2GNCELL");
		File fichero_UCELLU2GNCELL = new File(carpetaEntrada,"U2GNCELL.txt");
		File ficheroSalida_UCELLU2GNCELL = new File(carpetaSalida,"U2GNCELL.txt");
		escribeFicheroUCELL_U2GNCELL(fichero_UCELLU2GNCELL,ficheroSalida_UCELLU2GNCELL,retornaParametrosCabeceraU2GNCELL(),retornaParametrosABuscarU2GNCELL());
		
		// Creamos el fichero UCELLADAPTRACH
		System.out.println("Creamos el fichero UCELLADAPTRACH");
		File fichero_UCELLUCELLADAPTRACH = new File(carpetaEntrada,"UCELLADAPTRACH.txt");
		File ficheroSalida_UCELLADAPTRACH = new File(carpetaSalida,"UCELLADAPTRACH.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLUCELLADAPTRACH,ficheroSalida_UCELLADAPTRACH, retornaParametrosCabeceraUCELLADAPTRACH(),retornaParametrosABuscarUCELLADAPTRACH());
		
		// Creamos el fichero UCELLALGOSWITCH
		System.out.println("Creamos el fichero UCELLALGOSWITCH");
		File fichero_UCELLALGOSWITCH = new File(carpetaEntrada,"UCELLALGOSWITCH.txt");
		File ficheroSalida_UCELLALGOSWITCH = new File(carpetaSalida, "UCELLALGOSWITCH.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLALGOSWITCH,ficheroSalida_UCELLALGOSWITCH,retornaParametrosCabeceraUCELLALGOSWITCH(),retornaParametrosABuscarUCELLALGOSWITCH());
		
		// Creamos el fichero UCELLCAC
		System.out.println("Creamos el fichero UCELLCAC");
		File fichero_UCELLCAC = new File(carpetaEntrada,"UCELLCAC.txt");
		File ficheroSalida_UCELLCAC = new File(carpetaSalida,"UCELLCAC.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLCAC,ficheroSalida_UCELLCAC,retornaParametrosCabeceraUCELLCAC(),retornaParametrosABuscarUCELLCAC());
		
		// Creamos el fichero UCELLCOALGOENHPARA   
		System.out.println("Creamos el fichero UCELLCOALGOENHPARA");
		File fichero_UCELLCOALGOENHPARA = new File(carpetaEntrada,"UCELLCOALGOENHPARA.txt");
		File ficheroSalida_UCELLCOALGOENHPARA = new File(carpetaSalida,"UCELLCOALGOENHPARA.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLCOALGOENHPARA,ficheroSalida_UCELLCOALGOENHPARA,retornaParametrosCabeceraUCELLCOALGOENHPARA(),
				retornaParametrosABuscarUCELLCOALGOENHPARA());
		
		// Creamos el fichero UCELLCONNREDIR
		System.out.println("Creamos el fichero UCELLCONNREDIR");
		File fichero_UCELLCONNREDIR = new File(carpetaEntrada,"UCELLCONNREDIR.txt");
		File ficheroSalida_UCELLCONNREDIR = new File(carpetaSalida,"UCELLCONNREDIR.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLCONNREDIR,ficheroSalida_UCELLCONNREDIR,retornaParametrosCabeceraUCELLCONNREDIR(),retornaParametrosABuscarUCELLCONNREDIR());
				
		// Creamos el fichero UCELLDRD
		System.out.println("Creamos el fichero UCELLDRD");
		File fichero_UCELLDRD = new File(carpetaEntrada,"UCELLDRD.txt");
		File ficheroSalida_UCELLDRD = new File(carpetaSalida,"UCELLDRD.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLDRD,ficheroSalida_UCELLDRD,retornaParametrosCabeceraUCELLDRD(),retornaParametrosABuscarUCELLDRD());
		
		// Creamos el fichero UCELLDYNSHUTDOWN
		System.out.println("Creamos el fichero UCELLDYNSHUTDOWN");
		File fichero_UCELLDYNSHUTDOWN = new File(carpetaEntrada,"UCELLDYNSHUTDOWN.txt");
		File ficheroSalida_UCELLDYNSHUTDOWN = new File(carpetaSalida,"UCELLDYNSHUTDOWN.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLDYNSHUTDOWN,ficheroSalida_UCELLDYNSHUTDOWN,retornaParametrosCabeceraUCELLDYNSHUTDOWN(),retornaParametrosABuscarUCELLDYNSHUTDOWN());
		
		// Creamos el fichero UCELLFRC 
		System.out.println("Creamos el fichero UCELLFRC");
		File fichero_UCELLFRC = new File(carpetaEntrada,"UCELLFRC.txt");
		File ficheroSalida_UCELLFRC = new File(carpetaSalida,"UCELLFRC.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLFRC,ficheroSalida_UCELLFRC,retornaParametrosCabeceraUCELLFRC(),retornaParametrosABuscarUCELLFRC());
		
		// Creamos el fichero UCELLHOCOMM
		System.out.println("Creamos el fichero UCELLHOCOMM");
		File fichero_UCELLHOCOMM = new File(carpetaEntrada,"UCELLHOCOMM.txt");
		File ficheroSalida_UCELLHOCOMM = new File(carpetaSalida,"UCELLHOCOMM.txt");
		escribeFicheroUCELL_NEID_CELLID(fichero_UCELLHOCOMM,ficheroSalida_UCELLHOCOMM,retornaParametrosCabeceraUCELLHOCOMM(),retornaParametrosABuscarUCELLHOCOMM());
		
		// Creamos el fichero UCELLHSDPA
		System.out.println("Creamos el fichero UCELLHSDPA");
		File ficheroUCELLHSDPA = new File(carpetaEntrada,"UCELLHSDPA.txt");
		File ficheroSalida_UCELLHSDPA = new File(carpetaSalida,"UCELLHSDPA.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLHSDPA,ficheroSalida_UCELLHSDPA,retornaParametrosCabeceraUCELLHSDPA(),retornaParametrosABuscarUCELLHSDPA());
		
		// Creamos el fichero UCELLHSDPCCH
		System.out.println("Creamos el fichero UCELLHSDPCCH");
		File ficheroUCELLHSDPCCH = new File(carpetaEntrada,"UCELLHSDPCCH.txt");
		File ficheroSalida_UCELLHSDPCCH = new File(carpetaSalida,"UCELLHSDPCCH.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLHSDPCCH,ficheroSalida_UCELLHSDPCCH,retornaParametrosCabeceraUCELLHSDPCCH(),retornaParametrosABuscarUCELLHSDPCCH());
		
		// Creamos el fichero UCELLHSUPA
		System.out.println("Creamos el fichero UCELLHSUPA");
		File ficheroUCELLHSUPA = new File(carpetaEntrada,"UCELLHSUPA.txt");
		File ficheroSalida_UCELLHSUPA = new File(carpetaSalida,"UCELLHSUPA.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLHSUPA,ficheroSalida_UCELLHSUPA,retornaParametrosCabeceraUCELLHSUPA(),retornaParametrosABuscarUCELLHSUPA());
		
		// Creamos el fichero UCELLINTERFREQHOCOV
		System.out.println("Creamos el fichero UCELLINTERFREQHOCOV");
		File ficheroUCELLINTERFREQHOCOV = new File(carpetaEntrada,"UCELLINTERFREQHOCOV.txt");
		File ficheroSalida_UCELLINTERFREQHOCOV = new File(carpetaSalida,"UCELLINTERFREQHOCOV.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLINTERFREQHOCOV,ficheroSalida_UCELLINTERFREQHOCOV,retornaParametrosCabeceraUCELLINTERFREQHOCOV(),retornaParametrosABuscarUCELLINTERFREQHOCOV());
		
		// Creamos el fichero UCELLINTERRATHOCOV
		System.out.println("Creamos el fichero UCELLINTERRATHOCOV");
		File ficheroUCELLINTERRATHOCOV = new File(carpetaEntrada,"UCELLINTERRATHOCOV.txt");
		File ficheroSalida_UCELLINTERRATHOCOV = new File(carpetaSalida,"UCELLINTERRATHOCOV.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLINTERRATHOCOV,ficheroSalida_UCELLINTERRATHOCOV,retornaParametrosCabeceraUCELLINTERRATHOCOV(),retornaParametrosABuscarUCELLINTERRATHOCOV());
		
		// Creamos el fichero UCELLINTERRATHONCOV
		System.out.println("Creamos el fichero UCELLINTERRATHONCOV");
		File ficheroUCELLINTERRATHONCOV = new File(carpetaEntrada,"UCELLINTERRATHONCOV.txt");
		File ficheroSalida_UCELLINTERRATHONCOV = new File(carpetaSalida,"UCELLINTERRATHONVOC.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLINTERRATHONCOV,ficheroSalida_UCELLINTERRATHONCOV,retornaParametrosCabeceraUCELLINTERRATHONCOV(), retornaParametrosABuscarUCELLINTERRATHONCOV());
		
		// Creamos el fichero UCELLINTRAFREQHO
		System.out.println("Creamos el fichero UCELLINTRAFREQHO");
		File ficheroUCELLINTRAFREQHO = new File(carpetaEntrada,"UCELLINTRAFREQHO.txt");
		File ficheroSalida_UCELLINTRAFREQHO = new File(carpetaSalida,"UCELLINTRAFREQHO.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLINTRAFREQHO,ficheroSalida_UCELLINTRAFREQHO,retornaParametrosCabeceraUCELLINTRAFREQHO(), retornaParametrosABuscarUCELLINTRAFREQHO());
		
		// Creamos el fichero UCELLDM
		System.out.println("Creamos el fichero UCELLDM");
		File ficheroUCELLLDM = new File(carpetaEntrada,"UCELLLDM.txt");
		File ficheroSalida_UCELLLDM = new File(carpetaSalida,"UCELLLDM.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLLDM,ficheroSalida_UCELLLDM,retornaParametrosCabeceraUCELLLDM(),retornaParametrosABuscarUCELLLDM());
		
		// Creamos el fichero UCELLLDR 
		System.out.println("Creamos el fichero UCELLLDR");
		File ficheroUCELLLDR = new File(carpetaEntrada,"UCELLLDR.txt");
		File ficheroSalida_UCELLLDR = new File(carpetaSalida,"UCELLLDR.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLLDR,ficheroSalida_UCELLLDR,retornaParametrosCabeceraUCELLLDR(),retornaParametrosABuscarUCELLLDR());
		
		// Creamos el fichero UCELLLICENSE
		System.out.println("Creamos el fichero LICENSE");
		File ficheroUCELLLICENSE = new File(carpetaEntrada,"UCELLLICENSE.txt");
		File ficheroSalida_UCELLLICENSE = new File(carpetaSalida,"UCELLLICENSE.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLLICENSE,ficheroSalida_UCELLLICENSE,retornaParametrosCabeceraUCELLLICENSE(),retornaParametrosABuscarUCELLLICENSE());
		
		// Creamos el fichero UCELLNFREQPRIOINFO
		System.out.println("Creamos el fichero UCELLNFREQPRIOINFO");
		File ficheroUCELLNFREQPRIOINFO = new File(carpetaEntrada,"UCELLNFREQPRIOINFO.txt");
		File ficheroSalida_UCELLNFREQPRIOINFO = new File(carpetaSalida,"UCELLNFREQPRIOINFO.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLNFREQPRIOINFO,ficheroSalida_UCELLNFREQPRIOINFO,retornaParametrosCabeceraUCELLNFREQPRIOINFO(),retornaParametrosABuscarUCELLNFREQPRIOINFO());
		
		// Creamos el fichero UCELLRLPWR
		System.out.println("Creamos el fichero UCELLRLPWR");
		File ficheroUCELLRLPWR = new File(carpetaEntrada,"UCELLRLPWR.txt");
		File ficheroSalida_UCELLRLPWR = new File(carpetaSalida,"UCELLRLPWR.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLRLPWR,ficheroSalida_UCELLRLPWR,retornaParametrosCabeceraUCELLRLPWR(),retornaParametrosABuscarUCELLRLPWR());
		
		// Creamos el fichero UCELLSELRESEL
		System.out.println("Creamos el fichero UCELLSELRESEL");
		File ficheroUCELLSELRESEL = new File(carpetaEntrada,"UCELLSELRESEL.txt");
		File ficheroSalida_UCELLSELRESEL = new File(carpetaSalida,"UCELLSELRESEL.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLSELRESEL,ficheroSalida_UCELLSELRESEL,retornaParametrosCabeceraUCELLSELRESEL(),retornaParametrosABuscarUCELLSELRESEL());
		
		// Creamos el fichero UCELLURA
		System.out.println("Creamos el fichero UCELLURA");
		File ficheroUCELLURA = new File(carpetaEntrada, "UCELLURA.txt");
		File ficheroSalida_UCELLURA = new File(carpetaSalida, "UCELLURA.txt");
		escribeFicheroUCELL_NEID_CELLID(ficheroUCELLURA,ficheroSalida_UCELLURA,retornaParametrosCabeceraUCELLURA(),retornaParametrosABuscarUCELLURA());
		
		// Creamos el fichero UDRD
		System.out.println("Creamos el fichero UDRD");
		File ficheroUDRD = new File(carpetaEntrada, "UDRD.txt");
		File ficheroSalida_UDRD = new File(carpetaSalida, "UDRD.txt");
		escribeFicheroUCELL_NEID(ficheroUDRD,ficheroSalida_UDRD,retornaParametrosCabeceraUDRD(),retornaParametrosABuscarUDRD());
		
		// Creamos el fichero UEXT2GCELL
		System.out.println("Creamos el fichero UEXT2GCELL");
		File ficheroUEXT2GCELL = new File(carpetaEntrada,"UEXT2GCELL.txt");
		File ficheroSalida_UEXT2GCELL = new File(carpetaSalida,"UEXT2GCELL.txt");
		escribeFicheroUCELL_PorDefecto(ficheroUEXT2GCELL,ficheroSalida_UEXT2GCELL,retornaParametrosCabeceraUEXT2GCELL(),retornaParametrosABuscarUEXT2GCELL());
		
		// Creamos el fichero UEXT3GCELL
		System.out.println("Creamos el fichero UEXT3GCELL");
		File ficheroUEXT3GCELL = new File(carpetaEntrada,"UEXT3GCELL.txt");
		File ficheroSalida_UEXT3GCELL = new File(carpetaSalida,"UEXT3GCELL.txt");
		escribeFicheroUCELL_UEXT3GCELL(ficheroUEXT3GCELL,ficheroSalida_UEXT3GCELL,retornaParametrosCabeceraUEXT3GCELL(),retornaParametrosABuscarUEXT3GCELL());
		
		// Creamos el fichero UHOCOMM
		System.out.println("Creamos el fichero UHOCOMM");
		File ficheroUHOCOMM = new File(carpetaEntrada,"UHOCOMM.txt");
		File ficheroSalida_UHOCOMM = new File(carpetaSalida,"UHOCOMM.txt");
		escribeFicheroUCELL_NEID(ficheroUHOCOMM,ficheroSalida_UHOCOMM,retornaParametrosCabeceraUHOCOMM(),retornaParametrosABuscarUHOCOMM());
		
		// Creamos el fichero UINTERFREQHOCOV
		System.out.println("Creamos el fichero UINTERFREQHOCOV");
		File ficheroUINTERFREQHOCOV = new File(carpetaEntrada,"UINTERFREQHOCOV.txt");
		File ficheroSalida_UINTERFREQHOCOV = new File(carpetaSalida,"UINTERFREQHOCOV.txt");
		escribeFicheroUCELL_NEID(ficheroUINTERFREQHOCOV,ficheroSalida_UINTERFREQHOCOV,retornaParametrosCabeceraUINTERFREQHOCOV(),retornaParametrosABuscarUINTERFREQHOCOV());
		
		// Creamos el fichero UINTERFREQNCELL
		System.out.println("Creamos el fichero UINTERFREQNCELL");
		File ficheroUINTERFREQNCELL = new File(carpetaEntrada,"UINTERFREQNCELL.txt");
		File ficheroSalida_UINTERFREQNCELL = new File(carpetaSalida,"UINTERFREQNCELL.txt");
		escribeFicheroUCELL_NEID_CELLID_NRNCID_NCELLID(ficheroUINTERFREQNCELL,ficheroSalida_UINTERFREQNCELL,retornaParametrosCabeceraUINTERFREQNCELL(),
				retornaParametrosABuscarUINTERFREQNCELL());
		
		// Creamos el fichero UINTERRATHOCOV
		System.out.println("Creamos el fichero UINTERRATHOCOV");
		File ficheroUINTERRATHOCOV = new File(carpetaEntrada,"UINTERRATHOCOV.txt");
		File ficheroSalida_UINTERRATHOCOV = new File(carpetaSalida,"UINTERRATHOCOV.txt");
		escribeFicheroUCELL_NEID(ficheroUINTERRATHOCOV,ficheroSalida_UINTERRATHOCOV,retornaParametrosCabeceraUINTERRATHOCOV(),retornaParametrosABuscarUINTERRATHOCOV());
		
		// Creamos el fichero UINTRAFREQNCELL 
		System.out.println("Creamos el fichero UINTRAFREQNCELL");
		File ficheroUINTRAFREQNCELL = new File(carpetaEntrada,"UINTRAFREQNCELL.txt");
		File ficheroSalida_UINTRAFREQNCELL = new File(carpetaSalida,"UINTRAFREQNCELL.txt");
		escribeFicheroUCELL_NEID_CELLID_NRNCID_NCELLID(ficheroUINTRAFREQNCELL,ficheroSalida_UINTRAFREQNCELL,retornaParametrosCabeceraUINTRAFREQNCELL(),retornaParametrosABuscarUINTRAFREQNCELL());
		
		// Creamos el fichero ULAC
		System.out.println("Creamos el fichero ULAC");
		File ficheroULAC = new File(carpetaEntrada,"ULAC.txt");
		File ficheroSalida_ULAC = new File(carpetaSalida,"ULAC.txt");
		escribeFicheroUCELL_PorDefecto(ficheroULAC,ficheroSalida_ULAC,retornaParametrosCabeceraULAC(),retornaParametrosABuscarULAC());
		
		// Creamos el fichero ULOCELL
		System.out.println("Creamos el fichero ULOCELL");
		File ficheroULOCELL = new File(carpetaEntrada,"ULOCELL.txt");
		File ficheroSalida_ULOCELL = new File(carpetaSalida,"ULOCELL.txt");
		escribeFicheroUCELL_ULOCELL(ficheroULOCELL,ficheroSalida_ULOCELL,retornaParametrosCabeceraULOCELL(),retornaParametrosABuscarULOCELL());
		
		// Creamos el fichero ULTECELL
		System.out.println("Creamos el fichero ULTECELL");
		File ficheroULTECELL = new File(carpetaEntrada,"ULTECELL.txt");
		File ficheroSalida_ULTECELL = new File(carpetaSalida,"ULTECELL.txt");
		escribeFicheroUCELL_PorDefecto(ficheroULTECELL,ficheroSalida_ULTECELL,retornaParametrosCabeceraULTECELL(),retornaParametrosABuscarULTECELL());
		
		// Creamos el fichero ULTENCELL
		System.out.println("Creamos el fichero ULTENCELL");
		File ficheroULTENCELL = new File(carpetaEntrada,"ULTENCELL.txt");
		File ficheroSalida_ULTENCELL = new File(carpetaSalida,"ULTENCELL.txt");
		escribeFicheroUCELL_ULTENCELL(ficheroULTENCELL,ficheroSalida_ULTENCELL,retornaParametrosCabeceraULTENCELL(),retornaParametrosABuscarULTENCELL());
		
		// Creamos el fichero UNODEB 
		System.out.println("Creamos el fichero UNODEB");
		File ficheroUNODEB = new File(carpetaEntrada,"UNODEB.txt");
		File ficheroSalida_UNODEB = new File(carpetaSalida,"UNODEB.txt");
		escribeFicheroUCELL_PorDefecto(ficheroUNODEB,ficheroSalida_UNODEB,retornaParametrosCabeceraUNODEB(),retornaParametrosABuscarUNODEB());
		
		// Creamos el fichero UNODEBLDR 
		System.out.println("Creamos el fichero UNODEBDLR");
		File ficheroUNODEBLDR = new File(carpetaEntrada,"UNODEBLDR.txt");
		File ficheroSalida_UNODEBDLR = new File(carpetaSalida,"UNODEBLDR.txt");
		escribeFicheroUCELL_ULOCELL(ficheroUNODEBLDR,ficheroSalida_UNODEBDLR,retornaParametrosCabeceraUNODEBLDR(),retornaParametrosABuscarUNODEBLDR());
		
		// Creamos el fichero UNRNC 
		System.out.println("Creamos el fichero UNRNC");
		File ficheroUNRNC = new File(carpetaEntrada,"UNRNC.txt");
		File ficheroSalida_UNRNC = new File(carpetaSalida,"UNRNC.txt");
		escribeFicheroUCELL_UNRNC(ficheroUNRNC,ficheroSalida_UNRNC,retornaParametrosCabeceraUNRNC(),retornaParametrosABuscarUNRNC());
		
		// Creamos el fichero UPCPICH
		System.out.println("Creamos el ficherol UPCPICH");
		File ficheroUPCPICH = new File(carpetaEntrada,"UPCPICH.txt");
		File ficheroSalida_UPCPICH = new File(carpetaSalida,"UPCPICH.txt");
		escribeFicheroUCELL_UPCPICH(ficheroUPCPICH,ficheroSalida_UPCPICH,retornaParametrosCabeceraUPCPICH(),retornaParametrosABuscarUPCPICH());
		
		// Creamos el fichero URAC
		System.out.println("Creamos el fichero URAC");
		File ficheroURAC = new File(carpetaEntrada,"URAC.txt");
		File ficheroSalida_URAC = new File(carpetaSalida,"URAC.txt");
		escribeFicheroUCELL_PorDefecto(ficheroURAC,ficheroSalida_URAC,retornaParametrosCabeceraURAC(),retornaParametrosABuscarURAC());
		System.out.println("FIN");		
		
	}

	private static void escribeFicheroUCELL_Vacio(File fichero_UCELL,
			File ficheroSalida_UCELL, String[] aParametrosCabecera,
			String[] aParametrosABuscar) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_UCELL));) {
			bw.write(" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void creaArbolUCELL_NEID_GSMCELLINDEX_GSMCELLNAME(File ficheroUCELL_Definiciones2G) {
		arbolUCELL_Definiciones2G = new TreeMap<String,TreeMap<Integer,TreeMap<String,String>>>();
		try (FileReader fr = new FileReader(ficheroUCELL_Definiciones2G);
		BufferedReader br = new BufferedReader(fr)) {
		String sCabeceraFichero = br.readLine();
		TreeMap<String, Integer> mapaCabeceraFicheroUCELL = retornaMapaCabecera(sCabeceraFichero);
		String sValoresParametros = br.readLine();
		while (sValoresParametros != null) {
			String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
			if(!arbolUCELL_Definiciones2G.containsKey(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])){
				arbolUCELL_Definiciones2G.put(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)],new TreeMap<Integer,TreeMap<String,String>>());
			}
			if(!arbolUCELL_Definiciones2G.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
					.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("GSMCELLINDEX")]))){
				arbolUCELL_Definiciones2G.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
						.put(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("GSMCELLINDEX")]),new TreeMap<String,String>());
			}
			if(!arbolUCELL_Definiciones2G.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
					.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("GSMCELLINDEX")]))
					.containsKey(aValoresParametros[mapaCabeceraFicheroUCELL.get("GSMCELLNAME")])){
				arbolUCELL_Definiciones2G.get(aValoresParametros[mapaCabeceraFicheroUCELL.get(sNeid)])
				.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUCELL.get("GSMCELLINDEX")]))
				.put("GSMCELLNAME", aValoresParametros[mapaCabeceraFicheroUCELL.get("GSMCELLNAME")]);
			}
			
			sValoresParametros = br.readLine();
		}
	} catch (FileNotFoundException e) {
		System.out.println("Excepción File Not found en arbol UCELL 1");
	} catch (IOException e) {
		System.out.println("Excepción IO en arbol UCELL 1");
	}
		
	}

	private static void escribeArbol_UNODEBID_UNODEBNAME(File ficheroArbol) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbol))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NODEBID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"NODEBNAME\"");
								
			bw.write(compositorCabecera.toString()+"\r\n");
			for (Integer nodebId : arbolUCELL_NODEBID_NODEBNAME.keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(nodebId);
					compositorValores.appendSeparator(",");
					compositorValores.append(arbolUCELL_NODEBID_NODEBNAME.get(nodebId));
					compositorValores.appendSeparator(",");
					bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
			}
		} catch (IOException e) {
			System.out.println("IO  Exception");
		}
		
		
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
		arbolUCELL_NODEBID_NODEBNAME = new  TreeMap<Integer,String>();
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
					if(!arbolUCELL_NODEBID_NODEBNAME.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUNODEB.get("NODEBID")]))){
						arbolUCELL_NODEBID_NODEBNAME.put(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroUNODEB.get("NODEBID")]),
								aValoresParametros[mapaCabeceraFicheroUNODEB.get("NODEBNAME")]);
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
	
	private static void escribeFicheroUCELL_NEID_CELLID(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
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
	
	private static void escribeFicheroUCELL_NEID(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sRncId = "---";
		
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
				
				if (arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					sRncId = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
					arbolParametroValor.put("RNCID", sRncId);
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
	
	private static void escribeFicheroUCELL_U2GNCELL(File fichero_UCELLU2GNCELL, File ficheroSalida_UCELLU2GNCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sCellName = "---";
		String sGsmCellName = "---";
		String sCellName_sNCellName = "---";
		try (FileReader fr = new FileReader(fichero_UCELLU2GNCELL);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_UCELLU2GNCELL))) {
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
				arbolParametroValor.put("RNC_SOURCE", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
			
				if (arbolUCELL_NODEB.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					if(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("CELLID")]))){
						if(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("CELLID")]))
								.containsKey("CELLNAME")){
							sCellName = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
									.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("CELLID")]))
									.get("CELLNAME");
						}
					}
	
				}
				arbolParametroValor.put("CELLNAME",sCellName);
				if (arbolUCELL_Definiciones2G.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					if(arbolUCELL_Definiciones2G.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("GSMCELLINDEX")]))){
						if(arbolUCELL_Definiciones2G.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("GSMCELLINDEX")]))
								.containsKey("GSMCELLNAME")){
							sGsmCellName = arbolUCELL_Definiciones2G.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
									.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("GSMCELLINDEX")]))
									.get("GSMCELLNAME");
						}
					}
	
				}
				arbolParametroValor.put("GSMCELLNAME",sGsmCellName);
				sCellName_sNCellName = "\""+sCellName.replaceAll("\"", "")+"-"+sGsmCellName.replace("\"", "")+"\"";
				arbolParametroValor.put("CELLNAME-NCELLNAME", sCellName_sNCellName);
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
	
	private static void escribeFicheroUCELL_PorDefecto(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
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
				if(ficheroEntrada_UCELL.getName().contains("UEXT2GCELL")||(ficheroEntrada_UCELL.getName().contains("ULTECELL"))){
					arbolParametroValor.put("RNC_SOURCE", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				}
				if((ficheroEntrada_UCELL.getName().contains("ULAC"))
						||(ficheroEntrada_UCELL.getName().contains("UNODEB"))
						||(ficheroEntrada_UCELL.getName().contains("UNRNC.txt"))
						||(ficheroEntrada_UCELL.getName().contains("URAC"))){
					arbolParametroValor.put("ControllerName", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				}
								
				for (String sParametro : aParametrosABuscar) {
					if(mapaCabeceraFichero.containsKey(sParametro)){
						arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabecera);
				if(ficheroEntrada_UCELL.getName().contains("ULTECELL")){
					if(!arbolUCELL_NEID_LTECI_EUTRANCI.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])){
						arbolUCELL_NEID_LTECI_EUTRANCI.put(aValoresParametros[mapaCabeceraFichero.get(sNeid)], new TreeMap<Integer,TreeMap<String,String>>());
					}
					if(!arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))){
						arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).put(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]), new TreeMap<String,String>());
					}
					if(!arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
							.containsKey("EUTRANCELLID")){
						arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
						.put("EUTRANCELLID", aValoresParametros[mapaCabeceraFichero.get("EUTRANCELLID")]);		
					}
					if(!arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
							.containsKey("LTECELLNAME")){
						arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
						.put("LTECELLNAME", aValoresParametros[mapaCabeceraFichero.get("LTECELLNAME")]);		
					}
				}
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found exception en escribe fichero GSM");
		} catch (IOException e) {
			System.out.println("IO exception en escribe fichero GSM");
		}
		
	}
	
	private static void escribeFicheroUCELL_UEXT3GCELL(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sNrncid = "---";
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
				arbolParametroValor.put("RNC_SOURCE", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
								
				if (arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					sNrncid = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
					arbolParametroValor.put("NRNCID", sNrncid);
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
	
	private static void escribeFicheroUCELL_NEID_CELLID_NRNCID_NCELLID(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sNneid = "---";
		String sNodeBId = "---";
		String sNodeBName = "---";
		String sCellName = "---";
		String sNCellName = "---";
		String sCellName_sNCellName = "---";

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
				arbolParametroValor.put("RNC_SOURCE", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				
				for(String sNRnc : arbolUCELLRncId.keySet()){
					if(arbolUCELLRncId.get(sNRnc).equalsIgnoreCase(aValoresParametros[mapaCabeceraFichero.get("NCELLRNCID")])){
						sNneid = sNRnc;
					}
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
				if(arbolUCELL_NODEB.containsKey(sNneid)
						&&(arbolUCELL_NODEB.get(sNneid).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("NCELLID")])))){
					sNCellName = arbolUCELL_NODEB.get(sNneid).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("NCELLID")])).get("CELLNAME");
					
				}
				
				sCellName_sNCellName = sCellName.replaceAll("\"", "") +"-"+sNCellName.replaceAll("\"", "");
				arbolParametroValor.put("CELLNAME-NCELLNAME", "\""+sCellName_sNCellName+"\"");
				arbolParametroValor.put("NCELLNAME", sNCellName);
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
	
	private static void escribeFicheroUCELL_ULOCELL(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sNodeBName = "---";
		String sRncId = "---";
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
				
				if (arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					sRncId = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
					arbolParametroValor.put("RNCID", sRncId);
				}
				if (arbolUCELL_NODEBID_NODEBNAME.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("NODEBID")]))) {
					sNodeBName = arbolUCELL_NODEBID_NODEBNAME.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("NODEBID")]));
					arbolParametroValor.put("NODEBNAME", sNodeBName);
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
	
	private static void escribeFicheroUCELL_UPCPICH( File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar){
		TreeMap<String, String> arbolParametroValor;
		String sRncID = "---";
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
				arbolParametroValor.put("ControllerName", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				if (arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					sRncID = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
					arbolParametroValor.put("RNCID", sRncID);
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
	
	private static void escribeFicheroUCELL_ULTENCELL(File ficheroEntrada_UCELL, File ficheroSalida_UCELL,
			String[] aParametrosCabecera, String[] aParametrosABuscar) {
		TreeMap<String, String> arbolParametroValor;
		String sNodeBId = "---";
		String sNodeBName = "---";
		String sCellName = "---";
		String sEutranCellId = "---";
		String sLTECellName = "---";

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
				arbolParametroValor.put("RNC_SOURCE", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				
				if(arbolUCELL_NODEB.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
						&&(arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])))){		
					sNodeBName = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBNAME");
					sNodeBId = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("NODEBID");
					sCellName = arbolUCELL_NODEB.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellId)])).get("CELLNAME");
					arbolParametroValor.put("NODEBID", sNodeBId);
					arbolParametroValor.put("NODEBNAME", sNodeBName);
					arbolParametroValor.put("CELLNAME", sCellName);
				}
				if(arbolUCELL_NEID_LTECI_EUTRANCI.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
						&&(arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")])))){
					if(arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
							.containsKey("LTECELLNAME")){
						sLTECellName=arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
								.get("LTECELLNAME");
					}
					if(arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
							.containsKey("EUTRANCELLID")){
						sEutranCellId=arbolUCELL_NEID_LTECI_EUTRANCI.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get("LTECELLINDEX")]))
								.get("EUTRANCELLID");
					}
				}
				arbolParametroValor.put("LTECELLNAME", sLTECellName);
				arbolParametroValor.put("EUTRANCELLID", sEutranCellId);
				
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
	
	private static void escribeFicheroUCELL_UNRNC(File ficheroUNRNC, File ficheroSalida_UNRNC,
			String[] retornaParametrosCabeceraUNRNC, String[] retornaParametrosABuscarUNRNC) {
		TreeMap<String, String> arbolParametroValor;
		String sRNCIDId = "---";
	
		try (FileReader fr = new FileReader(ficheroUNRNC);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroUNRNC))) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFichero = retornaMapaCabecera(sCabeceraFichero);
			StrBuilder compositorCabecera = new StrBuilder();
			for (String sParametro : retornaParametrosCabeceraUNRNC) {
				compositorCabecera.append("\""+sParametro+"\"");
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolParametroValor = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolParametroValor.put("ControllerName", aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				if(arbolUCELLRncId.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])){
					sRNCIDId = arbolUCELLRncId.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				}
				arbolParametroValor.put("RNCID", sRNCIDId);
				
				for (String sParametro : retornaParametrosABuscarUNRNC) {
					if(mapaCabeceraFichero.containsKey(sParametro)){
						arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolParametroValor, retornaParametrosABuscarUNRNC);
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
		String[] aParametrosCabecera={"RNC_SOURCE","RNCID","CELLID","GSMCELLINDEX","BLINDHOFLAG","BLINDHOPRIO","NPRIOFLAG","NPRIO","CIOOFFSET","QOFFSET1SN","QRXLEVMIN","TPENALTYHCSRESELECT","TEMPOFFSET1",
				"DRDECN0THRESHHOLD","SIB11IND","SIB12IND","MBDRFLAG","MBDRPRIO","SRVCCSWITCH","INTERRATADJSQHCS","NIRATOVERLAP","CELLNAME","GSMCELLNAME","CELLNAME-NCELLNAME","Fecha", "Red", "ipEntrada",
				"HOPRIO","U2GNCELLSRC"};
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
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","BACKGROUNDNOISE","BGNABNORMALTHD","BGNADJUSTTIMELEN","BGNENDTIME","BGNEQUSERNUMTHD",
				"BGNOPTSWITCH","BGNPERSISTSWITCH","BGNSTARTTIME","BGNSWITCH","BGNUPDATETHD","CELLENVTYPE","CELLULEQUNUMCAPACITY","DEFPCPICHECNO","DLCCHLOADRSRVCOEFF","DLCELLTOTALTHD","DLCONVAMRTHD",
				"DLCONVNONAMRTHD","DLHOCECODERESVSF","DLHOTHD","DLHSUPARSVDFACTOR","DLMBMSRSVDFACTOR","DLNRTRRCCACCECODERESVSF","DLOTHERRRCCACCECODERESVSF","DLOTHERTHD","DLRRCCECODERESVSF",
				"DLTOTALEQUSERNUM","HSDPABEPBRTHD","HSDPAMAXGBPTHD","HSDPASTRMPBRTHD","HSUPAEQUALPRIORITYUSERPBRTHD","HSUPAHIGHPRIORITYUSERPBRTHD","HSUPALOWPRIORITYUSERPBRTHD","HSUPAMAXGBPTHD",
				"LOADBALANCERATIO","MAXEFACHUSERNUM","MAXERACHUSERNUM","MAXHSDPAUSERNUM","MAXHSUPAUSERNUM","MAXULTXPOWERFORBAC","MAXULTXPOWERFORCONV","MAXULTXPOWERFORINT","MAXULTXPOWERFORSTR",
				"MTCHMAXPWR","MTCHMAXSF","MTCHRSVPWR","MTCHRSVSF","NONHPWRFORGBPPREEMP","NRTRRCCACTHDOFFSET","OTHERRRCCACTHDOFFSET","ROTCONTROLTARGET","RTRRCCACTHDOFFSET","TERMCONVUSINGHORESTHD",
				"ULCCHLOADFACTOR","ULCELLTOTALTHD","ULHOCERESVSF","ULHSDPCCHRSVDFACTOR","ULICLDCOPTSWITCH","ULNONCTRLTHDFORAMR","ULNONCTRLTHDFORHO","ULNONCTRLTHDFORNONAMR","ULNONCTRLTHDFOROTHER",
				"ULNRTRRCCACCERESVSF","ULOTHERRRCCACCERESVSF","ULRRCCERESVSF","ULTOTALEQUSERNUM","Fecha", "Red", "ipEntrada","BGNFILTERCOEF","BGNOPTENHSWITCH","BGNULLOADTHD","CSRLMAXDLPWROFFSET",
				"DEFAULTECNO","DLINTERFACTOR","DLOTHERTHDFORPT","FREEUSERGBPRSVD","HHOPROCPCPREAMBLE","HHOPROCSRBDELAY","HSUPAMAXGBPTHD","HSUPANONSERVINTERFEREFACTOR","IFFASTMULRLDLCECODERESVSF",
				"IFFASTMULRLDLTHD","IFFASTMULRLINITPWRPO","IFFASTMULRLULCERESVSF","LOGICRNCID","MAXERACHUSERNUM","MAXHSDPAUSERNUM","MAXUPAUSERNUMDYNADJFACTOR","OFFSETECNO","PREAMBLEACKTHD",
				"PTTPCPREAMBLE","PTTSRBDELAY","RRCPROCPCPREAMBLE","RRCPROCSRBDELAY","SHOINITPWRPO"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLCOALGOENHPARA(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","CSPSSERVICEHOSWITCH","RABCOMBDRDSWITCHVALID","Fecha", "Red", "ipEntrada","ACCESSLIMITPROPADELAYTHD",
				"CELLCOALGOENHSWITCH","CSPSRECFGSWBASERFVALID","DLSERVICETHROUTHD","DLSERVICETHROUTHD1","DLSERVICETHROUTHD2","FAKERSCP","INTERFREQHOTHLDECN0ICRPH3","INTERFREQMEASTIMEFORICRPH3",
				"INTERRATHOTHLDICRPH3","INTERRATMEASTIMEFORICRPH3","LOGICRNCID","RSVSWITCH0","RSVSWITCH1","RSVSWITCH2","RSVSWITCH3","RSVSWITCH4","RSVSWITCH5","RSVU32PARA0","RSVU32PARA1","RSVU32PARA10",
				"RSVU32PARA11","RSVU32PARA2","RSVU32PARA3","RSVU32PARA4","RSVU32PARA5","RSVU32PARA6","RSVU32PARA7","RSVU32PARA8","RSVU32PARA9","RSVU8PARA0","RSVU8PARA1","RSVU8PARA10","RSVU8PARA11","RSVU8PARA12",
				"RSVU8PARA2","RSVU8PARA3","RSVU8PARA4","RSVU8PARA5","RSVU8PARA6","RSVU8PARA7","RSVU8PARA8","RSVU8PARA9"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLCONNREDIR(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","FACHNUMABSTHD","FACHNUMRELTHD","NRTREDIRFACTOROFLDR","NRTREDIRFACTOROFNORM","REDIRBANDIND",
				"REDIRSWITCH","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLDRD(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","BASEDONMEASHRETRYDRDSWITCH","BASEDUELOCDRDREMAINTHD","BASEDUELOCDRDSWITCH",
				"CODEBALANCINGDRDCODERATETHD","CODEBALANCINGDRDMINSFTHD","CODEBALANCINGDRDSWITCH","CONNECTFAILRRCREDIRSWITCH","D2EDRDSWITCH","DPGDRDSWITCH","DRMAXGSMNUM","GRIDBASEDDRDSRTHD",
				"HRETRYTARGCELLLOADSTUSIND","HSPAPLUSSATISSWITCH","LDBDRDCHOICE","LDBDRDLOADREMAINTHDDCH","LDBDRDLOADREMAINTHDHSDPA","LDBDRDSWITCHDCH","LDBDRDSWITCHHSDPA","LOGICRNCID",
				"MBDRDBASEDGRIDSWITCH","PATHLOSSTHDFORCENTER","PATHLOSSTHDFOREDGE","REDIRBANDIND","RRCCELLLOADSORTSWITCH","SECCELLLDBDRDCHOICE","SECCELLREFBHFLAGSWITCH","SERVICEDIFFDRDSWITCH",
				"TRAFFTYPEFORBASEDUELOC","UELOCBASEDDRDFORC2DSWITCH","ULLDBDRDLOADREMAINTHDDCHSDPA","ULLDBDRDSWITCHDCHSDPA","Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLDYNSHUTDOWN(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","CELLSTARTUPTYPE","DYNSHUTDOWNCCHUSERSWITCH","DYNSHUTDOWNSWITCH","DYNSHUTDOWNTYPE",
				"ENDTIME1","HSDPAUSERNUMTHD","HSUPAUSERNUMTHD","MCHSDPAUSERNUMTHD","NCELLLDRREMAINTHD","STARTTIME1","TOTALUSERNUMTHD","USERMIGRATIONTYPE","Fecha", "Red", "ipEntrada",
				"BLINDHOLIMITSWITCH","PCPICHPOWERADJPERIOD","PCPICHPOWERADJSTEP","USERACCESSCTRLBFSHUTSWITCH"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLFRC(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","ALLOWEDSAVECODERESOURCE","DCHUSERLOADSTAFORPTPRIOR","DLBETRAFFDECTHS","DLBETRAFFINITBITRATE",
				"DLPWRLOADSTAFORPTPRIOR","ECN0EFFECTTIME","ECN0THS","ECN0THSFOR2MSTO10MS","H2DACCBASCOVSWITCH","LOADSTATEFORPILOTPWRADJ","LOGICRNCID","PSRECFGECNOTHDFORCSPS","PSRECFGRSCPTHDFORCSPS",
				"RRCCAUSESIGCHTYPEIND","RRCSIGCHTYPEOPTNONHLDSTATE","ULACTULLOADSTAFORPTPRIOR","ULBETRAFFDECTHS","ULBETRAFFINITBITRATE","Fecha", "Red", "ipEntrada","ECN0THDFORBASECOVERE2D",
				"ECN0THDFORBASECOVERE2DCSPS","ECN0THDFORSRBE2D","ECN0THSFOR2MSTO10MSCSPS","FAKEECNO","FAKEECNOH2D","WEAKCOVRRCREDIRECNOTHS"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHOCOMM(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","COEXISTMEASTHDCHOICE","CSSERVICEHOSWITCH","FASTRETURNTOLTESWITCH","HSPATIMERLEN",
				"INTERFREQRATSWITCH","MACROMICRO1APREMEASSWITCH","PSSERVICEHOSWITCH","SPECUSERCSTHD2DECN0","SPECUSERCSTHD2DRSCP","SPECUSERCSTHD2FECN0","SPECUSERCSTHD2FRSCP","SPECUSERHYSTFOR2D",
				"U2LBLINDREDIRSWITCH","U2LLTELOADSWITCH","Fecha", "Red", "ipEntrada","CSHOPRIOMEASTIMERLEN","CSIFFASTMULRLSETUPSWITCH","LOGICRNCID","PENALTYTIMERFORCMFAILCOV","PSHOPRIOMEASTIMERLEN",
				"PSIFFASTMULRLSETUPSWITCH","RELOCPREFAILPENALTYTIMER"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHSDPA(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","ACTSTATUS","ALLOCCODEMODE","CODEADJFORHSDPASWITCH","CODEADJFORHSDPAUSERNUMTHD",
				"DYNHSSCCHALLOCSWITCH","HCODEADJPUNSHTIMERLENGTH","HSDPCCHPREAMBLESWITCH","HSPAPOWER","HSPDSCHMAXCODENUM","HSPDSCHMINCODENUM","HSPDSCHMPOCONSTENUM","HSSCCHCODENUM",
				"MIMOMPOCONSTANT","Fecha", "Red", "ipEntrada","LOGICRNCID","HSPDSCHCODENUM"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHSDPCCH(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","CQIFBCK","CQIFBCKBASECELLLOAD","CQIFBCKBASECOVERAGE","CQIFBCKBASECSCOMBSERV","CQIFBCKFORCONVER",
				"CQIFBCKFORDCMIMO","CQIFBCKFORMIMO","CQIFBCKFORSHO","CQIPO","CQIPOFORSHO","CQIREF","CQIREFFORSHO","Fecha", "Red", "ipEntrada","ACKNACKPOFORMF","ACKPO1","ACKPO1FORSHO","ACKPO2",
				"ACKPO2FORSHO","ACKPO3","ACKPO3FORSHO","CQIPOFORMF","LOGICRNCID","NACKPO1","NACKPO1FORSHO","NACKPO2","NACKPO2FORSHO","NACKPO3","NACKPO3FORSHO"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLHSUPA(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","ACTSTATUS","DYNTGTROTCTRLSWITCH","EAGCHCODENUM","ERGCHEHICHCODENUM","MAXTARGETULLOADFACTOR",
				"NONSERVTOTOTALEDCHPWRRATIO","Fecha", "Red", "ipEntrada","LOGICRNCID","TGTROTADJPERIOD","TGTROTDOWNADJSTEP","TGTROTUPADJSTEP","UPLIMITFORMAXULTGTLDFACTOR"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTERFREQHOCOV(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","HYSTFOR2D","HYSTFOR2F","HYSTFORPRDINTERFREQ","INTERFREQCSTHD2DECN0","INTERFREQCSTHD2DRSCP",
				"INTERFREQCSTHD2FECN0","INTERFREQCSTHD2FRSCP","INTERFREQFILTERCOEF","INTERFREQHO2DEVENTTYPE","INTERFREQHTHD2DECN0","INTERFREQHTHD2DRSCP","INTERFREQHTHD2FECN0","INTERFREQHTHD2FRSCP",
				"INTERFREQINULCOVLIMITTHLD","INTERFREQMCMODE","INTERFREQMEASTIME","INTERFREQR99PSTHD2DECN0","INTERFREQR99PSTHD2DRSCP","INTERFREQR99PSTHD2FECN0","INTERFREQR99PSTHD2FRSCP",
				"INTERFREQREPORTMODE","LOGICRNCID","PRDREPORTINTERVAL","TARGETFREQCSTHDECN0","TARGETFREQCSTHDRSCP","TARGETFREQHTHDECN0","TARGETFREQHTHDRSCP","TARGETFREQR99PSTHDECN0",
				"TARGETFREQR99PSTHDRSCP","TIMETOINTERFREQHO","TIMETOTRIG2D","TIMETOTRIG2F","TIMETOTRIGFORPRDINTERFREQ","USEDFREQCSTHDECN0","USEDFREQCSTHDRSCP","USEDFREQHTHDECN0","USEDFREQHTHDRSCP",
				"USEDFREQR99PSTHDECN0","USEDFREQR99PSTHDRSCP","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada","HHOECNOMIN","HHORSCPMIN","IFHOFAILNUM","IFHOPINGPONGTIMER","INTERFREQHOCONSIDERRTWPSW",
				"PENALTYTIMERFORIFHOFAIL","TARGETFREQULCOVERLIMITTHD","UEPENALTYTIMERFORIFHOFAIL"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTERRATHOCOV(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","BSICVERIFY","CSBSICVERIFYINDICATION","FILTERCOEFOF2D2F","HYSTFOR2D","HYSTFOR2F","HYSTFORINTERRAT",
				"INTERRATCSTHD2DECN0","INTERRATCSTHD2DRSCP","INTERRATCSTHD2FECN0","INTERRATCSTHD2FRSCP","INTERRATFILTERCOEF","INTERRATHTHD2DECN0","INTERRATHTHD2DRSCP","INTERRATHTHD2FECN0","INTERRATHTHD2FRSCP",
				"INTERRATMEASTIME","INTERRATPERIODREPORTINTERVAL","INTERRATPHYCHFAILNUM","INTERRATPINGPONGHYST","INTERRATPINGPONGTIMER","INTERRATR99PSTHD2DECN0","INTERRATR99PSTHD2DRSCP","INTERRATR99PSTHD2FECN0",
				"INTERRATR99PSTHD2FRSCP","INTERRATREPORTMODE","PENALTYTIMEFORPHYCHFAIL","PSBSICVERIFYINDICATION","TARGETRATCSTHD","TARGETRATHTHD","TARGETRATR99PSTHD","TIMETOTRIGFORNONVERIFY","TIMETOTRIGFORVERIFY",
				"TRIGTIME2D","TRIGTIME2F","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada","INTERRATCOVPENALTYTIME","INTERRATHO2DEVENTTYPE","LOGICRNCID","USEDIRATHOLOWERTHDECNO","USEDIRATHOUPPERTHDECNO"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTERRATHONCOV(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","AMNTOFRPT3C","BSICVERIFY","CSHOOUT2GLOADTHD","HYSTFOR3C","INTERRATFILTERCOEF","INTERRATHOATTEMPTS",
				"INTERRATMEASTIME","INTERRATNCOVHOCSTHD","INTERRATNCOVHOPSTHD","INTERRATPHYCHFAILNUM","LOGICRNCID","PENALTYTIMEFORPHYCHFAIL","PERIODFOR3C","PSHOOUT2GLOADTHD","TRIGTIME3C","Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLINTRAFREQHO(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","BLINDHORSCP1FTHRESHOLD","HYST1AOR1CTRIGSCC","HYST1DJUDGETRIGSCC","HYSTFOR1A","HYSTFOR1B","HYSTFOR1C","HYSTFOR1D",
				"HYSTFOR1DRSCP","HYSTFOR1F","HYSTFOR1J","INTRAABLTHDFOR1FECNO","INTRAFREQFILTERCOEF","INTRAFREQMEASQUANTITY","INTRARELTHDFOR1ACSNVP","INTRARELTHDFOR1ACSVP","INTRARELTHDFOR1APS","INTRARELTHDFOR1BCSNVP",
				"INTRARELTHDFOR1BCSVP","INTRARELTHDFOR1BPS","INTRARELTHLDFOR1APRE","MAXCELLINACTIVESET","PERIODMRREPORTNUMFOR1A","PERIODMRREPORTNUMFOR1APRE","PERIODMRREPORTNUMFOR1C","PERIODMRREPORTNUMFOR1J",
				"REPORTINTERVALFOR1A","REPORTINTERVALFOR1APRE","REPORTINTERVALFOR1C","REPORTINTERVALFOR1J","SECHYSTFOR1A","SECHYSTFOR1B","SECHYSTFOR1C","SECHYSTFOR1F","SECINTRAABLTHDFOR1FECNO","SECINTRAABLTHDFOR1FRSCP",
				"SECINTRARELTHDFOR1APS","SECINTRARELTHDFOR1BPS","SECPERIODMRREPORTNUMFOR1A","SECPERIODMRREPORTNUMFOR1C","SECREPORTINTERVALFOR1A","SECREPORTINTERVALFOR1C","SECTRIGTIME1A","SECTRIGTIME1B","SECTRIGTIME1C",
				"SECTRIGTIME1F","SHOBCELLECNOTHLDFORPSADD","SHOBCELLECNOTHLDFORPSREJ","SHOECNOOFFSETFORPSREJ","SHOQUALMIN","TRIGTIME1A","TRIGTIME1B","TRIGTIME1C","TRIGTIME1D","TRIGTIME1DFORSRBOVERHSDPA","TRIGTIME1DRSCP",
				"TRIGTIME1F","TRIGTIME1J","WEIGHT","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLLDM(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","DLLDRTRIGTHD","DLLDRRELTHD","ULLDRTRIGTHD","ULLDRRELTHD","Fecha", "Red", "ipEntrada","ACTUALLOADSTATRANSHYSTIME",
				"CAPAOPTTCPLOADTHLD","DCHUENUMHEAVYTHD","DCHUENUMLOADEDTHD","DCHUENUMNORMALTHD","DCHUENUMOVERLOADTHD","DCHUSERLOADSTAFORULUNIOLC","DCHUSERNUMCONGTHD","DCHUSERNUMNORMALTHD","DCHUSERNUMSTATRANSHYSTIME",
				"DLCONGSTATETRANSHYSTIME","DLHEAVYTHD","DLLDTRNSHYSTIME","DLLOADEDTHD","DLNONHLOADCONGSTATETHD","DLNONHLOADNORMALSTATETHD","DLNORMALTHD","DLOLCRELTHD","DLOLCTRIGTHD","DLOVERLOADTHD","DLPWRCSCLBRELTHD",
				"DLPWRCSCLBTRIGTHD","DLPWRLOADSTAFORULUNIOLC","DLPWRPSCLBRELTHD","DLPWRPSCLBTRIGTHD","DLSFDIV2CMVALIDCODETHD","DYNMULTILINKTCPLOADTHLD","FAIRNESSTHD","HSUPAURETRNSLDRELTHD","HSUPAURETRNSLDTRIGTHD",
				"LOGICRNCID","MAXFACHPOWERADJLOADSTATHD","OFFLOADRELATIVETHD","PCPICHPWRDOWNDLLOADSTATE","PCPICHPWRUPDLLOADSTATE","RACHCONGRELTHD","RACHCONGTRIGTHD","RELRATIOFORULRTWP","RTWPLOADSTATETRANSHYSTIME",
				"SPECUSERPWRENDLPWRTRIGTHD","TRIGRATIOFORULRTWP","ULACTUALLOADHEAVYTHD","ULACTUALLOADLOADEDTHD","ULACTUALLOADNORMALTHD","ULACTUALLOADOVERLOADTHD","ULACTUALLOADTHDFOREXTLRATE","ULACTUALLOADTRIGLDRTHD",
				"ULACTULLOADSTAFORULUNIOLC","ULLDTRNSHYSTIME","ULOLCRELTHD","ULOLCTRIGTHD","ULPWRCSCLBRELTHD","ULPWRCSCLBTRIGTHD","ULPWRPSCLBRELTHD","ULPWRPSCLBTRIGTHD","ULRTWPCONGTHD","ULRTWPNORMALTHD"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLLDR(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","DLLDRBERATEREDUCTIONRABNUM","DLLDRCREDITSFRESTHD","DLLDRFIRSTACTION","DLLDRFOURTHACTION","DLLDRSECONDACTION",
				"DLLDRTHIRDACTION","MAXUSERNUMCODEADJ","ULLDRBERATEREDUCTIONRABNUM","ULLDRCREDITSFRESTHD","ULLDRFIRSTACTION","ULLDRFOURTHACTION","ULLDRSECONDACTION","ULLDRTHIRDACTION","ULTTICREDITSFRESTHD",
				"Fecha", "Red", "ipEntrada","CCCHCONGCTRLSWITCH","CELLLDRSFRESTHD","CODECONGHOENHANCEIND","CODECONGSELINTERFREQHOIND","DLCSINTERRATSHOULDBEHOUENUM","DLCSINTERRATSHOULDNOTHOUENUM",
				"DLINTERFREQHOBWTHD","DLINTERFREQHOCELLLOADSPACETHD","DLLDRAMRRATEREDUCTIONRABNUM","DLLDREIGHTHACTION","DLLDRELEVENTHACTION","DLLDRFIFTHACTION","DLLDRSEVENTHACTION","DLLDRSIXTHACTION","DLLDRTENTHACTION",
				"DLLDRTWELFTHACTION","DLLDRWAMRSFRECFGUENUM","DLPSINTERRATSHOULDBEHOUENUM","DLPSINTERRATSHOULDNOTHOUENUM","DLPSU2LHOUENUM","GOLDUSERLOADCONTROLSWITCH","HSDPAUSERCONGFBDIFLDHOIN","INTERFREQLDHOFORBIDENTC",
				"INTERFREQLDHOMETHODSELECTION","LDRCODEPRIUSEIND","LDRCODEUSEDSPACETHD","LOGICRNCID","MBMSDECPOWERRABTHD","ULCSINTERRATSHOULDBEHOUENUM","ULCSINTERRATSHOULDNOTHOUENUM","ULINTERFREQHOBWTHD",
				"ULINTERFREQHOCELLLOADSPACETHD","ULLDRAMRRATEREDUCTIONRABNUM","ULLDREIGHTHACTION","ULLDRFIFTHACTION","ULLDRNINTHACTION","ULLDRPSRTQOSRENEGRABNUM","ULLDRSEVENTHACTION","ULLDRSIXTHACTION",
				"ULPSINTERRATSHOULDBEHOUENUM","ULPSINTERRATSHOULDNOTHOUENUM","ULPSU2LHOUENUM"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLLICENSE(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","FUNCSWITCH1","FUNCSWITCH2","LOGICRNCID","Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLNFREQPRIOINFO(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","BLACKLSTCELLNUMBER","EARFCN","EDETECTIND","EMEASBW","EQRXLEVMIN","NPRIORITY","RSRQSWITCH","SUPCNOPGRPINDEX",
				"THDTOHIGH","THDTOLOW","Fecha", "Red", "ipEntrada","BCELLID1","CNOPGRPINDEX","EQQUALMINOFFSET","EQQUALMINSTEP","EQRXLEVMINOFFSET","EQRXLEVMINSTEP","FREQUSEPOLICYIND","LOGICRNCID","NPRIORITYCONNECT",
				"SLAVEBANDINDICATOR"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLRLPWR(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","CNDOMAINID","DLSF","LOGICRNCID","MAXBITRATE","RLMAXDLPWR","RLMINDLPWR","SPECUSERRLMAXDLPWR",
				"Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLSELRESEL(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","CELLFACHMEASLAYER","CELLFACHPRIORESELSWITCH","CONNQHYST1S","CONNQHYST2S","CONNSINTERSEARCH",
				"CONNSINTRASEARCH","IDLEQHYST1S","IDLEQHYST2S","IDLESINTERSEARCH","IDLESINTRASEARCH","INTERFREQTRESELSCALINGFACTOR","INTERRATTRESELSCALINGFACTOR","MAXALLOWEDULTXPOWER","NONHCSIND",
				"PRIORESELECTSWITCH","QHYST1SFACH","QHYST1SPCH","QHYST2SFACH","QHYST2SPCH","QQUALMIN","QRXLEVMIN","QRXLEVMINEXTSUP","QUALMEAS","SPEEDDEPENDENTSCALINGFACTOR","SPRIORITY","SSEARCHRAT",
				"THDPRIORITYSEARCH1","THDPRIORITYSEARCH2","THDSERVINGLOW","THDSERVINGLOW2","TRESELECTIONS","TRESELECTIONSFACH","TRESELECTIONSPCH","Fecha", "Red", "ipEntrada","HYSTFOR1AFORSIB",
				"HYSTFOR1DFORSIB","INTRARELTHD1AFORSIB","LOGICRNCID","TRIGTIME1AFORSIB","TRIGTIME1DFORSIB"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUCELLURA(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","URAID","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUDRD(){
		String[] aParametrosCabecera={"ControllerName","RNCID","BASEDONMEASHRETRYDRDSWITCH","BASEDUELOCDRDREMAINTHD","BASEDUELOCDRDSWITCH","CODEBALANCINGDRDCODERATETHD","CODEBALANCINGDRDMINSFTHD",
				"CODEBALANCINGDRDSWITCH","COMACROMICROIFREDIRSWITCH","CONNECTFAILRRCREDIRSWITCH","DCHADVDRDSWITCH","DELTACODEOCCUPIEDRATE","DLSFTHDFORRRCDRDPRECAC","DPGDRDSWITCH","DRMAXGSMNUM",
				"GRIDBASEDDRDSRTHD","HRETRYTARGCELLLOADSTUSIND","HRTSERVICEDIFFDRDSWITCH","HSDPAADVDRDSWITCH","HSDPALOADDRDOPTSWITCH","HSPAPLUSDRDLOADOFFSETMC","HSPAPLUSLOADDIFFSWITCH",
				"HSPAPLUSSATISSWITCH","LDBDRDCHOICE","LDBDRDLOADREMAINTHDDCH","LDBDRDLOADREMAINTHDHSDPA","LDBDRDOFFSETDCH","LDBDRDOFFSETHSDPA","LDBDRDSWITCHDCH","LDBDRDSWITCHHSDPA","LDBDRDTOTALPWRPROTHD",
				"LOGICRNCID","MBDRDBASEDGRIDSWITCH","PATHLOSSTHDFORCENTER","PATHLOSSTHDFOREDGE","POWLOADDRDOPTSWITCH","PWRTHDFORRRCDRDPRECAC","REDIRBANDIND","RESCONGDRDOPTSWITCH","RRCREDIRCONSIDERBARSWITCH",
				"SECCELLLDBDRDCHOICE","SECCELLREFBHFLAGSWITCH","SERVICEDIFFDRDSWITCH","TRAFFTYPEFORBASEDUELOC","UELOCBASEDDRDFORC2DSWITCH","ULCETHDFORRRCDRDPRECAC","ULLDBDRDLOADREMAINTHDDCHSDPA",
				"ULLDBDRDOFFSETDCHSDPA","ULLDBDRDSWITCHDCHSDPA","Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUEXT2GCELL(){
		String[] aParametrosCabecera={"RNC_SOURCE","GSMCELLINDEX","GSMCELLNAME","MCC","MNC","RAC","CNOPGRPINDEX","LAC","CFGRACIND","CID","NCC","BCC","BCCHARFCN","BANDIND","RATCELLTYPE","USEOFHCS",
				"NCMODE","SUPPRIMFLAG","SUPPPSHOFLAG","CIO","NBSCINDEX","LDPRDRPRTSWITCH","Fecha", "Red", "ipEntrada","GSMVOICEWEAKRXLEVTHLD","HCSPRIO","LOGICRNCID","QHCS","TCH1ARFCN","TCH2ARFCN",
				"TCH3ARFCN","TCH4ARFCN","TCH5ARFCN","TCH6ARFCN"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUEXT3GCELL(){
		String[] aParametrosCabecera={"RNC_SOURCE","CELLID","NRNCID","CELLNAME","CELLHOSTTYPE","UARFCNUPLINKIND","UARFCNUPLINK","UARFCNDOWNLINK","CFGRACIND","RAC","QQUALMININD","QQUALMIN","QRXLEVMININD",
				"QRXLEVMIN","QRXLEVMINEXTSUP","MAXALLOWEDULTXPOWERIND","MAXALLOWEDULTXPOWER","CNOPGRPINDEX","PSCRAMBCODE","BANDIND","TXDIVERSITYIND","LAC","MIDRATERLACTTIMEDEFOFFVAL",
				"HIGHRATERLACTTIMEDEFOFFVAL","OAMGUARDVALFORLOWRATE","OAMGUARDVALFORMIDRATE","OAMGUARDVALFORHIGHRATE","ACTTIMEDEFOFFVALFORUNCELLNOH","ACTTIMEDEFOFFVALFORUNCELLH",
				"ACTTIMEDEFOFFVALFORSAMECELL","USEOFHCS","SUPPDPCMODECHGFLAG","CELLCAPCONTAINERFDD","OVERLAYMOBILITYFLAG","HARQPREACAP","CIO","EFACHSUPIND","APFLAG","VPLIMITIND","STTDSUPIND","CP1SUPIND",
				"DPCHDIVMODFOROTHER","DPCHDIVMODFORMIMO","FDPCHDIVMODFORMIMO","FDPCHDIVMODFOROTHER","DIVMODFORDCHSDPA","Fecha", "Red", "ipEntrada","ACTTIMEDEFOFFVALFORUNCELLNOH","ALLSERVICELIMITIND",
				"CELLCOVERAGETYPE","DYNCESUPIND","FASTHSCCACTTIMEDEFOFFVAL","HCSPRIO","LOGICRNCID","MOVEUSERPSLIMITIND","QHCS","SPLITIND"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUHOCOMM(){
		String[] aParametrosCabecera={"ControllerName","RNCID","COEXISTMEASTHDCHOICE","COVBASEDGULMEASMODE","CSHOPRIOMEASTIMERLEN","CSIFFASTMULRLSETUPSWITCH","DIVCTRLFIELD","DRNCSHOINITPWRPO","HSPATIMERLEN",
				"IFANTIPINGPANGTIMERLENGTH","IFFASTMULRLSETUPDLTPCPAT","IFFASTMULRLSETUPRELTHR","IFTHENGSMMEATYPSWITCH","IUBRLFAILSUSPSHODELTMR","LOGICRNCID","MACROMICRO1APREMEASSWITCH",
				"MAXEDCHCELLINACTIVESET","PENALTYTIMERFORCMFAILCOV","PSHOPRIOMEASTIMERLEN","PSIFFASTMULRLSETUPSWITCH","RELOCPREFAILPENALTYTIMER","RELOCPREPFAILSELECTSWITCH","REPORTINTERVALFOR1APRE",
				"RXTXLOWERTHD","RXTXUPPERTHD","SFNOBSTDDEFVALIDTIME","SPECUSERCSTHD2DECN0","SPECUSERCSTHD2DRSCP","SPECUSERCSTHD2FECN0","SPECUSERCSTHD2FRSCP","SPECUSERHYSTFOR2D","T322FORLOADBALANCE",
				"U2LBLINDREDIRPINGPONGTIMER","UERELSUPIFFASTMULRL","WEAKCOVHSPAQUALTHDS","Fecha", "Red", "ipEntrada","ICRUEINTERFREQMBDRECN0THLD","ICRUEINTERFREQMBDRRSCPTHLD","SECFREQHOINTERFREQMEASTIME",
				"SECFREQHOONLINETIMETHD","SECFREQHOQUARELTHD","SECFREQHOUSERRATETHD"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTERFREQHOCOV(){
		String[] aParametrosCabecera={"ControllerName","RNCID","HHOECNOMIN","HHORSCPMIN","HYSTFOR2D","HYSTFOR2F","HYSTFORPRDINTERFREQ","IFHOFAILNUM","IFHOPINGPONGTIMER","INTERFREQCSTHD2DECN0","INTERFREQCSTHD2DRSCP",
				"INTERFREQCSTHD2FECN0","INTERFREQCSTHD2FRSCP","INTERFREQFILTERCOEF","INTERFREQHO2DEVENTTYPE","INTERFREQHTHD2DECN0","INTERFREQHTHD2DRSCP","INTERFREQHTHD2FECN0","INTERFREQHTHD2FRSCP","INTERFREQMCMODE",
				"INTERFREQMEASTIME","INTERFREQR99PSTHD2DECN0","INTERFREQR99PSTHD2DRSCP","INTERFREQR99PSTHD2FECN0","INTERFREQR99PSTHD2FRSCP","INTERFREQREPORTMODE","LOGICRNCID","PENALTYTIMERFORIFHOFAIL","PRDREPORTINTERVAL",
				"TARGETFREQCSTHDECN0","TARGETFREQCSTHDRSCP","TARGETFREQHTHDECN0","TARGETFREQHTHDRSCP","TARGETFREQR99PSTHDECN0","TARGETFREQR99PSTHDRSCP","TIMETOINTERFREQHO","TIMETOTRIG2D","TIMETOTRIG2F",
				"TIMETOTRIGFORPRDINTERFREQ","UEPENALTYTIMERFORIFHOFAIL","USEDFREQCSTHDECN0","USEDFREQCSTHDRSCP","USEDFREQHTHDECN0","USEDFREQHTHDRSCP","USEDFREQLOWERTHDECNO","USEDFREQR99PSTHDECN0","USEDFREQR99PSTHDRSCP",
				"USEDFREQUPPERTHDECNO","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTERFREQNCELL(){
		String[] aParametrosCabecera={"RNC_SOURCE","RNCID","CELLID","NCELLRNCID","NCELLID","BLINDHOFLAG","BLINDHOQUALITYCONDITION","NPRIOFLAG","NPRIO","CIOOFFSET","SIB11IND","IDLEQOFFSET1SN","IDLEQOFFSET2SN","SIB12IND",
				"CONNQOFFSET1SN","CONNQOFFSET2SN","TPENALTYHCSRESELECT","HOCOVPRIO","DRDECN0THRESHHOLD","MBDRFLAG","MBDRPRIO","DRDORLDRFLAG","INTERFREQADJSQHCS","INTERNCELLQUALREQFLAG","QQUALMIN","QRXLEVMIN","CLBFLAG",
				"CLBPRIO","CELLNAME-NCELLNAME","UARFCNDOWNLINK_CELL","PSCRAMBCODE_CELL","UARFCNDOWNLINK_NCELL","PSCRAMBCODE_NCELL","NODEBNAME","NODEBID","CELLNAME","NCELLNAME","Fecha", "Red", "ipEntrada",
				"DRDTARGETULCOVERLIMITTHD","DYNCELLSHUTDOWNFLAG","NCELLCAPCONTAINER","TEMPOFFSET1","TEMPOFFSET2","UINTERNCELLSRC"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTERRATHOCOV(){
		String[] aParametrosCabecera={"ControllerName","RNCID","BSICVERIFY","CSBSICVERIFYINDICATION","FILTERCOEFOF2D2F","HYSTFOR2D","HYSTFOR2F","HYSTFORINTERRAT","INTERRATCSTHD2DECN0","INTERRATCSTHD2DRSCP",
				"INTERRATCSTHD2FECN0","INTERRATCSTHD2FRSCP","INTERRATFILTERCOEF","INTERRATHO2DEVENTTYPE","INTERRATHTHD2DECN0","INTERRATHTHD2DRSCP","INTERRATHTHD2FECN0","INTERRATHTHD2FRSCP","INTERRATMEASTIME",
				"INTERRATPERIODREPORTINTERVAL","INTERRATPHYCHFAILNUM","INTERRATPINGPONGHYST","INTERRATPINGPONGTIMER","INTERRATR99PSTHD2DECN0","INTERRATR99PSTHD2DRSCP","INTERRATR99PSTHD2FECN0",
				"INTERRATR99PSTHD2FRSCP","INTERRATREPORTMODE","LOGICRNCID","PENALTYTIMEFORPHYCHFAIL","PSBSICVERIFYINDICATION","TARGETRATCSTHD","TARGETRATHTHD","TARGETRATR99PSTHD","TIMETOTRIGFORNONVERIFY",
				"TIMETOTRIGFORVERIFY","TRIGTIME2D","TRIGTIME2F","USEDIRATHOLOWERTHDECNO","USEDIRATHOUPPERTHDECNO","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada","INTERRATCOVPENALTYTIME"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUINTRAFREQNCELL(){
		String[] aParametrosCabecera={"RNC_SOURCE","RNCID","CELLID","NCELLRNCID","NCELLID","IDLEQOFFSET1SN","IDLEQOFFSET2SN","NPRIOFLAG","CIOOFFSET","CELLSFORBIDDEN1A","CELLSFORBIDDEN1B","SIB11IND",
				"SIB12IND","TPENALTYHCSRESELECT","MBMSNCELLIND","CONNQOFFSET1SN","CONNQOFFSET2SN","CELLNAME-NCELLNAME","UARFCNDOWNLINK_CELL","PSCRAMBCODE_CELL","UARFCNDOWNLINK_NCELL","PSCRAMBCODE_NCELL",
				"NODEBNAME","NODEBID","CELLNAME","NCELLNAME","Fecha", "Red", "ipEntrada","DYNCELLSHUTDOWNFLAG","NCELLCAPCONTAINER","NPRIO","TEMPOFFSET1","TEMPOFFSET2","UINTRANCELLSRC"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULAC(){
		String[] aParametrosCabecera={"ControllerName","CNOPINDEX","LAC","PLMNVALTAGMAX","PLMNVALTAGMIN","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULOCELL(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","LOCELL","LOGICRNCID","Fecha", "Red", "ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULTECELL(){
		String[] aParametrosCabecera={"RNC_SOURCE","LTECELLINDEX","LTECELLNAME","MCC","MNC","TAC","CNOPGRPINDEX","CELLPHYID","LTEBAND","LTEARFCN","SUPPPSHOFLAG","EUTRANCELLID","BLACKFLAG","Fecha", "Red",
				"ipEntrada","LOGICRNCID","SLAVEBANDINDICATOR","U2LRIMCNOPERATORRTINDEX"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraULTENCELL(){
		String[] aParametrosCabecera={"RNC_SOURCE","RNCID","CELLID","LTECELLINDEX","NODEBNAME","NODEBID","CELLNAME","EUTRANCELLID","LTECELLNAME","BLINDFLAG","ULTENCELLSRC","Fecha", "Red","ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUNODEB(){
		String[] aParametrosCabecera={"ControllerName","NODEBNAME","NODEBID","ACTSTATUS","AUTOHOMINGFLAG","CNOPINDEX","DSSFLAG","HOSTTYPE","IPTRANSAPARTIND","IUBFLEXFLAG","LOGICRNCID","NODEBAUTOREDUNDANCYFLAG",
				"NODEBPROTCLVER","NODEBTRACESWITCH","RSCMNGMODE","SATELLITEIND","SHARINGTYPE","SIGNALCREATETYPE","SN","SRN","SSN","TNLBEARERTYPE","TRANSDELAY","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUNODEBLDR(){
		String[] aParametrosCabecera={"ControllerName","NODEBNAME","NODEBID","DLCSINTERRATSHOULDBEHOUENUM","DLCSINTERRATSHOULDNOTHOUENUM","DLLDRAMRRATEREDUCTIONRABNUM","DLLDRBERATEREDUCTIONRABNUM","DLLDRCREDITSFRESTHD",
				"DLLDRFIFTHACTION","DLLDRFIRSTACTION","DLLDRFOURTHACTION","DLLDRPSRTQOSRENEGRABNUM","DLLDRSECONDACTION","DLLDRSEVENTHACTION","DLLDRSIXTHACTION","DLLDRTHIRDACTION","DLPSINTERRATSHOULDBEHOUENUM",
				"DLPSINTERRATSHOULDNOTHOUENUM","LOGICRNCID","NODEBID","ULCSINTERRATSHOULDBEHOUENUM","ULCSINTERRATSHOULDNOTHOUENUM","ULINTERFREQHOCELDRSPACETHD","ULLDRAMRRATEREDUCTIONRABNUM",
				"ULLDRBERATEREDUCTIONRABNUM","ULLDRCREDITSFRESTHD","ULLDREIGHTHACTION","ULLDRFIFTHACTION","ULLDRFIRSTACTION","ULLDRFOURTHACTION","ULLDRPSRTQOSRENEGRABNUM","ULLDRSECONDACTION","ULLDRSEVENTHACTION",
				"ULLDRSIXTHACTION","ULLDRTHIRDACTION","ULPSINTERRATSHOULDBEHOUENUM","ULPSINTERRATSHOULDNOTHOUENUM","ULTTICREDITSFRESTHD","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUNRNC(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NRNCID","CSVOICEOVERHSPASUPPIND","DPX","FUNCTIONSWITCH","HHORELOCPROCSWITCH","HHOTRIG","IUBCPPRIVATEINTERFACESWITCH","IUBUPPRIVATEINTERFACESWITCH",
				"IURCRSTVOICEDPCOVERSUPPIND","IUREXISTIND","IURHSDPASUPPIND","IURHSUPASUPPIND","PROCESSSWITCH","PSBEPROCTYPE","RNCPROTCLVER","SERVICEIND","SHOTRIG","STATEINDTMR","SUPPIURCCH","TNLBEARERTYPE","WAMRSUPPIND",
				"Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraUPCPICH(){
		String[] aParametrosCabecera={"ControllerName","RNCID","NODEBNAME","NODEBID","CELLNAME","CELLID","DSSSMALLCOVPCPICHPOWER","MAXPCPICHPOWER","MINPCPICHPOWER","PCPICHPOWER","PHYCHID","Fecha","Red","ipEntrada",
				"LOGICRNCID","MAXPCPICHPOWER","MINPCPICHPOWER"};
		return aParametrosCabecera;
	}
	private static String[] retornaParametrosCabeceraURAC(){
		String[] aParametrosCabecera={"ControllerName","CNOPINDEX","LAC","RAC","PLMNVALTAGMAX","PLMNVALTAGMIN","Fecha","Red","ipEntrada","LOGICRNCID"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosABuscarRET(){
		String[] aParametrosABuscar={};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarRETDEVICEDATA(){
		String[] aParametrosABuscar={};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarRETSUBUNIT(){
		String[] aParametrosABuscar={};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarU2GNCELL(){
		String[] aParametrosABuscar={"RNCID","CELLID","GSMCELLINDEX","BLINDHOFLAG","BLINDHOPRIO","NPRIOFLAG","NPRIO","CIOOFFSET","QOFFSET1SN",
				"QRXLEVMIN","TPENALTYHCSRESELECT","TEMPOFFSET1","DRDECN0THRESHHOLD","SIB11IND","SIB12IND","MBDRFLAG","MBDRPRIO","SRVCCSWITCH",
				"INTERRATADJSQHCS","NIRATOVERLAP","CELLNAME","GSMCELLNAME","CELLNAME-NCELLNAME","Fecha", "Red", "ipEntrada","HOPRIO","U2GNCELLSRC"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELL(){
		String[] aParametrosABuscar={"ACTSTATUS","BANDIND","BLKSTATUS","CCHCNOPINDEX","CELLCOVERAGETYPE","CELLHETFLAG","CELLNAME","CFGRACIND","CIO","CNOPGRPINDEX","DLTPCPATTERN01COUNT",
				"DPGID","DSSFLAG","DSSSMALLCOVMAXTXPOWER","Fecha","LAC","LOCELL","LOGICRNCID","MAXTXPOWER","NEEDSELFPLANFLAG","NINSYNCIND","NODEBNAME","NOUTSYNCIND","PRIORITY","PSCRAMBCODE",
				"RAC","REMARK","Red","SAC","SN","SPGID","SPLITCELLIND","SRN","SSN","TCELL","TIMER","TRLFAILURE","TXDIVERSITYIND","UARFCNDOWNLINK","UARFCNUPLINK","UARFCNUPLINKIND","VPLIMITIND","ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLADAPTRACH(){
		String[] aParametrosABuscar={"NODEBNAME","CELLNAME","Fecha","Red","ipEntrada","OPTICONSTANTVALUE","OPTIPOWERRAMPSTEP","OPTIPREAMBLERETRANSMAX",
				"OPTISTARTLOADSTATE","OPTISTOPHYST","RANDOMACCESSCLEARTHD","RANDOMACCESSCONGESTTHD"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLALGOSWITCH(){
		String[] aParametrosABuscar={"ADAPALGOSWITCH","BERATEREDUCEONFAIRNESSSWITCH","CELLCALLSHOCKSWITCH","CELLCAPACITYAUTOHANDLESWITCH","CELLMOCNDEMARCSWITCH","CELLOMENHSWITCH","CELLSYSINFOENHSWITCH",
				"CSRABCACOPTSWITCH","DCHENHSWITCH","DEMARCPREEMPTSWITCH","DLCMAVOIDCHGSCCODESWITCH","DLPWRLDCUESELSWITCH","DLSFADMALGOSWITCH","Fecha","HSPAENHSWITCH","HSPAPLUSSWITCH","LOGICRNCID","NBMCACALGOSWITCH",
				"NBMCACALGOSWITCH2","NBMDLCACALGOSELSWITCH","NBMLDCALGOSWITCH","NBMLDCIRATUESELSWITCH","NBMLDCUESELSWITCH","NBMMACHSRESETALGOSELSWITCH","NBMSEREXPCACALGOSWITCH","NBMSFLDCUESELSWITCH",
				"NBMULCACALGOSELSWITCH","OFFLOADSWITCH","RRCCACCHOICE","RRCCECODECACCHOICE"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLCAC(){
		String[] aParametrosABuscar={"BACKGROUNDNOISE","BGNABNORMALTHD","BGNADJUSTTIMELEN","BGNENDTIME","BGNEQUSERNUMTHD",
				"BGNOPTSWITCH","BGNPERSISTSWITCH","BGNSTARTTIME","BGNSWITCH","BGNUPDATETHD","CELLENVTYPE","CELLULEQUNUMCAPACITY","DEFPCPICHECNO","DLCCHLOADRSRVCOEFF","DLCELLTOTALTHD","DLCONVAMRTHD",
				"DLCONVNONAMRTHD","DLHOCECODERESVSF","DLHOTHD","DLHSUPARSVDFACTOR","DLMBMSRSVDFACTOR","DLNRTRRCCACCECODERESVSF","DLOTHERRRCCACCECODERESVSF","DLOTHERTHD","DLRRCCECODERESVSF",
				"DLTOTALEQUSERNUM","HSDPABEPBRTHD","HSDPAMAXGBPTHD","HSDPASTRMPBRTHD","HSUPAEQUALPRIORITYUSERPBRTHD","HSUPAHIGHPRIORITYUSERPBRTHD","HSUPALOWPRIORITYUSERPBRTHD","HSUPAMAXGBPTHD",
				"LOADBALANCERATIO","MAXEFACHUSERNUM","MAXERACHUSERNUM","MAXHSDPAUSERNUM","MAXHSUPAUSERNUM","MAXULTXPOWERFORBAC","MAXULTXPOWERFORCONV","MAXULTXPOWERFORINT","MAXULTXPOWERFORSTR",
				"MTCHMAXPWR","MTCHMAXSF","MTCHRSVPWR","MTCHRSVSF","NONHPWRFORGBPPREEMP","NRTRRCCACTHDOFFSET","OTHERRRCCACTHDOFFSET","ROTCONTROLTARGET","RTRRCCACTHDOFFSET","TERMCONVUSINGHORESTHD",
				"ULCCHLOADFACTOR","ULCELLTOTALTHD","ULHOCERESVSF","ULHSDPCCHRSVDFACTOR","ULICLDCOPTSWITCH","ULNONCTRLTHDFORAMR","ULNONCTRLTHDFORHO","ULNONCTRLTHDFORNONAMR","ULNONCTRLTHDFOROTHER",
				"ULNRTRRCCACCERESVSF","ULOTHERRRCCACCERESVSF","ULRRCCERESVSF","ULTOTALEQUSERNUM","Fecha", "Red", "ipEntrada","BGNFILTERCOEF","BGNOPTENHSWITCH","BGNULLOADTHD","CSRLMAXDLPWROFFSET",
				"DEFAULTECNO","DLINTERFACTOR","DLOTHERTHDFORPT","FREEUSERGBPRSVD","HHOPROCPCPREAMBLE","HHOPROCSRBDELAY","HSUPAMAXGBPTHD","HSUPANONSERVINTERFEREFACTOR","IFFASTMULRLDLCECODERESVSF",
				"IFFASTMULRLDLTHD","IFFASTMULRLINITPWRPO","IFFASTMULRLULCERESVSF","LOGICRNCID","MAXERACHUSERNUM","MAXHSDPAUSERNUM","MAXUPAUSERNUMDYNADJFACTOR","OFFSETECNO","PREAMBLEACKTHD",
				"PTTPCPREAMBLE","PTTSRBDELAY","RRCPROCPCPREAMBLE","RRCPROCSRBDELAY","SHOINITPWRPO"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLCOALGOENHPARA(){
		String[] aParametrosABuscar={"CSPSSERVICEHOSWITCH","RABCOMBDRDSWITCHVALID","Fecha", "Red", "ipEntrada","ACCESSLIMITPROPADELAYTHD",
				"CELLCOALGOENHSWITCH","CSPSRECFGSWBASERFVALID","DLSERVICETHROUTHD","DLSERVICETHROUTHD1","DLSERVICETHROUTHD2","FAKERSCP","INTERFREQHOTHLDECN0ICRPH3","INTERFREQMEASTIMEFORICRPH3",
				"INTERRATHOTHLDICRPH3","INTERRATMEASTIMEFORICRPH3","LOGICRNCID","RSVSWITCH0","RSVSWITCH1","RSVSWITCH2","RSVSWITCH3","RSVSWITCH4","RSVSWITCH5","RSVU32PARA0","RSVU32PARA1","RSVU32PARA10",
				"RSVU32PARA11","RSVU32PARA2","RSVU32PARA3","RSVU32PARA4","RSVU32PARA5","RSVU32PARA6","RSVU32PARA7","RSVU32PARA8","RSVU32PARA9","RSVU8PARA0","RSVU8PARA1","RSVU8PARA10","RSVU8PARA11","RSVU8PARA12",
				"RSVU8PARA2","RSVU8PARA3","RSVU8PARA4","RSVU8PARA5","RSVU8PARA6","RSVU8PARA7","RSVU8PARA8","RSVU8PARA9"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLCONNREDIR(){
		String[] aParametrosABuscar={"FACHNUMABSTHD","FACHNUMRELTHD","NRTREDIRFACTOROFLDR","NRTREDIRFACTOROFNORM","REDIRBANDIND","REDIRSWITCH","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLDRD(){
		String[] aParametrosABuscar={"BASEDONMEASHRETRYDRDSWITCH","BASEDUELOCDRDREMAINTHD","BASEDUELOCDRDSWITCH",
				"CODEBALANCINGDRDCODERATETHD","CODEBALANCINGDRDMINSFTHD","CODEBALANCINGDRDSWITCH","CONNECTFAILRRCREDIRSWITCH","D2EDRDSWITCH","DPGDRDSWITCH","DRMAXGSMNUM","GRIDBASEDDRDSRTHD",
				"HRETRYTARGCELLLOADSTUSIND","HSPAPLUSSATISSWITCH","LDBDRDCHOICE","LDBDRDLOADREMAINTHDDCH","LDBDRDLOADREMAINTHDHSDPA","LDBDRDSWITCHDCH","LDBDRDSWITCHHSDPA","LOGICRNCID",
				"MBDRDBASEDGRIDSWITCH","PATHLOSSTHDFORCENTER","PATHLOSSTHDFOREDGE","REDIRBANDIND","RRCCELLLOADSORTSWITCH","SECCELLLDBDRDCHOICE","SECCELLREFBHFLAGSWITCH","SERVICEDIFFDRDSWITCH",
				"TRAFFTYPEFORBASEDUELOC","UELOCBASEDDRDFORC2DSWITCH","ULLDBDRDLOADREMAINTHDDCHSDPA","ULLDBDRDSWITCHDCHSDPA","Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLDYNSHUTDOWN(){
		String[] aParametrosABuscar={"CELLSTARTUPTYPE","DYNSHUTDOWNCCHUSERSWITCH","DYNSHUTDOWNSWITCH","DYNSHUTDOWNTYPE",
				"ENDTIME1","HSDPAUSERNUMTHD","HSUPAUSERNUMTHD","MCHSDPAUSERNUMTHD","NCELLLDRREMAINTHD","STARTTIME1","TOTALUSERNUMTHD","USERMIGRATIONTYPE","Fecha", "Red", "ipEntrada",
				"BLINDHOLIMITSWITCH","PCPICHPOWERADJPERIOD","PCPICHPOWERADJSTEP","USERACCESSCTRLBFSHUTSWITCH"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLFRC(){
		String[] aParametrosABuscar={"ALLOWEDSAVECODERESOURCE","DCHUSERLOADSTAFORPTPRIOR","DLBETRAFFDECTHS","DLBETRAFFINITBITRATE",
				"DLPWRLOADSTAFORPTPRIOR","ECN0EFFECTTIME","ECN0THS","ECN0THSFOR2MSTO10MS","H2DACCBASCOVSWITCH","LOADSTATEFORPILOTPWRADJ","LOGICRNCID","PSRECFGECNOTHDFORCSPS","PSRECFGRSCPTHDFORCSPS",
				"RRCCAUSESIGCHTYPEIND","RRCSIGCHTYPEOPTNONHLDSTATE","ULACTULLOADSTAFORPTPRIOR","ULBETRAFFDECTHS","ULBETRAFFINITBITRATE","Fecha", "Red", "ipEntrada","ECN0THDFORBASECOVERE2D",
				"ECN0THDFORBASECOVERE2DCSPS","ECN0THDFORSRBE2D","ECN0THSFOR2MSTO10MSCSPS","FAKEECNO","FAKEECNOH2D","WEAKCOVRRCREDIRECNOTHS"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLHOCOMM(){
		String[] aParametrosABuscar={"COEXISTMEASTHDCHOICE","CSSERVICEHOSWITCH","FASTRETURNTOLTESWITCH","HSPATIMERLEN",
				"INTERFREQRATSWITCH","MACROMICRO1APREMEASSWITCH","PSSERVICEHOSWITCH","SPECUSERCSTHD2DECN0","SPECUSERCSTHD2DRSCP","SPECUSERCSTHD2FECN0","SPECUSERCSTHD2FRSCP","SPECUSERHYSTFOR2D",
				"U2LBLINDREDIRSWITCH","U2LLTELOADSWITCH","Fecha", "Red", "ipEntrada","CSHOPRIOMEASTIMERLEN","CSIFFASTMULRLSETUPSWITCH","LOGICRNCID","PENALTYTIMERFORCMFAILCOV","PSHOPRIOMEASTIMERLEN",
				"PSIFFASTMULRLSETUPSWITCH","RELOCPREFAILPENALTYTIMER"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLHSDPA(){
		String[] aParametrosABuscar={"ACTSTATUS","ALLOCCODEMODE","CODEADJFORHSDPASWITCH","CODEADJFORHSDPAUSERNUMTHD",
				"DYNHSSCCHALLOCSWITCH","HCODEADJPUNSHTIMERLENGTH","HSDPCCHPREAMBLESWITCH","HSPAPOWER","HSPDSCHCODENUM","HSPDSCHMAXCODENUM","HSPDSCHMINCODENUM","HSPDSCHMPOCONSTENUM","HSSCCHCODENUM",
				"MIMOMPOCONSTANT","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLHSDPCCH(){
		String[] aParametrosABuscar={"CQIFBCK","CQIFBCKBASECELLLOAD","CQIFBCKBASECOVERAGE","CQIFBCKBASECSCOMBSERV","CQIFBCKFORCONVER",
				"CQIFBCKFORDCMIMO","CQIFBCKFORMIMO","CQIFBCKFORSHO","CQIPO","CQIPOFORSHO","CQIREF","CQIREFFORSHO","Fecha", "Red", "ipEntrada","ACKNACKPOFORMF","ACKPO1","ACKPO1FORSHO","ACKPO2",
				"ACKPO2FORSHO","ACKPO3","ACKPO3FORSHO","CQIPOFORMF","LOGICRNCID","NACKPO1","NACKPO1FORSHO","NACKPO2","NACKPO2FORSHO","NACKPO3","NACKPO3FORSHO"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLHSUPA(){
		String[] aParametrosABuscar={"ACTSTATUS","DYNTGTROTCTRLSWITCH","EAGCHCODENUM","ERGCHEHICHCODENUM","MAXTARGETULLOADFACTOR",
				"NONSERVTOTOTALEDCHPWRRATIO","Fecha", "Red", "ipEntrada","LOGICRNCID","TGTROTADJPERIOD","TGTROTDOWNADJSTEP","TGTROTUPADJSTEP","UPLIMITFORMAXULTGTLDFACTOR"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLINTERFREQHOCOV(){
		String[] aParametrosABuscar={"HYSTFOR2D","HYSTFOR2F","HYSTFORPRDINTERFREQ","INTERFREQCSTHD2DECN0","INTERFREQCSTHD2DRSCP",
				"INTERFREQCSTHD2FECN0","INTERFREQCSTHD2FRSCP","INTERFREQFILTERCOEF","INTERFREQHO2DEVENTTYPE","INTERFREQHTHD2DECN0","INTERFREQHTHD2DRSCP","INTERFREQHTHD2FECN0","INTERFREQHTHD2FRSCP",
				"INTERFREQINULCOVLIMITTHLD","INTERFREQMCMODE","INTERFREQMEASTIME","INTERFREQR99PSTHD2DECN0","INTERFREQR99PSTHD2DRSCP","INTERFREQR99PSTHD2FECN0","INTERFREQR99PSTHD2FRSCP",
				"INTERFREQREPORTMODE","LOGICRNCID","PRDREPORTINTERVAL","TARGETFREQCSTHDECN0","TARGETFREQCSTHDRSCP","TARGETFREQHTHDECN0","TARGETFREQHTHDRSCP","TARGETFREQR99PSTHDECN0",
				"TARGETFREQR99PSTHDRSCP","TIMETOINTERFREQHO","TIMETOTRIG2D","TIMETOTRIG2F","TIMETOTRIGFORPRDINTERFREQ","USEDFREQCSTHDECN0","USEDFREQCSTHDRSCP","USEDFREQHTHDECN0","USEDFREQHTHDRSCP",
				"USEDFREQR99PSTHDECN0","USEDFREQR99PSTHDRSCP","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada","HHOECNOMIN","HHORSCPMIN","IFHOFAILNUM","IFHOPINGPONGTIMER","INTERFREQHOCONSIDERRTWPSW",
				"PENALTYTIMERFORIFHOFAIL","TARGETFREQULCOVERLIMITTHD","UEPENALTYTIMERFORIFHOFAIL"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLINTERRATHOCOV(){
		String[] aParametrosABuscar={"BSICVERIFY","CSBSICVERIFYINDICATION","FILTERCOEFOF2D2F","HYSTFOR2D","HYSTFOR2F","HYSTFORINTERRAT",
				"INTERRATCSTHD2DECN0","INTERRATCSTHD2DRSCP","INTERRATCSTHD2FECN0","INTERRATCSTHD2FRSCP","INTERRATFILTERCOEF","INTERRATHTHD2DECN0","INTERRATHTHD2DRSCP","INTERRATHTHD2FECN0","INTERRATHTHD2FRSCP",
				"INTERRATMEASTIME","INTERRATPERIODREPORTINTERVAL","INTERRATPHYCHFAILNUM","INTERRATPINGPONGHYST","INTERRATPINGPONGTIMER","INTERRATR99PSTHD2DECN0","INTERRATR99PSTHD2DRSCP","INTERRATR99PSTHD2FECN0",
				"INTERRATR99PSTHD2FRSCP","INTERRATREPORTMODE","PENALTYTIMEFORPHYCHFAIL","PSBSICVERIFYINDICATION","TARGETRATCSTHD","TARGETRATHTHD","TARGETRATR99PSTHD","TIMETOTRIGFORNONVERIFY","TIMETOTRIGFORVERIFY",
				"TRIGTIME2D","TRIGTIME2F","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada","INTERRATCOVPENALTYTIME","INTERRATHO2DEVENTTYPE","LOGICRNCID","USEDIRATHOLOWERTHDECNO","USEDIRATHOUPPERTHDECNO"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLINTERRATHONCOV(){
		String[] aParametrosABuscar={"AMNTOFRPT3C","BSICVERIFY","CSHOOUT2GLOADTHD","HYSTFOR3C","INTERRATFILTERCOEF","INTERRATHOATTEMPTS",
				"INTERRATMEASTIME","INTERRATNCOVHOCSTHD","INTERRATNCOVHOPSTHD","INTERRATPHYCHFAILNUM","LOGICRNCID","PENALTYTIMEFORPHYCHFAIL","PERIODFOR3C","PSHOOUT2GLOADTHD","TRIGTIME3C","Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLINTRAFREQHO(){
		String[] aParametrosABuscar={"BLINDHORSCP1FTHRESHOLD","HYST1AOR1CTRIGSCC","HYST1DJUDGETRIGSCC","HYSTFOR1A","HYSTFOR1B","HYSTFOR1C","HYSTFOR1D",
				"HYSTFOR1DRSCP","HYSTFOR1F","HYSTFOR1J","INTRAABLTHDFOR1FECNO","INTRAFREQFILTERCOEF","INTRAFREQMEASQUANTITY","INTRARELTHDFOR1ACSNVP","INTRARELTHDFOR1ACSVP","INTRARELTHDFOR1APS","INTRARELTHDFOR1BCSNVP",
				"INTRARELTHDFOR1BCSVP","INTRARELTHDFOR1BPS","INTRARELTHLDFOR1APRE","MAXCELLINACTIVESET","PERIODMRREPORTNUMFOR1A","PERIODMRREPORTNUMFOR1APRE","PERIODMRREPORTNUMFOR1C","PERIODMRREPORTNUMFOR1J",
				"REPORTINTERVALFOR1A","REPORTINTERVALFOR1APRE","REPORTINTERVALFOR1C","REPORTINTERVALFOR1J","SECHYSTFOR1A","SECHYSTFOR1B","SECHYSTFOR1C","SECHYSTFOR1F","SECINTRAABLTHDFOR1FECNO","SECINTRAABLTHDFOR1FRSCP",
				"SECINTRARELTHDFOR1APS","SECINTRARELTHDFOR1BPS","SECPERIODMRREPORTNUMFOR1A","SECPERIODMRREPORTNUMFOR1C","SECREPORTINTERVALFOR1A","SECREPORTINTERVALFOR1C","SECTRIGTIME1A","SECTRIGTIME1B","SECTRIGTIME1C",
				"SECTRIGTIME1F","SHOBCELLECNOTHLDFORPSADD","SHOBCELLECNOTHLDFORPSREJ","SHOECNOOFFSETFORPSREJ","SHOQUALMIN","TRIGTIME1A","TRIGTIME1B","TRIGTIME1C","TRIGTIME1D","TRIGTIME1DFORSRBOVERHSDPA","TRIGTIME1DRSCP",
				"TRIGTIME1F","TRIGTIME1J","WEIGHT","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLLDM(){
		String[] aParametrosABuscar={"DLLDRTRIGTHD","DLLDRRELTHD","ULLDRTRIGTHD","ULLDRRELTHD","Fecha", "Red", "ipEntrada","ACTUALLOADSTATRANSHYSTIME",
				"CAPAOPTTCPLOADTHLD","DCHUENUMHEAVYTHD","DCHUENUMLOADEDTHD","DCHUENUMNORMALTHD","DCHUENUMOVERLOADTHD","DCHUSERLOADSTAFORULUNIOLC","DCHUSERNUMCONGTHD","DCHUSERNUMNORMALTHD","DCHUSERNUMSTATRANSHYSTIME",
				"DLCONGSTATETRANSHYSTIME","DLHEAVYTHD","DLLDTRNSHYSTIME","DLLOADEDTHD","DLNONHLOADCONGSTATETHD","DLNONHLOADNORMALSTATETHD","DLNORMALTHD","DLOLCRELTHD","DLOLCTRIGTHD","DLOVERLOADTHD","DLPWRCSCLBRELTHD",
				"DLPWRCSCLBTRIGTHD","DLPWRLOADSTAFORULUNIOLC","DLPWRPSCLBRELTHD","DLPWRPSCLBTRIGTHD","DLSFDIV2CMVALIDCODETHD","DYNMULTILINKTCPLOADTHLD","FAIRNESSTHD","HSUPAURETRNSLDRELTHD","HSUPAURETRNSLDTRIGTHD",
				"LOGICRNCID","MAXFACHPOWERADJLOADSTATHD","OFFLOADRELATIVETHD","PCPICHPWRDOWNDLLOADSTATE","PCPICHPWRUPDLLOADSTATE","RACHCONGRELTHD","RACHCONGTRIGTHD","RELRATIOFORULRTWP","RTWPLOADSTATETRANSHYSTIME",
				"SPECUSERPWRENDLPWRTRIGTHD","TRIGRATIOFORULRTWP","ULACTUALLOADHEAVYTHD","ULACTUALLOADLOADEDTHD","ULACTUALLOADNORMALTHD","ULACTUALLOADOVERLOADTHD","ULACTUALLOADTHDFOREXTLRATE","ULACTUALLOADTRIGLDRTHD",
				"ULACTULLOADSTAFORULUNIOLC","ULLDTRNSHYSTIME","ULOLCRELTHD","ULOLCTRIGTHD","ULPWRCSCLBRELTHD","ULPWRCSCLBTRIGTHD","ULPWRPSCLBRELTHD","ULPWRPSCLBTRIGTHD","ULRTWPCONGTHD","ULRTWPNORMALTHD"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLLDR(){
		String[] aParametrosABuscar={"DLLDRBERATEREDUCTIONRABNUM","DLLDRCREDITSFRESTHD","DLLDRFIRSTACTION","DLLDRFOURTHACTION","DLLDRSECONDACTION",
				"DLLDRTHIRDACTION","MAXUSERNUMCODEADJ","ULLDRBERATEREDUCTIONRABNUM","ULLDRCREDITSFRESTHD","ULLDRFIRSTACTION","ULLDRFOURTHACTION","ULLDRSECONDACTION","ULLDRTHIRDACTION","ULTTICREDITSFRESTHD",
				"Fecha", "Red", "ipEntrada","CCCHCONGCTRLSWITCH","CELLLDRSFRESTHD","CODECONGHOENHANCEIND","CODECONGSELINTERFREQHOIND","DLCSINTERRATSHOULDBEHOUENUM","DLCSINTERRATSHOULDNOTHOUENUM",
				"DLINTERFREQHOBWTHD","DLINTERFREQHOCELLLOADSPACETHD","DLLDRAMRRATEREDUCTIONRABNUM","DLLDREIGHTHACTION","DLLDRELEVENTHACTION","DLLDRFIFTHACTION","DLLDRSEVENTHACTION","DLLDRSIXTHACTION","DLLDRTENTHACTION",
				"DLLDRTWELFTHACTION","DLLDRWAMRSFRECFGUENUM","DLPSINTERRATSHOULDBEHOUENUM","DLPSINTERRATSHOULDNOTHOUENUM","DLPSU2LHOUENUM","GOLDUSERLOADCONTROLSWITCH","HSDPAUSERCONGFBDIFLDHOIN","INTERFREQLDHOFORBIDENTC",
				"INTERFREQLDHOMETHODSELECTION","LDRCODEPRIUSEIND","LDRCODEUSEDSPACETHD","LOGICRNCID","MBMSDECPOWERRABTHD","ULCSINTERRATSHOULDBEHOUENUM","ULCSINTERRATSHOULDNOTHOUENUM","ULINTERFREQHOBWTHD",
				"ULINTERFREQHOCELLLOADSPACETHD","ULLDRAMRRATEREDUCTIONRABNUM","ULLDREIGHTHACTION","ULLDRFIFTHACTION","ULLDRNINTHACTION","ULLDRPSRTQOSRENEGRABNUM","ULLDRSEVENTHACTION","ULLDRSIXTHACTION",
				"ULPSINTERRATSHOULDBEHOUENUM","ULPSINTERRATSHOULDNOTHOUENUM","ULPSU2LHOUENUM"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLLICENSE(){
		String[] aParametrosABuscar={"FUNCSWITCH1","FUNCSWITCH2","LOGICRNCID","Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLNFREQPRIOINFO(){
		String[] aParametrosABuscar={"BLACKLSTCELLNUMBER","EARFCN","EDETECTIND","EMEASBW","EQRXLEVMIN","NPRIORITY","RSRQSWITCH","SUPCNOPGRPINDEX",
				"THDTOHIGH","THDTOLOW","Fecha", "Red", "ipEntrada","BCELLID1","CNOPGRPINDEX","EQQUALMINOFFSET","EQQUALMINSTEP","EQRXLEVMINOFFSET","EQRXLEVMINSTEP","FREQUSEPOLICYIND","LOGICRNCID","NPRIORITYCONNECT",
				"SLAVEBANDINDICATOR"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLRLPWR(){
		String[] aParametrosABuscar={"CNDOMAINID","DLSF","LOGICRNCID","MAXBITRATE","RLMAXDLPWR","RLMINDLPWR","SPECUSERRLMAXDLPWR","Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLSELRESEL(){
		String[] aParametrosABuscar={"CELLFACHMEASLAYER","CELLFACHPRIORESELSWITCH","CONNQHYST1S","CONNQHYST2S","CONNSINTERSEARCH",
				"CONNSINTRASEARCH","IDLEQHYST1S","IDLEQHYST2S","IDLESINTERSEARCH","IDLESINTRASEARCH","INTERFREQTRESELSCALINGFACTOR","INTERRATTRESELSCALINGFACTOR","MAXALLOWEDULTXPOWER","NONHCSIND",
				"PRIORESELECTSWITCH","QHYST1SFACH","QHYST1SPCH","QHYST2SFACH","QHYST2SPCH","QQUALMIN","QRXLEVMIN","QRXLEVMINEXTSUP","QUALMEAS","SPEEDDEPENDENTSCALINGFACTOR","SPRIORITY","SSEARCHRAT",
				"THDPRIORITYSEARCH1","THDPRIORITYSEARCH2","THDSERVINGLOW","THDSERVINGLOW2","TRESELECTIONS","TRESELECTIONSFACH","TRESELECTIONSPCH","Fecha", "Red", "ipEntrada","HYSTFOR1AFORSIB",
				"HYSTFOR1DFORSIB","INTRARELTHD1AFORSIB","LOGICRNCID","TRIGTIME1AFORSIB","TRIGTIME1DFORSIB"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUCELLURA(){
		String[] aParametrosABuscar={"URAID","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUDRD(){
		String[] aParametrosABuscar={"ControllerName","RNCID","BASEDONMEASHRETRYDRDSWITCH","BASEDUELOCDRDREMAINTHD","BASEDUELOCDRDSWITCH","CODEBALANCINGDRDCODERATETHD","CODEBALANCINGDRDMINSFTHD",
				"CODEBALANCINGDRDSWITCH","COMACROMICROIFREDIRSWITCH","CONNECTFAILRRCREDIRSWITCH","DCHADVDRDSWITCH","DELTACODEOCCUPIEDRATE","DLSFTHDFORRRCDRDPRECAC","DPGDRDSWITCH","DRMAXGSMNUM",
				"GRIDBASEDDRDSRTHD","HRETRYTARGCELLLOADSTUSIND","HRTSERVICEDIFFDRDSWITCH","HSDPAADVDRDSWITCH","HSDPALOADDRDOPTSWITCH","HSPAPLUSDRDLOADOFFSETMC","HSPAPLUSLOADDIFFSWITCH",
				"HSPAPLUSSATISSWITCH","LDBDRDCHOICE","LDBDRDLOADREMAINTHDDCH","LDBDRDLOADREMAINTHDHSDPA","LDBDRDOFFSETDCH","LDBDRDOFFSETHSDPA","LDBDRDSWITCHDCH","LDBDRDSWITCHHSDPA","LDBDRDTOTALPWRPROTHD",
				"LOGICRNCID","MBDRDBASEDGRIDSWITCH","PATHLOSSTHDFORCENTER","PATHLOSSTHDFOREDGE","POWLOADDRDOPTSWITCH","PWRTHDFORRRCDRDPRECAC","REDIRBANDIND","RESCONGDRDOPTSWITCH","RRCREDIRCONSIDERBARSWITCH",
				"SECCELLLDBDRDCHOICE","SECCELLREFBHFLAGSWITCH","SERVICEDIFFDRDSWITCH","TRAFFTYPEFORBASEDUELOC","UELOCBASEDDRDFORC2DSWITCH","ULCETHDFORRRCDRDPRECAC","ULLDBDRDLOADREMAINTHDDCHSDPA",
				"ULLDBDRDOFFSETDCHSDPA","ULLDBDRDSWITCHDCHSDPA","Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUEXT2GCELL(){
		String[] aParametrosABuscar={"RNC_SOURCE","GSMCELLINDEX","GSMCELLNAME","MCC","MNC","RAC","CNOPGRPINDEX","LAC","CFGRACIND","CID","NCC","BCC","BCCHARFCN","BANDIND","RATCELLTYPE","USEOFHCS",
				"NCMODE","SUPPRIMFLAG","SUPPPSHOFLAG","CIO","NBSCINDEX","LDPRDRPRTSWITCH","Fecha", "Red", "ipEntrada","GSMVOICEWEAKRXLEVTHLD","HCSPRIO","LOGICRNCID","QHCS","TCH1ARFCN","TCH2ARFCN",
				"TCH3ARFCN","TCH4ARFCN","TCH5ARFCN","TCH6ARFCN"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUEXT3GCELL(){
		String[] aParametrosABuscar={"RNC_SOURCE","CELLID","CELLNAME","CELLHOSTTYPE","UARFCNUPLINKIND","UARFCNUPLINK","UARFCNDOWNLINK","CFGRACIND","RAC","QQUALMININD","QQUALMIN","QRXLEVMININD",
				"QRXLEVMIN","QRXLEVMINEXTSUP","MAXALLOWEDULTXPOWERIND","MAXALLOWEDULTXPOWER","CNOPGRPINDEX","PSCRAMBCODE","BANDIND","TXDIVERSITYIND","LAC","MIDRATERLACTTIMEDEFOFFVAL",
				"HIGHRATERLACTTIMEDEFOFFVAL","OAMGUARDVALFORLOWRATE","OAMGUARDVALFORMIDRATE","OAMGUARDVALFORHIGHRATE","ACTTIMEDEFOFFVALFORUNCELLNOH","ACTTIMEDEFOFFVALFORUNCELLH",
				"ACTTIMEDEFOFFVALFORSAMECELL","USEOFHCS","SUPPDPCMODECHGFLAG","CELLCAPCONTAINERFDD","OVERLAYMOBILITYFLAG","HARQPREACAP","CIO","EFACHSUPIND","APFLAG","VPLIMITIND","STTDSUPIND","CP1SUPIND",
				"DPCHDIVMODFOROTHER","DPCHDIVMODFORMIMO","FDPCHDIVMODFORMIMO","FDPCHDIVMODFOROTHER","DIVMODFORDCHSDPA","Fecha", "Red", "ipEntrada","ACTTIMEDEFOFFVALFORUNCELLNOH","ALLSERVICELIMITIND",
				"CELLCOVERAGETYPE","DYNCESUPIND","FASTHSCCACTTIMEDEFOFFVAL","HCSPRIO","LOGICRNCID","MOVEUSERPSLIMITIND","QHCS","SPLITIND"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUHOCOMM(){
		String[] aParametrosABuscar={"COEXISTMEASTHDCHOICE","COVBASEDGULMEASMODE","CSHOPRIOMEASTIMERLEN","CSIFFASTMULRLSETUPSWITCH","DIVCTRLFIELD","DRNCSHOINITPWRPO","HSPATIMERLEN",
				"IFANTIPINGPANGTIMERLENGTH","IFFASTMULRLSETUPDLTPCPAT","IFFASTMULRLSETUPRELTHR","IFTHENGSMMEATYPSWITCH","IUBRLFAILSUSPSHODELTMR","LOGICRNCID","MACROMICRO1APREMEASSWITCH",
				"MAXEDCHCELLINACTIVESET","PENALTYTIMERFORCMFAILCOV","PSHOPRIOMEASTIMERLEN","PSIFFASTMULRLSETUPSWITCH","RELOCPREFAILPENALTYTIMER","RELOCPREPFAILSELECTSWITCH","REPORTINTERVALFOR1APRE",
				"RXTXLOWERTHD","RXTXUPPERTHD","SFNOBSTDDEFVALIDTIME","SPECUSERCSTHD2DECN0","SPECUSERCSTHD2DRSCP","SPECUSERCSTHD2FECN0","SPECUSERCSTHD2FRSCP","SPECUSERHYSTFOR2D","T322FORLOADBALANCE",
				"U2LBLINDREDIRPINGPONGTIMER","UERELSUPIFFASTMULRL","WEAKCOVHSPAQUALTHDS","Fecha", "Red", "ipEntrada","ICRUEINTERFREQMBDRECN0THLD","ICRUEINTERFREQMBDRRSCPTHLD","SECFREQHOINTERFREQMEASTIME",
				"SECFREQHOONLINETIMETHD","SECFREQHOQUARELTHD","SECFREQHOUSERRATETHD"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUINTERFREQHOCOV(){
		String[] aParametrosABuscar={"HHOECNOMIN","HHORSCPMIN","HYSTFOR2D","HYSTFOR2F","HYSTFORPRDINTERFREQ","IFHOFAILNUM","IFHOPINGPONGTIMER","INTERFREQCSTHD2DECN0","INTERFREQCSTHD2DRSCP",
				"INTERFREQCSTHD2FECN0","INTERFREQCSTHD2FRSCP","INTERFREQFILTERCOEF","INTERFREQHO2DEVENTTYPE","INTERFREQHTHD2DECN0","INTERFREQHTHD2DRSCP","INTERFREQHTHD2FECN0","INTERFREQHTHD2FRSCP","INTERFREQMCMODE",
				"INTERFREQMEASTIME","INTERFREQR99PSTHD2DECN0","INTERFREQR99PSTHD2DRSCP","INTERFREQR99PSTHD2FECN0","INTERFREQR99PSTHD2FRSCP","INTERFREQREPORTMODE","LOGICRNCID","PENALTYTIMERFORIFHOFAIL","PRDREPORTINTERVAL",
				"TARGETFREQCSTHDECN0","TARGETFREQCSTHDRSCP","TARGETFREQHTHDECN0","TARGETFREQHTHDRSCP","TARGETFREQR99PSTHDECN0","TARGETFREQR99PSTHDRSCP","TIMETOINTERFREQHO","TIMETOTRIG2D","TIMETOTRIG2F",
				"TIMETOTRIGFORPRDINTERFREQ","UEPENALTYTIMERFORIFHOFAIL","USEDFREQCSTHDECN0","USEDFREQCSTHDRSCP","USEDFREQHTHDECN0","USEDFREQHTHDRSCP","USEDFREQLOWERTHDECNO","USEDFREQR99PSTHDECN0","USEDFREQR99PSTHDRSCP",
				"USEDFREQUPPERTHDECNO","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUINTERFREQNCELL(){
		String[] aParametrosABuscar={"RNCID","CELLID","NCELLRNCID","NCELLID","BLINDHOFLAG","BLINDHOQUALITYCONDITION","NPRIOFLAG","NPRIO","CIOOFFSET","SIB11IND","IDLEQOFFSET1SN","IDLEQOFFSET2SN","SIB12IND",
				"CONNQOFFSET1SN","CONNQOFFSET2SN","TPENALTYHCSRESELECT","HOCOVPRIO","DRDECN0THRESHHOLD","MBDRFLAG","MBDRPRIO","DRDORLDRFLAG","INTERFREQADJSQHCS","INTERNCELLQUALREQFLAG","QQUALMIN","QRXLEVMIN","CLBFLAG",
				"CLBPRIO","UARFCNDOWNLINK_CELL","PSCRAMBCODE_CELL","UARFCNDOWNLINK_NCELL","PSCRAMBCODE_NCELL","Fecha", "Red", "ipEntrada",
				"DRDTARGETULCOVERLIMITTHD","DYNCELLSHUTDOWNFLAG","NCELLCAPCONTAINER","TEMPOFFSET1","TEMPOFFSET2","UINTERNCELLSRC"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUINTERRATHOCOV(){
		String[] aParametrosABuscar={"BSICVERIFY","CSBSICVERIFYINDICATION","FILTERCOEFOF2D2F","HYSTFOR2D","HYSTFOR2F","HYSTFORINTERRAT","INTERRATCSTHD2DECN0","INTERRATCSTHD2DRSCP",
				"INTERRATCSTHD2FECN0","INTERRATCSTHD2FRSCP","INTERRATFILTERCOEF","INTERRATHO2DEVENTTYPE","INTERRATHTHD2DECN0","INTERRATHTHD2DRSCP","INTERRATHTHD2FECN0","INTERRATHTHD2FRSCP","INTERRATMEASTIME",
				"INTERRATPERIODREPORTINTERVAL","INTERRATPHYCHFAILNUM","INTERRATPINGPONGHYST","INTERRATPINGPONGTIMER","INTERRATR99PSTHD2DECN0","INTERRATR99PSTHD2DRSCP","INTERRATR99PSTHD2FECN0",
				"INTERRATR99PSTHD2FRSCP","INTERRATREPORTMODE","LOGICRNCID","PENALTYTIMEFORPHYCHFAIL","PSBSICVERIFYINDICATION","TARGETRATCSTHD","TARGETRATHTHD","TARGETRATR99PSTHD","TIMETOTRIGFORNONVERIFY",
				"TIMETOTRIGFORVERIFY","TRIGTIME2D","TRIGTIME2F","USEDIRATHOLOWERTHDECNO","USEDIRATHOUPPERTHDECNO","WEIGHTFORUSEDFREQ","Fecha", "Red", "ipEntrada","INTERRATCOVPENALTYTIME"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUINTRAFREQNCELL(){
		String[] aParametrosABuscar={"RNCID","CELLID","NCELLRNCID","NCELLID","IDLEQOFFSET1SN","IDLEQOFFSET2SN","NPRIOFLAG","CIOOFFSET","CELLSFORBIDDEN1A","CELLSFORBIDDEN1B","SIB11IND",
				"SIB12IND","TPENALTYHCSRESELECT","MBMSNCELLIND","CONNQOFFSET1SN","CONNQOFFSET2SN","CELLNAME-NCELLNAME","UARFCNDOWNLINK_CELL","PSCRAMBCODE_CELL","UARFCNDOWNLINK_NCELL","PSCRAMBCODE_NCELL",
				"Fecha", "Red", "ipEntrada","DYNCELLSHUTDOWNFLAG","NCELLCAPCONTAINER","NPRIO","TEMPOFFSET1","TEMPOFFSET2","UINTRANCELLSRC"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarULAC(){
		String[] aParametrosABuscar={"CNOPINDEX","LAC","PLMNVALTAGMAX","PLMNVALTAGMIN","Fecha", "Red", "ipEntrada","LOGICRNCID"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarULOCELL(){
		String[] aParametrosABuscar={"RNCID","NODEBID","LOCELL","LOGICRNCID","Fecha", "Red", "ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarULTECELL(){
		String[] aParametrosABuscar={"LTECELLINDEX","LTECELLNAME","MCC","MNC","TAC","CNOPGRPINDEX","CELLPHYID","LTEBAND","LTEARFCN","SUPPPSHOFLAG","EUTRANCELLID","BLACKFLAG","Fecha", "Red",
				"ipEntrada","LOGICRNCID","SLAVEBANDINDICATOR","U2LRIMCNOPERATORRTINDEX"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarULTENCELL(){
		String[] aParametrosABuscar={"RNCID","CELLID","LTECELLINDEX","BLINDFLAG","ULTENCELLSRC","Fecha", "Red","ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUNODEB(){
		String[] aParametrosABuscar={"NODEBNAME","NODEBID","ACTSTATUS","AUTOHOMINGFLAG","CNOPINDEX","DSSFLAG","HOSTTYPE","IPTRANSAPARTIND","IUBFLEXFLAG","LOGICRNCID","NODEBAUTOREDUNDANCYFLAG",
				"NODEBPROTCLVER","NODEBTRACESWITCH","RSCMNGMODE","SATELLITEIND","SHARINGTYPE","SIGNALCREATETYPE","SN","SRN","SSN","TNLBEARERTYPE","TRANSDELAY","Fecha","Red","ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUNODEBLDR(){
		String[] aParametrosABuscar={"DLCSINTERRATSHOULDBEHOUENUM","DLCSINTERRATSHOULDNOTHOUENUM","DLLDRAMRRATEREDUCTIONRABNUM","DLLDRBERATEREDUCTIONRABNUM","DLLDRCREDITSFRESTHD",
				"DLLDRFIFTHACTION","DLLDRFIRSTACTION","DLLDRFOURTHACTION","DLLDRPSRTQOSRENEGRABNUM","DLLDRSECONDACTION","DLLDRSEVENTHACTION","DLLDRSIXTHACTION","DLLDRTHIRDACTION","DLPSINTERRATSHOULDBEHOUENUM",
				"DLPSINTERRATSHOULDNOTHOUENUM","LOGICRNCID","NODEBID","ULCSINTERRATSHOULDBEHOUENUM","ULCSINTERRATSHOULDNOTHOUENUM","ULINTERFREQHOCELDRSPACETHD","ULLDRAMRRATEREDUCTIONRABNUM",
				"ULLDRBERATEREDUCTIONRABNUM","ULLDRCREDITSFRESTHD","ULLDREIGHTHACTION","ULLDRFIFTHACTION","ULLDRFIRSTACTION","ULLDRFOURTHACTION","ULLDRPSRTQOSRENEGRABNUM","ULLDRSECONDACTION","ULLDRSEVENTHACTION",
				"ULLDRSIXTHACTION","ULLDRTHIRDACTION","ULPSINTERRATSHOULDBEHOUENUM","ULPSINTERRATSHOULDNOTHOUENUM","ULTTICREDITSFRESTHD","Fecha","Red","ipEntrada"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUNRNC(){
		String[] aParametrosABuscar={"NRNCID","CSVOICEOVERHSPASUPPIND","DPX","FUNCTIONSWITCH","HHORELOCPROCSWITCH","HHOTRIG","IUBCPPRIVATEINTERFACESWITCH","IUBUPPRIVATEINTERFACESWITCH",
				"IURCRSTVOICEDPCOVERSUPPIND","IUREXISTIND","IURHSDPASUPPIND","IURHSUPASUPPIND","PROCESSSWITCH","PSBEPROCTYPE","RNCPROTCLVER","SERVICEIND","SHOTRIG","STATEINDTMR","SUPPIURCCH","TNLBEARERTYPE","WAMRSUPPIND",
				"Fecha","Red","ipEntrada","DPC","DPCSPDF","IURALGOSWITCH","IUREDCHSFSUPP","IURIMPVDULCVRSRBODCHIND","IURLTECELLINFOSWITCH","IURMULTHSPASUPPIND","IURPRIVATEINTERFACESWITCH","IURRLFAILSUSPSHODELTMR",
				"IURSMLESSCRSTVOICESUPPIND","IURSRBOVEREDCHSUPPIND","LOGICRNCID","NI","OPCSPDF","SPC","SPCBITS","SRNSWITHCSFBINDSW","STATEINDTMR"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarUPCPICH(){
		String[] aParametrosABuscar={"CELLID","DSSSMALLCOVPCPICHPOWER","MAXPCPICHPOWER","MINPCPICHPOWER","PCPICHPOWER","PHYCHID","Fecha","Red","ipEntrada","LOGICRNCID","MAXPCPICHPOWER","MINPCPICHPOWER"};
		return aParametrosABuscar;
	}
	private static String[] retornaParametrosABuscarURAC(){
		String[] aParametrosABuscar={"CNOPINDEX","LAC","RAC","PLMNVALTAGMAX","PLMNVALTAGMIN","Fecha","Red","ipEntrada","LOGICRNCID"};
		return aParametrosABuscar;
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

	
	
}
