package jeu;

import java.util.Scanner;

public class Methods {

	public final static byte CASE_VIDE = (byte) 0b1111;
	public final static byte PION_JOUEUR1 = 'X';
	public final static byte PION_JOUEUR2 = 'O';
	public final static String[] DIRECTION_COMBO={"verticale","horizontale","digonaleMontante","digonaleDescendante"};

	public static char[][] terrain;
	public static byte[][] tableauCombosPossible;
	public static int tailleTerrain;
	public static int nbManchesTotal;
	public static int nbManchesJou�;
	public static Scanner saisie;
	public static int scoreJoueur1,scoreJoueur2;
	public static char joueurEnCour;
	public static int[] propositionPlacementPion=new int[2];

	public static void saisirTailleTerrain() {
		saisie = new Scanner(System.in);
		System.out.println("Veuillez choisir la taille du terrain (4 ou plus)!");
		tailleTerrain = saisie.nextInt();
	}

	public static boolean tailleTerrainIncorrect() {
		if(tailleTerrain<4) {
			System.out.println("La taille de votre terrain doit �tre de 4 au minimum !");
			return true;
		}
		else {
			//saisie.nextLine();
			return false;
		}
	}

	public static void d�terminerNbManches() {
		nbManchesTotal = tailleTerrain*tailleTerrain;
	}

	public static void initialisationNbManchesJou�() {
		nbManchesJou� = 0;
	}

	public static void initialisationScoreJoueurs() {
		scoreJoueur1 = 0;
		scoreJoueur2 = 0;
	}

	public static void genererTerrain() {
		terrain=new char[tailleTerrain][tailleTerrain];
		for(int hauteur = 0;hauteur<tailleTerrain;hauteur++)
			for(int largeur = 0;largeur<tailleTerrain;largeur++)
				terrain[largeur][hauteur]= ' ';
	}

	public static void genererTableauCombos() {
		tableauCombosPossible=new byte[tailleTerrain][tailleTerrain];
		for(int hauteur = 0;hauteur<tailleTerrain;hauteur++)
			for(int largeur = 0;largeur<tailleTerrain;largeur++)
				tableauCombosPossible[largeur][hauteur] = CASE_VIDE;
	}

	public static void afficherTerrain() {
		System.out.print(" ");
		for(int indicationGrilleHorizontale = 0;indicationGrilleHorizontale<tailleTerrain;indicationGrilleHorizontale++) 
			System.out.print(" "+indicationGrilleHorizontale);
		System.out.println("");

		for(int hauteur = 0;hauteur<tailleTerrain;hauteur++) {
			System.out.print(hauteur);
			for(int largeur = 0;largeur<tailleTerrain;largeur++) {
				System.out.print("|"+terrain[largeur][hauteur]);
			}
			System.out.println("|");
		}
		System.out.println("Le score est de : "+scoreJoueur1+" - "+scoreJoueur2);
	}

	public static void auTourDe() {
		if(nbManchesJou�%2==0)
			joueurEnCour = PION_JOUEUR1;
		else
			joueurEnCour = PION_JOUEUR2;
	}

	public static void r�cupererPlacementPionJoueur() {
		int coordonn�esX,coordonn�esY;
		System.out.println("Veuillez choisir la position en x du pion joueur "+ joueurEnCour +" !");
		coordonn�esX=saisie.nextInt();
		System.out.println("Veuillez choisir la position en y du pion joueur "+ joueurEnCour +" !");
		coordonn�esY=saisie.nextInt();
		propositionPlacementPion[0]=coordonn�esX;
		propositionPlacementPion[1]=coordonn�esY;
	}

	public static boolean caseD�jaPriseOuHorsLimite() {
		if(propositionPlacementPion[0]<0 | propositionPlacementPion[0]>tailleTerrain | propositionPlacementPion[1]<0 | propositionPlacementPion[1]>tailleTerrain ) {
			System.out.println("La case n'est pas dans le terrain");
			return true;
		}
		else if(terrain[propositionPlacementPion[0]][propositionPlacementPion[1]]!=' ') {
				System.out.println("La case est d�ja prise");
				return true;
			}
		else
			return false;
	}

	public static void attribuerCase() {
		terrain[propositionPlacementPion[0]][propositionPlacementPion[1]]=joueurEnCour;
	}

	public static int combienJoueurAmarquerPoints() {
		int nbPointsMarqu�=0;
		for(int comboPossible=0;comboPossible<4;comboPossible++) {
			if(verifierSiComboGagnant(DIRECTION_COMBO[comboPossible]))
				nbPointsMarqu�++;
		}
		return nbPointsMarqu�;
	}

	public static boolean verifierSiComboGagnant(String direction) {
		int vecteurX,vecteurY;
		int d�calageX,d�calageY;
		int d�calageBinaire;
		int positionPionsRetenus[][]=new int[4][2];
		int nombrePionsretenus=1;
		positionPionsRetenus[0][0]=propositionPlacementPion[0];
		positionPionsRetenus[0][1]=propositionPlacementPion[1];	

		switch (direction) {
		case "verticale":
			vecteurX=0;
			vecteurY=1;
			d�calageBinaire=3;
			break;
		case "horizontale":
			vecteurX=1;
			vecteurY=0;
			d�calageBinaire=2;
			break;	
		case "digonaleMontante":
			vecteurX=1;
			vecteurY=-1;
			d�calageBinaire=1;
			break;		
		case "digonaleDescendante":
			vecteurX=1;
			vecteurY=1;
			d�calageBinaire=0;
			break;
		default:
			vecteurX=0;
			vecteurY=0;
			d�calageBinaire=0;
			break;
		}
		for(int passage=-1;passage<2;passage++) {
			for(int valeurVecteur=1;valeurVecteur<4;valeurVecteur++) {
				d�calageX=vecteurX*valeurVecteur*passage;
				d�calageY=vecteurY*valeurVecteur*passage;
				if(passage!=0 && propositionPlacementPion[0]+d�calageX<tailleTerrain && propositionPlacementPion[0]+d�calageX>=0 && propositionPlacementPion[1]+d�calageY<tailleTerrain && propositionPlacementPion[1]+d�calageY>=0 && terrain[propositionPlacementPion[0]+d�calageX][propositionPlacementPion[1]+d�calageY]==joueurEnCour && ((tableauCombosPossible[propositionPlacementPion[0]+d�calageX][propositionPlacementPion[1]+d�calageY] >> d�calageBinaire) & 0b0001)==1 && nombrePionsretenus<4)
					nombrePionsretenus++;
				System.out.println(nombrePionsretenus);
			}
		}
		if(nombrePionsretenus==4){
			majComboRestant(positionPionsRetenus,d�calageBinaire);
			return true;
		}		
		return false;
	}

	public static void majComboRestant(int[][] pionsFormeCombo,int directionComboConsommer){
		byte masque;
		switch (directionComboConsommer) {
		case 0:
			masque=0b1110;
			break;
		case 1:
			masque=0b1101;
			break;
		case 2:
			masque=0b1011;
			break;
		case 3:
			masque=0b0111;
			break;
		default:
			masque=0b0000;
			break;
		}
		for(int pions=0;pions<4;pions++) {
			tableauCombosPossible[pionsFormeCombo[pions][0]][pionsFormeCombo[pions][1]]=(byte) (tableauCombosPossible[pionsFormeCombo[pions][0]][pionsFormeCombo[pions][1]] & masque);
		}

	}

	public static void ajouterScoreJoueur(int score) {
		if(joueurEnCour=='X')
			scoreJoueur1+=score;
		else
			scoreJoueur2+=score;
	}

	public static boolean partieTermin�() {
		if(nbManchesJou�>=nbManchesTotal)
			return true;
		else return false;
	}

	public static void afficherGagant() {
		if(scoreJoueur1>scoreJoueur2)
			System.out.println("Le joueur X a gagn� "+scoreJoueur1+" � "+scoreJoueur2);
		else if (scoreJoueur2>scoreJoueur1)
			System.out.println("Le joueur O a gagn� "+scoreJoueur2+" � "+scoreJoueur1);
		else
			System.out.println("C'est une �galit� parfaite");

	}

	public static void d�marerPartie() {
		do {
			saisirTailleTerrain();
		}while(tailleTerrainIncorrect());
		d�terminerNbManches();
		initialisationNbManchesJou�();
		initialisationScoreJoueurs();
		genererTerrain();
		genererTableauCombos();		
		do {
			auTourDe();
			afficherTerrain();
			do {
				r�cupererPlacementPionJoueur();
			}
			while(caseD�jaPriseOuHorsLimite());
			attribuerCase();
			ajouterScoreJoueur(combienJoueurAmarquerPoints());
			nbManchesJou�++;
		}
		while(!partieTermin�());
		afficherTerrain();
		afficherGagant();
	}



}
