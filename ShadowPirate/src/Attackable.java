/**
 * An interface for classes that can attack others.
 */
public interface Attackable {
    /**
     * Inflicts damage on moving character.
     * @param movingCharacter The character being inflicted damage on.
     */
    void inflictDamage(MovingCharacter movingCharacter);

    /**
     * Prints a log message to console when the character is attacked.
     * @param movingCharacter The character being attacked.
     */
    void printAttackLog(MovingCharacter movingCharacter);
}