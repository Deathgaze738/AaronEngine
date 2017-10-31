package aaron.game.pokemonatb.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.manager.ResourceManager;
import aaron.game.pokemonatb.map.AnimatedMapTile;
import aaron.game.pokemonatb.map.StaticMapTile;
import aaron.game.pokemonatb.map.Tile;
import aaron.game.pokemonatb.map.TileType;

public class TileMapFactory {
		//Temp Var
		private static final String basePath = "C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources";
		
		public static TileMapComponent tileMapFromFile(int mapID, int layer, int mapType, int tileSize){
			//load files
			try{
				String mapPath = basePath + "\\maps\\"+mapID+".xml";
				File map = new File(mapPath);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(map);
				
				doc.getDocumentElement().normalize();

				int width = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
				int height = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
				
				//System.out.println("Width element : " + width);
				//System.out.println("Height element : " + height);
				//System.out.println("Total Memory Usage: " + Double.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000.0));

				Tile[][] tileMap = new Tile[width][height];
				//System.out.println("Total Memory Usage: " + Double.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000.0));

				NodeList tileSets = doc.getElementsByTagName("tileset");
				
				Element element = (Element) tileSets.item(0);
				int firstgid = Integer.parseInt(element.getAttribute("firstgid"));
				String tilePath = element.getAttribute("source");
				
				File tilesetFile = new File(tilePath);
				Document doc2 = dBuilder.parse(tilesetFile);
				doc2.getDocumentElement().normalize();
				NodeList imagenode = doc2.getElementsByTagName("image");
				Element imageelement = (Element) imagenode.item(0);
				String imageSource = imageelement.getAttribute("source");
				
				NodeList tileset = doc2.getElementsByTagName("tile");
				
				doc.getDocumentElement().normalize();
				
				//System.out.println("FirstID element : " + firstgid);
				//System.out.println("TilePath element : " + tilePath);
				
				NodeList tiles = doc.getElementsByTagName("tile");
				int temp = 0;
				//System.out.println("Total Memory Usage: " + Double.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000.0));

				for (int x = 0; x < width; x++) {
					for(int y = 0; y < height; y++){
						Element gid = (Element) tiles.item(temp);
						Tile newTile = getTile(Integer.parseInt(gid.getAttribute("gid")), tileset, firstgid, imageSource, tileSize);
						tileMap[x][y] = newTile;
						//System.out.println("Current Tile ["+Integer.toString(x)+"]["+Integer.toString(y)+"] : " + gid.getAttribute("gid"));
						temp++;
						//System.out.println("Total Memory Usage Tiles: " + Double.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000.0));
					}
				}
				//System.out.println("Total Memory Usage: " + Double.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000.0));
				return new TileMapComponent(mapID, layer, mapType, tileSize, tileMap, height, width);
			}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
		private static Tile getTile(int gid, NodeList tileset, int firstgid, String imageSource, int tileSize){
			int encounterRate = 0;
			ResourceManager rsm = ResourceManager.getInstance();
			TileType type = TileType.WALKING;
			for (int temp = 0; temp < tileset.getLength(); temp++) {
				Element tile = (Element) tileset.item(temp);
				if(Integer.parseInt(tile.getAttribute("id")) == gid){
					type = TileType.valueOf(tile.getAttribute("type"));
					NodeList properties = tile.getElementsByTagName("property");
					for (int k = 0; k < properties.getLength(); k++) {
						Element property = (Element) properties.item(k); 
						if(property.hasAttribute("encounter")){
							encounterRate = Integer.parseInt(property.getAttribute("value"));
						}
					}
					NodeList animations = tile.getElementsByTagName("animation");
					if(animations.getLength() > 0){
						NodeList frameNodes = tile.getElementsByTagName("frame");
						List<BufferedImage> frames = new ArrayList<BufferedImage>();
						List<Integer> times = new ArrayList<Integer>();
						for (int k = 0; k < frameNodes.getLength(); k++) {
							Element frame = (Element) frameNodes.item(k); 
							int tileID = Integer.parseInt(frame.getAttribute("tileid"));
							int frameTime = Integer.parseInt(frame.getAttribute("duration"));
							frames.add(rsm.getSprite(imageSource, tileID, firstgid, tileSize));
							times.add(frameTime);
						}
						return new AnimatedMapTile(type, frames, times);
					}
				}
			}
			return new StaticMapTile(type, rsm.getSprite(imageSource, gid, firstgid, tileSize));
		}
}
