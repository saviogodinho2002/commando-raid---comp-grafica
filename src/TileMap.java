import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class TileMap {
	private List<Tile> tileList;
	public TileMap() {
		tileList = new ArrayList<>();

		for(int iterator = 0; iterator < 15*2; iterator++) {
			tileList.add(new Tile(32*iterator, Game.HEIGHT-32));
		}

		
	}
	public boolean isFree(int spriteX, int spriteY,Rectangle sprite) {
		// TODO Auto-generated method stub
		for (Tile tile : tileList) {
			if(tile.intersects(new Rectangle(spriteX,spriteY,sprite.width,sprite.height)))
				return false;
		}
		return true;

	}
	public void render(Graphics graphics) {
		for (Tile tile : tileList) {
			tile.render(graphics);
		}
		
	}
	public void tick() {
		
	}
	public List<Tile> getTileList() {
		return tileList;
	}
	public void setTileList(List<Tile> tileList) {
		this.tileList = tileList;
	}
	
}
