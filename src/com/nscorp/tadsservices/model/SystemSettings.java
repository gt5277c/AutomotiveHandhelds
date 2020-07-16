package com.nscorp.tadsservices.model;

import java.util.List;

public class SystemSettings {
	
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private List<Option> options;

	private String AppName;
	private String Version;
	private String RampCode;
	private String RampSPLC;
	private String RampFSAC;
	
	//Confirmation Message sent for reliability testing
	private String ConfirmMessage = "N";
	
	//Default TADS Options
	private String InboundOriginDefault = "N/A";
	private String InboundSCACDefault = "N/A";
	private String PlantinSCAC = "N/A";
	private String DriveinSCAC = "N/A";
	private String TruckinSCAC = "N/A";
	private String TruckoutSCAC = "N/A";
	private String DefaultDealerZone = "N/A";
	private Boolean BayVINOnHold = false;
	private Boolean RestrictMABL = false;
	private Boolean GenWaybill = false;
	private Boolean BypassCheckDeliver = false;
	private Boolean AllowLabelPrint = true;
	private Boolean UseUpfitter = false;
	private Boolean UseVTypeOverride = false;
	private Boolean UseTransmitToTrucker = false;
	private Boolean UseNoASNHold = false;
	private Boolean ForceSealNumValidate = false;
	private Boolean AllowTruckoutHold = false;
	private Boolean GenLoadMessage = false;
	private Boolean UsePreload = true;
	private Boolean UseRSAAudit = true;
	private Boolean UseReverseTrackAuth = false;
	private Boolean UsePortLogic = false;
	private Boolean AllowPrebayErrorPrompt = false;
	private Boolean AllowPrint = true;
	private Boolean AddSpacesInZoneADVC = true;
	private Boolean AddSpacesInZoneRC = true;
	private Boolean GenRailcarDelMessage = false;
	private Boolean PrebayVINOnReleaseHoldStorage = false;
	private Boolean RSAReptLoadChecklist = true;
	private Boolean RSAReptAutoLoadList = true;
	private Boolean AutoGenDealers = false;
	private Boolean AutoAssignRoutes = false;
	private Boolean AutoAssignDealers = false;
	private Boolean PrintZoneOnLabel = true;
	private Boolean PrintSpotOnLabel = true;
	private Boolean PromptOnAssign = false;
	private Boolean PromptOnVINAssign = false;
	private Boolean AutoWackInv = false;
	private Boolean PhysicalAutoCode = true;
	private Boolean PhysicalViewOnlyOpen = true;
	private Boolean PhysicalUpdateFixes = true;
	private Boolean PhysicalPrintLabels = true;
	private Boolean ExcludeReturnToPlant = false;
	private Boolean RSAWaybillRestriction = false;
	private Boolean RSACoLoadCheck = false;

	//Defaults for Handheld
	private String LastLoginID;
	private String PrinterLabel;
	private String PrinterIDLabel;
	private String Printer1;
	private String Printer2;
	private String Printer3;

	private String HHLoadSeq;
	private String HHPrinter;
	private Integer HHDeckCount = 5;
	private Boolean HHRevLoading = false;
	private Boolean HHRouteReq = false;
	private Boolean HHPrintZone = true;
	private Boolean HHShowDateTime = false;
	private Integer HHTruckOutDateRangeHours = 0;
	
	//Advance Consist Defaults
	private String AdvcType = "None";
	private String AdvcEventType = "000";
	private String AdvcSiteType = "SPLC";
	private String AdvcDestType = "SPLC";
	private String AdvcGsDest = "SPLC";
	private String AdvcRouteDestType = "Station";

	//Preload
	private String PreLoadExpirationMinutes = "120";
	private String ReverseExpirationMinutes = "60";
	private String PreLoadMaxVehicles = "200";
	private String PreLoadExtensions = "1";
	private String OverridePassword = "Password";
	private String PreLoadPassword = "Password";
	private Boolean PreloadExcludeShipThru = false;
	// }}
	
	//Constructor
	public SystemSettings() {};
	
	public SystemSettings(Database database) {
		//Get the options from the database
		Option option = new Option(database);
		setOptions(option.getOptionList());
		
		//Set the options
		initializeOptions();
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	public String getAppName() {		
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}
	
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getConfirmMessage() {
		
		return ConfirmMessage;
	}

	public void setConfirmMessage(String confirmMessage) {
		ConfirmMessage = confirmMessage;
	}

	public String getInboundOriginDefault() {
		return InboundOriginDefault;
	}

	public void setInboundOriginDefault(String inboundOriginDefault) {
		InboundOriginDefault = inboundOriginDefault;
	}

	public String getInboundSCACDefault() {
		return InboundSCACDefault;
	}

	public void setInboundSCACDefault(String inboundSCACDefault) {
		InboundSCACDefault = inboundSCACDefault;
	}

	public String getPlantinSCAC() {
		return PlantinSCAC;
	}

	public void setPlantinSCAC(String plantinSCAC) {
		PlantinSCAC = plantinSCAC;
	}

	public String getDriveinSCAC() {
		return DriveinSCAC;
	}

	public void setDriveinSCAC(String driveinSCAC) {
		DriveinSCAC = driveinSCAC;
	}

	public String getTruckinSCAC() {
		return TruckinSCAC;
	}

	public void setTruckinSCAC(String truckinSCAC) {
		TruckinSCAC = truckinSCAC;
	}

	public String getTruckoutSCAC() {
		return TruckoutSCAC;
	}

	public void setTruckoutSCAC(String truckoutSCAC) {
		TruckoutSCAC = truckoutSCAC;
	}

	public String getDefaultDealerZone() {
		return DefaultDealerZone;
	}

	public void setDefaultDealerZone(String defaultDealerZone) {
		DefaultDealerZone = defaultDealerZone;
	}

	public Boolean getBayVINOnHold() {
		return BayVINOnHold;
	}

	public void setBayVINOnHold(Boolean bayVINOnHold) {
		BayVINOnHold = bayVINOnHold;
	}

	public Boolean getRestrictMABL() {
		return RestrictMABL;
	}

	public void setRestrictMABL(Boolean restrictMABL) {
		RestrictMABL = restrictMABL;
	}

	public Boolean getGenWaybill() {
		return GenWaybill;
	}

	public void setGenWaybill(Boolean genWaybill) {
		GenWaybill = genWaybill;
	}

	public Boolean getBypassCheckDeliver() {
		return BypassCheckDeliver;
	}

	public void setBypassCheckDeliver(Boolean bypassCheckDeliver) {
		BypassCheckDeliver = bypassCheckDeliver;
	}

	public Boolean getAllowLabelPrint() {
		return AllowLabelPrint;
	}

	public void setAllowLabelPrint(Boolean allowLabelPrint) {
		AllowLabelPrint = allowLabelPrint;
	}

	public Boolean getUseUpfitter() {
		return UseUpfitter;
	}

	public void setUseUpfitter(Boolean useUpfitter) {
		UseUpfitter = useUpfitter;
	}

	public Boolean getUseVTypeOverride() {
		return UseVTypeOverride;
	}

	public void setUseVTypeOverride(Boolean useVTypeOverride) {
		UseVTypeOverride = useVTypeOverride;
	}

	public Boolean getUseTransmitToTrucker() {
		return UseTransmitToTrucker;
	}

	public void setUseTransmitToTrucker(Boolean useTransmitToTrucker) {
		UseTransmitToTrucker = useTransmitToTrucker;
	}

	public Boolean getUseNoASNHold() {
		return UseNoASNHold;
	}

	public void setUseNoASNHold(Boolean useNoASNHold) {
		UseNoASNHold = useNoASNHold;
	}

	public Boolean getForceSealNumValidate() {
		return ForceSealNumValidate;
	}

	public void setForceSealNumValidate(Boolean forceSealNumValidate) {
		ForceSealNumValidate = forceSealNumValidate;
	}

	public Boolean getAllowTruckoutHold() {
		return AllowTruckoutHold;
	}

	public void setAllowTruckoutHold(Boolean allowTruckoutHold) {
		AllowTruckoutHold = allowTruckoutHold;
	}

	public Boolean getGenLoadMessage() {
		return GenLoadMessage;
	}

	public void setGenLoadMessage(Boolean genLoadMessage) {
		GenLoadMessage = genLoadMessage;
	}

	public Boolean getUsePreload() {
		return UsePreload;
	}

	public void setUsePreload(Boolean usePreload) {
		UsePreload = usePreload;
	}

	public Boolean getUseRSAAudit() {
		return UseRSAAudit;
	}

	public void setUseRSAAudit(Boolean useRSAAudit) {
		UseRSAAudit = useRSAAudit;
	}

	public Boolean getUseReverseTrackAuth() {
		return UseReverseTrackAuth;
	}

	public void setUseReverseTrackAuth(Boolean useReverseTrackAuth) {
		UseReverseTrackAuth = useReverseTrackAuth;
	}

	public Boolean getUsePortLogic() {
		return UsePortLogic;
	}

	public void setUsePortLogic(Boolean usePortLogic) {
		UsePortLogic = usePortLogic;
	}

	public Boolean getAllowPrebayErrorPrompt() {
		return AllowPrebayErrorPrompt;
	}

	public void setAllowPrebayErrorPrompt(Boolean allowPrebayErrorPrompt) {
		AllowPrebayErrorPrompt = allowPrebayErrorPrompt;
	}

	public Boolean getAllowPrint() {
		return AllowPrint;
	}

	public void setAllowPrint(Boolean allowPrint) {
		AllowPrint = allowPrint;
	}

	public Boolean getAddSpacesInZoneADVC() {
		return AddSpacesInZoneADVC;
	}

	public void setAddSpacesInZoneADVC(Boolean addSpacesInZoneADVC) {
		AddSpacesInZoneADVC = addSpacesInZoneADVC;
	}

	public Boolean getAddSpacesInZoneRC() {
		return AddSpacesInZoneRC;
	}

	public void setAddSpacesInZoneRC(Boolean addSpacesInZoneRC) {
		AddSpacesInZoneRC = addSpacesInZoneRC;
	}

	public Boolean getGenRailcarDelMessage() {
		return GenRailcarDelMessage;
	}

	public void setGenRailcarDelMessage(Boolean genRailcarDelMessage) {
		GenRailcarDelMessage = genRailcarDelMessage;
	}

	public Boolean getPrebayVINOnReleaseHoldStorage() {
		return PrebayVINOnReleaseHoldStorage;
	}

	public void setPrebayVINOnReleaseHoldStorage(Boolean prebayVINOnReleaseHoldStorage) {
		PrebayVINOnReleaseHoldStorage = prebayVINOnReleaseHoldStorage;
	}

	public Boolean getRSAReptLoadChecklist() {
		return RSAReptLoadChecklist;
	}

	public void setRSAReptLoadChecklist(Boolean rSAReptLoadChecklist) {
		RSAReptLoadChecklist = rSAReptLoadChecklist;
	}

	public Boolean getRSAReptAutoLoadList() {
		return RSAReptAutoLoadList;
	}

	public void setRSAReptAutoLoadList(Boolean rSAReptAutoLoadList) {
		RSAReptAutoLoadList = rSAReptAutoLoadList;
	}

	public Boolean getAutoGenDealers() {
		return AutoGenDealers;
	}

	public void setAutoGenDealers(Boolean autoGenDealers) {
		AutoGenDealers = autoGenDealers;
	}

	public Boolean getAutoAssignRoutes() {
		return AutoAssignRoutes;
	}

	public void setAutoAssignRoutes(Boolean autoAssignRoutes) {
		AutoAssignRoutes = autoAssignRoutes;
	}

	public Boolean getAutoAssignDealers() {
		return AutoAssignDealers;
	}

	public void setAutoAssignDealers(Boolean autoAssignDealers) {
		AutoAssignDealers = autoAssignDealers;
	}

	public Boolean getPrintZoneOnLabel() {
		return PrintZoneOnLabel;
	}

	public void setPrintZoneOnLabel(Boolean printZoneOnLabel) {
		PrintZoneOnLabel = printZoneOnLabel;
	}

	public Boolean getPrintSpotOnLabel() {
		return PrintSpotOnLabel;
	}

	public void setPrintSpotOnLabel(Boolean printSpotOnLabel) {
		PrintSpotOnLabel = printSpotOnLabel;
	}

	public Boolean getPromptOnAssign() {
		return PromptOnAssign;
	}

	public void setPromptOnAssign(Boolean promptOnAssign) {
		PromptOnAssign = promptOnAssign;
	}

	public Boolean getPromptOnVINAssign() {
		return PromptOnVINAssign;
	}

	public void setPromptOnVINAssign(Boolean promptOnVINAssign) {
		PromptOnVINAssign = promptOnVINAssign;
	}

	public Boolean getAutoWackInv() {
		return AutoWackInv;
	}

	public void setAutoWackInv(Boolean autoWackInv) {
		AutoWackInv = autoWackInv;
	}

	public Boolean getPhysicalAutoCode() {
		return PhysicalAutoCode;
	}

	public void setPhysicalAutoCode(Boolean physicalAutoCode) {
		PhysicalAutoCode = physicalAutoCode;
	}

	public Boolean getPhysicalViewOnlyOpen() {
		return PhysicalViewOnlyOpen;
	}

	public void setPhysicalViewOnlyOpen(Boolean physicalViewOnlyOpen) {
		PhysicalViewOnlyOpen = physicalViewOnlyOpen;
	}

	public Boolean getPhysicalUpdateFixes() {
		return PhysicalUpdateFixes;
	}

	public void setPhysicalUpdateFixes(Boolean physicalUpdateFixes) {
		PhysicalUpdateFixes = physicalUpdateFixes;
	}

	public Boolean getPhysicalPrintLabels() {
		return PhysicalPrintLabels;
	}

	public void setPhysicalPrintLabels(Boolean physicalPrintLabels) {
		PhysicalPrintLabels = physicalPrintLabels;
	}

	public Boolean getExcludeReturnToPlant() {
		return ExcludeReturnToPlant;
	}

	public void setExcludeReturnToPlant(Boolean excludeReturnToPlant) {
		ExcludeReturnToPlant = excludeReturnToPlant;
	}

	public Boolean getRSAWaybillRestriction() {
		return RSAWaybillRestriction;
	}

	public void setRSAWaybillRestriction(Boolean rSAWaybillRestriction) {
		RSAWaybillRestriction = rSAWaybillRestriction;
	}

	public Boolean getRSACoLoadCheck() {
		return RSACoLoadCheck;
	}

	public void setRSACoLoadCheck(Boolean rSACoLoadCheck) {
		RSACoLoadCheck = rSACoLoadCheck;
	}

	public String getLastLoginID() {
		return LastLoginID;
	}

	public void setLastLoginID(String lastLoginID) {
		LastLoginID = lastLoginID;
	}

	public String getPrinterLabel() {
		return PrinterLabel;
	}

	public void setPrinterLabel(String printerLabel) {
		PrinterLabel = printerLabel;
	}

	public String getPrinterIDLabel() {
		return PrinterIDLabel;
	}

	public void setPrinterIDLabel(String printerIDLabel) {
		PrinterIDLabel = printerIDLabel;
	}

	public String getPrinter1() {
		return Printer1;
	}

	public void setPrinter1(String printer1) {
		Printer1 = printer1;
	}

	public String getPrinter2() {
		return Printer2;
	}

	public void setPrinter2(String printer2) {
		Printer2 = printer2;
	}

	public String getPrinter3() {
		return Printer3;
	}

	public void setPrinter3(String printer3) {
		Printer3 = printer3;
	}

	public String getHHLoadSeq() {
		return HHLoadSeq;
	}

	public void setHHLoadSeq(String hHLoadSeq) {
		HHLoadSeq = hHLoadSeq;
	}

	public String getHHPrinter() {
		return HHPrinter;
	}

	public void setHHPrinter(String hHPrinter) {
		HHPrinter = hHPrinter;
	}

	public Integer getHHDeckCount() {
		return HHDeckCount;
	}

	public void setHHDeckCount(Integer hHDeckCount) {
		HHDeckCount = hHDeckCount;
	}

	public Boolean getHHRevLoading() {
		return HHRevLoading;
	}

	public void setHHRevLoading(Boolean hHRevLoading) {
		HHRevLoading = hHRevLoading;
	}

	public Boolean getHHRouteReq() {
		return HHRouteReq;
	}

	public void setHHRouteReq(Boolean hHRouteReq) {
		HHRouteReq = hHRouteReq;
	}

	public Boolean getHHPrintZone() {
		return HHPrintZone;
	}

	public void setHHPrintZone(Boolean hHPrintZone) {
		HHPrintZone = hHPrintZone;
	}

	public Boolean getHHShowDateTime() {
		return HHShowDateTime;
	}

	public void setHHShowDateTime(Boolean hHShowDateTime) {
		HHShowDateTime = hHShowDateTime;
	}

	public Integer getHHTruckOutDateRangeHours() {
		return HHTruckOutDateRangeHours;
	}

	public void setHHTruckOutDateRangeHours(Integer hHTruckOutDateRangeHours) {
		HHTruckOutDateRangeHours = hHTruckOutDateRangeHours;
	}

	public String getAdvcType() {
		return AdvcType;
	}

	public void setAdvcType(String advcType) {
		AdvcType = advcType;
	}

	public String getAdvcEventType() {
		return AdvcEventType;
	}

	public void setAdvcEventType(String advcEventType) {
		AdvcEventType = advcEventType;
	}

	public String getAdvcSiteType() {
		return AdvcSiteType;
	}

	public void setAdvcSiteType(String advcSiteType) {
		AdvcSiteType = advcSiteType;
	}

	public String getAdvcDestType() {
		return AdvcDestType;
	}

	public void setAdvcDestType(String advcDestType) {
		AdvcDestType = advcDestType;
	}

	public String getAdvcGsDest() {
		return AdvcGsDest;
	}

	public void setAdvcGsDest(String advcGsDest) {
		AdvcGsDest = advcGsDest;
	}

	public String getAdvcRouteDestType() {
		return AdvcRouteDestType;
	}

	public void setAdvcRouteDestType(String advcRouteDestType) {
		AdvcRouteDestType = advcRouteDestType;
	}

	public String getPreLoadExpirationMinutes() {
		return PreLoadExpirationMinutes;
	}

	public void setPreLoadExpirationMinutes(String preLoadExpirationMinutes) {
		PreLoadExpirationMinutes = preLoadExpirationMinutes;
	}

	public String getReverseExpirationMinutes() {
		return ReverseExpirationMinutes;
	}

	public void setReverseExpirationMinutes(String reverseExpirationMinutes) {
		ReverseExpirationMinutes = reverseExpirationMinutes;
	}

	public String getPreLoadMaxVehicles() {
		return PreLoadMaxVehicles;
	}

	public void setPreLoadMaxVehicles(String preLoadMaxVehicles) {
		PreLoadMaxVehicles = preLoadMaxVehicles;
	}

	public String getPreLoadExtensions() {
		return PreLoadExtensions;
	}

	public void setPreLoadExtensions(String preLoadExtensions) {
		PreLoadExtensions = preLoadExtensions;
	}

	public String getOverridePassword() {
		return OverridePassword;
	}

	public void setOverridePassword(String overridePassword) {
		OverridePassword = overridePassword;
	}

	public String getPreLoadPassword() {
		return PreLoadPassword;
	}

	public void setPreLoadPassword(String preLoadPassword) {
		PreLoadPassword = preLoadPassword;
	}

	public Boolean getPreloadExcludeShipThru() {
		return PreloadExcludeShipThru;
	}

	public void setPreloadExcludeShipThru(Boolean preloadExcludeShipThru) {
		PreloadExcludeShipThru = preloadExcludeShipThru;
	}
	
	public String getRampCode() {
		return RampCode;
	}

	public void setRampCode(String rampCode) {
		RampCode = rampCode;
	}
	
	public String getRampSPLC() {
		return RampSPLC;
	}

	public void setRampSPLC(String rampSPLC) {
		RampSPLC = rampSPLC;
	}
	
	public String getRampFSAC() {
		return RampFSAC;
	}

	public void setRampFSAC(String rampFSAC) {
		RampFSAC = rampFSAC;
	}
	// }}

	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	
	private void initializeOptions() {
		String optionName;
		
		for(Option option : options) {
			optionName = option.getName();
			
			switch(optionName) {
			case "AppName":
				AppName = (String)option.getValue();
				break;
			case "Version":
				Version = (String)option.getValue();
				break;
			case "ConfirmMessage":
				ConfirmMessage = (String)option.getValue();
				break;
			case "InboundOriginDefault":
				InboundOriginDefault = (String)option.getValue();
				break;
			case "InboundSCACDefault":
				InboundSCACDefault = (String)option.getValue();
				break;
			case "PlantinSCAC":
				PlantinSCAC = (String)option.getValue();
				break;
			case "DriveinSCAC":
				DriveinSCAC = (String)option.getValue();
				break;
			case "TruckinSCAC":
				TruckinSCAC = (String)option.getValue();
				break;
			case "TruckoutSCAC":
				TruckoutSCAC = (String)option.getValue();
				break;
			case "DefaultDealerZone":
				DefaultDealerZone = (String)option.getValue();
				break;
			case "BayVINOnHold":
				BayVINOnHold = (Boolean)option.getValue();
				break;
			case "RestrictMABL":
				RestrictMABL = (Boolean)option.getValue();
				break;
			case "GenWaybill":
				GenWaybill = (Boolean)option.getValue();
				break;
			case "BypassCheckDeliver":
				BypassCheckDeliver = (Boolean)option.getValue();
				break;
			case "AllowLabelPrint":
				AllowLabelPrint = (Boolean)option.getValue();
				break;
			case "UseUpfitter":
				UseUpfitter = (Boolean)option.getValue();
				break;
			case "UseVTypeOverride":
				UseVTypeOverride = (Boolean)option.getValue();
				break;
			case "UseTransmitToTrucker":
				UseTransmitToTrucker = (Boolean)option.getValue();
				break;
			case "UseNoASNHold":
				UseNoASNHold = (Boolean)option.getValue();
				break;
			case "ForceSealNumValidate":
				ForceSealNumValidate = (Boolean)option.getValue();
				break;
			case "AllowTruckoutHold":
				AllowTruckoutHold = (Boolean)option.getValue();
				break;
			case "GenLoadMessage":
				GenLoadMessage = (Boolean)option.getValue();
				break;
			case "UsePreload":
				UsePreload = (Boolean)option.getValue();
				break;
			case "UseRSAAudit":
				UseRSAAudit = (Boolean)option.getValue();
				break;
			case "UseReverseTrackAuth":
				UseReverseTrackAuth = (Boolean)option.getValue();
				break;
			case "UsePortLogic":
				UsePortLogic = (Boolean)option.getValue();
				break;
			case "AllowPrebayErrorPrompt":
				AllowPrebayErrorPrompt = (Boolean)option.getValue();
				break;
			case "AllowPrint":
				AllowPrint = (Boolean)option.getValue();
				break;
			case "AddSpacesInZoneADVC":
				AddSpacesInZoneADVC = (Boolean)option.getValue();
				break;
			case "AddSpacesInZoneRC":
				AddSpacesInZoneRC = (Boolean)option.getValue();
				break;
			case "GenRailcarDelMessage":
				GenRailcarDelMessage = (Boolean)option.getValue();
				break;
			case "PrebayVINOnReleaseHoldStorage":
				PrebayVINOnReleaseHoldStorage = (Boolean)option.getValue();
				break;
			case "RSAReptLoadChecklist":
				RSAReptLoadChecklist = (Boolean)option.getValue();
				break;
			case "RSAReptAutoLoadList":
				RSAReptAutoLoadList = (Boolean)option.getValue();
				break;
			case "AutoGenDealers":
				AutoGenDealers = (Boolean)option.getValue();
				break;
			case "AutoAssignRoutes":
				AutoAssignRoutes = (Boolean)option.getValue();
				break;
			case "AutoAssignDealers":
				AutoAssignDealers = (Boolean)option.getValue();
				break;
			case "PrintZoneOnLabel":
				PrintZoneOnLabel = (Boolean)option.getValue();
				break;
			case "PrintSpotOnLabel":
				PrintSpotOnLabel = (Boolean)option.getValue();
				break;
			case "PromptOnAssign":
				PromptOnAssign = (Boolean)option.getValue();
				break;
			case "PromptOnVINAssign":
				PromptOnVINAssign = (Boolean)option.getValue();
				break;
			case "AutoWackInv":
				AutoWackInv = (Boolean)option.getValue();
				break;
			case "PhysicalAutoCode":
				PhysicalAutoCode = (Boolean)option.getValue();
				break;
			case "PhysicalViewOnlyOpen":
				PhysicalViewOnlyOpen = (Boolean)option.getValue();
				break;
			case "PhysicalUpdateFixes":
				PhysicalUpdateFixes = (Boolean)option.getValue();
				break;
			case "PhysicalPrintLabels":
				PhysicalPrintLabels = (Boolean)option.getValue();
				break;
			case "ExcludeReturnToPlant":
				ExcludeReturnToPlant = (Boolean)option.getValue();
				break;
			case "RSAWaybillRestriction":
				RSAWaybillRestriction = (Boolean)option.getValue();
				break;
			case "RSACoLoadCheck":
				RSACoLoadCheck = (Boolean)option.getValue();
				break;
			case "LastLoginID":
				LastLoginID = (String)option.getValue();
				break;
			case "PrinterLabel":
				PrinterLabel = (String)option.getValue();
				break;
			case "PrinterIDLabel":
				PrinterIDLabel = (String)option.getValue();
				break;
			case "Printer1":
				Printer1 = (String)option.getValue();
				break;
			case "Printer2":
				Printer2 = (String)option.getValue();
				break;
			case "Printer3":
				Printer3 = (String)option.getValue();
				break;
			case "HHLoadSeq":
				HHLoadSeq = (String)option.getValue();
				break;
			case "HHPrinter":
				HHPrinter = (String)option.getValue();
				break;
			case "HHDeckCount":
				try {
					HHDeckCount = (Integer)option.getValue();
				} catch(Exception e) {
					HHDeckCount = 0;
				}
				
				break;
			case "HHRevLoading":
				HHRevLoading = (Boolean)option.getValue();
				break;
			case "HHRouteReq":
				HHRouteReq = (Boolean)option.getValue();
				break;
			case "HHPrintZone":
				HHPrintZone = (Boolean)option.getValue();
				break;
			case "HHShowDateTime":
				HHShowDateTime = (Boolean)option.getValue();
				break;
			case "HHTruckOutDateRangeHours":
				HHTruckOutDateRangeHours = (Integer)option.getValue();
				break;
			case "AdvcType":
				AdvcType = (String)option.getValue();
				break;
			case "AdvcEventType":
				AdvcEventType = (String)option.getValue();
				break;
			case "AdvcSiteType":
				AdvcSiteType = (String)option.getValue();
				break;
			case "AdvcDestType":
				AdvcDestType = (String)option.getValue();
				break;
			case "AdvcGsDest":
				AdvcGsDest = (String)option.getValue();
				break;
			case "AdvcRouteDestType":
				AdvcRouteDestType = (String)option.getValue();
				break;
			case "PreLoadExpirationMinutes":
				PreLoadExpirationMinutes = (String)option.getValue();
				break;
			case "ReverseExpirationMinutes":
				ReverseExpirationMinutes = (String)option.getValue();
				break;
			case "PreLoadMaxVehicles":
				PreLoadMaxVehicles = (String)option.getValue();
				break;
			case "PreLoadExtensions":
				PreLoadExtensions = (String)option.getValue();
				break;
			case "OverridePassword":
				OverridePassword = (String)option.getValue();
				break;
			case "PreLoadPassword":
				PreLoadPassword = (String)option.getValue();
				break;
			case "PreloadExcludeShipThru":
				PreloadExcludeShipThru = (Boolean)option.getValue();
				break;
			case "Ramp Code":
				RampCode = (String)option.getValue();
				break;
			case "Ramp SPLC":
				RampSPLC = (String)option.getValue();
				break;
			case "Ramp FSAC":
				RampFSAC = (String)option.getValue();
				break;
			default:
				break;
			}
		}		
	}
}
