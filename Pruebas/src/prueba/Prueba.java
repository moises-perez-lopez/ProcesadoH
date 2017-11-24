
package prueba;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

import org.IS2.Nikola.UtilidadesTexto;
import org.apache.commons.lang3.text.StrBuilder;

public class Prueba {
	private final static String sControllerName = "ControllerName";
	private final static String sNeid = "NEID";
	private final static String sBSC_SOURCE = "BSC_SOURCE";
	private final static String sCellIndex = "CELLID";
	private final static String sCellId = "CELLID";
	private final static String sCellName = "CELLNAME";
	private final static String sFecha = "Fecha";
	private final static String sEXT2GCellIndex = "EXT2GCELLID";
	private final static String sEXT3GCellIndex = "EXT3GCELLID";
	private final static String sEXTLTECELLID = "EXTLTECELLID";
	private final static String sBTSId = "BTSID";
	private final static String sBTSNAME = "BTSNAME";
	private final static String sDeviceNo = "DEVICENO";
	private final static String sDeviceName = "DEVICENAME";
	private final static String sCell2GIndexSource = "SRC2GNCELLID";
	private final static String sCell2GIndexNeigh = "NBR2GNCELLID";
	private final static String sCell3GIndexSource = "SRC3GNCELLID";
	private final static String sCell3GIndexNeigh = "NBR3GNCELLID";
	private final static String sCellLTEIndexSource = "SRCLTENCELLID";
	private final static String sCellLTEIndexNeigh = "NBRLTENCELLID";
	private final static String sSRCLTENCELLNAME = "SRCLTENCELLNAME";
	private final static String sNBRLTENCELLNAME = "NBRLTENCELLNAME";
	private final static String sTRXID = "TRXID";
	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> mapaGCELLCellIdParametroValor = new TreeMap<String, TreeMap<Integer, TreeMap<String, String>>>();
	private static TreeMap<String, TreeMap<String, TreeMap<String, String>>> mapaGSMBTS;
	private static TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String,String>>>> mapaGSMBTSRET;
	private static TreeMap<String, TreeMap<Integer, TreeMap<String, String>>> mapaGSMLTE;
	private static TreeMap<String, TreeMap<String, TreeMap<String,String>>> mapaGSMGTRX;

	public static void main(String[] args) {
		
		String sCarpetaEntradaT = "C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_2G_BD\\";
		String sCarpetaSalidaT = "C:\\Users\\Moises\\Downloads\\Murdoc\\HuaweiParser\\SALIDA_2G_BD_PROCESADOS\\";

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
		
		// Añadimos al mapa GCELL nuevos registros desde el fichero GEXT3GCELL
		File ficheroGEXT3GCELL = new File(sCarpetaEntradaT + "GEXT3GCELL.txt");
		creaArbolGCELL_GEXT3GCELL(ficheroGEXT3GCELL);
		
		// Añadimos al mapa GCELL nuevos registros desde el fichero GEXTLTECELL
		File ficheroGEXTLTECELL = new File(sCarpetaEntradaT + "GEXTLTECELL.txt");
		creaArbolGCELL_GEXTLTECELL(ficheroGEXTLTECELL);

		// Creamos el fichero GCELL con el arbol GCELL FINAL
//		File ficheroGSM_GCELLFINAL = new File(sCarpetaSalidaT + "Arbol_GCELL_FINAL.txt");
//		escribeArbolGCELLFINAL(ficheroGSM_GCELLFINAL);

		// Creamos el fichero GSM_BTS
		File ficheroGSM_BTS = new File(sCarpetaEntradaT + "BTS.txt");
		File ficheroArbolGSM_BTS = new File(sCarpetaSalidaT + "Arbol_BTS_Salida.txt");
		File ficheroSalida_GSM_BTS = new File(sCarpetaSalidaT + "BTS_Salida.txt");
		creaArbolGSMBTS(ficheroGSM_BTS);
//		escribeArbolBTS(ficheroArbolGSM_BTS);
		escribeFicheroGSMBTS(ficheroGSM_BTS,ficheroSalida_GSM_BTS,retornaParametrosABuscarBTS(),
				retornaParametrosCabeceraGSM_BTS());
		borraFichero(ficheroGSM_BTS);

		// Creamos el fichero GSM_BTSRET
		File ficheroGSM_BTSRET = new File(sCarpetaEntradaT + "BTSRET.txt");
		File ficheroArbolGSM_BTSRET = new File(sCarpetaSalidaT + "Arbol_BTSRET_Salida.txt");
		File ficheroSalida_GSM_BTSRET = new File(sCarpetaSalidaT + "BTSRET_Salida.txt");
		creaArbolGSM_BTSRET(ficheroGSM_BTSRET);
//		escribeArbolBTSRET(ficheroArbolGSM_BTSRET);
		escribeFicheroGSMBTSRET(ficheroGSM_BTSRET,ficheroSalida_GSM_BTSRET,retornaParametrosABuscarBTSRET(),
				retornaParametrosCabeceraGSM_BTSRET());
		borraFichero(ficheroGSM_BTSRET);

		// Creamos el fichero GSM_BTSRETDEVICEDATA
		File ficheroGSM_BTSRETDEVICEDATA = new File(sCarpetaEntradaT + "BTSRETDEVICEDATA.txt");
		File ficheroSalida_GSM_BTSRETDEVICEDATA = new File(sCarpetaSalidaT + "BTSRETDEVICEDATA_Salida.txt");
		escribeFicheroGSM_BTSRETDEVICEDATA_SUBUNIT(ficheroGSM_BTSRETDEVICEDATA,ficheroSalida_GSM_BTSRETDEVICEDATA,
				retornaParametrosABuscarBTSDEVICEDATA(), retornaParametrosCabeceraGSM_BTSDEVICEDATA());
		borraFichero(ficheroGSM_BTSRETDEVICEDATA);

		// Creamos el fichero GSM_BTSRETSUBUNIT
		File ficheroGSM_BTSRETSUBUNIT = new File(sCarpetaEntradaT + "BTSRETSUBUNIT.txt");
		File ficheroSalida_GSM_BTSRETSUBUNIT = new File(sCarpetaSalidaT + "BTSRETSUBUNIT_Salida.txt");
		escribeFicheroGSM_BTSRETDEVICEDATA_SUBUNIT(ficheroGSM_BTSRETSUBUNIT,ficheroSalida_GSM_BTSRETSUBUNIT,
				retornaParametrosABuscarBTSRETSUBUNIT(),retornaParametrosCabeceraGSM_BTSRETSUBUNIT());
		borraFichero(ficheroGSM_BTSRETSUBUNIT);

		// Creamos el fichero G2GNCELL
		File ficheroGSM_G2GNCELL = new File(sCarpetaEntradaT+"G2GNCELL.txt");
		File ficheroSalida_GSM_G2GNCELL = new File(sCarpetaSalidaT+"G2GNCELL.txt");
		escribeFicheroGSM_G2GNCELL(ficheroGSM_G2GNCELL,ficheroSalida_GSM_G2GNCELL);
		borraFichero(ficheroGSM_G2GNCELL);
		
		// Creamos el fichero G3GNCELL
		File ficheroGSM_G3GNCELL = new File(sCarpetaEntradaT+"G3GNCELL.txt");
		File ficheroSalida_GSM_G3GNCELL = new File(sCarpetaSalidaT+"G3GNCELL.txt");
		escribeFicheroGSM_G3GNCELL(ficheroGSM_G3GNCELL,ficheroSalida_GSM_G3GNCELL,retornaParametrosABuscarG3GNCELL(),
				retornaParametrosCabeceraG3GNCELL());
		borraFichero(ficheroGSM_G3GNCELL);

		// Creamos el fichero GCELL2GBA1
		File ficheroGSM_GCELL2GBA1 = new File(sCarpetaEntradaT + "GCELL2GBA1.txt");
		File ficheroSalida_GSM_GCELL2GBA1 = new File(sCarpetaSalidaT + "GCELL2GBA1_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELL2GBA1, ficheroSalida_GSM_GCELL2GBA1, retornaParametrosABuscarGCELL2GBA1(),
				retornaParametrosCabeceraGSM_GCELL2GBA1());

		// Creamos el fichero GCELLAMRQUL
		File ficheroGSM_GCELLAMRQUL = new File(sCarpetaEntradaT + "GCELLAMRQUL.txt");
		File ficheroSalida_GSM_GCELLAMRQUL = new File(sCarpetaSalidaT + "GCELLAMRQUL_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLAMRQUL, ficheroSalida_GSM_GCELLAMRQUL, retornaParametrosABuscarGCELLAMRQUL(),
				retornaParametrosCabeceraGSM_GCELLAMRQUL());

		// Creamos el fichero GCELLBASICPARA
		File ficheroGSM_GCELLBASICPARA = new File(sCarpetaEntradaT + "GCELLBASICPARA.txt");
		File ficheroSalida_GSM_GCELLBASICPARA = new File(sCarpetaSalidaT + "GCELLBASICPARA_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLBASICPARA, ficheroSalida_GSM_GCELLBASICPARA,
				retornaParametrosABuscarGCELLBASICPARA(), retornaParametrosCabeceraGSM_GCELLBASICPARA());

		// Creamos el fichero GCELLCCAD
		File ficheroGSM_GCELLCCAD = new File(sCarpetaEntradaT + "GCELLCCAD.txt");
		File ficheroSalida_GSM_GCELLCCAD = new File(sCarpetaSalidaT + "GCELLCCAD_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCAD, ficheroSalida_GSM_GCELLCCAD, retornaParametrosABuscarGCELLCCAD(),
				retornaParametrosCabeceraGSM_GCELLCCAD());

		// Creamos el fichero GCELLCCBASIC
		File ficheroGSM_GCELLCCBASIC = new File(sCarpetaEntradaT + "GCELLCCBASIC.txt");
		File ficheroSalida_GSM_GCELLCCBASIC = new File(sCarpetaSalidaT + "GCELLCCBASIC_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCBASIC, ficheroSalida_GSM_GCELLCCBASIC,
				retornaParametrosABuscarGCELLCCBASIC(), retornaParametrosCabeceraGSM_GCELLCCBASIC());

		// Creamos el fichero GCELLCCCH
		File ficheroGSM_GCELLCCCH = new File(sCarpetaEntradaT + "GCELLCCCH.txt");
		File ficheroSalida_GSM_GCELLCCCH = new File(sCarpetaSalidaT + "GCELLCCCH_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCCH, ficheroSalida_GSM_GCELLCCCH, retornaParametrosABuscarGCELLCCCH(),
				retornaParametrosCabeceraGSM_GCELLCCCH());

		// Creamos el fichero GCELLCCUTRANSYS
		File ficheroGSM_GCELLCCUTRANSYS = new File(sCarpetaEntradaT + "GCELLCCUTRANSYS.txt");
		File ficheroSalida_GSM_GCELLCCUTRANSYS = new File(sCarpetaSalidaT + "GCELLCCUTRANSYS_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCCUTRANSYS, ficheroSalida_GSM_GCELLCCUTRANSYS,
				retornaParametrosABuscarGCELLCCUTRANSYS(), retornaParametrosCabeceraGSM_GCELLCCUTRANSYS());

		// Creamos el fichero GCELLCHMGAD
		File ficheroGSM_GCELLCHMGAD = new File(sCarpetaEntradaT + "GCELLCHMGAD.txt");
		File ficheroSalida_GSM_GCELLCHMGAD = new File(sCarpetaSalidaT + "GCELLCHMGAD_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCHMGAD, ficheroSalida_GSM_GCELLCHMGAD, retornaParametrosABuscarGCELLCHMGAD(),
				retornaParametrosCabeceraGSM_GCELLCHMGAD());

		// Creamos el fichero GCELLCHMGBASIC
		File ficheroGSM_GCELLCHMGBASIC = new File(sCarpetaEntradaT + "GCELLCHMGBASIC.txt");
		File ficheroSalida_GSM_GCELLCHMGBASIC = new File(sCarpetaSalidaT + "GCELLCHMGBASIC_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLCHMGBASIC, ficheroSalida_GSM_GCELLCHMGBASIC,
				retornaParametrosABuscarGCELLCHMGBASIC(), retornaParametrosCabeceraGSM_GCELLCHMGBASIC());

		// Creamos el fichero GCELLFREQ
		File ficheroGSM_GCELLFREQ = new File(sCarpetaEntradaT + "GCELLFREQ.txt");
		File ficheorSalida_GSM_GCELLFREQ = new File(sCarpetaSalidaT + "GCELLFREQ_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLFREQ, ficheorSalida_GSM_GCELLFREQ, retornaParametrosABuscarGCELLFREQ(),
				retornaParametrosCabeceraGSM_GCELLFREQ());

		// Creamos el fichero GCELLGPRS
		File ficheroGSM_GCELLGPRS = new File(sCarpetaEntradaT + "GCELLGPRS.txt");
		File ficheroSalida_GSM_GCELLGPRS = new File(sCarpetaSalidaT + "GCELLGPRS_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLGPRS, ficheroSalida_GSM_GCELLGPRS, retornaParametrosABuscarGCELLGPRS(),
				retornaParametrosCabeceraGSM_GCELLGPRS());

		// Creamos el fichero GCELLHO2GBA2
		File ficheroGSM_GCELLHO2GBA2 = new File(sCarpetaEntradaT + "GCELLHO2GBA2.txt");
		File ficheroSalida_GSM_GCELLHO2GBA2 = new File(sCarpetaSalidaT + "GCELLHO2GBA2_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHO2GBA2, ficheroSalida_GSM_GCELLHO2GBA2,
				retornaParametrosABuscarGCELLHO2GBA2(), retornaParametrosCabeceraGSM_GCELLHO2GBA2());

		// Creamos el fichero GCELLHOAD
		File ficheroGSM_GCELLHOAD = new File(sCarpetaEntradaT + "GCELLHOAD.txt");
		File ficheroSalida_GSM_GCELLHOAD = new File(sCarpetaSalidaT + "GCELLHOAD_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOAD, ficheroSalida_GSM_GCELLHOAD, retornaParametrosABuscarGCELLHOAD(),
				retornaParametrosCabeceraGSM_GCELLHOAD());

		// Creamos el fichero GCELLHOBASIC
		File ficheroGSM_GCELLHOBASIC = new File(sCarpetaEntradaT + "GCELLHOBASIC.txt");
		File ficheroSalida_GSM_GCELLHOBASIC = new File(sCarpetaSalidaT + "GCELLHOBASIC_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOBASIC, ficheroSalida_GSM_GCELLHOBASIC,
				retornaParametrosABuscarGCELLHOBASIC(), retornaParametrosCabeceraGSM_GCELLHOBASIC());

		// Creamos el fichero GCELLHOCTRL
		File ficheroGSM_GCELLHOCTRL = new File(sCarpetaEntradaT + "GCELLHOCTRL.txt");
		File ficheroSalida_GSM_GCELLHOCTRL = new File(sCarpetaSalidaT + "GCELLHOCTRL_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOCTRL, ficheroSalida_GSM_GCELLHOCTRL, retornaParametrosABuscarGCELLHOCTRL(),
				retornaParametrosCabeceraGSM_GCCELHOCTRL());

		// Creamos el fichero GCELLHOEMG
		File ficheroGSM_GCELLHOEMG = new File(sCarpetaEntradaT + "GCELLHOEMG.txt");
		File ficheroSalida_GSM_GCELLHOEMG = new File(sCarpetaSalidaT + "GCELLHOEMG_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOEMG, ficheroSalida_GSM_GCELLHOEMG, retornaParametrosABuscarGCELLHOEMG(),
				retornaParametrosCabeceraGSM_GCELLHOEMG());

		// Creamos el fichero GCELLHOFITPEN
		File ficheroGSM_GCELLHOFITPEN = new File(sCarpetaEntradaT + "GCELLHOFITPEN.txt");
		File ficheroSalida_GSM_GCELLHOFITPEN = new File(sCarpetaSalidaT + "GCELLHOFITPEN_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOFITPEN, ficheroSalida_GSM_GCELLHOFITPEN,
				retornaParametrosABuscarGCELLHOFITPEN(), retornaParametrosCabeceraGSM_GCELLHOFITPEN());

		// Creamos el fichero GCELLHOUTRANFDD
		File ficheroGSM_GCELLHOUTRANFDD = new File(sCarpetaEntradaT + "GCELLHOUTRANFDD.txt");
		File ficheroSalida_GSM_GCELLHOUTRANFDD = new File(sCarpetaSalidaT + "GCELLHOUTRANFDD_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLHOUTRANFDD, ficheroSalida_GSM_GCELLHOUTRANFDD,
				retornaParametrosABuscarGCELLHOUTRANFDD(), retornaParametrosCabeceraGSM_GCELLHOUTRANFDD());

		// Creamos el fichero GCELLIDLEAD
		File ficheroGSM_GCELLIDLEAD = new File(sCarpetaEntradaT + "GCELLIDLEAD.txt");
		File ficheroSalida_GSM_GCELLIDLEAD = new File(sCarpetaSalidaT + "GCELLIDLEAD_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLIDLEAD, ficheroSalida_GSM_GCELLIDLEAD, retornaParametrosABuscarGCELLIDLEAD(),
				retornaParametrosCabeceraGSM_GCELLIDLEAD());

		// Creamos el fichero GCELLIDLEADBASIC
		File ficheroGSM_GCELLIDLEADBASIC = new File(sCarpetaEntradaT + "GCELLIDLEBASIC.txt");
		File ficheroSalida_GSM_GCELLIDLEADBASIC = new File(sCarpetaSalidaT + "GCELLIDLEBASIC_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLIDLEADBASIC, ficheroSalida_GSM_GCELLIDLEADBASIC,
				retornaParametrosABuscarGCELLIDLEADBASIC(), retornaParametrosCabeceraGSM_GCELLIDLEADBASIC());

		// Creamos el fichero GCELLMAGRP
		File ficheroGSM_GCELLMAGRP = new File(sCarpetaEntradaT + "GCELLMAGRP.txt");
		File ficheroSalida_GSM_GCELLMAGRP = new File(sCarpetaSalidaT + "GCELLMAGRP_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLMAGRP, ficheroSalida_GSM_GCELLMAGRP, retornaParametrosABuscarGCELLMAGRP(),
				retornaParametrosCabeceraGSM_GCELLMAGRP());
		
		// Creamos el fichero GCELLOTHEXT 
		File ficheroGSM_GCELLOTHEXT = new File(sCarpetaEntradaT + "GCELLOTHEXT.txt");
		File ficheroSalida_GSM_GCELLOTHEXT = new File(sCarpetaSalidaT + "GCELLOTHEXT_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLOTHEXT,ficheroSalida_GSM_GCELLOTHEXT,retornaParametrosABuscarGCELLOTHEXT(),
				retornaParametrosCabeceraGSM_GCELLOTHEXT());
		
		// Creamos el fichero GCELLPRIEUTRANSYS
		File ficheroGSM_GCELLPRIEUTRANSYS = new File(sCarpetaEntradaT + "GCELLPRIEUTRANSYS.txt");
		File ficheroSalida_GSM_GCELLPRIEUTRANSYS = new File(sCarpetaSalidaT + "GCELLPRIEUTRANSYS_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLPRIEUTRANSYS,ficheroSalida_GSM_GCELLPRIEUTRANSYS,retornaParametrosABuscarGCELLPRIEUTRANSYS(),
				retornaParametrosCabeceraGCELLPRIEUTRANSYS());
		
		// Creamos el fichero GCELLPSBASE
		File ficheroGSM_GCELLPSBASE = new File(sCarpetaEntradaT + "GCELLPSBASE.txt");
		File ficheroSalida_GSM_GCELLPSBASE = new File(sCarpetaSalidaT + "GCELLPSBASE_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLPSBASE,ficheroSalida_GSM_GCELLPSBASE,retornaParametrosABuscarGCELLPSBASE(),
				retornaParametrosCabeceraGCELLPSBASE());
		
		// Creamos el fichero GCELLPSCHM
		File ficheroGSM_GCELLPSCHM = new File(sCarpetaEntradaT + "GCELLPSCHM.txt");
		File ficheroSalida_GSM_GCELLPSCHM = new File(sCarpetaSalidaT + "GCELLPSCHM_Salida.txt");
		escribeFicheroGSM(ficheroGSM_GCELLPSCHM,ficheroSalida_GSM_GCELLPSCHM,retornaParametrosABuscarGCELLPSCHM(),
				retornaParametrosCabeceraGCELLPSCHM());
	
		// Creamos el fichero GEXT2GCELL
		File ficheroGSM_GEXT2GCELL = new File(sCarpetaEntradaT + "GEXT2GCELL.txt");
		File ficheroSalida_GSM_GEXT2GCELL = new File(sCarpetaSalidaT + "GEXT2GCELL_Salida.txt");
		escribeFicheroGSM_GEXT(ficheroGSM_GEXT2GCELL,ficheroSalida_GSM_GEXT2GCELL,retornaParametrosABuscarGEXT2GCELL(),
				retornaParametrosCabeceraGEXT2GCELL(),sEXT2GCellIndex);
		
		// Creamos el fichero GEXT3GCELL 
		File ficheroGSM_GEXT3GCELL = new File(sCarpetaEntradaT + "GEXT3GCELL.txt");
		File ficheroSalida_GSM_GEXT3GCELL = new File(sCarpetaSalidaT + "GEXT3GCELL_Salida.txt");
		escribeFicheroGSM_GEXT(ficheroGSM_GEXT3GCELL,ficheroSalida_GSM_GEXT3GCELL,retornaParametrosABuscarGEXT3GCELL(),
				retornaParametrosCabeceraGEXT3GCELL(),sEXT3GCellIndex);
		
		// Creamos el fichero GEXTLTECELL
		File ficheroGSM_GEXTLTECELL = new File(sCarpetaEntradaT + "GEXTLTECELL.txt");
		File ficheroSalida_GSM_GEXTLTECELL = new File(sCarpetaSalidaT + "GEXTLTECELL_Salida.txt");
		creaArbolLTE(ficheroGSM_GEXTLTECELL);
		escribeFicheroGSM_LTE(ficheroGSM_GEXTLTECELL,ficheroSalida_GSM_GEXTLTECELL,retornaParametrosABuscarGEXTLTECELL(),
				retornaParametrosCabeceraGEXTLETCELL());
		
		// Creamos el fichero GLTENCELL
		File ficheroGSM_GLTENCELL = new File(sCarpetaEntradaT + "GLTENCELL.txt");
		File ficheroSalida_GSM_GLTENCELL = new File(sCarpetaSalidaT + "GLTENCELL_Salida.txt");
		escribeFicheroGSM_LTENCELL(ficheroGSM_GLTENCELL,ficheroSalida_GSM_GLTENCELL, retornaParametrosABuscarGLTENCELL(),
				retornaParametrosCabeceraGLTENCELL());
		
		// Creamos el fichero GTRX
		File ficheroGSM_GTRX = new File(sCarpetaEntradaT + "GTRX.txt");
		File ficheroSalida_GSM_GTRX = new File(sCarpetaSalidaT + "GTRX_Salida.txt");
		creaArbol_GSM_GTRS(ficheroGSM_GTRX);
		escribeFicheroGSM(ficheroGSM_GTRX,ficheroSalida_GSM_GTRX,retornaParametrosABuscarGTRX(),
				retornaParametrosCabeceraGTRX());

		// Creamos el fichero GTRXCHAN
		File ficheroGSM_GTRXCHAN = new File(sCarpetaEntradaT + "GTRXCHAN.txt");
		File ficheroSalida_GSM_GTRXCHAN = new File(sCarpetaSalidaT + "GTRXCHAN_Salida.txt");
		escribeFicheroGSM_GTRX(ficheroGSM_GTRXCHAN,ficheroSalida_GSM_GTRXCHAN,retornaParametrosABuscarGTRXCHAN(),
				retornaParametrosCabeceraGTRXCHAN());
		
		// Creamos el fichero GTRXCHANHOP
		File ficheroGSM_GTRXCHANHOP = new File(sCarpetaEntradaT + "GTRXCHANHOP.txt");
		File ficheroSalida_GSM_GTRXCHANHOP = new File(sCarpetaSalidaT + "GTRXCHANHOP_Salida.txt");
		escribeFicheroGSM_GTRX(ficheroGSM_GTRXCHANHOP,ficheroSalida_GSM_GTRXCHANHOP,retornaParametrosABuscarGTRXCHANHOP(),
				retornaParametrosCabeceraGTXCHANHOP());
		
		// Creamos el fichero GTRXDEV
		File ficheroGSM_GTRXDEV = new File(sCarpetaEntradaT + "GTRXDEV.txt");
		File ficheroSalida_GSM_GTRXDEV = new File(sCarpetaSalidaT + "GTRXDEV_Salida.txt");
		escribeFicheroGSM_GTRX(ficheroGSM_GTRXDEV,ficheroSalida_GSM_GTRXDEV,retornaParametrosABuscarGTRXDEV(),
				retornaParametrosCabeceraGTRXDEV());
		
		// Creamos el fichero PTPBVC
		File ficheroGSM_PTPBVC = new File(sCarpetaEntradaT + "PTPBVC.txt");
		File ficheroSalida_GSM_PTPBVC = new File(sCarpetaSalidaT + "PTVBVC_Salida.txt");
		escribeFicheroGSM(ficheroGSM_PTPBVC,ficheroSalida_GSM_PTPBVC,retornaParametrosABuscarPTPBVC(),
				retornaParametrosCabeceraPTPBVC());	
		System.out.println("FIN");
	}

	
	private static void creaMapaGGCELL(File ficheroGCELL) {
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
		
	}

	private static void creaArbolGCELLconBCCH(File ficheroGCELLFreq) {
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
							if (iCellIndex == UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroCELLFreq.get(sCellId)])) {
									mapaGCELLCellIdParametroValor.get(sNeid).get(iCellIndex).put("BCCH",aValoresParametros[mapaCabeceraFicheroCELLFreq.get("FREQ1")]);
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
		
	}

	private static void creaArbolGCELL_GEXT2GCELL(File ficheroGEXT2GCELL) {
		TreeMap<String, String> arbolParametrosGEXT2GCELL = retornaParametrosABuscarGEXT2GCELLAux();
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
		
	}
	
	private static void creaArbolGCELL_GEXT3GCELL(File ficheroGEXT3GCELL) {
		TreeMap<String, String> arbolParametrosGEXT3GCELL = retornaParametrosABuscarGEXT3GCELLAux();
		try (FileReader fr = new FileReader(ficheroGEXT3GCELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGEXT3GCELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sNeid)])) {
					mapaGCELLCellIdParametroValor.put(aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sNeid)],
							new TreeMap<Integer, TreeMap<String, String>>());
				}
				if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(
								aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sEXT3GCellIndex)]))) {
					mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sNeid)])
							.put(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sEXT3GCellIndex)]),
									new TreeMap<String, String>());
				}

				for (String sParametro : arbolParametrosGEXT3GCELL.keySet()) {
					if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sEXT3GCellIndex)]))
							.containsKey(arbolParametrosGEXT3GCELL.get(sParametro))) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sEXT3GCellIndex)]))
								.put(arbolParametrosGEXT3GCELL.get(sParametro),
										aValoresParametros[mapaCabeceraFicheroGEXT3GCELL.get(sParametro)]);
					}
				}

				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception GCELL_GEXT3GCELL");
		} catch (IOException e) {
			System.out.println("IOException GCELL_GEXT3GCELL");
		}
		
	}
	
	private static void creaArbolGCELL_GEXTLTECELL(File ficheroGEXTLTECELL) {
		TreeMap<String, String> arbolParametrosGEXTLTECELL = retornaParametrosABuscarGEXTLTECELLAux();
		try (FileReader fr = new FileReader(ficheroGEXTLTECELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGEXTLTECELL = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGCELLCellIdParametroValor
						.containsKey(aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sNeid)])) {
					mapaGCELLCellIdParametroValor.put(aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sNeid)],
							new TreeMap<Integer, TreeMap<String, String>>());
				}
				if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(
								aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sEXTLTECELLID)]))) {
					mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sNeid)])
							.put(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sEXTLTECELLID)]),
									new TreeMap<String, String>());
				}

				for (String sParametro : arbolParametrosGEXTLTECELL.keySet()) {
					if (!mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(
									aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sEXTLTECELLID)]))
							.containsKey(arbolParametrosGEXTLTECELL.get(sParametro))) {
						mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(
										aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sEXTLTECELLID)]))
								.put(arbolParametrosGEXTLTECELL.get(sParametro),
										aValoresParametros[mapaCabeceraFicheroGEXTLTECELL.get(sParametro)]);
					}
				}

				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception GCELL_GEXTLTECELL");
		} catch (IOException e) {
			System.out.println("IOException GCELL_GEXTLTECELL");
		}
		
	}
	
	
	private static void creaArbolGSMBTS(File ficheroGSM_BTS){
		mapaGSMBTS = new TreeMap<String, TreeMap<String, TreeMap<String, String>>>();
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
						.containsKey(sBTSNAME)) {
					mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])
							.put(sBTSNAME,aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSNAME)]);	
				}
				if (!mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])
						.containsKey(sFecha)) {
					mapaGSMBTS.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sNeid)])
							.get(aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sBTSId)])
							.put(sFecha,aValoresParametros[mapaCabeceraFicheroGSMBTS.get(sFecha)]);
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
	}
	
	private static void creaArbol_GSM_GTRS(File ficheroGSM_GTRX) {
		mapaGSMGTRX = new TreeMap<String,TreeMap<String,TreeMap<String,String>>>();
		try (FileReader fr = new FileReader(ficheroGSM_GTRX); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMGTRX = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGSMGTRX.containsKey(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sNeid)])) {
					mapaGSMGTRX.put(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sNeid)],
							new TreeMap<String, TreeMap<String, String>>());
				}
				if(!mapaGSMGTRX.get(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sNeid)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sTRXID)])){
					mapaGSMGTRX.get(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sNeid)])
					.put(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sTRXID)],new TreeMap<String,String>());
				}
				if(!mapaGSMGTRX.get(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sTRXID)])
						.containsKey(sCellId)){
					mapaGSMGTRX.get(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sTRXID)])
					.put(sCellId,aValoresParametros[mapaCabeceraFicheroGSMGTRX.get(sCellId)]);
				}
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void escribeArbolLTE(File ficheroArbolGSM_LTE) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbolGSM_LTE))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"CELLID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"NOMBRE\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"Fecha\"");
			bw.write(compositorCabecera.toString()+"\r\n");
			for (String sNeid : mapaGSMLTE.keySet()) {
				for (Integer sBTSid : mapaGSMLTE.get(sNeid).keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(sBTSid);
					compositorValores.appendSeparator(",");
					for(String sParametro : mapaGSMLTE.get(sNeid).get(sBTSid).keySet()){
						compositorValores.append(mapaGSMLTE.get(sNeid).get(sBTSid).get(sParametro));
						compositorValores.appendSeparator(",");
					}
					bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
				}
			}
		} catch (IOException e) {
			System.out.println("IO  Exception");
		}
	}


	private static void escribeArbolBTS(File ficheroArbolGSM_BTS) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbolGSM_BTS))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"BTSID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"NOMBRE\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"Fecha\"");
			bw.write(compositorCabecera.toString()+"\r\n");
			for (String sNeid : mapaGSMBTS.keySet()) {
				for (String sBTSid : mapaGSMBTS.get(sNeid).keySet()) {
					StrBuilder compositorValores = new StrBuilder();
					compositorValores.append(sNeid);
					compositorValores.appendSeparator(",");
					compositorValores.append(sBTSid);
					compositorValores.appendSeparator(",");
					for(String sParametro : mapaGSMBTS.get(sNeid).get(sBTSid).keySet()){
						compositorValores.append(mapaGSMBTS.get(sNeid).get(sBTSid).get(sParametro));
						compositorValores.appendSeparator(",");
					}
					bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
				}
			}
		} catch (IOException e) {
			System.out.println("IO  Exception");
		}

	}
	
	private static void escribeArbolBTSRET(File ficheroArbolGSM_BTSRET) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroArbolGSM_BTSRET))) {
			StrBuilder compositorCabecera = new StrBuilder();
			compositorCabecera.append("\"NEID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"BTSID\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"DEVICE\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"DEVICENAME\"");
			compositorCabecera.appendSeparator(",");
			compositorCabecera.append("\"Fecha\"");
			bw.write(compositorCabecera.toString()+"\r\n");
			for (String sNeid : mapaGSMBTSRET.keySet()) {
				for (String sBTSid : mapaGSMBTSRET.get(sNeid).keySet()) {
					for(String sDevice : mapaGSMBTSRET.get(sNeid).get(sBTSid).keySet()){
						StrBuilder compositorValores = new StrBuilder();
						compositorValores.append(sNeid);
						compositorValores.appendSeparator(",");
						compositorValores.append(sBTSid);
						compositorValores.appendSeparator(",");
						compositorValores.append(sDevice);
						compositorValores.appendSeparator(",");
						for(String sParametro : mapaGSMBTSRET.get(sNeid).get(sBTSid).get(sDevice).keySet()){
							compositorValores.append(mapaGSMBTSRET.get(sNeid).get(sBTSid).get(sDevice).get(sParametro));
							compositorValores.appendSeparator(",");
						}
						bw.write(compositorValores.toString().substring(0, compositorValores.size()-1)+"\r\n");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("IO  Exception");
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
	
	private static void creaArbolGSM_BTSRET(File ficheroGSM_BTSRET){
		mapaGSMBTSRET =  new TreeMap<String, TreeMap<String, TreeMap<String, TreeMap<String,String>>>> ();
		try (FileReader fr = new FileReader(ficheroGSM_BTSRET); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMBTSRET = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGSMBTSRET.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])) {
					mapaGSMBTSRET.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)],
							new TreeMap<String, TreeMap<String,TreeMap<String,String>>>());
				}
				if(!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])){
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
					.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)], new TreeMap<String,TreeMap<String,String>>());
				}
				if(!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])){
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
					.put(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)],new TreeMap<String,String>());
				}
				if(!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceName)])){
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
					.put(sDeviceName, aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceName)]);
				}
				if(!mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
						.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
						.containsKey(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sFecha)])){
					mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sNeid)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sBTSId)])
					.get(aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sDeviceNo)])
					.put(sFecha, aValoresParametros[mapaCabeceraFicheroGSMBTSRET.get(sFecha)]);
				}
				
				sValoresParametros = br.readLine();
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	}
	
	private static void creaArbolLTE(File ficheroGSM_GEXTLTECELL){
		mapaGSMLTE = new TreeMap<String,TreeMap<Integer,TreeMap<String,String>>>();
		try (FileReader fr = new FileReader(ficheroGSM_GEXTLTECELL); BufferedReader br = new BufferedReader(fr)) {
			String sCabeceraFichero = br.readLine();
			TreeMap<String, Integer> mapaCabeceraFicheroGSMLTE = retornaMapaCabecera(sCabeceraFichero);
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				if (!mapaGSMLTE.containsKey(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)])) {
					mapaGSMLTE.put(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)],
							new TreeMap<Integer, TreeMap<String, String>>());
				}
				if(!mapaGSMLTE.get(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sEXTLTECELLID)]))){
					mapaGSMLTE.get(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)])
					.put(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sEXTLTECELLID)]),new TreeMap<String,String>());
				}
				if(!mapaGSMLTE.get(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)])
						.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sEXTLTECELLID)]))
						.containsKey("EXTLTECELLNAME")){
					mapaGSMLTE.get(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)])
					.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sEXTLTECELLID)]))
					.put("EXTLTECELLNAME",aValoresParametros[mapaCabeceraFicheroGSMLTE.get("EXTLTECELLNAME")]);
				}
				if(!mapaGSMLTE.get(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)])
						.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sEXTLTECELLID)]))
						.containsKey(sFecha)){
					mapaGSMLTE.get(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sNeid)])
					.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sEXTLTECELLID)]))
					.put(sFecha,aValoresParametros[mapaCabeceraFicheroGSMLTE.get(sFecha)]);
				}
				
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				compositorCabecera.append("\""+sParametro+"\"");
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {

				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolMapaValor.put(sBSC_SOURCE, aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)]);
				arbolMapaValor.put(sCell2GIndexSource,aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]);
				arbolMapaValor.put(sCell2GIndexNeigh,aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]);

				if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))) {
					sRC2GNCELLNAME = mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))
							.get("CELLNAME");

					arbolMapaValor.put("SRC2GNCELLNAME", sRC2GNCELLNAME);
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))
							.containsKey("NCC")) {
						sNCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))
								.get("NCC");
						arbolMapaValor.put("NCC", sNCC);
					}
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))
							.containsKey("BCC")) {
						sBCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))
								.get("BCC");
						arbolMapaValor.put("BCC", sBCC);
					}
				}
				if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
						.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))
						.containsKey("BCCH")) {
					sBCCH = mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexSource)]))
							.get("BCCH");
					arbolMapaValor.put("BCCH", sBCCH);
				}

				if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
						.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))) {
					sNRC2GNCELLNAME = mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))
							.get("CELLNAME");
					arbolMapaValor.put("NBR2GNCELLNAME", sNRC2GNCELLNAME);
					if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))
							.containsKey("NCC")) {
						NBR2GNCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))
								.get("NCC");
						arbolMapaValor.put("NBR2GNCC", NBR2GNCC);
					}
					if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))
							.containsKey("BCC")) {
						NBR2GBCC = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))
								.get("BCC");
						
						arbolMapaValor.put("NBR2GBCC", NBR2GBCC);
					}
					if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))
							.containsKey("BCCH")) {
						NBR2GBCCH = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFicheroGSMG2GNCELL.get(sCell2GIndexNeigh)]))
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
	
	private static void escribeFicheroGSM_G3GNCELL(File ficheroGSM_entrada, File ficheroSalida_GSM, String[] aParametrosABuscar,
			String[] aParametrosCabecera) {

		TreeMap<String, String> arbolParametroValor;
		String sNombreCeldaFuente = "---";
		String sNombreCeldaVecina = "---";

		try (FileReader fr = new FileReader(ficheroGSM_entrada);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM))) {
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
				arbolParametroValor.put(sBSC_SOURCE, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				if (mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCell3GIndexSource)]))) {
						sNombreCeldaFuente = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCell3GIndexSource)]))
								.get("CELLNAME");
						arbolParametroValor.put("SRC3GNCELLNAME", sNombreCeldaFuente);
					}
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCell3GIndexNeigh)]))) {
						sNombreCeldaVecina = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCell3GIndexNeigh)]))
								.get("CELLNAME");
						arbolParametroValor.put("NBR3GNCELLNAME", sNombreCeldaVecina);
					}
				}
				for (String sParametro : aParametrosABuscar) {
//					if(sParametro.equalsIgnoreCase("CMEPRIOR")){
//						String sParametroAux = "PRIOR";
//					}
					if(mapaCabeceraFichero.containsKey(sParametro)){
						arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabecera);
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found exception en G3GNCELL");
		} catch (IOException e) {
			System.out.println("IO exception en G3GNCELL");
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
	
	private static void escribeFicheroGSMBTS(File ficheroGSMBTS_entrada, File ficheroSalida_GSMBTS,String[] aParametrosABuscar,
			String[] aParametrosCabecera){
		TreeMap<String, String> arbolParametroValor;
		try (FileReader fr = new FileReader(ficheroGSMBTS_entrada);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSMBTS))) {
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
				arbolParametroValor.put(sBTSId, aValoresParametros[mapaCabeceraFichero.get(sBTSId)]);
				for (String sParametro : aParametrosABuscar) {
					if(mapaCabeceraFichero.containsKey(sParametro)){
						arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabecera);
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	private static void escribeFicheroGSMBTSRET(File ficheroGSMBTSRET_entrada,File ficheroSalida_GSMBTSRET,String[] aParametrosABuscar,
			String[] aParametrosCabecera){
		TreeMap<String, String> arbolParametroValor;
		String sNombreBTS = "---";
		try (FileReader fr = new FileReader(ficheroGSMBTSRET_entrada);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSMBTSRET))) {
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
				arbolParametroValor.put(sBTSId, aValoresParametros[mapaCabeceraFichero.get(sBTSId)]);
				if(mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])){
					if(mapaGSMBTS.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(aValoresParametros[mapaCabeceraFichero.get(sBTSId)])){
						if(mapaGSMBTS.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(aValoresParametros[mapaCabeceraFichero.get(sBTSId)])
								.containsKey(sBTSNAME)){
							sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
									.get(aValoresParametros[mapaCabeceraFichero.get(sBTSId)])
									.get(sBTSNAME);
							arbolParametroValor.put(sBTSNAME, sNombreBTS);
						}	
					}
				}
				for (String sParametro : aParametrosABuscar) {
					if(mapaCabeceraFichero.containsKey(sParametro)){
						if(sParametro.equalsIgnoreCase("DEVICENO")){
							String sParametroAux = "DEVICE";
							arbolParametroValor.put(sParametroAux, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}else{
							arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabecera);
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void escribeFicheroGSM_BTSRETDEVICEDATA_SUBUNIT(File ficheroGSMBTSRETDEVICEDATA_entrada, File ficheroSalida_GSMBTSRETDEVICEDATA,
			String[] aParametrosABuscar,String[] aParametrosCabecera){
		TreeMap<String, String> arbolParametroValor;
		String sNombreBTS = "---";
		String sNombreDevice = "---";
		try (FileReader fr = new FileReader(ficheroGSMBTSRETDEVICEDATA_entrada);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSMBTSRETDEVICEDATA))) {
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
				arbolParametroValor.put(sBTSId, aValoresParametros[mapaCabeceraFichero.get(sBTSId)]);
				if(mapaGSMBTS.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])){
					if(mapaGSMBTS.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(aValoresParametros[mapaCabeceraFichero.get(sBTSId)])){
						if(mapaGSMBTS.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(aValoresParametros[mapaCabeceraFichero.get(sBTSId)]).containsKey(sBTSNAME)){
							sNombreBTS = mapaGSMBTS.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
									.get(aValoresParametros[mapaCabeceraFichero.get(sBTSId)])
									.get(sBTSNAME);
							arbolParametroValor.put(sBTSNAME, sNombreBTS);
						}	
					}
					if(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(aValoresParametros[mapaCabeceraFichero.get(sBTSId)])){
						if(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(aValoresParametros[mapaCabeceraFichero.get(sBTSId)]).containsKey(aValoresParametros[mapaCabeceraFichero.get(sDeviceNo)])){
							if(mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(aValoresParametros[mapaCabeceraFichero.get(sBTSId)]).get(aValoresParametros[mapaCabeceraFichero.get(sDeviceNo)]).containsKey(sDeviceName)){
								sNombreDevice = mapaGSMBTSRET.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(aValoresParametros[mapaCabeceraFichero.get(sBTSId)]).get(aValoresParametros[mapaCabeceraFichero.get(sDeviceNo)]).get(sDeviceName);
								arbolParametroValor.put(sDeviceName,sNombreDevice);
							}
						}
					}
				}
				for (String sParametro : aParametrosABuscar) {
					if(sParametro.equalsIgnoreCase(sDeviceNo)){
						String sParametroAux = "DEVICE";
						arbolParametroValor.put(sParametroAux, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}else{
						arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabecera);
				sValoresParametros = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				compositorCabecera.append("\""+sParametro+"\"");
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolParametroValor = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolParametroValor.put(sControllerName, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				arbolParametroValor.put(sCellIndex, aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]);

				if (mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))) {
						sNombreCelda = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))
								.get("CELLNAME");
						arbolParametroValor.put("CELLNAME", sNombreCelda);
					}
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))) {
						sCellIdentificador = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellIndex)]))
								.get("CELLID");
						arbolParametroValor.put("CI", sCellIdentificador);
					}
				}
				for (String sParametro : aParametrosABuscar) {
//					if(sParametro.equalsIgnoreCase("CMEPRIOR")){
//						String sParametroAux = "PRIOR";
//					}
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

	private static void escribeFicheroGSM_LTE(File ficheroLTE_entrada, File ficheroSalida_LTE, String[] aParametrosABuscar,
			String[] aParametrosCabecera) {

		TreeMap<String, String> arbolParametroValor;
		try (FileReader fr = new FileReader(ficheroLTE_entrada);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_LTE))) {
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
				arbolParametroValor.put(sBSC_SOURCE, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				arbolParametroValor.put(sEXT3GCellIndex, aValoresParametros[mapaCabeceraFichero.get(sEXTLTECELLID)]);

				for (String sParametro : aParametrosABuscar) {
					if(sParametro.equalsIgnoreCase("EXTLTECELLNAME")){
						String sParametroAux = "EXT3GCELLNAME";
						if(mapaCabeceraFichero.containsKey(sParametro)){
							arbolParametroValor.put(sParametroAux, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}
					}else{
						if(mapaCabeceraFichero.containsKey(sParametro)){
							arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}
					}
				}
				escribirFichero(bw, arbolParametroValor, aParametrosCabecera);
				sValoresParametros = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found exception en LTE");
		} catch (IOException e) {
			System.out.println("IO exception en LTE");
		}

	}

	private static void escribeFicheroGSM_LTENCELL(File ficheroGSM_GLTENCELL, File ficheroSalida_GSM_GLTENCELL,
			String[] aParametrosABuscar, String[] aParametrosCabecera) {
		TreeMap<String, String> arbolParametroValor;
		String sNombreCelda = "---";
		String sNombreCeldaVecina = "---";
		try (FileReader fr = new FileReader(ficheroGSM_GLTENCELL);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM_GLTENCELL))) {
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
				arbolParametroValor.put(sBSC_SOURCE, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				if(mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])){
					if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellLTEIndexSource)]))){
						if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellLTEIndexSource)]))
							.containsKey(sCellName)){
							sNombreCelda = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
									.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellLTEIndexSource)]))
									.get(sCellName);
							arbolParametroValor.put(sSRCLTENCELLNAME, sNombreCelda);
						}
					}
				}
				if(mapaGSMLTE.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])){
					if(mapaGSMLTE.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellLTEIndexNeigh)]))){

						if(mapaGSMLTE.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellLTEIndexNeigh)]))
							.containsKey("EXTLTECELLNAME")){
							sNombreCeldaVecina = mapaGSMLTE.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
									.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sCellLTEIndexNeigh)]))
									.get("EXTLTECELLNAME");
							arbolParametroValor.put(sNBRLTENCELLNAME, sNombreCeldaVecina);
						}
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
	
	private static void escribeFicheroGSM_GEXT(File ficheroGSM_entrada, File ficheroSalida_GSM, String[] aParametrosABuscar,
			String[] aParametrosCabecera,String sIndiceTecnologia) {

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
				compositorCabecera.append("\""+sParametro+"\"");
				compositorCabecera.appendSeparator(",");
			}
			bw.write(compositorCabecera.toString().substring(0, compositorCabecera.size() - 1) + "\r\n");
			String sValoresParametros = br.readLine();
			while (sValoresParametros != null) {
				arbolParametroValor = new TreeMap<String, String>();
				String[] aValoresParametros = UtilidadesTexto.divideTextoEnTokens(sValoresParametros, ",\t");
				arbolParametroValor.put(sBSC_SOURCE, aValoresParametros[mapaCabeceraFichero.get(sNeid)]);
				arbolParametroValor.put(sIndiceTecnologia, aValoresParametros[mapaCabeceraFichero.get(sIndiceTecnologia)]);

				if (mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sIndiceTecnologia)]))) {
						sNombreCelda = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sIndiceTecnologia)]))
								.get(sCellName);
						arbolParametroValor.put(sCellName, sNombreCelda);
					}
					if (mapaGCELLCellIdParametroValor
							.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
							.containsKey(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sIndiceTecnologia)]))) {
						sCellIdentificador = mapaGCELLCellIdParametroValor
								.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(aValoresParametros[mapaCabeceraFichero.get(sIndiceTecnologia)]))
								.get("CELLID");
						arbolParametroValor.put("CI", sCellIdentificador);
					}
				}
				for (String sParametro : aParametrosABuscar) {
					if(sParametro.equalsIgnoreCase("CMEPRIOR")){
						String sParametroAux = "PRIOR";
						if(mapaCabeceraFichero.containsKey(sParametro)){
							arbolParametroValor.put(sParametroAux, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}
					}else{				
						if(mapaCabeceraFichero.containsKey(sParametro)){
							arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}
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
	
	private static void escribeFicheroGSM_GTRX(File ficheroGSM_entrada, File ficheroSalida_GSM, String[] aParametrosABuscar,
			String[] aParametrosCabecera) {

		TreeMap<String, String> arbolParametroValor;
		String sCellIndexA = "---";
		String sNombreCelda = "---";
		String sCellIdentificador = "---";

		try (FileReader fr = new FileReader(ficheroGSM_entrada);
				BufferedReader br = new BufferedReader(fr);
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida_GSM))) {
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
				if(mapaGSMGTRX.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])){
					if(mapaGSMGTRX.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).containsKey(aValoresParametros[mapaCabeceraFichero.get(sTRXID)])){
						if(mapaGSMGTRX.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(aValoresParametros[mapaCabeceraFichero.get(sTRXID)]).containsKey(sCellId)){
							sCellIndexA = mapaGSMGTRX.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)]).get(aValoresParametros[mapaCabeceraFichero.get(sTRXID)]).get(sCellId);
							arbolParametroValor.put(sCellIndex, sCellIndexA);
						}
					}
				}
				if(!sCellIndexA.equals("---")){
					if (mapaGCELLCellIdParametroValor.containsKey(aValoresParametros[mapaCabeceraFichero.get(sNeid)])) {
						if (mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.containsKey(UtilidadesTexto.dameValorEntero(sCellIndexA))){
							if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
								.get(UtilidadesTexto.dameValorEntero(sCellIndexA))
								.containsKey(sCellName)){
									sNombreCelda = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
											.get(UtilidadesTexto.dameValorEntero(sCellIndexA))
											.get(sCellName);
									arbolParametroValor.put(sCellName, sNombreCelda);
							}
							if(mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
									.get(UtilidadesTexto.dameValorEntero(sCellIndexA))
									.containsKey("CELLID")){
									sCellIdentificador = mapaGCELLCellIdParametroValor.get(aValoresParametros[mapaCabeceraFichero.get(sNeid)])
												.get(UtilidadesTexto.dameValorEntero(sCellIndexA))
												.get("CELLID");
									arbolParametroValor.put("CI", sCellIdentificador);
							}
						}
					}
				}
				
				for (String sParametro : aParametrosABuscar) {
					if(sParametro.equalsIgnoreCase("CMEPRIOR")){
						String sParametroAux = "PRIOR";
						if(mapaCabeceraFichero.containsKey(sParametro)){
							arbolParametroValor.put(sParametroAux, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}
					}else{				
						if(mapaCabeceraFichero.containsKey(sParametro)){
							arbolParametroValor.put(sParametro, aValoresParametros[mapaCabeceraFichero.get(sParametro)]);
						}
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

	private static TreeMap<String, String> retornaParametrosABuscarGEXT2GCELLAux() {
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
	
	private static TreeMap<String, String> retornaParametrosABuscarGEXT3GCELLAux() {
		TreeMap<String, String> aParametrosABuscar = new TreeMap<String, String>();
		aParametrosABuscar.put("EXT3GCELLNAME", "CELLNAME");
		aParametrosABuscar.put("CI", "CELLID");
		aParametrosABuscar.put("Fecha", "Fecha");
		aParametrosABuscar.put("Red", "Red");
		aParametrosABuscar.put("ipEntrada", "ipEntrada");
		return aParametrosABuscar;
	}
	
	private static TreeMap<String, String> retornaParametrosABuscarGEXTLTECELLAux() {
		TreeMap<String, String> aParametrosABuscar = new TreeMap<String, String>();
		aParametrosABuscar.put("EXTLTECELLNAME", "CELLNAME");
		aParametrosABuscar.put("CI", "CELLID");
		aParametrosABuscar.put("Fecha", "Fecha");
		aParametrosABuscar.put("Red", "Red");
		aParametrosABuscar.put("ipEntrada", "ipEntrada");
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarBTS(){
		String[] aParametrosABuscar = {"BTSNAME","BTSDESC","BTSTYPE","ABISBYPASSMODE","ACTSTATUS","FLEXABISMODE","HOSTTYPE","INNBBULICSHAEN",
			"INTRABBPOOLSWITCH","IPPHYTRANSTYPE","ISCONFIGEDRING","MAINBMPMODE","MAINPORTNO","MPMODE","SEPERATEMODE","SERVICEMODE","SRANMODE",
			"TRANDETSWITCH","TSASSIGNOPTISW","WORKMODE","Fecha","Red","ipEntrada"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarBTSRET(){
		String[] aParametrosABuscar = {"DEVICENO","DEVICENAME","CTRLPORTCN","CTRLPORTNO", 
				"CTRLPORTSN","CTRLPORTSRN","POLARTYPE","RETTYPE","SCENARIO","SERIALNO","VENDORCODE","Fecha","Red","ipEntrada"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarBTSDEVICEDATA() {
		String[] aParametrosABuscar = {"DEVICENO","BAND1","BAND2","BAND3","BAND4","BEARING","SUBUNITNO","TILT","SERIALNO","Fecha","Red","ipEntrada"};
		return aParametrosABuscar;
	}
		
	private static String[] retornaParametrosABuscarBTSRETSUBUNIT() {
		String[] aParametrosABuscar = {"DEVICENO","AER","CELLID","CONNCN1","CONNCN2","CONNPN1","CONNPN2","CONNSN1","CONNSN2","CONNSRN1","CONNSRN2","SUBUNITNO","TILT","Fecha","Red","ipEntrada"};
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
	
	private static String[] retornaParametrosABuscarG3GNCELL() {
		String [] aParametrosABuscar = {"RSCPOFF","ECNOOFF","HOSTAT3GTDD","HODURT3GTDD","HOSTAT3G","HODURT3G","NCELLPRI",
				"Fecha", "Red", "ipEntrada"};
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
	
	private static String[] retornaParametrosABuscarGCELLPSCHM() {
		String[] aParametrosABuscar = {"ABISTSFREETM","ACTIVETBFSWITCH",
			"ALLOCSINGLEPDCHFORSIGNALLING","APPLYMULTIABISTS","AUTOGPRSCHPRI","BEARP1PRIMAXPDCHNUM",
			"BEARP1PRIWEIGHT","BEARP2PRIMAXPDCHNUM","BEARP2PRIWEIGHT","BEARP3PRIMAXPDCHNUM","BEARP3PRIWEIGHT","BKGARP1PRIMAXPDCHNUM",
			"BKGARP1PRIWEIGHT","BKGARP2PRIMAXPDCHNUM","BKGARP2PRIWEIGHT","BKGARP3PRIMAXPDCHNUM","BKGARP3PRIWEIGHT","CHIDLHIGHTHR",
			"CHIDLLOWTHR","CSBUSYPDAPPINTERVAL","DEFAULTDYNPDCHPRETRANNUM","DUTYCYCLEBASEDPDCHFETCH","DUTYCYCLEBASEDPDCHMAG",
			"DUTYCYCLEBASEDPDCHREL","DWNDYNCHNTRANLEV","DYNCHFREETM","DYNCHNPREEMPTLEV","DYNCHTRANRESLEV","ENPDADMINOPT",
			"IMARP1PRIMAXPDCHNUM","IMARP1PRIWEIGHT","IMARP2PRIMAXPDCHNUM","IMARP2PRIWEIGHT","IMARP3PRIMAXPDCHNUM","IMARP3PRIWEIGHT",
			"IMOPTTHRSH","IMPDCHMULTIPLEXWEIGHT","IOUPDCHSWTICH","IUOCHNTRAN","MAXPDCHRATE","MSRDMCSLEV","MSRDPDCHLEV","PDCHDWNLEV",
			"PDCHPOWERPLENT","PDCHPOWERPLENTTHRES","PDCHREFORMING","PDCHUPLEV","POWTUNIT","PRECONNECTSLAVEABIS","PSRESPREEMPT",
			"PSRESPREEMPTED","PSSERVICEBUSYTHRESHOLD","RADIORESADAADJDLLOADTHD","RADIORESADAADJSWITCH","RADIORESADAADJULLOADTHD",
			"RAMBCAP","RESERVEDDYNPDCHPRETRANNUM","RTTIPDCHMULTIPLEXTHRESH","THP1ARP1PRIMAXPDCHNUM","THP1ARP1PRIWEIGHT",
			"THP1ARP2PRIMAXPDCHNUM","THP1ARP2PRIWEIGHT","THP1ARP3PRIMAXPDCHNUM","THP1ARP3PRIWEIGHT","THP2ARP1PRIMAXPDCHNUM",
			"THP2ARP1PRIWEIGHT","THP2ARP2PRIMAXPDCHNUM","THP2ARP2PRIWEIGHT","THP2ARP3PRIMAXPDCHNUM","THP2ARP3PRIWEIGHT",
			"THP3ARP1PRIMAXPDCHNUM","THP3ARP1PRIWEIGHT","THP3ARP2PRIMAXPDCHNUM","THP3ARP2PRIWEIGHT","THP3ARP3PRIMAXPDCHNUM",
			"THP3ARP3PRIWEIGHT","UPDYNCHNTRANLEV","Fecha","Red","ipEntrada","ACCTECHREQSW","EXTNMOISW"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGEXT2GCELL() {
		String[] aParametrosABuscar = {"BSC_SOURCE","EXT2GCELLID","EXT2GCELLID","MCC","MNC","LAC","CI","BCCH","NCC","BCC",
				"COMSC","LAYER","CMEPRIOR","HOTHRES","SDPUNVAL","SDPUNTIME","MSRXMIN","TIMEPUNISH","HOPUNISHVALUE","HOOFFSET",
				"LOADHOENEXT2G","LOADACCTHRES","ISNC2SUPPORT","ISGPRSSUPPORT","ISEDGESUPPORT","RA","BSCIDX","OPNAME","Fecha",
				"Red","ipEntrada","GCNOPGRPINDEX","IBCAIIINTERBSCHOINFOSW"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGEXT3GCELL() {
		String[] aParametrosABuscar = {"BSC_SOURCE","EXT3GCELLID","EXT3GCELLNAME","MCC","MNC","LAC","CI","RNCID","DF","SCRAMBLE",
				"DIVERSITY","UTRANCELLTYPE","ECNOTHRES","MINECNOTHRES","FDDECQUALTHRSH","FDDRSCPQUALTHRSH","RSCPTHRES","MINRSCPTHRES",
				"LOADHOENEXT3G","LOADACCTHRES","CELLLAYER","OPNAME","RA","RNCINDEX","Fecha","Red","ipEntrada","GCNOPGRPINDEX"
				
		};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGEXTLTECELL() {
		String[] aParametrosABuscar = {"EXTLTECELLNAME","MCC","MNC","TAC","FREQ","PCID","EUTRANTYPE","OPNAME","ENODEBTYPE","CI",
				"GCNOPGRPINDEX","Fecha","Red","ipEntrada"};	
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGLTENCELL() {
		String[] aParametrosABuscar = {"SPTRESEL","SPTBLINDHO","SPTRAPIDSEL","NCELLPRI","Fecha","Red","ipEntrada",
				"EUTRANNCELLHIGHTHLD","EUTRANNCELLLOWTHLD","EUTRANNCELLRXLEVMIN","NBRLTENCELLID","SRCLTENCELLID"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGTRX() {
		String[] aParametrosABuscar ={"TRXID","ACTSTATUS","ADMSTAT","FREQ","GTRXGROUPID",
				"ISMAINBCCH","TRXNAME","TRXNO","Fecha","Red","ipEntrada","ISTMPTRX"
		};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGTRXCHAN() {
		String[] aParametrosABuscar ={"TRXID","ADMSTAT","CHANRSV","CHNO","CHTYPE","GPRSCHPRI","TSPRIORITY","Fecha","Red","ipEntrada"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGTRXCHANHOP() {
		String[] aParametrosABuscar ={"TRXID","CHNO","TRXDSSHOPINDEX","TRXDSSMAIO",
				"TRXHOPINDEX","TRXMAIO","Fecha","Red","ipEntrada"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarGTRXDEV() {
		String[] aParametrosABuscar ={"TRXID","CPS","DSSTRXOFFLINE","FREQREUSEMODE",
				"IBCATGTCIRMAIOFIXED","INHOPWROVERLOADTHRESHOLD","OPTL","OUTHOPWROVERLOADTHRESHOLD","PAOPTILEVEL","PL16QAM","PL32QAM",
				"PL8PSK","POWL","POWT","POWTUNIT","PWRSPNR","RCVMD","SDFLAG","SNDMD","TCHAJFLAG","TSPWRRESERVE","Fecha","Red","ipEntrada",
				"TRXLOGICLOCKSW"};
		return aParametrosABuscar;
	}
	
	private static String[] retornaParametrosABuscarPTPBVC() {
		String[] aParametrosABuscar ={"BVCI","NSEI","Fecha","Red","ipEntrada"};
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
		String[] aParametrosCabecera = { "ControllerName","BTSID","BTSNAME","DEVICE","DEVICENAME","CTRLPORTCN","CTRLPORTNO", 
				"CTRLPORTSN","CTRLPORTSRN","POLARTYPE","RETTYPE","SCENARIO","SERIALNO","VENDORCODE","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGSM_BTSDEVICEDATA(){
		String[] aParametrosCabecera = {"ControllerName","BTSID","BTSNAME","DEVICE","DEVICENAME","BAND1","BAND2","BAND3","BAND4","BEARING","SUBUNITNO","TILT","SERIALNO","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGSM_BTSRETSUBUNIT() {
		String[] aParametrosCabecera = { "ControllerName","BTSID","BTSNAME","DEVICE","DEVICENAME","AER","CELLID","CONNCN1","CONNCN2","CONNPN1","CONNPN2","CONNSN1","CONNSN2","CONNSRN1","CONNSRN2",
				"SUBUNITNO","TILT","Fecha","Red","ipEntrada" };
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
	
	private static String[] retornaParametrosCabeceraG3GNCELL() {
		String[] aParametrosCabecera = {"BSC_SOURCE","SRC3GNCELLNAME","NBR3GNCELLNAME","RSCPOFF","ECNOOFF","HOSTAT3GTDD",
				"HODURT3GTDD","HOSTAT3G","HODURT3G","NCELLPRI","Fecha", "Red", "ipEntrada"};
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
	
	private static String[] retornaParametrosCabeceraGCELLPSCHM() {
		String [] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","ABISTSFREETM","ACTIVETBFDWNDYNCHNTRANLEV","ACTIVETBFSWITCH",
				"ACTIVETBFUPDYNCHNTRANLEV","ALLOCSINGLEPDCHFORSIGNALLING","APPLYMULTIABISTS","AUTOGPRSCHPRI","BEARP1PRIMAXPDCHNUM",
				"BEARP1PRIWEIGHT","BEARP2PRIMAXPDCHNUM","BEARP2PRIWEIGHT","BEARP3PRIMAXPDCHNUM","BEARP3PRIWEIGHT","BKGARP1PRIMAXPDCHNUM",
				"BKGARP1PRIWEIGHT","BKGARP2PRIMAXPDCHNUM","BKGARP2PRIWEIGHT","BKGARP3PRIMAXPDCHNUM","BKGARP3PRIWEIGHT","CHIDLHIGHTHR",
				"CHIDLLOWTHR","CSBUSYPDAPPINTERVAL","DEFAULTDYNPDCHPRETRANNUM","DUTYCYCLEBASEDPDCHFETCH","DUTYCYCLEBASEDPDCHMAG",
				"DUTYCYCLEBASEDPDCHREL","DWNDYNCHNTRANLEV","DYNCHFREETM","DYNCHNPREEMPTLEV","DYNCHTRANRESLEV","ENPDADMINOPT",
				"IMARP1PRIMAXPDCHNUM","IMARP1PRIWEIGHT","IMARP2PRIMAXPDCHNUM","IMARP2PRIWEIGHT","IMARP3PRIMAXPDCHNUM","IMARP3PRIWEIGHT",
				"IMOPTTHRSH","IMPDCHMULTIPLEXWEIGHT","IOUPDCHSWTICH","IUOCHNTRAN","MAXPDCHRATE","MSRDMCSLEV","MSRDPDCHLEV","PDCHDWNLEV",
				"PDCHPOWERPLENT","PDCHPOWERPLENTTHRES","PDCHREFORMING","PDCHUPLEV","POWTUNIT","PRECONNECTSLAVEABIS","PSRESPREEMPT",
				"PSRESPREEMPTED","PSSERVICEBUSYTHRESHOLD","RADIORESADAADJDLLOADTHD","RADIORESADAADJSWITCH","RADIORESADAADJULLOADTHD",
				"RAMBCAP","RESERVEDDYNPDCHPRETRANNUM","RTTIPDCHMULTIPLEXTHRESH","THP1ARP1PRIMAXPDCHNUM","THP1ARP1PRIWEIGHT",
				"THP1ARP2PRIMAXPDCHNUM","THP1ARP2PRIWEIGHT","THP1ARP3PRIMAXPDCHNUM","THP1ARP3PRIWEIGHT","THP2ARP1PRIMAXPDCHNUM",
				"THP2ARP1PRIWEIGHT","THP2ARP2PRIMAXPDCHNUM","THP2ARP2PRIWEIGHT","THP2ARP3PRIMAXPDCHNUM","THP2ARP3PRIWEIGHT",
				"THP3ARP1PRIMAXPDCHNUM","THP3ARP1PRIWEIGHT","THP3ARP2PRIMAXPDCHNUM","THP3ARP2PRIWEIGHT","THP3ARP3PRIMAXPDCHNUM",
				"THP3ARP3PRIWEIGHT","UPDYNCHNTRANLEV","Fecha","Red","ipEntrada","ACCTECHREQSW","EXTNMOISW"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGEXT2GCELL() {
		String[] aParametrosCabecera = {"BSC_SOURCE","EXT2GCELLID","EXT2GCELLID","MCC","MNC","LAC","CI","BCCH","NCC","BCC",
				"COMSC","LAYER","PRIOR","HOTHRES","SDPUNVAL","SDPUNTIME","MSRXMIN","TIMEPUNISH","HOPUNISHVALUE","HOOFFSET",
				"LOADHOENEXT2G","LOADACCTHRES","ISNC2SUPPORT","ISGPRSSUPPORT","ISEDGESUPPORT","RA","BSCIDX","OPNAME","Fecha",
				"Red","ipEntrada","GCNOPGRPINDEX","IBCAIIINTERBSCHOINFOSW"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGEXT3GCELL() {
		String[] aParametrosCabecera = {"BSC_SOURCE","EXT3GCELLID","EXT3GCELLNAME","MCC","MNC","LAC","CI","RNCID","DF","SCRAMBLE",
				"DIVERSITY","UTRANCELLTYPE","ECNOTHRES","MINECNOTHRES","FDDECQUALTHRSH","FDDRSCPQUALTHRSH","RSCPTHRES","MINRSCPTHRES",
				"LOADHOENEXT3G","LOADACCTHRES","CELLLAYER","OPNAME","RA","RNCINDEX","BSCN3GCELLNAME","Fecha","Red","ipEntrada","GCNOPGRPINDEX"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGEXTLETCELL() {
		String[] aParametrosCabecera = {"BSC_SOURCE","EXT3GCELLID","EXT3GCELLNAME","MCC","MNC","TAC","FREQ","PCID","EUTRANTYPE",
				"OPNAME","ENODEBTYPE","CI","GCNOPGRPINDEX","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGLTENCELL() {
		String[] aParametrosCabecera = {"BSC_SOURCE","SRCLTENCELLNAME","NBRLTENCELLNAME","SPTRESEL","SPTBLINDHO","SPTRAPIDSEL",
				"NCELLPRI","Fecha","Red","ipEntrada","EUTRANNCELLHIGHTHLD","EUTRANNCELLLOWTHLD","EUTRANNCELLRXLEVMIN",
				"NBRLTENCELLID","SRCLTENCELLID"};
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGTRX() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","TRXID","ACTSTATUS","ADMSTAT","FREQ","GTRXGROUPID",
				"ISMAINBCCH","TRXNAME","TRXNO","Fecha","Red","ipEntrada","ISTMPTRX"};		
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGTRXCHAN() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","TRXID","ADMSTAT","CHANRSV","CHNO","CHTYPE","GPRSCHPRI",
				"TSPRIORITY","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraGTXCHANHOP() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","TRXID","CHNO","TRXDSSHOPINDEX","TRXDSSMAIO",
				"TRXHOPINDEX","TRXMAIO","Fecha","Red","ipEntrada"};
		return aParametrosCabecera;
	}

	private static String[] retornaParametrosCabeceraGTRXDEV() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","TRXID","CPS","DSSTRXOFFLINE","FREQREUSEMODE",
				"IBCATGTCIRMAIOFIXED","INHOPWROVERLOADTHRESHOLD","OPTL","OUTHOPWROVERLOADTHRESHOLD","PAOPTILEVEL","PL16QAM","PL32QAM",
				"PL8PSK","POWL","POWT","POWTUNIT","PWRSPNR","RCVMD","SDFLAG","SNDMD","TCHAJFLAG","TSPWRRESERVE","Fecha","Red","ipEntrada",
				"TRXLOGICLOCKSW"};
		return aParametrosCabecera;
	}
	
	private static String[] retornaParametrosCabeceraPTPBVC() {
		String[] aParametrosCabecera = {"ControllerName","CELLNAME","CELLID","CI","BVCI","NSEI","Fecha","Red","ipEntrada"};
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