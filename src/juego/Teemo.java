package juego;
import java.awt.Image;
import entorno.*;

public class Teemo{
	int diametro,x,y,escala=30;
	Image teemo=Herramientas.cargarImagen("teemo.gif");
	Image teemoQuieto=Herramientas.cargarImagen("Teemoq.png");
	public Teemo(){
		this.diametro=escala+1;
		this.x=Juego.RandomN(diametro).x;
		this.y=Juego.RandomN(diametro).y;
		}
	
	
	public void refrescaTeemo(Entorno entorno){
		if (!Juego.GameOver){
		entorno.dibujarImagen(teemo, this.x, this.y, 0, this.diametro-escala);}
		else if (Juego.GameOver){entorno.dibujarImagen(teemoQuieto, this.x, this.y, 0, this.diametro-escala);}
		
	}
}
