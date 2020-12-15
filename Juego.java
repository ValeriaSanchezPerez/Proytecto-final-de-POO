import java.awt.*;
import javax.swing.*;
import graficos.Pantalla;
import control.Teclado;

//faltaban imports asi que aqui los agrego
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimensions;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.DataBufferInt;
import java.swing.ImageIcon;
import java.swing.JFrame;

public class Juego extends Canvas implements Runnable{

  private static final long serialVersionUID = 1L;
  private static final int Ancho = 800;
  private static final int Largo = 600;
  private static volatile boolean enFuncionamiento = false;
  private static final String NOMBRE = "MATH OR DIE";
  private static final String CONTADOR_FPS = "";
  private static final String CONTADOR_APS = "";

  private static int aps = 0;
  private static int fps = 0;

  private static JFrame ventana;
  private static Thread thread;
  private static Teclado teclado;
  private static Pantalla pantalla;
  private static Mapa mapa;
  private static Jugador jugador;
  
  private static BufferedImage = new BufferedImage(Ancho, Largo, BufferedImage.TYPE_INT_RGB);
  private static int[] pixeles = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();
  
  private static final ImageIcon icono = new ImageIcon(Juego.class.getResource(//ruta de foto icono//)); 
  
  
  private Juego(){
    setPreferredSize(new Dimension(Ancho, Largo));
    pantalla = new Pantalla(Ancho, Largo);
   // mapa = new MapaGenerado(128, 128);

    teclado = new Teclado();
    addKeyListener(teclado);
    
    mapa=new MapaCargado(""); //ruta de foto del mapa pixeleado video 31
    jugador = new Jugador(teclado, Sprite.MAGO_FRENTE_1,225,225);
    
    ventana = new JFrame(NOMBRE);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventana.setResizable(false);
    ventana.setIconImage(icono.getImage());
    ventana.setLayout(new BorderLayout());
    ventana.add(this.BorderLayout.CENTER);
    ventana.set(Undecorated(true));
    ventana.pack();
    ventana.setLocationRelativeTo(null);
    ventana.setVisible(true);
  }
  public static void main(String[] args){
    Juego juego = new Juego();
    juego.iniciar();
  }
  private synchronized void iniciar(){
    enFuncionamiento = true;
    thread = new Thread(this, "Graficos");
    thread.start();
  }
  private synchronized void detener(){
    enFuncionamiento = false;
    try{
      thread.join();
    }catch(InterruptedException e){
      e.printStackTrace();
    }
  }
  private void actualizar(){
    teclado.actualizar();
    
    jugador.actualizar();
    
    if(teclado.salir){
      System.exit(0);
    }
    aps++;
  }
  private void mostrar(){
    BufferStrategy estrategia = getBufferStrategy();
    if(estrategia == null){
      createBufferStrategy(3);
      return;
    }
    ///pantalla.limpiar();
    mapa.mostrar(jugador.getPosicionX() - pantalla.getAncho()/2 + jugador.getSprite.getLado()/2, jugador.getPosicionY() - pantalla.getLargo()/2 + jugador.getSprite.getLado()/2, pantalla);
    jugador.mostrar(pantalla);
    
    System.arraycopy(pantalla.pixeles, 0, pixeles, 0, pixeles.lenght);
    Graphics g = estrategia.getDrawGrafics();
    
    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
    g.setColor(Color.white); 
    g.drawString(CONTADOR_APS, 10, 20);
    g.drawString(CONTADOR_FPS, 10, 35);
    g.drawString("X: " + jugador.getPosicionX(), 10, 50);
    g.drawString("Y: " + jugador.getPosicionY(), 10, 65);
    g.dispose();
    
    estrategia.show();
    fps++;
  }
  public void run(){
    final int NSxS = 1000000000;
    final byte APS = 60;
    final double NSxAPS = NSxS/APS;
    long referenciaActualizacion = System.nanoTime();
    long referenciaContador = System.nanoTime();
    double tiempoTranscurrido;
    double delta = 0;
    requestFocus();
    while(enFuncionamiento){
      final long inicioBucle = System.nanoTime();
      tiempoTranscurrido = inicioBucle - referenciaActualizacion;
      referenciaActualizacion = inicioBucle;
      delta += tiempoTranscurrido/NSxAPS;
      while(delta >= 1){
        actualizar();
        delta--;
      }
      mostrar();
      if(System.nanoTime() - referenciaContador > NSxS){
        CONTADOR_APS = "APS:"+ aps;
        CONTADOR_FPS = "FPS:"+ fps;
        ventana.setTitle(NOMBRE + " || APS: " + aps + " || FPS: " + fps);
        aps = 0;
        fps = 0;
        referenciaContador = System.nanoTime();
      }
    }
  }
}
