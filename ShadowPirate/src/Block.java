/**
 * A class for Block.
 */
public class Block extends StationaryEntity {
    private final static String BLOCK = "res/block.png";

    /**
     * The constructor for Block.
     * @param startX The starting x-coordinate of the block.
     * @param startY The starting y-coordinate of the block.
     */
    public Block(int startX, int startY) {
        super(startX, startY, BLOCK);
    }
}