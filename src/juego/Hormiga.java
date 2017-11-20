package juego;


import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import entorno.*;

public class Hormiga{
	int ataque,vida;
	float x,y,destinox,destinoy,pendiente,ordenada,velocidad,velocidadanterior;
	boolean infeccion,EnMovimiento,select,Activada;
	double direccion,direccionr;
	private Image Hormi2=Herramientas.cargarImagen("Hormiga1.png");
	private Image Hormi2Infectada=Herramientas.cargarImagen("Hormiga1T.png");
	private Image Hormi1=Herramientas.cargarImagen("Hormiga2.png");
	private Image Hormi1Infectada=Herramientas.cargarImagen("Hormiga2T.png");
	Jugador jugador;
	Hormiguero Hormiguero;
	Point Destino;
	
	
	
	
	public Hormiga(Jugador jugador,Hormiguero Hormiguero){
		this.jugador=jugador;
		this.Hormiguero=Hormiguero;
		this.x=Hormiguero.x;
		this.y=Hormiguero.y;
		this.Destino=new Point((int)this.x,(int)this.y);
		this.EnMovimiento=false;
		this.select=false;
		this.Activada=false;
		this.pendiente=0;
		this.ordenada=0;
		if (this.jugador==Juego.Jugador1){
		this.direccionr=Herramientas.radianes(270);} //Direccion inical
		
		else if (this.jugador==Juego.Jugador2){
		this.direccionr=Herramientas.radianes(90);}//Direccion inicial
		this.vida=1;
		this.infeccion=false;
		Random ran = new Random();
		this.velocidad=(float)(ran.nextFloat()+0.4);
		this.ataque=1;
		}
		
	
	public void refrescaHormiga(Entorno entorno){
		//seleccion y Ataque

		
		
		
		if ((this.jugador.Ataque==true)&(this.Hormiguero.select)){this.Activada=true;}// Si el jugador ataca, se desbloquea el segundo select
		
		if((this.select)&(this.Activada)){ //Si la hormiga esta en un hormi select y ademas el jugador ataca
			
			if(this.Hormiguero.getObjetivo()!=null){ //La Hormiga recibe un objetivo
				/*Hormiguero hf=new Hormiguero(this.jugador,(int)this.x,(int)this.y);
				if(!Juego.Hormiselect(hf,this.Hormiguero.getObjetivo(),10)){*/
				
					this.setDestino(this.Hormiguero.getObjetivo());
			}}
		
		
		

		//Dibujo
		if((this.EnMovimiento)||(!this.enHormiguero())){ //SI se esta moviendo la dibuja
			this.select=true;
			if(this.infeccion){
				if (this.jugador==Juego.Jugador1){
					entorno.dibujarImagen(Hormi1Infectada, this.x, this.y, this.direccionr,0.5);}
				else if (this.jugador==Juego.Jugador2){
					entorno.dibujarImagen(Hormi2Infectada, this.x, this.y, this.direccionr,0.5);
					}}
		
			else if(!this.infeccion){
				if (this.jugador==Juego.Jugador1){
					entorno.dibujarImagen(Hormi1, this.x, this.y, this.direccionr,0.5);}
					else if (this.jugador==Juego.Jugador2){
						entorno.dibujarImagen(Hormi2, this.x, this.y, this.direccionr,0.5);}}}
		
		else{this.Activada=false;} //Sino la "desactiva" asi no se mueven solas las nuevas hormis que se generan
		

		}
	
	
	public void pesteTeemo(){
		Teemo[] timos=Juego.Gaia.getTeemos();
	
		for(int z=0;z<timos.length;z++){
			if (timos[z]!=null){
			int cat1=(int) Math.abs(timos[z].x-this.x);
			int cat2=(int) Math.abs(timos[z].y-this.y);
		double Distancia=Math.sqrt(((cat1)*(cat1))+((cat2)*(cat2)));
		if ((Distancia<=timos[z].diametro*2)){
			if (!this.infeccion){
			this.infeccion=true;
			this.velocidad/=2;
			return;}
			if(this.infeccion){return;}}}}
		if(this.infeccion){this.infeccion=false;this.velocidad*=2;}
		}
	
	
	public void mueveHormiga(){ 

		
		if ((Math.abs(this.x-this.Destino.x)>1)||(Math.abs(this.y-this.Destino.y)>1)){
			if ((!this.EnMovimiento)&(enHormiguero())){
				if(Math.abs(Juego.contTicks-Juego.UltimoSpawn)>10){
					Juego.UltimoSpawn=Juego.contTicks;
					Herramientas.play("SpawnHormiga.wav");}
			}
			this.EnMovimiento=true;
			
			float catx=(this.Destino.x-this.x);
			float caty=(this.Destino.y-this.y);
			float h=(float)Math.sqrt(catx*catx+caty*caty);
			this.x+=(catx*this.velocidad)/h;
			this.y+=(caty*this.velocidad)/h;
			
			
			if ((catx>=0)&(caty>=0)){
				this.direccionr=Math.asin(caty/h)+Math.toRadians(90);
			}
			
			if ((catx<0)&(caty>0)){
				this.direccionr=Math.acos(caty/h)+Math.toRadians(180);
			}
			
			if ((catx<0)&(caty<0)){
				this.direccionr=Math.acos(caty/h)+Math.toRadians(180);
			}
			
			if ((catx>0)&(caty<0)){
				this.direccionr=Math.asin(caty/h)+Math.toRadians(90);
			}
				
			
			
		}
		else{
			this.EnMovimiento=false;
		}
		
		this.pesteTeemo();// revisa que no este en una mancha
		
	}
	
	
	public boolean enHormiguero(){ //Si esta en el hormiguero
		if(Juego.Hormiselect(this.Hormiguero,new Point((int)this.x,(int)this.y),0)){
			return true;
		}
		return false;
	}
			
	public Point getDestino() {
		return new Point((int)this.destinox,(int)this.destinoy);
	}
	public void setDestino(Point Destino1) {
		this.Destino=Destino1;
	}	
	public void MatarHormiga(){

		
	
		for(int x=0;x<this.Hormiguero.Hormigas.length;x++){
			
			if (this==this.Hormiguero.Hormigas[x]){
		
				this.Hormiguero.Hormigas[x]=null;
				return;
			}
	}
			}
	

}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	



			
			
			
			
			
			
			
			
			
			
			
			
		
	


	
	

