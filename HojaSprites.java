import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HojaSprites{
	private final int ancho;
	private final int alto;
	public final int[] pixeles;

	// Rutas de hojas de sprites
	public static HojaSprites BaseGraficos = new HojaSprites("C:\\Users\\hp1\\Desktop\\1.png", 320, 320);
	public static HojaSprites jugador = new HojaSprites("C:\\Users\\hp1\\Desktop\\2.png", 320, 320);
	//--------------------------
	
	public HojaSprites(final String ruta, final int ancho, final int alto){
		this.ancho = ancho;
		this.alto = alto;
		pixeles = new int[ancho * alto];
		BufferedImage imagen;
		try{
			imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public int obtenAncho(){
		return ancho;
	}
}
