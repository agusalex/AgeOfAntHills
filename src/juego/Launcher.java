package juego;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class Launcher extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Launcher() {
		setForeground(new Color(205, 133, 63));
		setBackground(new Color(210, 105, 30));
		setResizable(false);
		setTitle("Age of AntHills 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				@SuppressWarnings("unused")
				Juego juegui=new Juego();
				/*Juego.RES.x=1000;
				Juego.RES.y=600;*/
				dispose();
				
			}
		});
		btnJugar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				@SuppressWarnings("unused")
				Juego juegui=new Juego();
				//Jugador.Ros=1;
				//Juego.RES.x=1000;
				//Juego.RES.y=600;
				dispose();
			}
		});
		btnJugar.setBounds(154, 84, 118, 43);
		contentPane.add(btnJugar);
		
		JLabel lblResolucion = new JLabel("Resolucion");
		lblResolucion.setForeground(Color.GREEN);
		lblResolucion.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblResolucion.setBounds(167, 153, 106, 27);
		contentPane.add(lblResolucion);
		
		JButton btnBaja = new JButton("Baja");
		btnBaja.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				//Jugador.Ros=1;
				//Juego.RES.x=1000;
				//Juego.RES.y=600;
			}
		});
		btnBaja.setBounds(57, 191, 89, 23);
		contentPane.add(btnBaja);
		
		JButton btnMedia = new JButton("Media");
		btnMedia.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mousePressed(MouseEvent e) {
				//Jugador.Ros=2;
				//Juego.RES.x=1024;
				//Juego.RES.y=768;
			}
		});
		btnMedia.setBounds(174, 191, 89, 23);
		contentPane.add(btnMedia);
		
		JButton btnAlta = new JButton("Alta");
		btnAlta.addMouseListener(new MouseAdapter() {
			@Override

			public void mousePressed(MouseEvent e) {
				//Jugador.Ros=3;
				//Juego.RES.x=1920;
				//Juego.RES.y=1080;
			}
		});
		btnAlta.setBounds(294, 191, 89, 23);
		contentPane.add(btnAlta);
	}
}
