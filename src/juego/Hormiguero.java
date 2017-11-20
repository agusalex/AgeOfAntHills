package juego;
import entorno.*;
import java.awt.Image;
import java.awt.Point;


public class Hormiguero{
	int x,y,lvl,CantidadDeTropasS,ContHormig;
	public static int[] lVLcant={0,50,110,310,510};
	
	double diametro,escala;//dim es el valor que hay que ajustar para agrandar la imagen
	boolean select,selectJugador2,selectJugador1,peste,infeccion,LimiteP;
	Hormiga[] Hormigas;
	Point Objetivo,Point;
	Jugador jugador;
	
	
	
	Image Hormi=Herramientas.cargarImagen("Hormiguero.png");
	Image HormiSelectP1=Herramientas.cargarImagen("HormigueroSelectP1.png");
	Image HormiSelectP2=Herramientas.cargarImagen("HormigueroSelectP2.png");
	Image HormiSelectP1P2=Herramientas.cargarImagen("HormigueroSelectP1P2.png");
	Image ManchaT=Herramientas.cargarImagen("HormigueroT.png");
	Image FlagP2=Herramientas.cargarImagen("Flag1.png");
	Image FlagP1=Herramientas.cargarImagen("Flag2.png");
	Image FlagGaia=Herramientas.cargarImagen("Flag0.png");
	Image Lvl2=Herramientas.cargarImagen("lvl2.png");
	Image Lvl3=Herramientas.cargarImagen("lvl3.png");
	Image Lvl4=Herramientas.cargarImagen("lvl4.png");
	Image [] Lvl={null,null,Lvl2,Lvl3,Lvl4};
	
	public Hormiguero(){
		
		this.jugador=Juego.Gaia;//GAIA
		this.Hormigas=new Hormiga[lVLcant[1]];
		this.Objetivo=null;
		this.ContHormig=0;
		this.CantidadDeTropasS=3;
		this.select=false;
		this.peste=false;
		this.infeccion=false;
		this.selectJugador1=false;
		this.selectJugador1=false;
		this.diametro=40;
		this.escala=0.25;
		this.x=Juego.RandomN(diametro).x;
		this.y=Juego.RandomN(diametro).y;
		
		this.lvl=1;}
	
	
	public Hormiguero(Jugador Jugador,int x,int y){
		this.jugador=Jugador;
		this.selectJugador1=false;
		this.selectJugador2=false;
		this.Hormigas=new Hormiga[lVLcant[1]];
		this.CantidadDeTropasS=3;
		this.ContHormig=0;
		this.infeccion=false;
		this.LimiteP=false;
		this.select=false;
		this.diametro=45;//45
		this.escala=0.25;
		this.x=x;
		this.y=y;
		this.Point=new Point(x,y);
		this.lvl=1;}
	
	public void refrescaHormigero(Entorno entorno){
		//NIveles etc
		ContHormig++;
		this.pesteTeemo();
		
		int CantEfect=this.CantEfectiva(false); //CANT EFECTIVA ES LAS QUE ESTAN DENTRO
		int CantRel=this.CantEfectiva(true);  //CANT RELATIVA ES LAS QUE ESTAN FUERA+ADENTRO
		
		if (this.lvl<4){
			
			if(CantEfect>=lVLcant[this.lvl]-10){ //Sube de nivel segun la Cant de Hormigas
				this.LevelUp();}}
		
		
		
		else if (this.lvl>1){
		
			if(CantRel<lVLcant[this.lvl-1]-40){this.LevelDown();}}
			
		
		
		else if((lvl==4)&(this.jugador!=Juego.Gaia)){if ((CantRel>=lVLcant[4])&(!LimiteP)){ //LIMITE POBLACIONAL
			if(Math.abs(Juego.contTicks-Juego.UltimaSobreP)>200){
				Juego.UltimaSobreP=Juego.contTicks;
				
				Herramientas.play("SobrePoblacion.wav");}
				LimiteP=true;}
		
			else if((CantRel<lVLcant[4])&(LimiteP)){LimiteP=false;}}
		
		if ((ContHormig==500)/*&(this.jugador!=Juego.Gaia)*/){
			ContHormig=0;
			if((!this.infeccion)&(!this.peste)){ //Si no esta INFECTADO genera una (o mas hormigas) cada 500 ticks
			for(int y=0;y<this.lvl;y++){
				this.GenerarHormiga();}}}
		
		
		///DIBUJO
		
		
		
		if ((!this.selectJugador1)&(!this.selectJugador2)&(!this.select)){
				entorno.dibujarImagen(Hormi,this.x,this.y-30,0,escala);
				

		}//Si no esta seleccionado
		else if ((this.selectJugador1)&(this.selectJugador2)&(!this.select)){
				entorno.dibujarImagen(HormiSelectP1P2,this.x,this.y-30,0,escala);}
				
			
			
			
			else if ((this.selectJugador1)&(!this.selectJugador2)&(!this.select)){
			
			entorno.dibujarImagen(HormiSelectP1,this.x,this.y-30,0,escala);}
			
			else if ((this.selectJugador2)&(!this.selectJugador1)&(!this.select)){
				
				entorno.dibujarImagen(HormiSelectP2,this.x,this.y-30,0,escala);}
				
			else if (this.select){
			
			if (this.jugador==Juego.Jugador1){
				entorno.dibujarImagen(HormiSelectP1,this.x,this.y-30,0,escala);}
			else if (this.jugador==Juego.Jugador2){
				entorno.dibujarImagen(HormiSelectP2,this.x,this.y-30,0,escala);}}
			
			if (this.jugador==Juego.Jugador2){
			entorno.dibujarImagen(FlagP2,this.x,this.y-30,0,escala);}
			else if (this.jugador==Juego.Jugador1){
			entorno.dibujarImagen(FlagP1,this.x,this.y-30,0,escala);}
			else if (this.jugador==Juego.Gaia){
			entorno.dibujarImagen(FlagGaia,this.x,this.y-30,0,escala);}
			
			if ((this.infeccion)||(this.peste)){entorno.dibujarImagen(ManchaT,this.x,this.y-30,0,escala);}
			
			if(this.lvl>1){
				
				entorno.dibujarImagen(Lvl[this.lvl], this.x, this.y-20,0,escala-0.05);}
			
			//VIDA
			entorno.dibujarRectangulo(this.x, this.y-110, 6,((CantRel*100/(lVLcant[this.lvl]+CantRel*0.5))),Herramientas.radianes(90), Juego.VERDE);
			//LIMITE POBLACIONAL
			if(this.LimiteP){entorno.dibujarRectangulo(this.x, this.y-110, 6,(5+(CantRel*100/(lVLcant[this.lvl]+CantRel*0.5))),Herramientas.radianes(90), Juego.ROJO);}
			
			if((this.jugador!=Juego.Gaia)&(this.select)){
				int cant=0;
				for(int z=0; z<this.CantidadDeTropasS;z++){
					
					entorno.dibujarRectangulo(this.x+cant-20, this.y-100, 6,15,Herramientas.radianes(90), Juego.AMARILLO);
					cant+=20;
				}}
					
	
	
	}
	
	
	public void GenerarHormiga(){
		
		if(this.Hormigas!=null){
		for(int x=0;x<this.Hormigas.length;x++){
			if (this.Hormigas[x]==null){
				this.Hormigas[x]=new Hormiga(this.jugador,this);
				if (this.select){this.Hormigas[x].select=true;}return;
			}
	}
		}
	}
	
		
	public Point getObjetivo() {
		return Objetivo;
	}
	public void setObjetivo(Point hormiguero) {
		Objetivo = hormiguero;
	}

	public void TransferirHormigas(Hormiga Hormiga){
		Hormiga Backup=Hormiga;//hace backup
		Hormiga.MatarHormiga();//La borra del hormi actual
		Backup.Hormiguero=this;
		if (this.Hormigas==null){ //Si era de gaia
			this.Hormigas=new Hormiga[lVLcant[this.lvl]];  //Cambiar
		}
		for(int x=0;x<this.Hormigas.length;x++){
			if(this.Hormigas[x]==null){
				if(Math.abs(Juego.contTicks-Juego.UltimaTransfer)>70){
					Juego.UltimaTransfer=Juego.contTicks;
					Herramientas.play("Transferir.wav");}
				this.Hormigas[x]=Backup;
				
				
				return;}}
		this.LevelUp();
		}
	
	public int CantEfectiva(boolean Moviendose){ //Cantidad de Hormigas 2 modos(En movimiento, o dentro del hormiguero
		int CantEfectiva=0;
		
		if(Moviendose){
		for(int z=0;z<this.Hormigas.length;z++){
			if (this.Hormigas[z]!=null){CantEfectiva++;}}
		return CantEfectiva;}
		else{
			for(int z=0;z<this.Hormigas.length;z++){
				if ((this.Hormigas[z]!=null)){
					if((!this.Hormigas[z].EnMovimiento)&(this.Hormigas[z].enHormiguero())){CantEfectiva++;}}}}
	return CantEfectiva;}
	
	
	public void ReclutaPorcentaje(){
		
		if(this.CantidadDeTropasS<=0){this.CantidadDeTropasS=3;}
		float cantidadDeTropas=this.CantidadDeTropasS;
		
		if (this.Hormigas==null){return;}
		float CantEfec=this.CantEfectiva(false);
		int HormigasSeleccionadas=0;
		
		
		float Aseleccionar=((cantidadDeTropas/3)*CantEfec);
		
		for(int x=0;x<this.Hormigas.length;x++){
			if (this.Hormigas[x]!=null){
				if((HormigasSeleccionadas<=Aseleccionar)&(Aseleccionar!=0)){
					HormigasSeleccionadas++;
					this.Hormigas[x].select=true;
					}
				else{this.Hormigas[x].select=false;}}}
		
	}
	
	public void LevelUp(){
		
		System.out.println("Level Up!");
		
		if(this.lvl<4){
		Hormiga[] Backup=this.Hormigas;
		this.lvl+=1;
		Herramientas.play("LvlUp.wav");
		this.Hormigas=new Hormiga[lVLcant[this.lvl]];
		this.setHormigas(Backup);
		}}	
	
		
		public void LevelDown(){
		System.out.println("Level Down!");
		
		if (this.lvl>1){
		this.lvl-=1;
		Herramientas.play("Conquistado.wav");
		if ((this.lvl==2)||(this.lvl==3)){
			if (this.lvl==2){this.Hormigas=new Hormiga[lVLcant[this.lvl]];}
			if (this.lvl==3){this.Hormigas=new Hormiga[lVLcant[this.lvl]];}
				}}}
	
	public void setHormigas(Hormiga[] Hormigas1) {
		int Cont=0;
		Hormiga [] NuevoArreglo=new Hormiga[lVLcant[this.lvl]]; //CREA NUEVO ARREGLO DE ACUERDO A LVL
		
		for(int y=0;y<this.Hormigas.length;y++){   //GUARDA LAS QUE ESTABAN EN EL HORMI
			if(this.Hormigas[y]!=null){
				Cont++;
				if(Cont>NuevoArreglo.length){Herramientas.play("SobrePoblacion.wav");System.out.println("SobrePoblacion!");break;}
				NuevoArreglo[Cont]=this.Hormigas[y];}}
			

		for (int x=0;x<Hormigas1.length;x++){ //METE LAS HORMIGAS NUEVAS AL NUEVO ARREGLO
			if(Hormigas1[x]!=null){
				Cont++;
				if(Cont>NuevoArreglo.length){Herramientas.play("SobrePoblacion.wav");System.out.println("SobrePoblacion!");break;}
				Hormigas1[x].Hormiguero=this;
				NuevoArreglo[Cont]=Hormigas1[x];}}
		this.Hormigas=NuevoArreglo;}      //REMPLAZA EL VIEJO ARR POR EL NUEVO
	
	public void pesteTeemo(){
		
		
		Teemo[] timos=Juego.Gaia.getTeemos();
		if(timos==null){return;}
		for(int z=0;z<timos.length;z++){
			if (timos[z]!=null){
			int cat1=(int) Math.abs(timos[z].x-this.x);
			int cat2=(int) Math.abs(timos[z].y-this.y);
		double Distancia=Math.sqrt(((cat1)*(cat1))+((cat2)*(cat2)));
		if (Distancia<=timos[z].diametro*4){this.contagiaPeste(true);this.infeccion=true;return;}}}
		
			if(!this.peste){
				contagiaPeste(false);
				this.infeccion=false;}
			}
	
	public void contagiaPeste(boolean SioNo){
		for(int x=0;x<this.jugador.Hormigueros.length;x++){
			if((this.jugador.Hormigueros[x]!=null)&(this.jugador.Hormigueros[x]!=this)){
				if((this.lvl>this.jugador.Hormigueros[x].lvl)&(SioNo)){
					
					if(!this.infeccion){this.jugador.Hormigueros[x].peste=true;}}
				else if((!SioNo)){
					this.jugador.Hormigueros[x].peste=false;}}}}
	
	public void Conquistar(Jugador Conquistador){
		Hormiguero Backup=null;
		for (int x=0;x<this.jugador.Hormigueros.length;x++){
			if (this.jugador.Hormigueros[x]==this){
				Backup=this.jugador.Hormigueros[x];
				this.jugador.Hormigueros[x]=null;
				Backup.jugador=Conquistador;
				Backup.select=true;
				this.peste=false;
				this.ContHormig=0;
				Backup.LevelDown();
				Conquistador.AgregaNuevoHormi(Backup);
				
				return;}
			}}
	

	}
	









