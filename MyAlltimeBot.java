import java.util.Arrays;

import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;

public class MyAlltimeBot extends DefaultBWListener {
    private Mirror mirror = new Mirror();
    private Game game;
    private Player self;
    
    
    public boolean debug_unit_create     	= false;
    public boolean debug_worker            	= false;
    
    
    public int order = 0;
    public int tempi = 0;
    public int tempx = 6;
    public int tempy = 58;
    
    public int myRaxCount = 0;
    public int buildingSupplyDepot = 0;
    public int buildingRax = 0;
    public int buildingGas = 0;
    public int buildingBunker = 0;
    public int BuiltRax = 0;
    public int BuiltBunker = 0;
    public int BuiltSupplyDepot = 0;
    public int countingRax = 0;
    public int countingBunker = 0;
    public int countingSupplyDepot = 0;
    
    
    public Unit closestGas         = null;
    public Unit allmystuff         = null;
    public Unit allmystuff2        = null;
    public Unit allmystuff3        = null;
    
    
    public Unit Builder_Depot    = null;
    public Unit Builder_Rax      = null;
    public Unit Builder_Gas      = null;
    public Unit Builder_Bunker   = null;
    public Unit Builder_Misc     = null;
    public Unit Builder_Tech     = null;
    public Unit Builder_HQ       = null;
    public Unit Builder_Repair   = null;
    
    
    public Unit tempUnit         = null;
    
    
    public Unit Scout            = null;
    public Unit Scout2           = null;
    public Unit Scout3           = null;
    public Unit Deffer           = null;
    public Unit FillingBunker    = null;
    public Unit RepairingBunker  = null;
    public Unit myNewGas		 = null;
    
    
    public Unit NatTank1		= null;
    public Unit NatTank2		= null;
    public Unit NatTank3		= null;
    
    
    public Position myScoutPosi 		= new Position(11,66);
    public Position BunkerPosi  		= null;
    public Position Gas		 			= null;
    public Position myPosi				= null;  
    public Position NatDeffPosi			= new Position(15*32,78*32);    
    public Position NatTank1Posi		= new Position(9*32,66*32);
    public Position NatTank2Posi		= new Position(10*32,67*32);
    public Position NatTank3Posi		= new Position(11*32,65*32);
       
    
    public TilePosition NatTank1TPosi		= new TilePosition(9,66);
    public TilePosition NatTank2TPosi		= new TilePosition(10,67);
    public TilePosition NatTank3TPosi		= new TilePosition(11,65);
    
    
    
    
    
    public Position[] Posi4DefferTanks4Nat		= new Position[3];

    
    
    public int Builder_ID_Depot = 0;
    public int Builder_ID_Rax = 0;
    public int Builder_ID_Gas = 0;
    public int Builder_ID_Bunker = 0;
    public int Builder_ID_Misc = 0;
    public int Builder_ID_Tech = 0;
    public int Builder_ID_HQ = 0;
    public int Builder_ID_Repair = 0;
    
    
    public int MissingGasGatherer = 0;
    
    
    public TilePosition[] myDepotPlaces     	= new TilePosition[18];
    public TilePosition[] myRaxPlaces         	= new TilePosition[8];
    public TilePosition[] myGasPlaces         	= new TilePosition[8];
    //GEHT NICHT OHNE LÄNGENANGABE, DA ES DIE ERSTE DIMENSION IST
    public TilePosition[] myBunkerPlaces     	= new TilePosition[8];
    public TilePosition[] myTurretPlaces     	= new TilePosition[8];
    public TilePosition myPlace             	= new TilePosition(8, 86);
    public TilePosition myScoutPlace         	= new TilePosition(11, 66);
    public TilePosition myNewPlace             	= new TilePosition(13, 96);
    public TilePosition myRaxPlace             	= new TilePosition(13, 96);
    public TilePosition myNewRaxPlace         	= new TilePosition(13, 96);
    
    
    
    public Unit[] DefferTanks4Nat				= new Unit[3];
    public Unit[] myGasGatherer                	= new Unit[3];
    public Unit[] myGasGathererNat              = new Unit[3];
    public Unit[] myGasBuilding					= new Unit[3];
    public Unit[] myBuilder						= new Unit[6];
    
    
    
    //1st Success Try
    //public TilePosition myPlace4CC1        = new TilePosition(6, 58);
    //public TilePosition myPlace4CC1        = new TilePosition(8, 61);
    //DAS IST EIN GUTER FUNZENDER PLACE FÜR MEINE NAT tiefer geht nicht!
    //public TilePosition myPlace4CC1          = new TilePosition(11, 70);
    
    
    
    public TilePosition myPlace4CC1            = new TilePosition(11, 70);
    public TilePosition myPlace4CC2            = new TilePosition(12, 37);
    
    
 
    //public Region myNaturalDeff                = new Region();
    
    //public myTechType                        = new TechType(Tank_Siege_Mode);
    
    
    public void run() {
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //BEIM ZERSTÖREN VON UNITS MÜSSEN DIE IDS GELÖSCHT WERDEN
    @Override
    public void onUnitDestroy(Unit unit) {
    

    	
    	
    	
    	if (Scout != null)
    	{
    		if (unit.getID() == Scout.getID())
    		{
    		Scout = null;
    		System.out.println("Der Scout wurde getötet und sein Objekt genullt!");	
    		}
    	
    	}

    	
    	
    	if (Deffer != null)
    	{
    		if (unit.getID() == Deffer.getID())
    		{
    		Deffer = null;
    		System.out.println("Der Deffer wurde getötet und sein Objekt genullt!");	
    		}
    	
    	}

    	
    	if (FillingBunker != null)
    	{
    		if (unit.getID() == FillingBunker.getID())
    		{
    			FillingBunker = null;
    		System.out.println("Der FillingBunker wurde getötet und sein Objekt genullt!");	
    		}
    	
    	}

    	  	
    	
    	
    	
    	
    }  
    
   
    
    
    
    
    @Override
    public void onUnitComplete(Unit unit) {
    
    
    
        //VIELLEICHT SOLLTE MAN AUCH DEN PLAYER CHECKEN -> habe aufeinma Toss Units im LOG
        
        
        
        if (debug_unit_create)
        {
        System.out.println("New unit completed : " + unit.getType() + " mit der ID " + unit.getID() + " ");
        }
        // System.out.println("My Supply is " + self.supplyUsed() + " from " +
        // self.supplyTotal() + "! Anscheind alles times 2");
        
        
        
        // FUNZT ALLES NICHT SO GENAU
        // if (unit.getType() == UnitType.Terran_Supply_Depot && buildingSupplyDepot ==
        // 1)
        
        
        //Neue Arbeiter dem Gas zuweisen sobald sie created wurden ( EASY MODE mit MissingGasGatherer)
        if (unit.getType() == UnitType.Terran_SCV && MissingGasGatherer > 0)
        {       
        	
        		//Position Gas = myGasBuilding[0].getPosition();
			  	//myUnit.rightClick(Gas);
        		
			  	//unit.rightClick(myGasBuilding[0]);
			  	
			  	unit.rightClick(myNewGas);
			  	
			  	System.out.println("Step 1 -> EASY MODE Completed -> Es sollte nun ein weiterer Arbeiter " + unit.getID() + " Gas sammeln (durch RightClick)!");
			  	MissingGasGatherer--;
			  	System.out.println("Wieviele müssen jetzt noch genau ins Gas?" + MissingGasGatherer );
        	
        	
        }
        
        
        
        
        
        /*
        //Neue Arbeiter dem Gas zuweisen sobald sie created wurden
        if (unit.getType() == UnitType.Terran_SCV)
        {
        System.out.println("Es wurde grade ein SCV ausgebildet und nun wird überprüft ob es Gas sammeln kann/darf ");
        	if (myGasBuilding[0] != null)
        	{
        	System.out.println("Step 1 -> Mein erstes Gas Building ist nicht null");
        	
        		if (!isUnitArrayFull(myGasGatherer))
        		{
        		System.out.println("Step 2 -> der Array myGasGatherer ist nicht voll");	
        		
        		System.out.println("Step 2.1 -> Jetzt wird gleich der Gassammler in den Array geschrieben. Der Array sieht wie folgt aus: " + myGasGatherer.toString() );
                
        		//myGasGatherer = AssignUnit2Array(unit,myGasGatherer);
        		//Brauch ja eigentlich kein rückgabewert da public variable
        		//AssignUnit2Array(unit,myGasGatherer);
                
        		System.out.println("Step 2.2 -> Jetzt wurde der Gassammler in den Array geschrieben. Der Array sieht wie folgt aus: " + myGasGatherer.toString() );
     			
                
                
                	//ist jetzt wirklich was neues eingetragen
                	if (isUnitAssigned2Array(unit,myGasGatherer))
                	{
                	System.out.println("Step 3 -> Die Unit ist tatsächlich in dem Array");
                		
                	}
                	else
                	{
                	System.out.println("Step 3 -> Die Unit is LEIDER IMMER NOCH NICHT ASSIGNED -> DIE FUNCTION FUNZT NICHT!");
                   	}
                	
                	Position Gas = myGasBuilding[0].getPosition();
     			  	//myUnit.rightClick(Gas);
     			  	unit.rightClick(myGasBuilding[0]);
     			  	System.out.println("Step 3 -> Completed -> Es sollte nun ein weiterer Arbeiter " + unit.getID() + " Gas sammeln (durch RightClick)!");

	
        			
        			
        			
        		}
        		
        		
        	}
        	
        }
        
        */
        
        
        
        
        
        if (unit.getType() == UnitType.Terran_Supply_Depot) {
            // System.out.println("Nun wurde grade ein Supply Depot fertig gestellt");
            System.out.println("Nun wurde grade ein Supply Depot fertiggestellt.");
            buildingSupplyDepot = 0;
        }
        if (unit.getType() == UnitType.Terran_Barracks) {
            System.out.println("Nun wurde grade eine Barracks fertiggestellt.");
            myRaxCount++;
            buildingRax = 0;
            
            //ERSTMAL WIRD NUR DIE NAT GEDEFFT -> Später sollte es allgemein gehandelt werden
            unit.setRallyPoint(NatDeffPosi);
        }

        
        if (unit.getType() == UnitType.Terran_Factory)
        {
            unit.setRallyPoint(NatDeffPosi);
        }
        
        
        if (unit.getType() == UnitType.Terran_Bunker) {
            System.out.println("Nun wurde grade ein Bunker fertiggestellt.");
            buildingBunker =0;
        }
        if (unit.getType() == UnitType.Terran_Command_Center) {
            System.out.println("Nun wurde grade ein Command Center fertiggestellt.");
            buildingBunker =0;
        }
        
        if (unit.getType() == UnitType.Terran_Refinery)
        {
        	System.out.println("Es wurde eine Refinery fertiggestellt und nun in den Array myGasBuilding geschrieben!");
        	int blah = self.allUnitCount(UnitType.Terran_Refinery)-1;
        	myGasBuilding[blah] = unit;
        	MissingGasGatherer = 3;
        	myNewGas = unit;
        }
        
        
        if (unit.getType() == UnitType.Terran_Marine)
        {
        	if (FillingBunker != null)
        	{
        		
        		unit.rightClick(FillingBunker);
        		
        	}
        }
        

        if (unit.getType() == UnitType.Terran_Siege_Tank_Tank_Mode)
        {
        	if (NatTank1 == null)
        	{
        		NatTank1 = unit;
        		NatTank1.attack(NatTank1Posi);
        	}
        	else if (NatTank2 == null)
        	{
        		NatTank2 = unit;
        		NatTank2.attack(NatTank2Posi);
            }
        	else if (NatTank3 == null)
        	{
        		NatTank3 = unit;
        		NatTank3.attack(NatTank3Posi);
            }
        	
        }
        
        
        
        
        
        
        
        
        
        
        
        //System.out.println("Es wurde " + unit.toString()  + " fertiggestellt. (ganz allgemein) " + unit.getType() );
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void onStart() {
        game = mirror.getGame();
        self = game.self();
        // ZUM START WIRD KEIN SUPPLY DEPOT GEBAUT
        // buildingSupplyDepot = 0;

        
        
        myBuilder[0]	= null;
        myBuilder[1]	= null;
        myBuilder[2]	= null;
        myBuilder[3]	= null;
        myBuilder[4]	= null;
        myBuilder[5]	= null;
        
        
        
        myGasGatherer[0] = null;
        myGasGatherer[1] = null;
        myGasGatherer[2] = null;

        myGasGathererNat[0] = null;
        myGasGathererNat[1] = null;
        myGasGathererNat[2] = null;
        
        myGasBuilding[0]	= null;
        myGasBuilding[1]	= null;
        myGasBuilding[2]	= null;
        
        
        DefferTanks4Nat[0] = null;
        DefferTanks4Nat[1] = null;
        DefferTanks4Nat[2] = null;
        
        myDepotPlaces[0] = new TilePosition(7, 86);
        myDepotPlaces[1] = new TilePosition(4, 86);
        myDepotPlaces[2] = new TilePosition(1, 86);
        myDepotPlaces[3] = new TilePosition(1, 84);
        myDepotPlaces[4] = new TilePosition(4, 84);
        myDepotPlaces[5] = new TilePosition(7, 84);
        myDepotPlaces[6] = new TilePosition(1, 82);
        myDepotPlaces[7] = new TilePosition(4, 82);
        myDepotPlaces[8] = new TilePosition(7, 82);
        myDepotPlaces[9] = new TilePosition(1, 80);
        myDepotPlaces[10] = new TilePosition(1,102);
        myDepotPlaces[11] = new TilePosition(1,104);
        myDepotPlaces[12] = new TilePosition(1,106);
        myDepotPlaces[13] = new TilePosition(1,108);
        myDepotPlaces[14] = new TilePosition(4,108);
        myDepotPlaces[15] = new TilePosition(7,108);
        myDepotPlaces[16] = new TilePosition(10,108);
        myDepotPlaces[17] = new TilePosition(13,108);
        
        //Bunker für die Main Base
        //myBunkerPlaces[0] = new TilePosition(18, 85);
        //myBunkerPlaces[1] = new TilePosition(15, 85);
        //DAS IST NEN GUTER SPOTT FÜR WEITERE RAX
        //myBunkerPlaces[0] = new TilePosition(7,  58);
        
        
        myBunkerPlaces[0] = new TilePosition(8, 62);
        myBunkerPlaces[1] = new TilePosition(11,62);
        myBunkerPlaces[2] = new TilePosition(18,85);
        myBunkerPlaces[3] = new TilePosition(8, 62);
        //myBunkerPlaces[0] = new TilePosition(8, 62);
        
        myTurretPlaces[0] = new TilePosition(14,64);
        myTurretPlaces[1] = new TilePosition(8, 60);
        myTurretPlaces[2] = new TilePosition(18,83);
        myTurretPlaces[3] = new TilePosition(19,86);
       
        
        
        
        Posi4DefferTanks4Nat[0] = new Position(8*32,63*32);
        Posi4DefferTanks4Nat[1] = new Position(9*32,63*32);
        Posi4DefferTanks4Nat[2] = new Position(12*32,63*32);

        
        
        myRaxPlaces[0] = new TilePosition(13, 96);
        myRaxPlaces[1] = new TilePosition(13, 93);
        myRaxPlaces[2] = new TilePosition(13, 90);
        myRaxPlaces[3] = new TilePosition(13, 87);
        myRaxPlaces[4] = new TilePosition(8, 57);
        myRaxPlaces[5] = new TilePosition(8, 54);
        myRaxPlaces[6] = new TilePosition(8, 51);

        myGasPlaces[0] = new TilePosition(8, 90);
        myGasPlaces[1] = new TilePosition(11, 76);
        
        // Use BWTA to analyze map
        // This may take a few minutes if the map is processed first time!
        System.out.println("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        System.out.println("Map data ready");
        int i = 0;
        for (BaseLocation baseLocation : BWTA.getBaseLocations()) {
            System.out.println("Base location #" + (++i) + ". Printing location's region polygon:");
            for (Position position : baseLocation.getRegion().getPolygon().getPoints()) {
                System.out.print(position + ", ");
            }
            System.out.println();
        }
        // ET GAME SPEED?!?! 55 ist wohl zu schnell
        // game.setLocalSpeed(55);
    }
    
    
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public boolean isUnitNearTPosi(Unit a, TilePosition b)
    {
    	boolean res = false;
    	int TPx = b.getX();
    	int TPy = b.getY();
    	
    	TilePosition UnitTP = a.getTilePosition();
    	
    	int UTPx= UnitTP.getX();
    	int UTPy= UnitTP.getY();
    	
    	
    	int xdiff = Math.abs(UTPx - TPx); 
    	int ydiff = Math.abs(UTPy - TPy); 
    	
    	
    	if (xdiff <= 1 && ydiff <= 1)
    	{
    		res = true;
    	}
    	
    	//System.out.println("Das Ergebnis der Methode isUnitNearTPosi ist " + res);
    	
    return res;	
    }
    
    
    
    
    
    
    
    
    
    //STATISCH DA OHNE OBJEKT
	public boolean isUnitAssigned2Array(Unit a,Unit[] b) {
	//private Unit s = null;
	//int bla = 0;	
	boolean res = false;	
		
		for (Unit s : b)
		{
			
			
			if (s.getID() == a.getID())
			{

			System.out.println("Es wurde die Unit im Array gefunden -> ID " + s.getID());	
			//bla = 1;	
			res = true;	
			}
			
		}
	
	System.out.println("Das Ergebnis der methode isUnitAssigned2Array ist " + res);	
		
	return res;	
	}
	
	
	
	
	
	
	
	
	
	
	public boolean isUnitArrayFull(Unit[] b)
	{

		
	//System.out.println("Es wird nun überprüft, ob der Array voll ist!");	
	boolean res = true;	
		
		for (Unit s : b)
		{
			
			
			if (s == null)
			{
			//System.out.println("Ist er nicht !");	
			res = false;	
			}
			
		}
	
		
		
	return res;	
	}
	
	
	
	
	
	
	
	
	  
	
	//public Unit[] AssignUnit2Array(Unit a , Unit[] b) {
	public void AssignUnit2Array(Unit a , Unit[] b) {
			//private Unit tempUnit;	
	 int MeineZahl = 0;
	
		for (Unit tempUnit : b)
		{
		
			System.out.println("Es wird nun das " + MeineZahl + "te Element des Arrays überprüft für AssignUnit2Array");
			
			if (tempUnit == null)
			{
				System.out.println("Dieses Element ist null");
				
				
				
				if  (!isUnitAssigned2Array(a,b) && !isUnitArrayFull(b))
				{
				b[MeineZahl] = a;
				System.out.println("Es wurde eine Einheit einem Array zugewiesen, da die Unit nicht zu dem Array gehört");
					
				}
				else
				{
				System.out.println("Entweder ist die Unit schon im Array oder der Array ist voll! (Last else in AssignUnit2Array)");	
					
				}
			}
		MeineZahl++;	
		}
		
	//return b;	
	}
    
    
    
    
    
	public int getPosi4UnitofArray(Unit a, Unit[] b) {
		int z = 0;
		
		for (Unit tempUnit : b)
		{
			
			if ( tempUnit.getID() == a.getID() )
			{
				z=tempUnit.getID();
				
			}
		}
		
		
	return z;	
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void onFrame() {
        // game.setTextSize(10);
        game.drawTextScreen(10, 10,
                "Playing as " + self.getName() + " - " + self.getRace() + " BuilderIDDepot " + Builder_ID_Depot
                        + " und RaxID " + Builder_ID_Rax + " und Bunker " + Builder_ID_Bunker + "! Supply "
                        + self.supplyUsed() + " / " + self.supplyTotal() + "!?!");
        game.drawTextScreen(10, 40, "Playing as " + self.getName() + " - " + self.getRace() + " myRaxCount "
                + myRaxCount + " und aus bwapi " + self.allUnitCount(UnitType.Terran_Barracks) + " !?!");
        game.drawTextScreen(10, 25,
                "Hier kann ich mir ganz viele Variablen ausgeben lassen! BuildingDepot " + buildingSupplyDepot
                        + " AllDepots " + self.allUnitCount(UnitType.Terran_Supply_Depot) + " !! BuildingRax "
                        + buildingRax + " AllRax " + self.allUnitCount(UnitType.Terran_Barracks) + " ??!");
        StringBuilder units = new StringBuilder("My units:\n");
        // SET GAME SPEED?!?! 38 sieht auch langsam aus
        game.setLocalSpeed(35);
        // iterate through my units
        for (Unit myUnit : self.getUnits()) 
		{
            units.append(myUnit.getType()).append(" ID ").append(myUnit.getID()).append("  ")
                    .append(myUnit.getTilePosition()).append("\n");
            
            
            
            
            
  
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            // DIE INTIALIZIERUNG DER IDs MUSS ZUM START VON JEDEM GAME PASSIEREN
            //ODER LIEBER ONFRAME?!
            if (self.supplyUsed() >= 16  ) {
                
            if (myUnit.getType() == UnitType.Terran_SCV)
{
                //wiechecke ich es ob es alive ist ?!?
                if (Builder_ID_Depot == 0) {
                    if ((myUnit.getID() != Builder_ID_Rax || Builder_ID_Rax == 0)
                            && (myUnit.getID() != Builder_ID_Bunker && myUnit.getID() != Builder_ID_Tech && myUnit.getID() != Builder_ID_Rax  && myUnit.getID() != Builder_ID_Gas)
                            && myUnit.getID()  != Builder_ID_Tech && myUnit.getID() != Builder_ID_HQ && myUnit.getID() != Builder_ID_Repair) {
                        Builder_ID_Depot = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " ) Depot wurde gesetzt");
                    }
                }
                if (Builder_ID_Rax == 0) {
                    if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Bunker && myUnit.getID() != Builder_ID_Gas
                             && myUnit.getID() != Builder_ID_Misc  && myUnit.getID() != Builder_ID_Tech  && myUnit.getID() != Builder_ID_HQ && myUnit.getID() != Builder_ID_Repair) {
                        Builder_ID_Rax = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " ) Rax wurde gesetzt");
                    }
                }
                if (Builder_ID_Bunker == 0) {
                    if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Rax && myUnit.getID() != Builder_ID_Gas
                             && myUnit.getID() != Builder_ID_Misc  && myUnit.getID() != Builder_ID_Tech  && myUnit.getID() != Builder_ID_HQ && myUnit.getID() != Builder_ID_Repair) {
                        Builder_ID_Bunker = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " )  Bunker wurde gesetzt");
                    }
                }
                if (Builder_ID_Gas == 0) {
                    if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Rax
                            && myUnit.getID() != Builder_ID_Bunker && myUnit.getID() != Builder_ID_Misc  
                            && myUnit.getID() != Builder_ID_Tech  && myUnit.getID() != Builder_ID_HQ && myUnit.getID() != Builder_ID_Repair) {
                        Builder_ID_Gas = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " ) Gas wurde gesetzt");
                    }
                }
                
                if (Builder_ID_Misc == 0)
                {
                    
                    if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Rax
                            && myUnit.getID() != Builder_ID_Bunker && myUnit.getID() != Builder_ID_Gas  
                            && myUnit.getID() != Builder_ID_Tech  && myUnit.getID() != Builder_ID_HQ && myUnit.getID() != Builder_ID_Repair) {
                        Builder_ID_Misc = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " ) Misc wurde gesetzt");
                    }
    
                }
                
                
                if (Builder_ID_Tech == 0)
                {
                    
                    if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Rax
                            && myUnit.getID() != Builder_ID_Bunker && myUnit.getID() != Builder_ID_Gas  
                            && myUnit.getID() != Builder_ID_Misc  && myUnit.getID() != Builder_ID_HQ && myUnit.getID() != Builder_ID_Repair) {
                        Builder_ID_Tech = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " ) Tech wurde gesetzt");
                    }
    
                }
                
                
                
                if (Builder_ID_HQ == 0)
                {
                    
                    if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Rax
                            && myUnit.getID() != Builder_ID_Bunker && myUnit.getID() != Builder_ID_Gas  
                            && myUnit.getID() != Builder_ID_Tech  && myUnit.getID() != Builder_ID_Misc && myUnit.getID() != Builder_ID_Repair) {
                        Builder_ID_HQ = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " ) HQ wurde gesetzt");
                    }
    
                }
                
                if (Builder_ID_Repair == 0)
                {
                    
                    if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Rax
                            && myUnit.getID() != Builder_ID_Bunker && myUnit.getID() != Builder_ID_Gas  
                            && myUnit.getID() != Builder_ID_Tech  && myUnit.getID() != Builder_ID_Misc && myUnit.getID() != Builder_ID_HQ) {
                        Builder_ID_Repair = myUnit.getID();
                        System.out.println("Builder ID ( " + myUnit.getID() + " ) Repair wurde gesetzt");
                    }
    
                }
                          
                
}
            } 
            else
            {
                Builder_ID_Tech = 0;
                Builder_ID_Misc = 0;
                Builder_ID_HQ = 0;
                
                Builder_ID_Depot = 0;
                Builder_ID_Bunker = 0;
                Builder_ID_Rax = 0;
                Builder_ID_Gas = 0;
            }

            
    
            
            
            
            
            
            
            
            
            
            
          /*  
            
            
          //HIER WERDEN MEINE GAS ARBEITER ZUGEWIESEN
          if (self.allUnitCount(UnitType.Terran_Refinery) != 0 && !isUnitArrayFull(myGasGatherer) && myUnit.getType() == UnitType.Terran_SCV && myUnit.isCompleted() && myGasBuilding[0] != null)
          {
        	  System.out.println("Es wurde eine Refinery entdeckt und nun muss Ausschau nach GasGatherern gehalten werden!");
        	  
        	  
        	  if (myUnit.getID() == Builder_ID_Depot) 		{ System.out.println("Depot SCV"); }
        	  else if (myUnit.getID() == Builder_ID_Rax)	{ System.out.println("Rax SCV"); }
        	  else if (myUnit.getID() == Builder_ID_Bunker)	{ System.out.println("Bunker SCV"); }
        	  else if (myUnit.getID() == Builder_ID_Gas)	{ System.out.println("Gas SCV"); }
        	  else if (myUnit.getID() == Builder_ID_Tech)	{ System.out.println("Tech SCV"); }
        	  else if (myUnit.getID() == Builder_ID_Misc)	{ System.out.println("Misc SCV"); }
        	  else if (myUnit.getID() == Builder_ID_HQ)		{ System.out.println("HQ SCV"); }
        	  else
        	  {

         		 boolean tempboolean = isUnitAssigned2Array(myUnit,myGasGatherer); 
        		 boolean tempboolean2= isUnitArrayFull(myGasGatherer); 
        		  
        		 System.out.println("Es wurde ein unzugewiesenes SCV gefunden!");
        		 
        		 
        		 System.out.println("DEBUGGING EXTREME");
        		 System.out.println("Ist die Unit im Array" + tempboolean);
        		 System.out.println("Ist der Array myGasGatherer voll ? " + tempboolean2 );
        		 System.out.println("DEBUGGING EXTREME");
        		 
        		 
        		 
        		  if (!isUnitAssigned2Array(myUnit,myGasGatherer) && !isUnitArrayFull(myGasGatherer))
        		  {
        			  myGasGatherer = AssignUnit2Array(myUnit,myGasGatherer);
        			  Position Gas = myGasBuilding[0].getPosition();
        			  //myUnit.rightClick(Gas);
        			  myUnit.rightClick(myGasBuilding[0]);
        			  System.out.println("Es sollte nun ein weiterer Arbeiter Gas sammeln!");
        		  }
        		  
        	  }
        	  
        	  
        	  
          }
            
  
            
            */
            
            
            
            
            
            
            
            
            
            
            
            
        //NEUE ART UND WEISE UM DIE BUILDER ZUZUWEISEN
        //if ()    
        
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            // if there's enough minerals, train an SCV
            if (myUnit.getType() == UnitType.Terran_Command_Center && self.minerals() >= 50 && myUnit.isIdle()) {
                // Ansich nur ausführen, wenn noch keins getraint wird -> myUnitisIdle
                myUnit.train(UnitType.Terran_SCV);
            }
            // Train a SCV -> for testing purpose
            if (myUnit.getType() == UnitType.Terran_Command_Center && self.minerals() >= 1150
                    && self.supplyUsed() < self.supplyTotal()) {
                // Ansich nur ausführen, wenn noch keins getraint wird -> myUnitisIdle
                myUnit.train(UnitType.Terran_SCV);
            }
            
            
            // MAL SO RICHTIGEN QUATSCH AUSPROBIEREN --> Alle Variablen ab und zu mal
            // reseten
            if ((self.supplyUsed() == 16) || (self.supplyUsed() == 20) || (self.supplyUsed() == 26)
                    || (self.supplyUsed() == 32) || (self.supplyUsed() == 38)) {
                // Jetzt ONUnitCreate
                // buildingSupplyDepot = 0;
                // buildingRax=0;
            }
            
            
            
            // Was machen meine Rax?
            if (myUnit.getType() == UnitType.Terran_Barracks) {
                if (myUnit.isIdle() && self.minerals() >= 50) {
                    myUnit.train(UnitType.Terran_Marine);
                }
            }
            
            
            //WAS MACHEN MEINE FAXX?
            if (myUnit.getType() == UnitType.Terran_Factory )
            {
                
                if (myUnit.isIdle()  && self.minerals() >= 50 && self.gas() >= 50)
                {
                    
                    myUnit.buildAddon(UnitType.Terran_Machine_Shop);
                }
                
                if (myUnit.getAddon() != null && self.minerals() >= 150 && self.gas() >= 200 && myUnit.isIdle() )
                {
                    myUnit.train(UnitType.Terran_Siege_Tank_Tank_Mode);
                }
                
            }
            
            
            //WAS MACHT MEIN FAX ADDON
            if (myUnit.getType() == UnitType.Terran_Machine_Shop)
            {
                
                if (myUnit.isIdle() && self.isResearchAvailable(TechType.Tank_Siege_Mode) && self.minerals() >= 150 && self.gas() >= 150 )
                {
                //JETZT MÜSSTE DIE FORSCHUNG ANGESCHMISSEN WERDEN    
                myUnit.research(TechType.Tank_Siege_Mode);    
                }
                
            }
    
            
            //WAS MACHEN MEINE Starports
            if (myUnit.getType() == UnitType.Terran_Starport )
            {
                
                if (myUnit.isIdle()  && self.minerals() >= 150 && self.gas() >= 225)
                {
                    
                    myUnit.buildAddon(UnitType.Terran_Control_Tower);
                }
                
                if (myUnit.getAddon() != null && self.minerals() >= 250 && self.gas() >= 250  && myUnit.isIdle() )
                {
                    myUnit.train(UnitType.Terran_Science_Vessel);
                }
                
            }
            
                
            
            
            
            
            
            
            
            
            
            // Was machen meine SupplyDepots
            if (myUnit.getType() == UnitType.Terran_Supply_Depot)
            {
            }
            
            
            
            // Was machen meine Bunker
            if (myUnit.getType() == UnitType.Terran_Bunker)
            {
            //MAn könnte checken ob ein Bunker nicht voll ist
            //und Marines sagen dass sie reingehen sollen    
            
            if (myUnit.isUnderAttack() || myUnit.getInitialHitPoints() != myUnit.getHitPoints())	
            	{
            	RepairingBunker = myUnit;
            	

            	tempUnit = game.indexToUnit(Builder_ID_Bunker);
            	tempUnit.repair(RepairingBunker);

            	tempUnit = game.indexToUnit(Builder_ID_Misc);
            	tempUnit.repair(RepairingBunker);
            	
            	tempUnit = game.indexToUnit(Builder_ID_Repair);
            	tempUnit.repair(RepairingBunker);
            	
            	}
            	
            	
            if (FillingBunker != null)
            	{
            	if (FillingBunker.getSpaceRemaining() == 0  )
            		{
            		
            	FillingBunker = null;	
            		}
            	
            	}
           
            if (FillingBunker == null && myUnit.getSpaceRemaining() != 0 && myUnit.isCompleted())
            	{
            	FillingBunker = myUnit;
            	
            	}
            
            }
    
            
            
            
            
            
            
            //WAS MACHEN MEINE SCOUTING - MARINES
            //if (Scout == null && myUnit.getType() == UnitType.Terran_Marine && myUnit.isCompleted() && myUnit.getID() != Deffer.getID())
            if (Scout == null && myUnit.getType() == UnitType.Terran_Marine && myUnit.isCompleted() && myUnit.isIdle())
                {
                System.out.println("Es müsste ein Scout ausgewählt worden sein und zum Angriff geschickt!");
                Scout  = myUnit;
                myScoutPosi = new Position(7*32,55*32);
                Scout.attack(myScoutPosi);
                
                //myUnit.attack(myScoutPosi);
				}
            
            
            if (Scout != null && Scout2 == null && myUnit.getID() != Scout.getID() && myUnit.getType() == UnitType.Terran_Marine && myUnit.isCompleted() && myUnit.isIdle())
            	{
            	System.out.println("Es müsste ein Scout2 ausgewählt worden sein und zum Angriff geschickt!");
                Scout2  = myUnit;
                myScoutPosi = new Position(9*32,39*32);
                Scout2.attack(myScoutPosi);
            	
            	}

            
            
            
            
            
            
            
            
            //ist der Deffer im Bunker, kann er null gesetzt werden
            if (FillingBunker != null)
            {
                if (FillingBunker.getSpaceRemaining() == 0)
                {
                FillingBunker = null ;    
                    
                }
                
            }
            
            
            
            if (Deffer != null)
            {	
            if (Deffer.isLoaded())
            	{
            	
            	Deffer = null;
            	}
            }
            
            
            //Auswahl eines Deffers
            if (Deffer == null && myUnit.getType() == UnitType.Terran_Marine && myUnit.isCompleted() && !myUnit.isLoaded() 
            		&& myUnit.getID() != Scout.getID() && myUnit.getID() != Scout2.getID() 
            		&& self.allUnitCount(UnitType.Terran_Bunker) >= 1)
            {
                
                
                System.out.println("Es wurde grade ein Deffer Marine gesetzt");
                Deffer = myUnit;
                
                
                if (FillingBunker != null)
                {
                	
                	Deffer.rightClick(FillingBunker);
                	
                }
                else
                {
                	
                	System.out.println("Es gibt keinen Bunker der befüllt werden muss");
                	
                }
            }
            
            
            //int tempi = self.minerals() / 8 % 14;
            
            
        
            
            //DEN DEFFER UNSETTEN
            if (Deffer != null )
            {
                
                
                //System.out.println("Der Deffer wurde gelöscht");
                //Deffer = null;
                
                
            if (Deffer.isLoaded() || !Deffer.exists())
                {
                System.out.println("Der Deffer wurde gelöscht (isgeladen)");
                Deffer = null;
                
                }
            else
                {
                
            	
            	
            	/*
                if (BunkerPosi == null)
                {
                    
                BunkerPosi = new Position(17*32,55*32);
                        
                }
                */
                                //WAR NICHT SO ERFOLREICH
                                
                                
                                if (FillingBunker != null)
                                {

                                	
                                	
                                int myRandom = self.minerals() / 8;
                                myRandom = myRandom % 2;
                                
                                myRandom = 0;

                                if ( myRandom == 1)
                                	{
                                	BunkerPosi = FillingBunker.getPosition();
               						Deffer.move(BunkerPosi);
	                            	
               						FillingBunker.load(Deffer);    
               						System.out.println("Der Deffer wurde eingeladen! und vorher auch noch hinbewegt zur Position (nicht Tile)!");    
                                	}
                                else
                                	{
                                	
                                	if (!Deffer.isLoaded() && Deffer.isIdle())
                                		{
                                		Deffer.rightClick(FillingBunker);
                                    	System.out.println("Ich probiere es nun mal mit nem Rechtsklick!");
                                    		
                                		}
                                	
                                	}
                                	
                                	
                              
                                //allmystuff.load(Deffer);
                                
                                //System.out.println("Es wurde der Deff Marine zum Bunker geschickt!");
                            
                
                
								}
           
                }
            }
            
            
            
            
            /*
			//DIESE ANWEISUNGEN SIND ALT UND SCHLECHT WEGEN DER FOR SCHLEIFE DAHER NEUSCHREIBEN BZW LÖSCHEN
            //MEINE ANDEREN MARINES SOLLEN IN DIE BUNKEr MARSCHIEREN
            if (myUnit.getType() == UnitType.Terran_Marine && myUnit.isCompleted() && myUnit.getID() == Deffer.getID() && !Deffer.isLoaded())
            {
                //System.out.println("Die interne forschleife beginnt");
                //EINE FALSCHE FOR SCHLEIFE ?!?
                for (Unit allmystuff : self.getUnits())
                //do 
                {
                    
                    if (allmystuff.getType() == UnitType.Terran_Bunker && allmystuff.getSpaceRemaining() != 0 && allmystuff.isCompleted() )
                    {
                        
                        BunkerPosi = new Position( allmystuff.getX() , allmystuff.getY() );
                        if (FillingBunker == null)
                        {
                        FillingBunker = allmystuff;
                        }
                        
                        if (allmystuff.canLoad(Deffer) && Deffer != null)
                        {
                            //WAR NICHT SO ERFOLREICH
                            Deffer.move(BunkerPosi);
                            allmystuff.load(Deffer);
                            System.out.println("Es wurde der Deff Marine zum Bunker geschickt! und ansich auch eingeladen");
                        }
                        
                        
                        //WENN DER MARINE DA IST UND UNGELOADED WERDEN KANN; KANN DEFFER NULL GESETTET WERDNE
                        if (allmystuff.canUnload(Deffer))
                        {
                            
                            Deffer = null;
                            
                        }
                        
                        
                    }
                    
                }
                //while(false);
            
                //System.out.println("Die interne forschleife ist beendet");
            
            }
            
            */
         
          
            
            
            //Die Tanks1-3 siegen lassen
            if (NatTank1 != null)
            {
            	
            	
            	if (isUnitNearTPosi(NatTank1,NatTank1TPosi))
            	{
            		
            		NatTank1.siege();
            		
            	}
            	else if (!NatTank1.isSieged())
            	{
            		
            		NatTank1.attack(NatTank1Posi);
            	}
            	
            	/*
            	if (!NatTank1.isMoving() && NatTank1.getPosition() == NatTank1Posi)
            	{
            		NatTank1.siege();
            	}
            	*/
            }
            
 
            if (NatTank2 != null)
            {
            	
            	if (isUnitNearTPosi(NatTank2,NatTank2TPosi))
            	{
            		
            		NatTank2.siege();
            		
            	}
            	else if (!NatTank2.isSieged())
            	{
            		
            		NatTank2.attack(NatTank1Posi);
            	}
            }
            

            if (NatTank3 != null)
            {
            	
            	if (isUnitNearTPosi(NatTank3,NatTank3TPosi))
            	{
            		
            		NatTank3.siege();
            		
            	}
            	else if (!NatTank3.isSieged())
            	{
            		
            		NatTank3.attack(NatTank3Posi);
            	}
            }

            
            
            
          //Tanks in Position bringen
          if (myUnit.getType() == UnitType.Terran_Siege_Tank_Tank_Mode && myUnit.isCompleted() )
          {
         
        	  if (!isUnitAssigned2Array(myUnit,DefferTanks4Nat))
        	  {
        		  AssignUnit2Array(myUnit,DefferTanks4Nat);
        	  }
            
        	  //DIE EINHEIT IS IM DEFFER ARRAY -> Also sollte sie nun zur Nat fahren und dort Siegen
        	  if (isUnitAssigned2Array(myUnit,DefferTanks4Nat))
        	  {
        		  //int blah=getPosi4UnitofArray(myUnit,DefferTanks4Nat);
        		  int blah = Arrays.asList(DefferTanks4Nat).indexOf(myUnit);
        		  
        		  if (myUnit.getPosition() != Posi4DefferTanks4Nat[blah])
        		  {
        		  myUnit.move(Posi4DefferTanks4Nat[blah]);
        		  }
        		  else
        		  {
        		  myUnit.siege();
        		  }
        		  
        	  }
        	  
          }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            // Build ur 1st Gas
            if (myUnit.getType() == UnitType.Terran_SCV && myUnit.getID() == Builder_ID_Gas && self.supplyUsed() >= 50
                    && self.minerals() >= 100 && !myUnit.isConstructing() && myUnit.isGatheringMinerals()) { 
              
            	
            	// find the closest Gas
                for (Unit neutralUnit : game.neutral().getUnits()) {
                    if (neutralUnit.getType() == UnitType.Resource_Vespene_Geyser ) {
                        if (closestGas == null || myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestGas)) {
                            closestGas = neutralUnit;
                        }
                    }
                }
                if (closestGas != null) {
                    // myPlace = myGasPlaces[0] = closestGas.getTilePosition(); ;
                    myPlace = closestGas.getTilePosition();
                    myUnit.build(UnitType.Terran_Refinery, myPlace);
                    buildingGas = 1;
                }
            }
            
            
            //Build ur 2nd Gas at Natural
            if (myUnit.getID() == Builder_ID_Gas && self.minerals() >= 100 && self.supplyUsed() >= 85  && !myUnit.isConstructing()
            		&& self.allUnitCount(UnitType.Terran_Command_Center) >= 2 && self.allUnitCount(UnitType.Terran_Refinery) == 1)
            {
            	System.out.println("Ich baue einfach mal das 2te Gas!");
            	myPlace = new TilePosition(11,76);
            	myPosi	= new Position(10*32,75*32);
            	
            	//myUnit.move(myScoutPosi);
            	//if (!myUnit.isMoving())
            	if (myUnit.getX() >= 15 && myUnit.getX() <= 10 && myUnit.getY() >= 80 && myUnit.getY() <= 70)
            	{
                myUnit.move(myPosi);
                System.out.println("Die Einheit geht hofffentlich in die richtige Richtung!");               		
            	}
            	else
            	{
            	
		        	if (myUnit.canBuild(UnitType.Terran_Refinery, myPlace))
		        	{
		        	myPlace = myGasPlaces[self.allUnitCount(UnitType.Terran_Refinery)];
		        	System.out.println("Es sollte gebaut werden.");	
		        	myUnit.build(UnitType.Terran_Refinery, myPlace);	
		        	}
		        	else
		        	{
		        	System.out.println("Die Koordinate is fürn Arsch!");
		        		
		        	}
		        }
            	
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            int myshita = self.allUnitCount(UnitType.Terran_Command_Center);
            int myshitb = self.allUnitCount(UnitType.Terran_Barracks);
            myshitb++;
            myshita--;
            myshita *= 3;
            
            //AB 30 SOLLTE CC 1st // 26 ist noch LateRax
            //BETTER RAX FOR RANDOM!
            if (self.supplyUsed() >= 20 && self.minerals() >= 200 && buildingRax == 0 
                    && myUnit.getID() == Builder_ID_Rax  && !myUnit.isConstructing()
                    && (self.allUnitCount(UnitType.Terran_Barracks) <= 0 || myshita >= myshitb)) 
                {
                
                
                tempi = self.minerals() % 12;
                tempi = tempi / 2 ;
                //System.out.println("Nun wird die erste RAX (nr " + self.allUnitCount(UnitType.Terran_Barracks) + " ) vom SCV (ID: " + Builder_ID_Rax + ") gebaut Place Nr " + tempi + " ! ");
                System.out.println("Nun wird die erste RAX (nr " + self.allUnitCount(UnitType.Terran_Barracks) + " ) vom SCV (ID: " + Builder_ID_Rax + ") gebaut aus dem Array ! ");
                
                
                myRaxPlace = myRaxPlaces[tempi];
                
                
                System.out.println(" der Wert wurde übergeben");
                tempi = self.allUnitCount(UnitType.Terran_Barracks);
                
                //myRaxPlace = new TilePosition(13,90);
                if (tempi == 0) { myRaxPlace = new TilePosition(13, 96); }
                if (tempi == 1) { myRaxPlace = new TilePosition(13, 93); }
                if (tempi == 2) { myRaxPlace = new TilePosition(13, 90); }
                if (tempi == 3) { myRaxPlace = new TilePosition(13, 87); }
                if (tempi == 4) { myRaxPlace = new TilePosition(13, 86); }
                if (tempi == 5) { myRaxPlace = new TilePosition(15, 74); }
                if (tempi == 6) { myRaxPlace = new TilePosition(15, 79); }
                
                
                myRaxPlace = myRaxPlaces[self.allUnitCount(UnitType.Terran_Barracks)];
                
                myUnit.build(UnitType.Terran_Barracks, myRaxPlace);
                
                //myUnit.build(UnitType.Terran_Barracks, myRaxPlace);
                
                
                System.out.println("Der Auftrag (Rax-Bauauftrag) wurde übergeben!");

                }            
            
            
            
            
            
            
            
            
            //EXPANDEN
            if (myUnit.getID() == Builder_ID_HQ && self.minerals() >= 420 && self.supplyUsed() >= 20 
                    && self.allUnitCount(UnitType.Terran_Command_Center) == 1 && !myUnit.isConstructing() && myUnit.isGatheringMinerals() )
            {
                //System.out.println("Es soll ein neues HQ entstehen!");
                //tempx = 8;
                //tempy = 65;
                //myPlace = new TilePosition(tempx,tempy);
               
                System.out.println("PERFEKT! ES WIRD GEBAUT bei meine myPlace4CC1 gebaut!");
                
                myPlace = myPlace4CC1;
                
                if (myUnit.canBuild(UnitType.Terran_Command_Center, myPlace))
                {
                	
                    myUnit.build(UnitType.Terran_Command_Center, myPlace);
                	
                }
                else
                {
                	
                	myUnit.move(new Position(myPlace.getX()*32 , myPlace.getY()*32));
                }

               
                
                /*
                if (!game.canBuildHere(myPlace, UnitType.Terran_Command_Center , myUnit))
                {
                    tempi = self.minerals() % 8 ;
                    tempi = tempi % 2;
                    //System.out.println("Leider kann auf dem Tile ( " + tempx + "  / " + tempy + " ) nicht gebaut werden");
                    myUnit.build(UnitType.Terran_Supply_Depot, myPlace);
                    
                    
                    if (tempi == 0)
                    {
                    tempx--;                        
                    }
                    else 
                    {
                    tempy--;    
                    }
                    
                }
                else 
                {
                    
                //System.out.println("PERFEKT! ES WIRD GEBAUT bei " + tempx + " / " + tempy + "!");
                System.out.println("PERFEKT! ES WIRD GEBAUT bei meine myPlace4CC1 gebaut!");
                
                myPlace = myPlace4CC1;
                myUnit.build(UnitType.Terran_Command_Center, myPlace);
                //erst hinbewegen und dann bauen? -> Fog of War
                //myUnit.move(myScoutPosi);
                }
                */
            }
            
            
            
            
            //EXPANDEN 3rd Base --> deactivated by Count 3->2
            if (myUnit.getID() == Builder_ID_HQ && self.minerals() >= 400 && self.supplyUsed() >= 140 
                    && self.allUnitCount(UnitType.Terran_Command_Center) == 2)
            {
                
                myPlace = myPlace4CC2;
                
                if (myUnit.canBuild(UnitType.Terran_Command_Center, myPlace))
                {

                System.out.println("PERFEKT! ES WIRD EIN DRITTEN HQ bei meine myPlace4CC2 gebaut!");
                myUnit.build(UnitType.Terran_Command_Center, myPlace);
                	
                }
                else
                {
                System.out.println("ES WIRD KEIN DRITTEN HQ bei meine myPlace4CC2 gebaut, scheiss koords!");
	
                	
                }
            
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            //TECHEN
            
            if (myUnit.getID() == Builder_ID_Tech && self.minerals() >= 200 && self.gas() >= 100 
                    && self.allUnitCount(UnitType.Terran_Factory) == 0)
            {
                
                myPlace = new TilePosition(5,103);
                myUnit.build(UnitType.Terran_Factory, myPlace);
                
    
            }
            
            if (myUnit.getID() == Builder_ID_Tech && self.minerals() >= 200 && self.gas() >= 100 
                    && self.allUnitCount(UnitType.Terran_Starport) == 0 && self.supplyUsed() >= 100 )
            {
                
                
                myPlace = new TilePosition(12,103);
                myUnit.build(UnitType.Terran_Starport, myPlace);
                
    
            }
            
            
            
            
            
            
            
     
            
            
            
            
            
            
            
            
            
            
            
            
            
            //DEFF BAUEN
            // Ich probiermal nen Bunker zu bauen

            int bla = self.allUnitCount(UnitType.Terran_Bunker);
            bla *= 10;
            
            if (bla <= 30) { bla +=20; }
            
            bla += 50;
            
             if (myUnit.getID() == Builder_ID_Bunker && self.supplyUsed() >= 50 && self.allUnitCount(UnitType.Terran_Command_Center) >= 2 && 
             self.minerals() >= 150 && myBunkerPlaces.length > self.allUnitCount(UnitType.Terran_Bunker) && !myUnit.isConstructing() && myUnit.isGatheringMinerals()
             && self.supplyUsed() >= bla) {
            
              
            	//System.out.println("Ansich sollte nun gebaut werden, aber ich hab nen canBuild eingefügt!");
              
                //2ndCC Safing
            	myBunkerPlaces[0] = new TilePosition(11,68);
                //Choke @ Natural deffen mit 3 Tanks und Turrents
            	myBunkerPlaces[1] = new TilePosition(8, 62);
                myBunkerPlaces[2] = new TilePosition(13,62);
                
                //Main Safety Bunker
                myBunkerPlaces[3] = new TilePosition(18,85);
                
                //3rd CC Safety Bunker
                myBunkerPlaces[4] = new TilePosition(14,50);
                //3rd CC Bridge Deffing
                myBunkerPlaces[5] = new TilePosition(15,45);
                myBunkerPlaces[6] = new TilePosition(15,41);
                myBunkerPlaces[7] = new TilePosition(16,37);
                
              
                myPlace = myBunkerPlaces[self.allUnitCount(UnitType.Terran_Bunker)];
               
                myUnit.build( UnitType.Terran_Bunker , myPlace );
            	
                
                
               /*
               if (myUnit.canBuild(UnitType.Terran_Bunker, myPlace))
               {
            	   System.out.println("Nun wurde grade eine weiterer Bunker (Nr " +
            	              self.allUnitCount(UnitType.Terran_Bunker) + ") beauftragt");
            	              
                   myUnit.build( UnitType.Terran_Bunker , myPlace );
            	   
               }
               else
               {
            	   System.out.println("Es ist leider eine üngültige TPosi");
            	   
               }
			*/
			
			
			
             }
            
            
            
             //Ich bau mal nen Ebay und dann nen Turret an die Front
             if (myUnit.getID() == Builder_ID_Misc && self.supplyUsed() >= 80 && self.minerals() >= 150 && self.allUnitCount(UnitType.Terran_Engineering_Bay) == 0 && !myUnit.isConstructing() && self.allUnitCount(UnitType.Terran_Command_Center) >= 2)
             {
                 
                 System.out.println("Ich bau mal nen Ebay für Upgrades und Turrets");
                 myPlace = new TilePosition(13,99);
                 myUnit.build(UnitType.Terran_Engineering_Bay, myPlace);
                 
             }
             
             
             if (myUnit.getID() == Builder_ID_Misc && self.allUnitCount(UnitType.Terran_Engineering_Bay) != 0 && self.minerals() >= 80 && !myUnit.isConstructing()
            		 && self.allUnitCount(UnitType.Terran_Missile_Turret) <= 3)
             {
            	 

            	 
            	 System.out.println("Ich bau mal nen Turret gegen DTs");
                 int furz = self.allUnitCount(UnitType.Terran_Missile_Turret);
            	 myPlace = myTurretPlaces[furz];
                 myUnit.build(UnitType.Terran_Missile_Turret, myPlace);
                 
            	 
            	 
             }
             
             
             
             
             
             
             
             
             
             
             
             
             
            
            
            
            
            
            
            // NUR DAS ERSTE DEPOT!
            if (myUnit.getType() == UnitType.Terran_SCV && self.minerals() >= 100 && myUnit.getID() == Builder_ID_Depot && self.supplyUsed() >= 18
                    && buildingSupplyDepot == 0 && self.allUnitCount(UnitType.Terran_Supply_Depot) == 0 && !myUnit.isConstructing()) {
                game.drawTextScreen(10, 25, "Playing as " + self.getName() + " - " + self.getRace()
                        + " und möchte nun DAS ERSTE Depot bauen! Aber hoffentlich nicht andauernd!");
                System.out.println("Nun wurde grade das erste Supply Depot beauftragt");
                myPlace = myDepotPlaces[self.allUnitCount(UnitType.Terran_Supply_Depot)];
                myUnit.build(UnitType.Terran_Supply_Depot, myPlace);
                
                //buildingSupplyDepot = 1;
            }
			
			
			
            // NUR DAS ZWEITE DEPOT!
            if (myUnit.getType() == UnitType.Terran_SCV && self.minerals() >= 160 && myUnit.getID() == Builder_ID_Depot
                    && buildingSupplyDepot == 0 && self.allUnitCount(UnitType.Terran_Supply_Depot) == 1
                    && self.supplyUsed() >= 28 && !myUnit.isConstructing()) {
                game.drawTextScreen(10, 25, "Playing as " + self.getName() + " - " + self.getRace()
                        + " und möchte nun DAS ERSTE Depot bauen! Aber hoffentlich nicht andauernd!");
                System.out.println("Nun wurde grade das zweite Supply Depot beauftragt");
                myPlace = myDepotPlaces[self.allUnitCount(UnitType.Terran_Supply_Depot)];
                myUnit.build(UnitType.Terran_Supply_Depot, myPlace);
                //buildingSupplyDepot = 1;
            }
			
			
			
			
            // AB DEM DRITTEN DEPOT
            if (myUnit.getType() == UnitType.Terran_SCV && self.supplyUsed() + 10 >= self.supplyTotal()
                    && self.minerals() >= 100 && myUnit.getID() == Builder_ID_Depot && buildingSupplyDepot == 0
                    && self.allUnitCount(UnitType.Terran_Supply_Depot) >= 2  && !myUnit.isConstructing()) {
                game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace()
                        + " und möchte nun ein Depot bauen! Aber hoffentlich nicht andauernd!");
                
                System.out.println("Nun wurde grade ein weiteres Supply Depot beauftragt");
                
                if (self.allUnitCount(UnitType.Terran_Supply_Depot) <= myDepotPlaces.length)
                {
                System.out.println("Es wurde ein Koordinate aus dem Array genommen");
                myPlace = myDepotPlaces[self.allUnitCount(UnitType.Terran_Supply_Depot)];
                myUnit.build(UnitType.Terran_Supply_Depot, myPlace);
                }
                else
                {
                System.out.println("Es wurden schon alle Koordinaten ausgelesen!Nun musst du dir was neues überlegen");    
                
                }
                // Vielleicht sollte man es umstellen darauf nur wenn der buuilder grade am
                // bauen ist
                //buildingSupplyDepot = 1;
            }
            
            
            
            
            
            
            /*
             * if (myUnit.getID() == Builder_ID_Depot && buildingSupplyDepot == 1 &&
             * !myUnit.isConstructing()) { buildingSupplyDepot = 0; }
             */
            
            
            // myUnit.isCompleted gibt true zurück wenn die Einheit fertig ausgebildet wurde
            // if it's a worker and it's idle, send it to the closest mineral patch

            
            /*
             Mal probiert was für die GasWorker zu schreiben
            if (myUnit.getType().isWorker() && myUnit.isIdle() && !myUnit.isGatheringMinerals() && !myUnit.isConstructing() && myUnit.isCompleted())
			{
            	
            	if (self.allUnitCount(UnitType.Terran_Refinery) == 1 && !isUnitArrayFull(myGasGatherer))
            	{
            	//JETZT MÜSSTE DER ARBEITER DEM ARRAY ZUGEWIESEN WERDEN UND DANN AUFS GAS GEKLICKT WERDEN	
   
            	myGasGatherer = AssignUnit2Array(myUnit,myGasGatherer);
 			  	Position Gas = myGasBuilding[0].getPosition();
 			  	//myUnit.rightClick(Gas);
 			  	myUnit.rightClick(myGasBuilding[0]);
 			  	System.out.println("Es sollte nun ein weiterer Arbeiter " + myUnit.getID() + " Gas sammeln (durch RightClick)!");

            		
            		
            		
            	}
            	
	
	
			}
            
             */
            
            
            
            
            if (myUnit.getType().isWorker() && myUnit.isIdle() && !myUnit.isGatheringMinerals() && !myUnit.isConstructing() && myUnit.isCompleted())
            {

            	
            	//Wenn der Worker was in der Hand hast soll er, das erstmal abgeben.
            	if (myUnit.isCarryingGas() || myUnit.isCarryingMinerals() )
            	{
            		
            		myUnit.returnCargo();
            		
            	}
            	else
            	{
            		
            	
            	
            	
            	
            	Unit closestMineral = null;
                // find the closest mineral 4 Harvesting
                for (Unit neutralUnit : game.neutral().getUnits()) {
                    
                	
                	
                	
                	
                	//EINGEFÜGT OB DIE MIBNERALS BEREITS GEGATHERD WERDEN
                    if ((neutralUnit.getType().isMineralField() || neutralUnit.getType().isRefinery()) && !neutralUnit.isBeingGathered()) {
                        if (closestMineral == null
                                || myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestMineral)) {
                            closestMineral = neutralUnit;
                        }
                    }
                }
                // if a mineral patch was found, send the worker to gather it
                if (closestMineral != null) {
                    myUnit.gather(closestMineral, false);
                    if (debug_worker)
                    {
                    System.out.println("Ich schicke ein SCV (ID: " + myUnit.getID() + ") wieder zur Arbeit!");
                        
                    }
                }
                
            }
           	}


            
            
        }
                 
                
                /*
                 * //NACH DER WIEDERAUFNAHME DER ARBEIT WERDEN DIE BUILDING VARIABLEN RESETET if
                 * (myUnit.getID() == Builder_ID_Depot) { buildingSupplyDepot=0; } if
                 * (myUnit.getID() == Builder_ID_Rax) { buildingRax=0; } if (myUnit.getID() ==
                 * Builder_ID_Gas) { buildingGas=0; }
                 */
       // draw my units on screen
        // not needed anymore
        // game.drawTextScreen(10, 55, units.toString());
		
		
            	

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        new MyAlltimeBot().run();
    }
    
}
 
