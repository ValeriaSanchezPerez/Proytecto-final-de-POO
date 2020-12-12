//este es del video 15 
//package mapa;

public abstract class Mapa{
  private int ancho;
  private int largo; //alto
  private int[] cuadros; 
  
  public Mapa(int ancho, int largo){
    this.ancho=ancho;
    this.largo=largo;  
    cuadros = new int[ancho*largo];
    generarMapa();
  }
  
  public Mapa(String ruta){
    cargarMapa(ruta);
  }
  
  protected void generarMapa(){
  
  }
  
  private void cargarMapa(String ruta){
  
  }
  
  public void actualizar(){
  
  }
  
  public void mostrar(int compensacionX, int compensacionY, Pantalla pantalla){
  
  }
  
}
