package juego;

import java.awt.Point;

public class Jugador {
	String player;
	boolean click,clickD,Up,Down,Left,Right,MasTropas,MenosTropas,Ataque;
	Hormiguero[] Hormigueros;
	Hormiguero HormigueroSelect;
	Teemo[] Teemos;
	Hormiga[] Hormigas;
	Point Objetivo; //Objetivo es un hormi.. en el EGM podria ser solo un point
	public static int Ros=1;
	
	
	public static Point Horm1p1=new Point(Juego.RES.x-((Juego.RES.x*45)/1000),Juego.RES.y-((Juego.RES.y*40)/600));
	public static Point Horm1p2=new Point(70,130);

	public Jugador(){
		this.player="Gaia";  //Si es Gaia 
		this.Hormigueros=null;
		this.Teemos=null;//para simplificar las manchas son objetos de gaia
	
	}
	//Si es un usuario
	public Jugador(String Jugador){

		this.player=Jugador;
		this.click=false;
		this.clickD=false;
		this.Hormigas=null;
		this.Objetivo=null;
		this.HormigueroSelect=null;
		this.Ataque=false;

		
		if (Jugador.equals("Jugador1")){
			Hormiguero Hormi1=new Hormiguero(this,Horm1p1.x,Horm1p1.y);
			Hormi1.select=true;
			Hormiguero[] a=new Hormiguero[Juego.MaxHormisNeutrales+2];
		    a[0]=Hormi1;
		    this.setHormigueros(a);
		}
		else if (Jugador.equals("Jugador2")){
			Hormiguero Hormi1=new Hormiguero(this,Horm1p2.x,Horm1p2.y);
			Hormi1.select=true;
			Hormiguero[] a=new Hormiguero[Juego.MaxHormisNeutrales+2];
		    a[0]=Hormi1;
			this.setHormigueros(a);;
			this.Up=false;  //MANEJO TECLADO
			this.Down=false;
			this.Left=false;
			this.Right=false;
			this.MasTropas=false;
			this.MenosTropas=false;
			this.Ataque=false;
		}
	
	}
	
	public boolean Select(Hormiguero hormi){


		
		if (this.player=="Jugador1"){
			if (Juego.Hormiselect(hormi,Juego.mouse(),50)){
				return true;}}
		else if (this.player=="Jugador2"){
			if (Juego.Hormiselect(hormi,new Point(Juego.xp2,Juego.yp2),0)){
			return true;}}
		return false;}
		
		
	public void setHormigas(Hormiga[] hormigas,Hormiguero Hormi) {
		if (Juego.EGM==true){this.Hormigas=hormigas;}
		for(int x=0;x<this.Hormigueros.length;x++){
			if (this.Hormigueros[x]==Hormi){
				this.Hormigueros[x].setHormigas(hormigas);
				return;}}}
	
	public void AgregaNuevoHormi(Hormiguero Hormi){
		for(int x=0;x<this.Hormigueros.length;x++){
			if (this.Hormigueros[x]==null){
				this.Hormigueros[x]=Hormi;
				return;}}
		}
	
	
	
	
	public Teemo[] getTeemos() {
		return Teemos;
	}
	public void setTeemos(Teemo[] teemos) {
		Teemos = teemos;
	}
	public Hormiguero[] getHormigueros() {
		return Hormigueros;
	}
	public void setHormigueros(Hormiguero[] hormiguero) {
		if (hormiguero==null){
			for(int x=0;x<this.Hormigueros.length;x++){
				this.Hormigueros[x]=null;
			}
			
		}
		
		Hormigueros = hormiguero;
	}
	
	public Point getObjetivo() {
		return Objetivo;
	}
	public void setObjetivo(Point hormiguero) {
		Objetivo = hormiguero;
	}

	
	
	
	
	

}
