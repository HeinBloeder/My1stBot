import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;

public class TestBot1 extends DefaultBWListener {

    private Mirror mirror = new Mirror();

    private Game game;

    private Player self;
    
    public int order = 0;
    public int tempi = 0;
    
    public int buildingSupplyDepot 	= 0;
    public int buildingRax 			= 0;
    public int buildingGas 			= 0;
    public int buildingBunker 		= 0;
    
    
    public int BuiltRax = 0;
    public int BuiltBunker = 0;
    public int BuiltSupplyDepot = 0;
    
    public int countingRax = 0;
    public int countingBunker = 0;
    public int countingSupplyDepot = 0;

    
    public Unit closestGas = null;
    
    
    public Unit Builder_Depot 	= null;
    public Unit Builder_Rax 	= null;
    public Unit Builder_Gas 	= null;
    public Unit Builder_Bunker 	= null;
    
    
    public int Builder_ID_Depot 	= 0;
    public int Builder_ID_Rax	 	= 0;
    public int Builder_ID_Gas 		= 0;
    public int Builder_ID_Bunker	= 0;
    
    
    public TilePosition[] myDepotPlaces = new TilePosition[8];
    public TilePosition[] myRaxPlaces = new TilePosition[8];
    public TilePosition[] myGasPlaces = new TilePosition[8];
    public TilePosition[] myBunkerPlaces = new TilePosition[8];
    
    public TilePosition myPlace = new TilePosition(8,86);
    public TilePosition myNewPlace = new TilePosition(13,96);
    
    
    public void run() {
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    @Override
    public void onUnitCreate(Unit unit) {
        System.out.println("New unit discovered " + unit.getType());
        System.out.println("My Supply is " + self.supplyUsed() + " from " + self.supplyTotal() + "!Anscheind alles times 2");
        
        
        //FUNZT ALLES NICHT SO GENAU
        //if (unit.getType() == UnitType.Terran_Supply_Depot && buildingSupplyDepot == 1)
        if (unit.getType() == UnitType.Terran_Supply_Depot)
        {
        	//System.out.println("Nun wurde grade ein Supply Depot fertig gestellt");
        	
        	System.out.println("Nun wurde grade ein Supply Depot angefangen zu bauen.");
        	//buildingSupplyDepot = 0;
        }
    }

    @Override
    public void onStart() {
        game = mirror.getGame();
        self = game.self();

        //ZUM START WIRD KEIN SUPPLY DEPOT GEBAUT
        //buildingSupplyDepot = 0;
        
        
        myDepotPlaces[0]= new TilePosition(7,86);
        myDepotPlaces[1]= new TilePosition(4,86);
        myDepotPlaces[2]= new TilePosition(1,86);
        myDepotPlaces[3]= new TilePosition(1,84);
        myDepotPlaces[4]= new TilePosition(4,84);
        myDepotPlaces[5]= new TilePosition(7,84);
        myDepotPlaces[6]= new TilePosition(1,82);
        myDepotPlaces[7]= new TilePosition(4,82);
        myDepotPlaces[8]= new TilePosition(7,82);
        
        myBunkerPlaces[0] = new TilePosition(18,85);
        myBunkerPlaces[1] = new TilePosition(15,85);
        
        
        
        myRaxPlaces[0] = new TilePosition(13,96);
        myRaxPlaces[1] = new TilePosition(13,85);
        myRaxPlaces[2] = new TilePosition(13,90);
        myRaxPlaces[3] = new TilePosition(13,93);
        myRaxPlaces[4] = new TilePosition(13,99);
        
        myGasPlaces[0] = new TilePosition(8,90);
        
        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        System.out.println("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        System.out.println("Map data ready");
        
        int i = 0;
        for(BaseLocation baseLocation : BWTA.getBaseLocations()){
        	System.out.println("Base location #" + (++i) + ". Printing location's region polygon:");
        	for(Position position : baseLocation.getRegion().getPolygon().getPoints()){
        		System.out.print(position + ", ");
        	}
        	System.out.println();
        }

        //ET GAME SPEED?!?! 55 ist wohl zu schnell
        //game.setLocalSpeed(55);        
    }

    @Override
    public void onFrame() {
        //game.setTextSize(10);
        game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace() + " BuilderIDDepot " + Builder_ID_Depot + " und RaxID " + Builder_ID_Rax + " und Bunker " + Builder_ID_Bunker + "! Supply " + self.supplyUsed() + " / " + self.supplyTotal() + "!?!");
        game.drawTextScreen(10, 25, "Hier kann ich mir ganz viele Variablen ausgeben lassen! BuildingDepot " + buildingSupplyDepot + " AllDepots " + self.allUnitCount(UnitType.Terran_Supply_Depot) + " !! BuildingRax " + buildingRax + " AllRax " + self.allUnitCount(UnitType.Terran_Barracks) + " ??!");

        StringBuilder units = new StringBuilder("My units:\n");

        
        //SET GAME SPEED?!?! 38 sieht auch langsam aus 
        game.setLocalSpeed(33);   
        
        
        
        //iterate through my units
        for (Unit myUnit : self.getUnits()) {
            units.append(myUnit.getType()).append(" ID ").append(myUnit.getID()).append("  ").append(myUnit.getTilePosition()).append("\n");

           
          
            
            
            //DIE INTIALIZIERUNG DER IDs MUSS ZUM START VON JEDEM GAME PASSIEREN
            
            
            if (self.supplyUsed() >= 14 )
            {
            if (Builder_ID_Depot == 0)
            {
            	if ((myUnit.getID() != Builder_ID_Rax || Builder_ID_Rax == 0 ) && (myUnit.getID() != Builder_ID_Bunker  || Builder_ID_Bunker == 0))
            	{
            		Builder_ID_Depot = myUnit.getID();
            		System.out.println("Builder ID Depot wurde gesetzt");
            	}
            }
            
            if ( Builder_ID_Rax == 0 )
            {
            	if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Bunker) 
            	{
            		Builder_ID_Rax = myUnit.getID();
            		System.out.println("Builder ID Rax wurde gesetzt");
            	}
            	
            }
            
            
            if ( Builder_ID_Bunker == 0 )
            {
            	if (myUnit.getID() != Builder_ID_Depot && myUnit.getID() != Builder_ID_Rax) 
            	{
            		Builder_ID_Bunker = myUnit.getID();
            		System.out.println("Builder ID Bunker wurde gesetzt");
            	}
            	
            }
            
            }
            else
            {
            	Builder_ID_Bunker = 0;
            	Builder_ID_Rax = 0;
            	Builder_ID_Gas = 0;
            }
            
            
            
            
            
            
            //if there's enough minerals, train an SCV
            if (myUnit.getType() == UnitType.Terran_Command_Center && self.minerals() >= 50 && myUnit.isIdle()) {
                
            	
            	//Ansich nur ausführen, wenn noch keins getraint wird -> myUnitisIdle
            	myUnit.train(UnitType.Terran_SCV);
            }

            //Train a SCV -> for testing purpose
            if (myUnit.getType() == UnitType.Terran_Command_Center && self.minerals() >= 1150 && self.supplyUsed() < self.supplyTotal() ) {
                
            	
            	//Ansich nur ausführen, wenn noch keins getraint wird -> myUnitisIdle
            	myUnit.train(UnitType.Terran_SCV);
            }
            
            
            
            
            
            

            
            
            //MAL SO RICHTIGEN QUATSCH AUSPROBIEREN --> Alle Variablen ab und zu mal reseten
            if ((self.supplyUsed() == 16) || (self.supplyUsed() == 20) || (self.supplyUsed() == 26) || (self.supplyUsed() == 32) || (self.supplyUsed() == 38))
            {
            	//Jetzt ONUnitCreate
            	//buildingSupplyDepot = 0;
            	//buildingRax=0;
            }
            
            
            
            //Was machen meine Rax?
            if (myUnit.getType() == UnitType.Terran_Barracks)
            {
            	
            	
            	if (myUnit.isIdle() && self.minerals() >= 50) {
            		
            		myUnit.train(UnitType.Terran_Marine);
            	}
            
            
            }
            
            //Was machen meine SupplyDepots
            if (myUnit.getType() == UnitType.Terran_Supply_Depot )
            {
            	           	
            }
            
          //Was machen meine Bunker
            if (myUnit.getType() == UnitType.Terran_Bunker )
            {
            	         	
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            //Build ur 1st Gas
            if (myUnit.getType() == UnitType.Terran_SCV && myUnit.getID() == Builder_ID_Gas && self.supplyUsed() >= 20 && self.minerals() >= 200 && buildingGas == 0)
            {
            	

        		
                //find the closest Gas
                for (Unit neutralUnit : game.neutral().getUnits()) {
                	
                    if (neutralUnit.getType().isMineralField() == false) {
                        if (closestGas == null || myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestGas)) {
                            closestGas = neutralUnit;
                        }
                    }
                }
        		
                
                if (closestGas != null)
                {
                //myPlace = myGasPlaces[0] = closestGas.getTilePosition(); ;
                myPlace = closestGas.getTilePosition();
        		myUnit.build( UnitType.Terran_Refinery , myPlace );
        		buildingGas = 1;
            
                
                
                }
            
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            //Build ur 1st RAX
            
            if (myUnit.getType() == UnitType.Terran_SCV && self.supplyUsed() >= 26 && self.minerals() >= 200 && buildingRax == 0 && myUnit.getID() == Builder_ID_Rax  && self.allUnitCount(UnitType.Terran_Barracks) == 0 ) {
            	
            	game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace() + " und möchte nun die erste Rax bauen! Aber nicht alle auf einmal!");
            	        		
            		System.out.println("Nun wird die erste RAX gebaut!");
            		myPlace = myRaxPlaces[self.allUnitCount(UnitType.Terran_Barracks)];
            		myUnit.build( UnitType.Terran_Barracks , myPlace );	
            		buildingRax=1;
            		//myPlace = null;
            	}
            
            
            
            //BUILD UR NEXT RAX
            //FUNZT NICHT GANZ
            //if (myUnit.getType() == UnitType.Terran_SCV && BuiltRax > 0 && self.minerals() >= 350 && buildingRax == 0)
            if (myUnit.getType() == UnitType.Terran_SCV && self.minerals() >= 350 && buildingRax == 0  && myUnit.getID() == Builder_ID_Rax )
            {
            	
            	//tempi = BuiltRax;
            	//tempi++;
            
            	System.out.println("Nun wurde grade eine weitere Rax beauftragt");
            	
            	myPlace = myRaxPlaces[self.allUnitCount(UnitType.Terran_Barracks)];
            	//myUnit.build( UnitType.Terran_Barracks , myNewPlace );	
        		
            	myUnit.build( UnitType.Terran_Barracks , myPlace );	
        		buildingRax=1;
            
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            //check ur building depots
            
            //KOMPLIZIERTE VERSION GGEN SUPPLYCAPPED @10
            //ICH GLAUBE DAS MACHE ICH MAL AUS
            /*
            if (buildingSupplyDepot == 1  && self.supplyUsed() < self.supplyTotal() && self.supplyUsed() >= 10) {
            buildingSupplyDepot = 0;
            order = 0;
            }
            */
            
            //NUR DAS ERSTE DEPOT BAUEN
            /*
            if (myUnit.getType() == UnitType.Terran_SCV && self.supplyTotal() == 10 && self.minerals() >= 100 && buildingSupplyDepot == 0) {
            
            
            	 if (self.supplyTotal() <= 10 ) { myDepotPlaces[0]= new TilePosition(8,86); }         
                 if (self.supplyTotal() <= 10 ) { myUnit.build(UnitType.Terran_Supply_Depot, myDepotPlaces[0] ); }
                 buildingSupplyDepot = 1;
                  
            }
            */
            
            //Ich probiermal nen Bunker zu bauen
            
            /*
            if (myUnit.getID() == Builder_ID_Bunker && self.supplyUsed() >= 55 && self.minerals() >= 150 && BuiltBunker == 0)
            {
            	myPlace = myBunkerPlaces[self.allUnitCount(UnitType.Terran_Bunker)];
        		
            	myUnit.build( UnitType.Terran_Bunker , myPlace );
            	
            }
            */
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            //NUR DAS ERSTE DEPOT!
            if (myUnit.getType() == UnitType.Terran_SCV && self.minerals() >= 120 && myUnit.getID() == Builder_ID_Depot && buildingSupplyDepot == 0 && self.allUnitCount(UnitType.Terran_Supply_Depot) == 0 ) 
                    	{
            	
            	
            	game.drawTextScreen(10, 25, "Playing as " + self.getName() + " - " + self.getRace() + " und möchte nun DAS ERSTE Depot bauen! Aber hoffentlich nicht andauernd!");
            	System.out.println("Nun wurde grade das erste Supply Depot beauftragt");
            	
            	myPlace = myDepotPlaces[self.allUnitCount(UnitType.Terran_Supply_Depot)];
            		
            	myUnit.build( UnitType.Terran_Supply_Depot , myPlace );
                     
                buildingSupplyDepot = 1;
            		
                    	}
            
            
            
            
            
            
   
            if (myUnit.getType() == UnitType.Terran_SCV && self.supplyUsed()+6 <= self.supplyTotal() && self.minerals() >= 100 && myUnit.getID() == Builder_ID_Depot && buildingSupplyDepot == 0)
            {
                    	
            game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace() + " und möchte nun ein Depot bauen! Aber hoffentlich nicht andauernd!");
            System.out.println("Nun wurde grade ein weiteres Supply Depot beauftragt");
        	
            
            
            myPlace = myDepotPlaces[self.allUnitCount(UnitType.Terran_Supply_Depot)];
            myUnit.build( UnitType.Terran_Supply_Depot , myPlace );
                
           
            //Vielleicht sollte man es umstellen darauf nur wenn der buuilder grade am bauen ist
            buildingSupplyDepot = 1;
                                 
            }
            
            
            

            
            if (myUnit.getID() == Builder_ID_Depot && buildingSupplyDepot == 1 && !myUnit.isConstructing())
            {
            	buildingSupplyDepot = 0;
            }
            
        
           
            
            //if it's a worker and it's idle, send it to the closest mineral patch
            if (myUnit.getType().isWorker() && myUnit.isIdle()) {
                Unit closestMineral = null;

                
                if (myUnit.getID() == Builder_ID_Depot) { buildingSupplyDepot=0; }
                if (myUnit.getID() == Builder_ID_Rax) 	{ buildingRax=0; }
                if (myUnit.getID() == Builder_ID_Gas) 	{ buildingGas=0; }
                
                
                
                //find the closest mineral
                for (Unit neutralUnit : game.neutral().getUnits()) {
                    if (neutralUnit.getType().isMineralField()) {
                        if (closestMineral == null || myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestMineral)) {
                            closestMineral = neutralUnit;
                        }
                    }
                }

                //if a mineral patch was found, send the worker to gather it
                if (closestMineral != null) {
                    myUnit.gather(closestMineral, false);
                }
            }
        }

        //draw my units on screen
        //not needed anymore
        game.drawTextScreen(10, 55, units.toString());
    }

    public static void main(String[] args) {
        new TestBot1().run();
    }
}