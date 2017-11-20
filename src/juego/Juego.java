package juego;


import java.util.Arrays;
import java.util.Random;

import javax.sound.sampled.Clip;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;


import entorno.*;
//@SuppressWarnings("unused")
public class Juego extends InterfaceJuego
{
	//RESOLUCION
	public static Entorno entorno;
	public static Entorno lol;
	public static Point RES=new Point(1920,1080);
	//VARS auxiliares
	private static int xp1=0,yp1=0; // importante guarda posicion del mouse si lo sacas de pantalla
	static int xp2=Jugador.Horm1p2.x;
	static int yp2=Jugador.Horm1p2.y;
	static int MaxHormisNeutrales=11;
	static int CantHormisNeutrales;
	static int tiempoDeCarga=200; //1 sec
	private static int x=0;
	private static int y=0;

	static int contTicks,contBug,AnteriorClick,AnteriorEnter,UltimaSobreP,UltimoSpawn,UltimaMuerte,UltimoAtaque,UltimaTransfer;
	int cTeemo;
	static boolean GameOver,PlayMusica,Senemigo,PerdioJugador1,PerdioJugador2,FstRun,Reiniciar,HD;
	static boolean EGM;
	static boolean CursorJ2Ataque=false;
	

	//CURSOR
	Toolkit tk = Toolkit.getDefaultToolkit();//LAScosas que hay que hacer para poner UN cursor
	Image FlechaP2=Herramientas.cargarImagen("FlechaP2.png");
	Image EspadaP2=Herramientas.cargarImagen("EspadaP2.png");
	Image Titulo=Herramientas.cargarImagen("Titulo.jpg");
	Image Parche=Herramientas.cargarImagen("Parche.jpg");
	Image FlechaP1=Herramientas.cargarImagen("FlechaP1.png");
	Image EspadaP1=Herramientas.cargarImagen("EspadaP1.png");
	Image JugarIluminado=Herramientas.cargarImagen("Jugar.png");
	Image Jugar=Herramientas.cargarImagen("JugarFeo.png");
	Image Resumir=Herramientas.cargarImagen("Resumir.png");
	Image FondoGrande=Herramientas.cargarImagen("fondo.jpg");
	Image FondoHD=Herramientas.cargarImagen("FondoBajaRes.jpg");

	
	Cursor Ataque = tk.createCustomCursor(EspadaP1, new Point(x,y), "trans");
	Cursor Normal = tk.createCustomCursor(FlechaP1, new Point(x,y), "trans");

	Clip Musica=Herramientas.cargarSonido("Funky Ants.wav");
	Clip MuerteHormiga=Herramientas.cargarSonido("HormigaMuerta.wav");
	Clip LvlUp=Herramientas.cargarSonido("LvlUp.wav");
	Clip Conquista=Herramientas.cargarSonido("Conquistado.wav");
	Clip SpawnHormiga=Herramientas.cargarSonido("SpawnHormiga.wav");
	Clip AliadoSeleccionado=Herramientas.cargarSonido("AliadoSeleccionado.wav");
	Clip Teemo=Herramientas.cargarSonido("Teemo.wav");
	Clip Tuduu=Herramientas.cargarSonido("Ataque.wav");
	Clip Transfer=Herramientas.cargarSonido("Transferir.wav");
	//Jugadores , el nombre del jugador es importante no cambiarlo
	static Jugador Jugador1=new Jugador("Jugador1");
	static Jugador Jugador2=new Jugador("Jugador2");
	static Jugador Gaia=new Jugador();
	
	
	
	Color blanco=new Color(255,255,255);
	public static Color ROJO=new Color(255,0,0);
	public static Color VERDE=new Color(0,255,0);
	public static Color NEGRO=new Color(0,0,0);
	public static Color BLANCO=new Color(255,255,255);
	public static Color AMARILLO=new Color(255,255 ,0);
	public static Jugador[] Jugadores={Gaia,Jugador1,Jugador2};
	
	
	Juego()
	{
		// Inicializa el objeto entorno, pero aun no lo inicia.
		entorno = new Entorno(this, "Age of AntHills 1.0", RES.x, RES.y);

		HD=false;
		FstRun=true;
		Reiniciar=false;
		PerdioJugador1=false;
		PerdioJugador2=false;
		GameOver=true;
		contTicks=0;
		contBug=0;
		cTeemo=0;
		EGM=false;
		Gaia.setTeemos(new Teemo[2]);
		entorno.setCursor(Normal);// Setea Cursor tipo flecha
		if((RES.x<=1024)&(RES.y<=768)){HD=true;}
		
		Jugador1.Hormigueros[0].GenerarHormiga();
		Jugador2.Hormigueros[0].GenerarHormiga();

		entorno.setIconImage(FlechaP2);
		
		System.out.print("Generando Hormigueros");
		HormigueroGenerator();//genera hormigueros de gaia
		entorno.iniciar();
		System.out.println("\nEntorno Iniciado");
		Herramientas.loop("Funky Ants.wav");
		

		/* Es fundamental que recién al final del constructor de la clase Juego se 
		 * inicie el objeto entorno de la siguiente manera.
		 * Durante el juego, el método tick() será ejecutado en cada instante y 
		 * por lo tanto es el método más importante de esta clase. Aquí se debe 
		 * actualizar el estado interno del juego para simular el paso del tiempo 
		 * (ver el enunciado del TP para mayor detalle).*/
}
		
	public void tick(){ 

			
		Controles();
		juego();
		JugadorManager(Jugadores);
		
		}
	

	public void Controles(){
	    //Controles Jug 1
		
		if(entorno.estaPresionada(entorno.TECLA_ENTER)){
			if(GameOver){
				
				if(FstRun){Herramientas.play("LvlUp.wav");}
				
				if(PerdioJugador1||PerdioJugador2){
					Herramientas.play("LvlUp.wav");
					Reiniciar=true;}
				
				else{
				
				GameOver=false;
					FstRun=false;}
			
			}
				
			else{GameOver=true;}} 
		
		if(entorno.estaPresionada(entorno.TECLA_CTRL)){  //DEBUGGING
			
			if(PerdioJugador1||PerdioJugador2){
				Reiniciar=true;}
		}
		
		if(entorno.estaPresionada(entorno.TECLA_FIN)){//DEBUGG
			GameOver=true;
			PerdioJugador2=true;}
		
		
		if(!GameOver){
		
		Point Mouse=mouse(); //Carga ubicacion del mouse;
		Point Cursor=Teclado();//Carga ubicacion del teclado virtual
		
		Jugador2.Ataque=false;//Atacar se pone en false asi las hormis no se mueven solas
		Jugador1.Ataque=false;//Atacar se pone en false asi las hormis no se mueven solas
		
		
		if ((xp1!=Mouse.x)||(yp1!=Mouse.y)){//SOLO SI moviste el mouse(si es diferente a la ultima posicion registrada)
			
			CtrlJ1(Mouse);}//Revisa si el mouse selecciono un hormi
		
		

		CtrlJ2(Cursor);
		
		
		
		if ((entorno.clickPresionado(entorno.CLICK_IZQUIERDO))&Jugador1.click!=true){
			Jugador1.click=true;
			
			ClickoEnter(Jugador1);}
		else if ((!entorno.clickPresionado(entorno.CLICK_IZQUIERDO)&Jugador1.click==true)){Jugador1.click=false;}
		
		if ((entorno.clickPresionado(entorno.CLICK_DERECHO))&Jugador1.clickD!=true){
			Jugador1.clickD=true;
			Jugador1.setObjetivo(
					Mouse);
			Jugador1.Ataque=true;}
		else if ((!entorno.clickPresionado(entorno.CLICK_DERECHO)&Jugador1.clickD==true)){Jugador1.clickD=false;}
		
		
		
		if ((entorno.estaPresionada(entorno.TECLA_ALT))&Jugador2.click!=true){
			Jugador2.click=true;
			
			ClickoEnter(Jugador2);
			
		}
		else if ((!entorno.estaPresionada(entorno.TECLA_ALT )&Jugador2.click==true)){Jugador2.click=false;}
		
		if ((entorno.estaPresionada(entorno.TECLA_ESPACIO))&Jugador2.clickD!=true){
			Jugador2.clickD=true;
			Jugador2.setObjetivo(new Point(xp2,yp2));
			Jugador2.Ataque=true;}
		else if ((!entorno.estaPresionada(entorno.TECLA_ESPACIO)&Jugador2.clickD==true)){Jugador2.clickD=false;}
		

		//Controles Jug 2
		//mueve el cursor al hormi mas cercano (150 pixeles) y lo selecciona

		
		xp1=Mouse.x;//Actualiza ubicacion del mouse en variable aux
		yp1=Mouse.y;
		
		}}
	
	public Point Teclado(){
		int Desplazamiento=150;
		Point Cursor=new Point(xp2,yp2); //
		
		if ((!entorno.estaPresionada(entorno.TECLA_ARRIBA)&Jugador2.Up)){Jugador2.Up=false;}
		if ((!entorno.estaPresionada(entorno.TECLA_ABAJO)&Jugador2.Down)){Jugador2.Down=false;}
		if ((!entorno.estaPresionada(entorno.TECLA_IZQUIERDA)&Jugador2.Left)){Jugador2.Left=false;}
		if ((!entorno.estaPresionada(entorno.TECLA_DERECHA)&Jugador2.Right)){Jugador2.Right=false;}
		
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)&!Jugador2.Up){
			Cursor.y-=Desplazamiento;
			Jugador2.Up=true;}
		
		if (entorno.estaPresionada(entorno.TECLA_ABAJO)&!Jugador2.Down){
			Cursor.y+=Desplazamiento;
			Jugador2.Down=true;}
		
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)&!Jugador2.Left){
			Cursor.x-=Desplazamiento;
			Jugador2.Left=true;}
		
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)&!Jugador2.Right){
			Cursor.x+=Desplazamiento;
			Jugador2.Right=true;}
		
		if (Cursor.x>RES.x){Cursor.x=RES.x;}//SI SE PASA queda en el borde
		if (Cursor.x<0){Cursor.x=0+90;}
		if (Cursor.y>RES.y-30){Cursor.y=RES.y-40;}
		if (Cursor.y<0){Cursor.y=0;}

		
		return Cursor;}
	
	
	
	
	public void CtrlJ2(Point Teclado){
		
		if(Jugadores!=null){
		for (int c=0;c<Jugadores.length;c++){
			if((Jugadores[c]!=null)&(Jugadores[c].Hormigueros!=null)){
			for (int i=0;i<Jugadores[c].Hormigueros.length;i++){
				if (Jugadores[c].Hormigueros[i]!=null){       //Si lo pones en 200 explota todo
					if (Hormiselect(Jugadores[c].Hormigueros[i],Teclado(),90-CantHormisNeutrales)&!Jugadores[c].Hormigueros[i].selectJugador2){

							Jugadores[c].Hormigueros[i].selectJugador2=true;
							Teclado.x=Jugadores[c].Hormigueros[i].x; //Mueve el cursor virtual a ese hormi
							Teclado.y=Jugadores[c].Hormigueros[i].y;
							}
					
					else if ((Jugadores[c].Hormigueros[i].selectJugador2)&(!Hormiselect(Jugadores[c].Hormigueros[i],Teclado(),100-CantHormisNeutrales))){//si le sacaste el Teclado de encima
						Jugadores[c].Hormigueros[i].selectJugador2=false;}
					}}}}}
		
		CursorJ2Ataque=false;			
		
		if(Gaia.Hormigueros!=null){	
		for(int x=0;x<Gaia.Hormigueros.length;x++){if(Gaia.Hormigueros[x]!=null){if(Hormiselect(Gaia.Hormigueros[x],Teclado,0)){CursorJ2Ataque=true;}}}}
		if(Jugador1.Hormigueros!=null){	
		for(int x=0;x<Jugador1.Hormigueros.length;x++){if(Jugador1.Hormigueros[x]!=null){if(Hormiselect(Jugador1.Hormigueros[x],Teclado,0)){CursorJ2Ataque=true;}}}}
		
			xp2=Teclado.x;
			yp2=Teclado.y;
	}
		
		
	
	
	
	public void CtrlJ1(Point Mouse){
		if(Jugadores!=null){
		for (int c=0;c<Jugadores.length;c++){
			if((Jugadores[c]!=null)&(Jugadores[c].Hormigueros!=null)){
			for (int i=0;i<Jugadores[c].Hormigueros.length;i++){
				
				
				if (Jugadores[c].Hormigueros[i]!=null){
				
		
					if (!Hormiselect(Jugadores[c].Hormigueros[i],Mouse,0)&Jugadores[c].Hormigueros[i].selectJugador1){//si le sacaste el mouse de encima
							Jugadores[c].Hormigueros[i].selectJugador1=false;
							entorno.setCursor(Normal);
						}
					else if (Hormiselect(Jugadores[c].Hormigueros[i],Mouse,0)&!Jugadores[c].Hormigueros[i].selectJugador1){
						Jugadores[c].Hormigueros[i].selectJugador1=true;
						if (Jugadores[c].Hormigueros[i].jugador!=Jugador1){
							entorno.setCursor(Ataque);}}}
							}	
				}
				}}}
		
	
	
	
	
	
	public void ClickoEnter(Jugador esteJugador){//TODO CAMBIAR EL SELECT JUGADOR 1 Y 2 POR ALGO MAS SIMPLE, QUE EL HORMI GUARDE LA VARIABLE JUGADOR Y LISTO
		//Si el jugador hace click				//TODO smplificar esto
		boolean hizoClickenAlgo=false;
		
		for (int x=0;x<Jugadores.length;x++){
			if (Jugadores[x].Hormigueros!=null){

				for(int b=0;b<Jugadores[x].Hormigueros.length;b++){  
					Hormiguero esteHormi=Jugadores[x].Hormigueros[b];
					
					if (esteHormi!=null){
						if (esteJugador.Select(esteHormi)){// si lo seleccionaste
							hizoClickenAlgo=true;
							if (esteHormi.jugador==esteJugador){ //si es tuyo
								
								if(!esteHormi.select){
									esteHormi.select=true;
									
									Herramientas.play("AliadoSeleccionado.wav");
									esteJugador.Objetivo=new Point(esteHormi.x,esteHormi.y);
									esteHormi.CantidadDeTropasS=3;
									esteHormi.ReclutaPorcentaje();			
									//
								}
								else{
								
								esteHormi.CantidadDeTropasS-=1;
								esteHormi.ReclutaPorcentaje();}
								System.out.println(esteHormi.CantidadDeTropasS);
								
								
								esteJugador.setObjetivo(null);	
							}
								
							else{				//Si es enemigo
								
								
					
								
									esteJugador.Ataque=true;
									esteJugador.Objetivo=new Point(esteHormi.x,esteHormi.y); //Setea OBEJETIVO
								
								if (esteJugador==Jugador1){
									esteHormi.selectJugador1=true;}
								else if (esteJugador==Jugador2){
									esteHormi.selectJugador2=true;} //OBJETIVO ENEMIGO SELECCIONADO
								
									}
							
		
					
							
						
						}}}}}
		if(!hizoClickenAlgo){ //Permite deseleccionar clickeando fuera de un hormi
			if (esteJugador.Hormigueros!=null){
				for(int k=0;k<esteJugador.Hormigueros.length;k++){
					if(esteJugador.Hormigueros[k]!=null){esteJugador.Hormigueros[k].select=false;}}}}
		
		else if(hizoClickenAlgo){
			if(esteJugador==Jugador1){		//PERMITE SELECCIONAR TODOS LOS HORMIS
				
				if(Math.abs(contTicks-AnteriorClick)<=20){
					if (esteJugador.Hormigueros!=null){
					for(int k=0;k<esteJugador.Hormigueros.length;k++){
						if(esteJugador.Hormigueros[k]!=null){esteJugador.Hormigueros[k].select=true;}}}
				}
				else{
					AnteriorClick = contTicks;}
			
			}
			
			
			else if(esteJugador==Jugador2){
				if(Math.abs(contTicks-AnteriorEnter)<=35){
					if (esteJugador.Hormigueros!=null){
					for(int k=0;k<esteJugador.Hormigueros.length;k++){
						if(esteJugador.Hormigueros[k]!=null){esteJugador.Hormigueros[k].select=true;}}}
				}
				else{
					AnteriorEnter = contTicks;}
				}
		
		
		
		}
		
		
		}
					
	
	 
	public static Point mouse(){  //Recibe ubicacion del mouse, no deja que se vaya de la pantalla
		Point mouse=entorno.getMousePosition();
		
		if (mouse==null){mouse=new Point(x,y);}
	
		x=mouse.x;
		y=mouse.y;
		if (x>RES.x){x=RES.x;}
		if (y>RES.y){y=RES.y;}

		return mouse;}
	
	
	
	
	
	
	public void juego() {
		
		
		//carga.refrescaTeemo(entorno);
		
		if(HD){entorno.dibujarImagen(Titulo,RES.x/2+30,RES.y/2+80,0,1); }  //FONDO MENU
		else{entorno.dibujarImagen(Titulo,RES.x/2,RES.y/2,0,1);}
		
		entorno.dibujarImagen(Parche,RES.x+25,RES.y-75,0,1);
		if(!GameOver){
			if(HD){entorno.dibujarImagen(FondoHD,RES.x/2,RES.y/2,0,1.05);}//Fondo	
			else{entorno.dibujarImagen(FondoGrande,RES.x/2,RES.y/2,0,1);}
			
			//if(!PlayMusica){Herramientas.loop("Funky Ants.wav");PlayMusica=true;}	
		
		
		contTicks=contTicks+1;
		if (contTicks<tiempoDeCarga){
			
			
			
			new Teemo().refrescaTeemo(entorno); //Carga el gif de los teemos

			entorno.cambiarFont("Ticks", 40, blanco);
			
			if(HD){entorno.dibujarImagen(Titulo,RES.x/2+30,RES.y/2+80,0,1); }  //FONDO MENU
			else{entorno.dibujarImagen(Titulo,RES.x/2,RES.y/2,0,1);}
			
			entorno.dibujarImagen(JugarIluminado,(RES.x/2)-15, (RES.y/2)+(((RES.y/2)*300)/520),0, 1);
		
			entorno.dibujarRectangulo(20+contTicks*9.5, RES.y-((RES.y*45)/1080),contTicks*19,15, 0, VERDE);
			
			
			entorno.dibujarImagen(Parche,RES.x+25,RES.y-75,0,1);
			
		}

		//TICKS

		//
		Teemo[] Timos=Gaia.getTeemos(); //Manchas, maneja la aparicion
		
		if(Timos==null){Timos=new Teemo[2];}
		
		
		if ((contTicks%3000)==0){

			for(int t=0;t<Timos.length;t++){
				if(Timos[t]==null){
					Herramientas.play("Teemo.wav");
					Timos[t]=new Teemo();
					cTeemo+=1;
					if (((cTeemo)>1)||(cTeemo<0)){cTeemo=0;}
					break;}}}
		
		else if ((contTicks%3000)==1500 && (contTicks != 4500)){
			
				if(Timos[cTeemo]!=null){
					Timos[cTeemo]=null;
					}}}
		
		
	
		else {Menu();}
		}

	
	public void Menu(){
		if(FstRun){entorno.dibujarImagen(Jugar,(RES.x/2)-15, (RES.y/2)+(((RES.y/2)*300)/520),0, 1);} //BOTON JUGAR	
		

		if((!PerdioJugador1)&(!PerdioJugador2)&(!FstRun)){
			
			entorno.dibujarImagen(Resumir,(RES.x/2)-15, (RES.y/2)+(((RES.y/2)*300)/520),0, 1);}
		
		
		else if(((PerdioJugador1)||(PerdioJugador2))&(!FstRun)){
					entorno.dibujarImagen(Jugar,(RES.x/2)-15, (RES.y/2)+(((RES.y/2)*300)/520),0, 1);
					
					entorno.cambiarFont("Ticks", 100,ROJO);
					entorno.escribirTexto("GAME OVER", (RES.x/2)-325, RES.y/2+100);
					if(Reiniciar){
						Reiniciar=false;
						GameOver=false;
						PerdioJugador2=false;
						PerdioJugador1=false;
						Jugador1.setHormigueros(null);
						Jugador2.setHormigueros(null);
						Gaia.setHormigueros(null);
						System.out.println("ENTER");
						Gaia.setTeemos(new Teemo[2]);
						HormigueroGenerator();
						
						Hormiguero Hormi1=new Hormiguero(Jugador1,Jugador.Horm1p1.x,Jugador.Horm1p1.y);
						Hormi1.select=true;
						Hormiguero[] a=new Hormiguero[Juego.MaxHormisNeutrales+2];
					    a[0]=Hormi1;
						Jugador1.setHormigueros(a);
						
						Hormiguero Hormi2=new Hormiguero(Jugador2,Jugador.Horm1p2.x,Jugador.Horm1p2.y);
						Hormi2.select=true;
						Hormiguero[] b=new Hormiguero[Juego.MaxHormisNeutrales+2];
					    b[0]=Hormi2;
						Jugador2.setHormigueros(b);
						
						Jugador1.Hormigueros[0].GenerarHormiga();
						Jugador2.Hormigueros[0].GenerarHormiga();
						contTicks=0;
						contBug=0;
						cTeemo=0;
						entorno.iniciar();}
				
				
			

			

		
		}}

		
		

	
	
	
	public void JugadorManager(Jugador[] Jugador ){
		
		if ((contTicks>=tiempoDeCarga)&(!GameOver)){
		
		for(int x=0;x<Jugador.length;x++){
			if (Jugador[x].Hormigueros!=null){	
				HormigueroManager(Jugador[x].Hormigueros);}}

		
		for(int x=0;x<Jugador.length;x++){
			if ((Jugador[x].Teemos!=null)&(Jugador[x]==Gaia)){
				TeemoManager(Jugador[x].Teemos);}}
		
		for(int x=0;x<Jugador.length;x++){
			if((Jugador[x].Hormigueros!=null)&&(!estaVacio(Jugador[x].Hormigueros,null))){
				for(int z=0;z<Jugador[x].Hormigueros.length;z++){
					
					if ((Jugador[x].Hormigueros[z]!=null)){
						if(Jugador[x].Hormigueros[z].Hormigas!=null){
						HormigaManager(Jugador[x].Hormigueros[z].Hormigas);}}}
				}
			else if (Jugador[x]!=Gaia){
				if(Jugador[x]==Jugador1){
					PerdioJugador1=true;}
				
				else if(Jugador[x]==Jugador2){
					PerdioJugador2=true;}
				
				GameOver=true;
			}
		
			
		}}
		
		
		
		if (!CursorJ2Ataque){
		entorno.dibujarImagen(FlechaP2, xp2-60, yp2+10, 0,1.3);}
		else {entorno.dibujarImagen(EspadaP2, xp2-60, yp2+10,0,1.2);} //DIBUJA EL CURSOR DEL P2, NO QUEDA OTRA QUE CARGARLO ACA X SUPERPOSICION
		
		}
	
	
	
	public void HormigueroManager(Hormiguero[] Hormigueros){
			
		for(int z=0;z<Hormigueros.length;z++){
			if (Hormigueros[z]!=null){
			
			Hormigueros[z].refrescaHormigero(entorno);
			if (Hormigueros[z].select){
				if (Hormigueros[z].jugador.getObjetivo()!=null){
				Hormigueros[z].Objetivo=Hormigueros[z].jugador.getObjetivo();}}
			}
			 
		}}
	
	public void TeemoManager(Teemo[] Teemos){
		for (int x=0;x<Teemos.length;x++){
			if (Teemos[x]!=null){
			Teemos[x].refrescaTeemo(entorno);}
			}}
	
	public void HormigaManager(Hormiga[] Hormigas){
		if (Hormigas!=null){
		for (int x=0;x<Hormigas.length;x++){
			if (Hormigas[x]!=null){
				
				if(!Hormigas[x].EnMovimiento){
					Point h=new Point((int)Hormigas[x].x,(int)Hormigas[x].y);
					for(int z=0;z<Jugadores.length;z++){
						if (Hormigas[x]!=null){
							if (Jugadores[z].Hormigueros!=null){
								for(int c=0;c<Jugadores[z].Hormigueros.length;c++){
								
									if (Jugadores[z].Hormigueros[c]!=null){
									
										if (Hormiselect(Jugadores[z].Hormigueros[c],h,15)){ //si la hormiga esta sobre el Hormi
											if (Jugadores[z]!=Hormigas[x].jugador){
												Atacar(Hormigas[x],Jugadores[z].Hormigueros[c]);//LA hormiga se muere
											}
											else if(Jugadores[z]==Hormigas[x].jugador){
												if (Hormigas[x].Hormiguero!=Jugadores[z].Hormigueros[c])
												Jugadores[z].Hormigueros[c].TransferirHormigas(Hormigas[x]);}
											break;}
							
							}}}}}
					
				}
					
					
					

				
				
				if(Hormigas[x]!=null){
					
				if ((Hormigas[x].Hormiguero.getObjetivo()!=null)&(!GameOver)){

					
					Hormigas[x].mueveHormiga();}
					
				
				Hormigas[x].refrescaHormiga(entorno);}}}}

		}
	

	
	//////////////////////////////////////////////////////////////
	
	 
	

	

	
	
	public void Atacar(Hormiga Hormiga, Hormiguero Objetivo){
	Random ran=new Random();
		
	if (estaVacio(null,Objetivo.Hormigas)){ //si esta vacio
		
		
		System.out.print("Hormiga Ataca:");
		Hormiga.EnMovimiento=false;
		
		Objetivo.Conquistar(Hormiga.jugador);
		Objetivo.TransferirHormigas(Hormiga);
		
		System.out.println(" Hormiguero Conquistado!");
	}
	else {
		Hormiga.MatarHormiga();
		if(Math.abs(contTicks-UltimaMuerte)>60){ 
			UltimaMuerte=contTicks;
			Herramientas.play("HormigaMuerta.wav");}
		
		if((Math.abs(contTicks-UltimoAtaque)>1000)&(Objetivo.jugador!=Gaia)){
			UltimoAtaque=contTicks;
			Herramientas.play("Ataque.wav");}
		
		if((ran.nextBoolean())||(Objetivo.lvl==1)||contTicks%2==0){ //LAS QUE DEFIENDEN TIENEN CIERTA CHANCE DE NO MORIR
		for(int x=0;x<Objetivo.Hormigas.length;x++){  //1 A 1
			if(Objetivo.Hormigas[x]!=null){
				Objetivo.Hormigas[x].MatarHormiga();break;}}}
	}
		
		
	}
	
		

		
	
	
	//Revisa si un arreglo esta vacio,sirve para revisar de hormigas y de hormigueros, se setea uno en null o el otro
	public boolean estaVacio(Hormiguero[] Hormig, Hormiga[] atomica){
		if ((Hormig==null)&(atomica!=null)){
			
			for (int x=0;x<atomica.length;x++){if (atomica[x]!=null){return false;}}}
		
		else if((atomica==null)&(Hormig!=null)){for (int x=0;x<Hormig.length;x++){if (Hormig[x]!=null){return false;}}}
	
		return true;
	}
	
	
	//Checkea si pasas el mouse por arriba del hormiguero(Interseccion Circulo-point)
	public static boolean Hormiselect (Hormiguero Hormi, Point mouse,int Sensitividad){ // Hipotenusa calcula distancia
		int cat1=Math.abs(Hormi.x-mouse.x);
		int cat2=Math.abs(Hormi.y-mouse.y);		
		double Distancia=Math.sqrt(((cat1)*(cat1))+((cat2)*(cat2)));
		if (Distancia<=Hormi.diametro+Sensitividad){return true;}
		return false;}

	//Checkea interseccion Punto-rectangulo  ---Para menu OPCIONES
	public static boolean IntersecPointRect(Point puntoB, Rectangle A){
		Point PAx1=new Point (A.x,A.y);
		Point PAy2=new Point ((A.x+A.width),(A.y+A.height));
		
		if ((puntoB.x>PAx1.x)&(puntoB.x<PAy2.x)&(puntoB.y>PAx1.y)&(puntoB.y<PAy2.y)){
			return true;}
		return false;}

	
	public static void HormigueroGenerator(){
		//8 Hormigueros para GAIA
		//TODO no es muy efectivo, habria que armar una grilla, consume mucho que sea random
		
		Random ran = new Random();
		                   //Cant de Hormigueros Neutrales entre 3 y 13
		Hormiguero[] hormis=new Hormiguero[ran.nextInt(MaxHormisNeutrales-1)+3];//reserva 2 para lso de usuario	
		//Hormiguero[] hormis=new Hormiguero[13];
		hormis[0]=new Hormiguero(Gaia,Jugador.Horm1p1.x,Jugador.Horm1p1.y);
		hormis[1]=new Hormiguero(Gaia,Jugador.Horm1p2.x,Jugador.Horm1p1.y);
		
		CantHormisNeutrales=hormis.length;
		for(int x=2;x<hormis.length;x++){
			System.out.print(":");
			if (hormis[x]==null){
				Hormiguero nuevohormi=new Hormiguero();
				while(HormiNear(hormis,new Point(nuevohormi.x,nuevohormi.y))){
					System.out.print(".");
					nuevohormi=new Hormiguero();
					if ((contBug>100)){System.out.print("\nCiclo interrumpido\n");nuevohormi=null;break;}}//Soluciona bug que se queda hasta la eternidad	
				contBug=0;
				hormis[x]=nuevohormi;}

		}
		Hormiguero[] nuevoarreglo = Arrays.copyOfRange(hormis, 2, CantHormisNeutrales+1);
		Gaia.setHormigueros(nuevoarreglo);
	}
	
	
	public static boolean HormiNear(Hormiguero[] Hormi,Point nuevoHormi){//IGual que Hormiselec pero por 2
		
		//11-------65 Regla de 3 inversa
		//CantHormisNeutrales-----distanciamin
		int distanciaMinima=(715/CantHormisNeutrales)+2*((int)Hormi[0].diametro-20);//ajusta proximidad entre hormis cercanos
		//OJO LA COMPLEJIDAd AUMENTA UNA LOCURA cuanto mas distancia
		for(int p=0;p<Hormi.length;p++){
			if(Hormi[p]!=null){
				if (Hormiselect(Hormi[p],nuevoHormi,distanciaMinima)){return true;}}}
		return false;}
	
	
	public static Point RandomN(double diametro){// Genera un random del T de la pantalla
		
		contBug+=1;
		if ((CantHormisNeutrales-2==1)&(contTicks<1)){return new Point(RES.x/2,RES.y/2);}
		
		Random ran = new Random();
		
		int Franja=1200/CantHormisNeutrales-2;
		int ddiametro=(int)diametro;
		
		int maximoy=Juego.RES.y-(ddiametro*2)-25;
		int minimoy=ddiametro*2+25;
		
		int maximox=Juego.RES.x-ddiametro*2-Franja;
		int minimox=(ddiametro*2+Franja);
		
		if ((CantHormisNeutrales-2==2)&(contTicks<1)){
			maximox=Juego.RES.x-(Juego.RES.x/3);
			minimox=Juego.RES.x/3;
			maximoy=Juego.RES.y-(Juego.RES.y/4);
			minimoy=Juego.RES.y/4;
		
		}
		
		 
		
		int x=ran.nextInt(maximox-minimox)+minimox;//evita los hormis iniciales
		int y=ran.nextInt(maximoy-minimoy)+minimoy;

		
		return new Point(x,y);}

	

	
	/*public static void main(String[] args)  //El launcher carga el juego
	{	
		Juego juego = new Juego(); 

	}*/}
